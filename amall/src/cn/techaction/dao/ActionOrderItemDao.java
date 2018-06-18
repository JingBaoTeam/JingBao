package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionOrderItem;

public interface ActionOrderItemDao {
	/**
	 * �������붩����
	 * @param orderItems
	 * @return
	 */
	public int[] batchInsert(List<ActionOrderItem> orderItems);
	/**
	 * ���ݶ����Ż�ȡ������                      
	 * @param orderNo	������
	 * @return
	 */
	public List<ActionOrderItem> getItemsByOrderNo(Long orderNo);
}
