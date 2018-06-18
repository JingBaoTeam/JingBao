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
			return SverResponse.createByErrorMessage("��������");
		}
		//�鿴�û��Ĺ��ﳵ���Ƿ���ڸ���Ʒ
		ActionCart actionCart = aCartDao.findCartByUserAndProductId(userId, productId);
		if(actionCart==null) {
			//������������
			ActionCart cart = new ActionCart();
			cart.setUserId(userId);
			cart.setProductId(productId);
			cart.setQuantity(count);
			cart.setCreated(new Date());
			cart.setUpdated(new Date());
			aCartDao.insertCart(cart);
		}else {
			//����Ѿ����ڣ�����������
			int cartCount= actionCart.getQuantity()+count;
			actionCart.setQuantity(cartCount);
			aCartDao.updateCartById(actionCart);
		}
		return SverResponse.createRespBySuccessMessage("��Ʒ�ѳɹ����빺�ﳵ��");
	}

	@Override
	public SverResponse<ActionCartVo> findAllCarts(Integer userId) {
		if(userId==null) {
			return SverResponse.createByErrorMessage("��������");
		}
		//���Ҹ��û����ﳵ�е���Ʒ
		List<ActionCart> list = aCartDao.findCartByUser(userId);
		//��װActionCartVo����
		ActionCartVo cartVo = createCartVo(list);
		return SverResponse.createRespBySuccess(cartVo);
	}
	/**
	 * ��װ���ﳵVO����
	 * @param carts
	 * @return
	 */
	private ActionCartVo createCartVo(List<ActionCart> carts) {
		ActionCartVo cartVo = new ActionCartVo();
		List<ActionCartListVo> list = Lists.newArrayList();
		//���ﳵ��Ʒ�ܼ۸�
		BigDecimal cartTotalPrice = new BigDecimal("0");
		if(CollectionUtils.isNotEmpty(carts)) {
			for(ActionCart cart:carts) {
				//ת������
				ActionCartListVo listVo = new ActionCartListVo();
				listVo.setId(cart.getId());
				listVo.setUserId(cart.getUserId());;
				listVo.setProductId(cart.getProductId());
				//��װ��Ʒ��Ϣ
				ActionProduct product =aProductDao.findProductById(listVo.getProductId());
				if(product!=null) {
					listVo.setName(product.getName());
					listVo.setStatus(product.getStatus());
					listVo.setPrice(product.getPrice());
					listVo.setStock(product.getStock());
					listVo.setIconUrl(product.getIconUrl());
					//�жϿ��
					int buyCount=0;
					if(product.getStock()>=cart.getQuantity()) {
						buyCount = cart.getQuantity();
					}else {
						buyCount = product.getStock();
						//���¹��ﳵ����Ʒ����
						ActionCart updateCart = new ActionCart();
						updateCart.setId(cart.getId());
						updateCart.setQuantity(buyCount);
						aCartDao.updateCartById(updateCart);
					}
					listVo.setQuantity(buyCount);
					//���㹺�ﳵ��ĳ��Ʒ���ܼ۸�
					BigDecimal totalPrice = CalcUtil.mul(listVo.getPrice().doubleValue(), listVo.getQuantity().doubleValue());
					listVo.setTotalPrice(totalPrice);
				}
				
				//�ۼƹ��ﳵ����Ʒ���ܼ۸�
				cartTotalPrice = CalcUtil.add(cartTotalPrice.doubleValue(), listVo.getTotalPrice().doubleValue());
				list.add(listVo);
			}
		}
		cartVo.setLists(list);
		cartVo.setTotalPrice(cartTotalPrice);
		return cartVo;
	}

	
	
	/**
	 * �ӹ��ﳵ��ɾ��ѡ�е���Ʒ
	 */
	@Override
	public SverResponse<ActionCartVo> deleteCart(Integer userId,Integer productId) {
		if(userId ==null || productId==null) {
			return SverResponse.createByErrorMessage("��������");
		}
		int rs = aCartDao.deleteCart(userId,productId);
		if(rs>0) {
			return this.findAllCarts(userId);
		}
		return SverResponse.createByErrorMessage("��Ʒɾ��ʧ�ܣ�");
	}

	@Override
	public SverResponse<String> clearCart(Integer userId) {
		if(userId ==null) {
			return SverResponse.createByErrorMessage("��������");
		}
		int rs = aCartDao.deleteCartByUserId(userId);
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("�ɹ���չ��ﳵ��");
		}
		return SverResponse.createByErrorMessage("��չ��ﳵʧ�ܣ�");
	}

	@Override
	public SverResponse<ActionCartVo> updateCart(Integer userId,Integer productId, Integer count) {
		if(userId==null || productId==null || count==null) {
			return SverResponse.createByErrorMessage("��������");
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
			return SverResponse.createByErrorMessage("��������");
		}
		int count = aCartDao.getCartCountByUserId(userId);
		return SverResponse.createRespBySuccess(Integer.valueOf(count));
	}	
	
	
}
