package cn.techaction.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionAddressDao;
import cn.techaction.dao.ActionCartDao;
import cn.techaction.dao.ActionOrderDao;
import cn.techaction.dao.ActionOrderItemDao;
import cn.techaction.dao.ActionProductDao;
import cn.techaction.pojo.ActionAddress;
import cn.techaction.pojo.ActionCart;
import cn.techaction.pojo.ActionOrder;
import cn.techaction.pojo.ActionOrderItem;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.service.ActionOrderService;
import cn.techaction.utils.CalcUtil;
import cn.techaction.utils.ConstUtil;
import cn.techaction.utils.DateUtils;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionAddressVo;
import cn.techaction.vo.ActionOrderItemVo;
import cn.techaction.vo.ActionOrderVo;

@Service
public class ActionOrderServiceImpl implements ActionOrderService {

	@Autowired
	private ActionCartDao aCartDao;

	@Autowired
	private ActionProductDao aProductDao;
	@Autowired
	private ActionOrderDao aOrderDao;
	@Autowired
	private ActionOrderItemDao aOrderItemDao;
	@Autowired
	private ActionAddressDao aAddressDao;

	@Override
	public SverResponse<ActionOrderVo> generateOrder(Integer uid, Integer addrId) {
		// 提取购物车中商品信息
		List<ActionCart> carts = aCartDao.findCartByUser(uid);
		// 计算购物车中每件商品的总价格并生成订单项
		SverResponse resp = this.cart2OrderItem(uid, carts);
		if (!resp.isSuccess()) {
			return resp;
		}
		// 取出订单项并计算订单总价格
		List<ActionOrderItem> orderItems = (List<ActionOrderItem>) resp.getData();
		BigDecimal totalPrice = this.calcOrderTotalPrice(orderItems);
		// 生成订单,向订单表中插入数据
		ActionOrder order = saveOrder(uid, addrId, totalPrice);
		if (order == null) {
			return SverResponse.createByErrorMessage("订单生产错误，请检查后重新提交！");
		}
		if (CollectionUtils.isEmpty(orderItems)) {
			return SverResponse.createByErrorMessage("订单项为空，请选择要购买的商品！");
		}
		// 批量插入订单项
		for (ActionOrderItem orderItem : orderItems) {
			// 为订单项设置订单主键
			orderItem.setOrderNo(order.getOrderNo());
		}
		aOrderItemDao.batchInsert(orderItems);

		// 减少商品表中的库存
		for (ActionOrderItem orderItem : orderItems) {
			ActionProduct product = aProductDao.findProductById(orderItem.getGoodsId());
			// 减少库存
			product.setStock(product.getStock() - orderItem.getQuantity());
			product.setUpdated(new Date());
			// 更新库存
			aProductDao.updateProduct(product);
		}
		// 清空购物车
		aCartDao.deleteCartByUserId(uid);
		// 将订单信息返回给前端
		ActionOrderVo orderVo = createOrderVo(order, orderItems);
		return SverResponse.createRespBySuccess(orderVo);
	}

	/**
	 * 保存订单
	 * 
	 * @param uid
	 * @param addrId
	 * @param totalPrice
	 * @return
	 * @throws Exception
	 */
	private ActionOrder saveOrder(Integer uid, Integer addrId, BigDecimal totalPrice) {
		ActionOrder order = new ActionOrder();
		// 生成订单号
		long currentTime = System.currentTimeMillis();
		Long orderNo = currentTime + new Random().nextInt(100);
		order.setOrderNo(orderNo);
		order.setStatus(ConstUtil.OrderStatus.ORDER_NO_PAY); // 默认未付款
		order.setFreight(0); // 默认邮费为0
		order.setType(ConstUtil.PaymentType.PAY_ON_LINE); // 在线支付
		order.setAmount(totalPrice); // 订单总额
		order.setAddrId(addrId); // 邮寄地址
		order.setUid(uid);

		order.setUpdated(new Date());
		order.setCreated(new Date());
		// 插入订单
		int rs = aOrderDao.insertOrder(order);
		if (rs > 0) {
			return order;
		}
		return null;
	}

