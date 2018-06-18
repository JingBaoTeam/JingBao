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
			return SverResponse.createByErrorMessage("²ÎÊý´íÎó£¡");
		}
		//²é¿´ÓÃ»§µÄ¹ºÎï³µÖÐÊÇ·ñ´æÔÚ¸ÃÉÌÆ·
		ActionCart actionCart = aCartDao.findCartByUserAndProductId(userId, productId);
		if(actionCart==null) {
<<<<<<< HEAD
			//²»´æÔÚÔò£¬ÐÂÔö
=======
			//ä¸å­˜åœ¨åˆ™ï¼Œæ–°å¢ž

>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77
			ActionCart cart = new ActionCart();
			cart.setUserId(userId);
			cart.setProductId(productId);
			cart.setQuantity(count);
			cart.setCreated(new Date());
			cart.setUpdated(new Date());
			aCartDao.insertCart(cart);
		}else {
			//Èç¹ûÒÑ¾­´æÔÚ£¬ÔòÊýÁ¿Ôö¼Ó
			int cartCount= actionCart.getQuantity()+count;
			actionCart.setQuantity(cartCount);
			aCartDao.updateCartById(actionCart);
		}
		return SverResponse.createRespBySuccessMessage("ÉÌÆ·ÒÑ³É¹¦¼ÓÈë¹ºÎï³µ£¡");
	}

	@Override
	public SverResponse<ActionCartVo> findAllCarts(Integer userId) {
		if(userId==null) {
			return SverResponse.createByErrorMessage("²ÎÊý´íÎó£¡");
		}
<<<<<<< HEAD
		//²éÕÒ¸ÃÓÃ»§¹ºÎï³µÖÐµÄÉÌÆ·
		List<ActionCart> list = aCartDao.findCartByUser(userId);
		//·â×°ActionCartVo¶ÔÏó
=======
		List<ActionCart> list = aCartDao.findCartByUser(userId);
>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77
		ActionCartVo cartVo = createCartVo(list);
		return SverResponse.createRespBySuccess(cartVo);
	}
	/**
	 * ·â×°¹ºÎï³µVO¶ÔÏó
	 * @param carts
	 * @return
	 */
	private ActionCartVo createCartVo(List<ActionCart> carts) {
		ActionCartVo cartVo = new ActionCartVo();
		List<ActionCartListVo> list = Lists.newArrayList();
		//¹ºÎï³µÉÌÆ·×Ü¼Û¸ñ
		BigDecimal cartTotalPrice = new BigDecimal("0");
		if(CollectionUtils.isNotEmpty(carts)) {
			for(ActionCart cart:carts) {
				//×ª»»¶ÔÏó
				ActionCartListVo listVo = new ActionCartListVo();
				listVo.setId(cart.getId());
				listVo.setUserId(cart.getUserId());;
				listVo.setProductId(cart.getProductId());
				//·â×°ÉÌÆ·ÐÅÏ¢
				ActionProduct product =aProductDao.findProductById(listVo.getProductId());
				if(product!=null) {
					listVo.setName(product.getName());
					listVo.setStatus(product.getStatus());
					listVo.setPrice(product.getPrice());
					listVo.setStock(product.getStock());
					listVo.setIconUrl(product.getIconUrl());
					//ÅÐ¶Ï¿â´æ
					int buyCount=0;
					if(product.getStock()>=cart.getQuantity()) {
						buyCount = cart.getQuantity();
					}else {
						buyCount = product.getStock();
<<<<<<< HEAD
						//¸üÐÂ¹ºÎï³µÖÐÉÌÆ·ÊýÁ¿
=======
>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77
						ActionCart updateCart = new ActionCart();
						updateCart.setId(cart.getId());
						updateCart.setQuantity(buyCount);
						aCartDao.updateCartById(updateCart);
					}
					listVo.setQuantity(buyCount);
					//¼ÆËã¹ºÎï³µÖÐÄ³ÉÌÆ·µÄ×Ü¼Û¸ñ
					BigDecimal totalPrice = CalcUtil.mul(listVo.getPrice().doubleValue(), listVo.getQuantity().doubleValue());
					listVo.setTotalPrice(totalPrice);
				}
				
				//ÀÛ¼Æ¹ºÎï³µÖÐÉÌÆ·µÄ×Ü¼Û¸ñ
				cartTotalPrice = CalcUtil.add(cartTotalPrice.doubleValue(), listVo.getTotalPrice().doubleValue());
				list.add(listVo);
			}
		}
		cartVo.setLists(list);
		cartVo.setTotalPrice(cartTotalPrice);
		return cartVo;
	}

	
	
	/**
	 * ´Ó¹ºÎï³µÖÐÉ¾³ýÑ¡ÖÐµÄÉÌÆ·
	 */
	@Override
	public SverResponse<ActionCartVo> deleteCart(Integer userId,Integer productId) {
		if(userId ==null || productId==null) {
			return SverResponse.createByErrorMessage("²ÎÊý´íÎó£¡");
		}
		int rs = aCartDao.deleteCart(userId,productId);
		if(rs>0) {
			return this.findAllCarts(userId);
		}
		return SverResponse.createByErrorMessage("ÉÌÆ·É¾³ýÊ§°Ü£¡");
	}

	@Override
	public SverResponse<String> clearCart(Integer userId) {
		if(userId ==null) {
			return SverResponse.createByErrorMessage("²ÎÊý´íÎó£¡");
		}
		int rs = aCartDao.deleteCartByUserId(userId);
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("³É¹¦Çå¿Õ¹ºÎï³µ£¡");
		}
		return SverResponse.createByErrorMessage("Çå¿Õ¹ºÎï³µÊ§°Ü£¡");
	}

	@Override
	public SverResponse<ActionCartVo> updateCart(Integer userId,Integer productId, Integer count) {
		if(userId==null || productId==null || count==null) {
			return SverResponse.createByErrorMessage("²ÎÊý´íÎó£¡");
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
			return SverResponse.createByErrorMessage("²ÎÊý´íÎó£¡");
		}
		int count = aCartDao.getCartCountByUserId(userId);
		return SverResponse.createRespBySuccess(Integer.valueOf(count));
	}	
	
	
}
