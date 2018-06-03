package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionOrderItem;

public interface ActionOrderItemDao {
	/**
	 * 批量插入订单项
	 * @param orderItems
	 * @return
	 */
	public int[] batchInsert(List<ActionOrderItem> orderItems);
	/**
	 * 根据订单号获取订单项                      
	 * @param orderNo	订单号
	 * @return
	 */
	public List<ActionOrderItem> getItemsByOrderNo(Long orderNo);
}
