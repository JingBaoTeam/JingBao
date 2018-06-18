package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionOrder;

public interface ActionOrderDao {

	/**
	 * 保存订单信息
	 * @param order	订单信息
	 * @return
	 */
	public int insertOrder(ActionOrder order);
	/**
	 * 获取用户订单总数
	 * @param uid	用户编号
	 * @return
	 */
	public int getTotalRecords(Integer uid);
	/**
	 * 获取用户订单分页列表
	 * @param uid		用户编号
	 * @param offset	开始位置
	 * @param pageSize	页面大小
	 * @return
	 */
	public List<ActionOrder> findOrders(Integer uid,int offset,int pageSize);
	/**
	 * 根据用户和订单编号查询订单信息
	 * @param uid			用户编号
	 * @param orderNo		订单编号
	 * @return
	 */
	public ActionOrder findOrderByUserAndOrderNo(Integer uid,Long orderNo);
	/**
	 * 更新订单信息
	 * @param order
	 * @return
	 */
	public int updateOrder(ActionOrder order);
	
	
	/******************************后台管理方法*******************************************/
	
	/**
	 * 获取订单总记录数
	 * @return
	 */
	public int mgrTotalRecords();
	/**
	 * 获取分页订单数据
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List<ActionOrder> mgrOrders(int offset,int pageSize);
	/**
	 * 查询订单
	 * @param orderNo  订单号，为null则查询所有订单
	 * @return
	 */
	public List<ActionOrder> searchOrders(Long orderNo);
	
	/**
	 * 根据订单编号查找订单详情
	 * @param orderNo
	 * @return
	 */
	public ActionOrder findOrderDetailByNo(Long orderNo);
}