	/**
	 * 将购物车中商品封装为订单项
	 * 
	 * @param uid
	 * @param carts
	 * @return
	 */
	private SverResponse cart2OrderItem(Integer uid, List<ActionCart> carts) {
		List<ActionOrderItem> items = Lists.newArrayList();
		if (CollectionUtils.isEmpty(carts)) {
			return SverResponse.createByErrorMessage("购物车为空，请选择要购买的商品！");
		}

		for (ActionCart cart : carts) {
			// 查看购物车中商品的状态
			ActionProduct product = aProductDao.findProductById(cart.getProductId());
			if (ConstUtil.ProductStatus.STATUS_ON_SALE != product.getStatus()) {
				// 如果商品不是上架-在售,则返回并提示
				return SverResponse.createByErrorMessage("商品" + product.getName() + "已经下架，不能在线购买！");
			}
			// 查看库存
			if (cart.getQuantity() > product.getStock()) {
				return SverResponse.createByErrorMessage("商品" + product.getName() + "库存不足！");
			}
			// 封装订单项
			ActionOrderItem orderItem = new ActionOrderItem();
			orderItem.setUid(uid);
			orderItem.setGoodsName(product.getName());
			orderItem.setGoodsId(product.getId());
			orderItem.setIconUrl(product.getIconUrl());
			orderItem.setPrice(product.getPrice());
			orderItem.setQuantity(cart.getQuantity());
			orderItem.setTotalPrice(
					CalcUtil.mul(orderItem.getPrice().doubleValue(), orderItem.getQuantity().doubleValue()));
			orderItem.setCreated(new Date());
			orderItem.setUpdated(new Date());
			items.add(orderItem);
		}
		return SverResponse.createRespBySuccess(items);
	}

	/**
	 * 计算订单总价格
	 * 
	 * @param orderItems
	 * @return
	 */
	private BigDecimal calcOrderTotalPrice(List<ActionOrderItem> orderItems) {
		BigDecimal totalPrice = new BigDecimal("0");
		for (ActionOrderItem item : orderItems) {
			totalPrice = CalcUtil.add(totalPrice.doubleValue(), item.getTotalPrice().doubleValue());
		}
		return totalPrice;
	}

	// 封装订单Vo
	private ActionOrderVo createOrderVo(ActionOrder order, List<ActionOrderItem> orderItems) {
		ActionOrderVo orderVo = new ActionOrderVo();
		setNormalProperty(order, orderVo);
		setAddressProperty(order, orderVo, true);
		// 设置订单项
		setOrderItemProperty(orderItems, orderVo);
		return orderVo;
	}

	// 封装订单VO
	private ActionOrderVo createOrderVo(ActionOrder order, boolean hasAddress) {
		ActionOrderVo orderVo = new ActionOrderVo();
		setNormalProperty(order, orderVo);
		setAddressProperty(order, orderVo, hasAddress);
		// 设置订单项
		List<ActionOrderItem> orderItems = aOrderItemDao.getItemsByOrderNo(order.getOrderNo());
		setOrderItemProperty(orderItems, orderVo);
		return orderVo;
	}

	/**
	 * 封装订单项属性
	 * 
	 * @param orderItems
	 * @param orderVo
	 */
	private void setOrderItemProperty(List<ActionOrderItem> orderItems, ActionOrderVo orderVo) {
		List<ActionOrderItemVo> items = Lists.newArrayList();
		for (ActionOrderItem orderItem : orderItems) {
			items.add(createOrderItemVo(orderItem));
		}
		orderVo.setOrderItems(items);
	}

	/**
	 * 封装地址属性
	 * 
	 * @param order
	 * @param orderVo
	 */
	private void setAddressProperty(ActionOrder order, ActionOrderVo orderVo, boolean flag) {
		ActionAddress address = aAddressDao.findAddrsById(order.getAddrId());
		if (address != null) {
			orderVo.setDeliveryName(address.getName());
			if (flag) {
				orderVo.setAddress(createAddressVo(address));
			} else {
				orderVo.setAddress(null);
			}
		}
	}

