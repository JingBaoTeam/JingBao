package cn.techaction.service;

import cn.techaction.common.SverResponse;
import cn.techaction.vo.ActionCartVo;

public interface ActionCartService {

	/**
	 * 保存商品信息到购物车中
	 * @param userId		用户编号
	 * @param productId		商品编号
	 * @param count			商品的数量
	 * @return
	 */
	public SverResponse<String> saveOrUpdate(Integer userId,Integer productId,Integer count);
	/**
	 * 查询用户购物车中商品信息
	 * @param userId		用户编号
	 * @return
	 */
	public SverResponse<ActionCartVo> findAllCarts(Integer userId);
	/**
	 * 删除购物车中的商品信息
	 * @param productId		商品编号
	 * @return
	 */
	public SverResponse<ActionCartVo> deleteCart(Integer userId,Integer productId);
	
	/**
	 * 更新购物车中商品的梳理
	 * @param userId
	 * @param productId
	 * @param count
	 * @return
	 */
	public SverResponse<ActionCartVo> updateCart(Integer userId,Integer productId,Integer count); 
	/**
	 * 清空购物车
	 * @param userId
	 * @return
	 */
	public SverResponse<String> clearCart(Integer userId);
	
	/**
	 * 获取登陆用户购物车中商品的个数
	 * @param userId
	 * @return
	 */
	public SverResponse<Integer> getCartCount(Integer userId);
}