package cn.techaction.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionCartDao;
import cn.techaction.dao.ActionProductDao;
import cn.techaction.pojo.ActionCart;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.service.ActionCartService;
import cn.techaction.utils.CalcUtil;
import cn.techaction.vo.ActionCartListVo;
import cn.techaction.vo.ActionCartVo;

@Service
public class ActionCartServiceImpl implements ActionCartService {
	
	@Autowired
	private ActionCartDao aCartDao;
	
	@Autowired
	private ActionProductDao aProductDao;
	
	
	@Override
	public SverResponse<String> saveOrUpdate(Integer userId,Integer productId,Integer count) {
		if(userId==null || productId==null || count==null) {
			return SverResponse.createByErrorMessage("参数错误！");
		}
		ActionCart actionCart = aCartDao.findCartByUserAndProductId(userId, productId);
		if(actionCart==null) {
			ActionCart cart = new ActionCart();
			cart.setUserId(userId);
			cart.setProductId(productId);
			cart.setQuantity(count);
			cart.setCreated(new Date());
			cart.setUpdated(new Date());
			aCartDao.insertCart(cart);
		}else {
			int cartCount= actionCart.getQuantity()+count;
			actionCart.setQuantity(cartCount);
			aCartDao.updateCartById(actionCart);
		}
		return SverResponse.createRespBySuccessMessage("商品已成功加入购物车！");
	}

	@Override
	public SverResponse<ActionCartVo> findAllCarts(Integer userId) {
		if(userId==null) {
			return SverResponse.createByErrorMessage("参数错误！");
		}
		List<ActionCart> list = aCartDao.findCartByUser(userId);
		ActionCartVo cartVo = createCartVo(list);
		return SverResponse.createRespBySuccess(cartVo);
	}
	/**
	 * 封装购物车VO对象
	 * @param carts
	 * @return
	 */
	private ActionCartVo createCartVo(List<ActionCart> carts) {
		ActionCartVo cartVo = new ActionCartVo();
		List<ActionCartListVo> list = Lists.newArrayList();
		BigDecimal cartTotalPrice = new BigDecimal("0");
		if(CollectionUtils.isNotEmpty(carts)) {
			for(ActionCart cart:carts) {
				ActionCartListVo listVo = new ActionCartListVo();
				listVo.setId(cart.getId());
				listVo.setUserId(cart.getUserId());;
				listVo.setProductId(cart.getProductId());
				ActionProduct product =aProductDao.findProductById(listVo.getProductId());
				if(product!=null) {
					listVo.setName(product.getName());
					listVo.setStatus(product.getStatus());
					listVo.setPrice(product.getPrice());
					listVo.setStock(product.getStock());
					listVo.setIconUrl(product.getIconUrl());
					int buyCount=0;
					if(product.getStock()>=cart.getQuantity()) {
						buyCount = cart.getQuantity();
					}else {
						buyCount = product.getStock();
						ActionCart updateCart = new ActionCart();
						updateCart.setId(cart.getId());
						updateCart.setQuantity(buyCount);
						aCartDao.updateCartById(updateCart);
					}
					listVo.setQuantity(buyCount);
					BigDecimal totalPrice = CalcUtil.mul(listVo.getPrice().doubleValue(), listVo.getQuantity().doubleValue());
					listVo.setTotalPrice(totalPrice);
				}
				cartTotalPrice = CalcUtil.add(cartTotalPrice.doubleValue(), listVo.getTotalPrice().doubleValue());
				list.add(listVo);
			}
		}
		cartVo.setLists(list);
		cartVo.setTotalPrice(cartTotalPrice);
		return cartVo;
	}

	
	
	/**
	 * 从购物车中删除选中的商品
	 */
	@Override
	public SverResponse<ActionCartVo> deleteCart(Integer userId,Integer productId) {
		if(userId ==null || productId==null) {
			return SverResponse.createByErrorMessage("参数错误！");
		}
		int rs = aCartDao.deleteCart(userId,productId);
		if(rs>0) {
			return this.findAllCarts(userId);
		}
		return SverResponse.createByErrorMessage("商品删除失败！");
	}

	@Override
	public SverResponse<String> clearCart(Integer userId) {
		if(userId ==null) {
			return SverResponse.createByErrorMessage("参数错误！");
		}
		int rs = aCartDao.deleteCartByUserId(userId);
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("成功清空购物车！");
		}
		return SverResponse.createByErrorMessage("清空购物车失败！");
	}

	@Override
	public SverResponse<ActionCartVo> updateCart(Integer userId,Integer productId, Integer count) {
		if(userId==null || productId==null || count==null) {
			return SverResponse.createByErrorMessage("参数错误！");
		}
		ActionCart actionCart = new ActionCart();
		actionCart.setUserId(userId);
		actionCart.setProductId(productId);
		actionCart.setQuantity(count);
		aCartDao.updateCartByUserIdAndProductId(actionCart);
		return findAllCarts(userId);
	}

	@Override
	public SverResponse<Integer> getCartCount(Integer userId) {
		if(userId==null) {
			return SverResponse.createByErrorMessage("参数错误！");
		}
		int count = aCartDao.getCartCountByUserId(userId);
		return SverResponse.createRespBySuccess(Integer.valueOf(count));
	}	
	
	
}