	/**
	 * 封装订单Vo普通属性
	 * 
	 * @param order
	 * @param orderVo
	 */
	private void setNormalProperty(ActionOrder order, ActionOrderVo orderVo) {
		orderVo.setOrderNo(order.getOrderNo());
		orderVo.setAmount(order.getAmount());
		orderVo.setType(order.getType());
		orderVo.setTypeDesc(ConstUtil.PaymentType.getTypeDesc(order.getType()));
		orderVo.setFreight(order.getFreight());
		orderVo.setStatus(order.getStatus());
		orderVo.setStatusDesc(ConstUtil.OrderStatus.getStatusDesc(order.getStatus()));
		orderVo.setAddrId(order.getAddrId());
		// 时间
		orderVo.setPaymentTime(DateUtils.date2Str(order.getPaymentTime()));
		orderVo.setDeliveryTime(DateUtils.date2Str(order.getDeliveryTime()));
		;
		orderVo.setFinishTime(DateUtils.date2Str(order.getFinishTime()));
		orderVo.setCloseTime(DateUtils.date2Str(order.getCloseTime()));
		orderVo.setCreated(DateUtils.date2Str(order.getCreated()));
	}

	// 封装地址Vo
	private ActionAddressVo createAddressVo(ActionAddress address) {
		ActionAddressVo addressVo = new ActionAddressVo();
		addressVo.setName(address.getName());
		addressVo.setMobile(address.getMobile());
		addressVo.setPhone(address.getPhone());
		addressVo.setProvince(address.getProvince());
		addressVo.setCity(address.getCity());
		addressVo.setDistrict(address.getDistrict());
		addressVo.setAddr(address.getAddr());
		addressVo.setZip(address.getZip());
		return addressVo;
	}

	// 封装订单项vo
	private ActionOrderItemVo createOrderItemVo(ActionOrderItem orderItem) {
		ActionOrderItemVo itemVo = new ActionOrderItemVo();
		itemVo.setOrderNo(orderItem.getOrderNo());
		itemVo.setGoodsId(orderItem.getGoodsId());
		itemVo.setGoodsName(orderItem.getGoodsName());
		itemVo.setIconUrl(orderItem.getIconUrl());
		itemVo.setCurPrice(orderItem.getPrice());
		itemVo.setTotalPrice(orderItem.getTotalPrice());
		itemVo.setQuantity(orderItem.getQuantity());
		return itemVo;
	}

	/******************************* 订单列表 ****************************************/
	@Override
	public SverResponse<PageBean<ActionOrderVo>> findOrders(Integer uid, int pageNum, int pageSize) {
		if (uid == null) {
			return SverResponse.createByErrorMessage("参数错误！");
		}
		// 查找符合条件的总记录数
		int totalRecord = aOrderDao.getTotalRecords(uid);
		// 创建分页封装对象
		PageBean<ActionOrderVo> pageBean = new PageBean<>(pageNum, pageSize, totalRecord);
		// 读取数据
		List<ActionOrder> orders = aOrderDao.findOrders(uid, pageBean.getStartIndex(), pageSize);
		// 封装VO
		List<ActionOrderVo> voList = Lists.newArrayList();
		for (ActionOrder order : orders) {
			voList.add(createOrderVo(order, false));
		}
		pageBean.setData(voList);
		return SverResponse.createRespBySuccess(pageBean);
	}

	/**************************** 取消订单 ********************************************/

	@Override
	public SverResponse<String> cancelOrder(Integer uid, Long orderNo) {
		// 查询订单
		ActionOrder order = aOrderDao.findOrderByUserAndOrderNo(uid, orderNo);
		if (order == null) {
			return SverResponse.createByErrorMessage("该用户订单不存在，或已删除！");
		}
		if (order.getStatus() == ConstUtil.OrderStatus.ORDER_PAID) {
			return SverResponse.createByErrorMessage("该订单已经付款，无法取消！");
		}
		ActionOrder updateOrder = new ActionOrder();
		updateOrder.setId(order.getId());
		updateOrder.setStatus(ConstUtil.OrderStatus.ORDER_CANCELED);
		updateOrder.setUpdated(new Date());
		int row = aOrderDao.updateOrder(updateOrder);
		if (row > 0) {
			return SverResponse.createRespBySuccessMessage("该订单已经取消！");
		}
		return SverResponse.createByErrorMessage("该订单已经取消失败！");
	}

