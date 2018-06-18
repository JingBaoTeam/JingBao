package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionCart;

public interface ActionCartDao {
	
	/**
	 * 根据用户id和产品id查询购物
	 * @param userId			用户编号
	 * @param productId			商品编号
	 * @return
	 */
	public ActionCart findCartByUserAndProductId(Integer userId,Integer productId);
	/**
	 * 保存购物车
	 * @param cart				购物车对象
	 * @return
	 */
	public int insertCart(ActionCart cart);
	/**
	 * 更新购物车中商品的数量
	 * @param cart				购物车对象
	 * @return
	 */
	public int updateCartById(ActionCart cart);
	/**
	 * 更新购物车中商品的数量
	 * @param cart				购物车对象
	 * @return
	 */
	public int updateCartByUserIdAndProductId(ActionCart cart);
	/**
	 * 删除购物车中的商品
	 * @param productId			商品编号
	 * @return
	 */
	public int deleteCart(Integer userId,Integer productId);
	
	/**
	 * 查找用户购物车中的商品信息
	 * @param userId			用户编号
	 * @return
	 */
	public List<ActionCart> findCartByUser(Integer userId);
	
	/**
	 * 删除某个用户购物车中所有的商品
	 * @param userId
	 * @return					用户编号
	 */
	public int deleteCartByUserId(Integer userId);
	
	/**
	 * 获取当前用户购物车中商品的数量
	 * @param userId
	 * @return
	 */
	public int getCartCountByUserId(Integer userId);

}
