package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionOrder;

public interface ActionOrderDao {

	/**
	 * ���涩����Ϣ
	 * @param order	������Ϣ
	 * @return
	 */
	public int insertOrder(ActionOrder order);
	/**
	 * ��ȡ�û���������
	 * @param uid	�û����
	 * @return
	 */
	public int getTotalRecords(Integer uid);
	/**
	 * ��ȡ�û�������ҳ�б�
	 * @param uid		�û����
	 * @param offset	��ʼλ��
	 * @param pageSize	ҳ���С
	 * @return
	 */
	public List<ActionOrder> findOrders(Integer uid,int offset,int pageSize);
	/**
	 * �����û��Ͷ�����Ų�ѯ������Ϣ
	 * @param uid			�û����
	 * @param orderNo		�������
	 * @return
	 */
	public ActionOrder findOrderByUserAndOrderNo(Integer uid,Long orderNo);
	/**
	 * ���¶�����Ϣ
	 * @param order
	 * @return
	 */
	public int updateOrder(ActionOrder order);
	
	
	/******************************��̨������*******************************************/
	
	/**
	 * ��ȡ�����ܼ�¼��
	 * @return
	 */
	public int mgrTotalRecords();
	/**
	 * ��ȡ��ҳ��������
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List<ActionOrder> mgrOrders(int offset,int pageSize);
	/**
	 * ��ѯ����
	 * @param orderNo  �����ţ�Ϊnull���ѯ���ж���
	 * @return
	 */
	public List<ActionOrder> searchOrders(Long orderNo);
	
	/**
	 * ���ݶ�����Ų��Ҷ�������
	 * @param orderNo
	 * @return
	 */
	public ActionOrder findOrderDetailByNo(Long orderNo);
}