	/*******************************
	 * 获取订单详情
	 *********************************************/
	@Override
	public SverResponse<ActionOrderVo> findOrderDetail(Integer uid, Long orderNo) {
		if (uid == null || orderNo == null) {
			return SverResponse.createByErrorMessage("参数错误！");
		}
		ActionOrder order = aOrderDao.findOrderByUserAndOrderNo(uid, orderNo);
		if (order == null) {
			return SverResponse.createByErrorMessage("该用户订单不存在，或已删除！");
		}
		ActionOrderVo orderVo = createOrderVo(order, true);
		return SverResponse.createRespBySuccess(orderVo);
	}

	/*********************************************************************************/
	/*******************************
	 * 后台管理方法
	 **************************************************/

	@Override
	public SverResponse<PageBean<ActionOrderVo>> mgrOrders(int pageNum, int pageSize) {
		// 查找符合条件的总记录数
		int totalRecord = aOrderDao.mgrTotalRecords();
		// 创建分页封装对象
		PageBean<ActionOrderVo> pageBean = new PageBean<>(pageNum, pageSize, totalRecord);
		// 读取数据
		List<ActionOrder> orders = aOrderDao.mgrOrders(pageBean.getStartIndex(), pageSize);
		// 封装VO
		List<ActionOrderVo> voList = Lists.newArrayList();
		for (ActionOrder order : orders) {
			voList.add(createOrderVo(order, false));
		}
		pageBean.setData(voList);
		return SverResponse.createRespBySuccess(pageBean);
	}

	@Override
	public SverResponse<List<ActionOrderVo>> findOrdersForNoPages(Long orderNo) {
		// 读取数据
		List<ActionOrder> orders = aOrderDao.searchOrders(orderNo);
		// 封装VO
		List<ActionOrderVo> voList = Lists.newArrayList();
		for (ActionOrder order : orders) {
			voList.add(createOrderVo(order, false));
		}
		return SverResponse.createRespBySuccess(voList);
	}

	@Override
	public SverResponse<ActionOrderVo> mgrDetail(Long orderNo) {
		if (orderNo == null) {
			return SverResponse.createByErrorMessage("参数错误！");
		}
		ActionOrder order = aOrderDao.findOrderDetailByNo(orderNo);
		if (order == null) {
			return SverResponse.createByErrorMessage("该用户订单不存在，或已删除！");
		}
		ActionOrderVo orderVo = createOrderVo(order, true);
		return SverResponse.createRespBySuccess(orderVo);
	}

	@Override
	public SverResponse<PageBean<ActionOrderVo>> searchOrderByNo(Long orderNo, int pageNum, int pageSize) {
		if (orderNo == null) {
			return SverResponse.createByErrorMessage("参数错误！");
		}
		ActionOrder order = aOrderDao.findOrderDetailByNo(orderNo);
		if (order == null) {
			return SverResponse.createByErrorMessage("该用户订单不存在，或已删除！");
		}
		// 创建分页封装对象
		PageBean<ActionOrderVo> pageBean = new PageBean<>(pageNum, pageSize, 1);
		ActionOrderVo orderVo = createOrderVo(order, true);
		List<ActionOrderVo> orders = Lists.newArrayList();
		orders.add(orderVo);
		pageBean.setData(orders);
		return SverResponse.createRespBySuccess(pageBean);
	}

	
	@Override
	public SverResponse<String> deliverGoods(Long orderNo) {
		if (orderNo == null) {
			return SverResponse.createByErrorMessage("参数错误！");
		}
		ActionOrder order = aOrderDao.findOrderDetailByNo(orderNo);

		if (order != null) {
			if (order.getStatus() == ConstUtil.OrderStatus.ORDER_PAID) {
				order.setStatus(ConstUtil.OrderStatus.ORDER_SHIPPED);
				order.setDeliveryTime(new Date());
				order.setUpdated(new Date());
				aOrderDao.updateOrder(order);
				return SverResponse.createRespBySuccessMessage("该订单发货成功！");
			}else {
				return SverResponse.createRespBySuccessMessage("该订单尚未付款，不能发货！");
			}
		}
		return SverResponse.createByErrorMessage("该订单不存在");
	}
}
