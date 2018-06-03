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
		// ��ȡ���ﳵ����Ʒ��Ϣ
		List<ActionCart> carts = aCartDao.findCartByUser(uid);
		// ���㹺�ﳵ��ÿ����Ʒ���ܼ۸����ɶ�����
		SverResponse resp = this.cart2OrderItem(uid, carts);
		if (!resp.isSuccess()) {
			return resp;
		}
		// ȡ����������㶩���ܼ۸�
		List<ActionOrderItem> orderItems = (List<ActionOrderItem>) resp.getData();
		BigDecimal totalPrice = this.calcOrderTotalPrice(orderItems);
		// ���ɶ���,�򶩵����в�������
		ActionOrder order = saveOrder(uid, addrId, totalPrice);
		if (order == null) {
			return SverResponse.createByErrorMessage("����������������������ύ��");
		}
		if (CollectionUtils.isEmpty(orderItems)) {
			return SverResponse.createByErrorMessage("������Ϊ�գ���ѡ��Ҫ�������Ʒ��");
		}
		// �������붩����
		for (ActionOrderItem orderItem : orderItems) {
			// Ϊ���������ö�������
			orderItem.setOrderNo(order.getOrderNo());
		}
		aOrderItemDao.batchInsert(orderItems);

		// ������Ʒ���еĿ��
		for (ActionOrderItem orderItem : orderItems) {
			ActionProduct product = aProductDao.findProductById(orderItem.getGoodsId());
			// ���ٿ��
			product.setStock(product.getStock() - orderItem.getQuantity());
			product.setUpdated(new Date());
			// ���¿��
			aProductDao.updateProduct(product);
		}
		// ��չ��ﳵ
		aCartDao.deleteCartByUserId(uid);
		// ��������Ϣ���ظ�ǰ��
		ActionOrderVo orderVo = createOrderVo(order, orderItems);
		return SverResponse.createRespBySuccess(orderVo);
	}

	/**
	 * ���涩��
	 * 
	 * @param uid
	 * @param addrId
	 * @param totalPrice
	 * @return
	 * @throws Exception
	 */
	private ActionOrder saveOrder(Integer uid, Integer addrId, BigDecimal totalPrice) {
		ActionOrder order = new ActionOrder();
		// ���ɶ�����
		long currentTime = System.currentTimeMillis();
		Long orderNo = currentTime + new Random().nextInt(100);
		order.setOrderNo(orderNo);
		order.setStatus(ConstUtil.OrderStatus.ORDER_NO_PAY); // Ĭ��δ����
		order.setFreight(0); // Ĭ���ʷ�Ϊ0
		order.setType(ConstUtil.PaymentType.PAY_ON_LINE); // ����֧��
		order.setAmount(totalPrice); // �����ܶ�
		order.setAddrId(addrId); // �ʼĵ�ַ
		order.setUid(uid);

		order.setUpdated(new Date());
		order.setCreated(new Date());
		// ���붩��
		int rs = aOrderDao.insertOrder(order);
		if (rs > 0) {
			return order;
		}
		return null;
	}

	/**
	 * �����ﳵ����Ʒ��װΪ������
	 * 
	 * @param uid
	 * @param carts
	 * @return
	 */
	private SverResponse cart2OrderItem(Integer uid, List<ActionCart> carts) {
		List<ActionOrderItem> items = Lists.newArrayList();
		if (CollectionUtils.isEmpty(carts)) {
			return SverResponse.createByErrorMessage("���ﳵΪ�գ���ѡ��Ҫ�������Ʒ��");
		}

		for (ActionCart cart : carts) {
			// �鿴���ﳵ����Ʒ��״̬
			ActionProduct product = aProductDao.findProductById(cart.getProductId());
			if (ConstUtil.ProductStatus.STATUS_ON_SALE != product.getStatus()) {
				// �����Ʒ�����ϼ�-����,�򷵻ز���ʾ
				return SverResponse.createByErrorMessage("��Ʒ" + product.getName() + "�Ѿ��¼ܣ��������߹���");
			}
			// �鿴���
			if (cart.getQuantity() > product.getStock()) {
				return SverResponse.createByErrorMessage("��Ʒ" + product.getName() + "��治�㣡");
			}
			// ��װ������
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
	 * ���㶩���ܼ۸�
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

	// ��װ����Vo
	private ActionOrderVo createOrderVo(ActionOrder order, List<ActionOrderItem> orderItems) {
		ActionOrderVo orderVo = new ActionOrderVo();
		setNormalProperty(order, orderVo);
		setAddressProperty(order, orderVo, true);
		// ���ö�����
		setOrderItemProperty(orderItems, orderVo);
		return orderVo;
	}

	// ��װ����VO
	private ActionOrderVo createOrderVo(ActionOrder order, boolean hasAddress) {
		ActionOrderVo orderVo = new ActionOrderVo();
		setNormalProperty(order, orderVo);
		setAddressProperty(order, orderVo, hasAddress);
		// ���ö�����
		List<ActionOrderItem> orderItems = aOrderItemDao.getItemsByOrderNo(order.getOrderNo());
		setOrderItemProperty(orderItems, orderVo);
		return orderVo;
	}

	/**
	 * ��װ����������
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
	 * ��װ��ַ����
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
	 * ��װ����Vo��ͨ����
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
		// ʱ��
		orderVo.setPaymentTime(DateUtils.date2Str(order.getPaymentTime()));
		orderVo.setDeliveryTime(DateUtils.date2Str(order.getDeliveryTime()));
		;
		orderVo.setFinishTime(DateUtils.date2Str(order.getFinishTime()));
		orderVo.setCloseTime(DateUtils.date2Str(order.getCloseTime()));
		orderVo.setCreated(DateUtils.date2Str(order.getCreated()));
	}

	// ��װ��ַVo
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

	// ��װ������vo
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

	/******************************* �����б� ****************************************/
	@Override
	public SverResponse<PageBean<ActionOrderVo>> findOrders(Integer uid, int pageNum, int pageSize) {
		if (uid == null) {
			return SverResponse.createByErrorMessage("��������");
		}
		// ���ҷ����������ܼ�¼��
		int totalRecord = aOrderDao.getTotalRecords(uid);
		// ������ҳ��װ����
		PageBean<ActionOrderVo> pageBean = new PageBean<>(pageNum, pageSize, totalRecord);
		// ��ȡ����
		List<ActionOrder> orders = aOrderDao.findOrders(uid, pageBean.getStartIndex(), pageSize);
		// ��װVO
		List<ActionOrderVo> voList = Lists.newArrayList();
		for (ActionOrder order : orders) {
			voList.add(createOrderVo(order, false));
		}
		pageBean.setData(voList);
		return SverResponse.createRespBySuccess(pageBean);
	}

	/**************************** ȡ������ ********************************************/

	@Override
	public SverResponse<String> cancelOrder(Integer uid, Long orderNo) {
		// ��ѯ����
		ActionOrder order = aOrderDao.findOrderByUserAndOrderNo(uid, orderNo);
		if (order == null) {
			return SverResponse.createByErrorMessage("���û����������ڣ�����ɾ����");
		}
		if (order.getStatus() == ConstUtil.OrderStatus.ORDER_PAID) {
			return SverResponse.createByErrorMessage("�ö����Ѿ�����޷�ȡ����");
		}
		ActionOrder updateOrder = new ActionOrder();
		updateOrder.setId(order.getId());
		updateOrder.setStatus(ConstUtil.OrderStatus.ORDER_CANCELED);
		updateOrder.setUpdated(new Date());
		int row = aOrderDao.updateOrder(updateOrder);
		if (row > 0) {
			return SverResponse.createRespBySuccessMessage("�ö����Ѿ�ȡ����");
		}
		return SverResponse.createByErrorMessage("�ö����Ѿ�ȡ��ʧ�ܣ�");
	}

	/*******************************
	 * ��ȡ��������
	 *********************************************/
	@Override
	public SverResponse<ActionOrderVo> findOrderDetail(Integer uid, Long orderNo) {
		if (uid == null || orderNo == null) {
			return SverResponse.createByErrorMessage("��������");
		}
		ActionOrder order = aOrderDao.findOrderByUserAndOrderNo(uid, orderNo);
		if (order == null) {
			return SverResponse.createByErrorMessage("���û����������ڣ�����ɾ����");
		}
		ActionOrderVo orderVo = createOrderVo(order, true);
		return SverResponse.createRespBySuccess(orderVo);
	}

	/*********************************************************************************/
	/*******************************
	 * ��̨������
	 **************************************************/

	@Override
	public SverResponse<PageBean<ActionOrderVo>> mgrOrders(int pageNum, int pageSize) {
		// ���ҷ����������ܼ�¼��
		int totalRecord = aOrderDao.mgrTotalRecords();
		// ������ҳ��װ����
		PageBean<ActionOrderVo> pageBean = new PageBean<>(pageNum, pageSize, totalRecord);
		// ��ȡ����
		List<ActionOrder> orders = aOrderDao.mgrOrders(pageBean.getStartIndex(), pageSize);
		// ��װVO
		List<ActionOrderVo> voList = Lists.newArrayList();
		for (ActionOrder order : orders) {
			voList.add(createOrderVo(order, false));
		}
		pageBean.setData(voList);
		return SverResponse.createRespBySuccess(pageBean);
	}

	@Override
	public SverResponse<List<ActionOrderVo>> findOrdersForNoPages(Long orderNo) {
		// ��ȡ����
		List<ActionOrder> orders = aOrderDao.searchOrders(orderNo);
		// ��װVO
		List<ActionOrderVo> voList = Lists.newArrayList();
		for (ActionOrder order : orders) {
			voList.add(createOrderVo(order, false));
		}
		return SverResponse.createRespBySuccess(voList);
	}

	@Override
	public SverResponse<ActionOrderVo> mgrDetail(Long orderNo) {
		if (orderNo == null) {
			return SverResponse.createByErrorMessage("��������");
		}
		ActionOrder order = aOrderDao.findOrderDetailByNo(orderNo);
		if (order == null) {
			return SverResponse.createByErrorMessage("���û����������ڣ�����ɾ����");
		}
		ActionOrderVo orderVo = createOrderVo(order, true);
		return SverResponse.createRespBySuccess(orderVo);
	}

	@Override
	public SverResponse<PageBean<ActionOrderVo>> searchOrderByNo(Long orderNo, int pageNum, int pageSize) {
		if (orderNo == null) {
			return SverResponse.createByErrorMessage("��������");
		}
		ActionOrder order = aOrderDao.findOrderDetailByNo(orderNo);
		if (order == null) {
			return SverResponse.createByErrorMessage("���û����������ڣ�����ɾ����");
		}
		// ������ҳ��װ����
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
			return SverResponse.createByErrorMessage("��������");
		}
		ActionOrder order = aOrderDao.findOrderDetailByNo(orderNo);

		if (order != null) {
			if (order.getStatus() == ConstUtil.OrderStatus.ORDER_PAID) {
				order.setStatus(ConstUtil.OrderStatus.ORDER_SHIPPED);
				order.setDeliveryTime(new Date());
				order.setUpdated(new Date());
				aOrderDao.updateOrder(order);
				return SverResponse.createRespBySuccessMessage("�ö��������ɹ���");
			}else {
				return SverResponse.createRespBySuccessMessage("�ö�����δ������ܷ�����");
			}
		}
		return SverResponse.createByErrorMessage("�ö���������");
	}
}
