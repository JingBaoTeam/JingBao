package cn.techaction.service;

import java.util.List;

import cn.techaction.common.SverResponse;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionOrderVo;

public interface ActionOrderService {
	/**
	 * ���ɶ���
	 * @param uid		�û�ID
	 * @param addrId	��ַ���
	 * @return
	 */
	public SverResponse<ActionOrderVo> generateOrder(Integer uid,Integer addrId);
	/**
	 * ������ҳ�б�
	 * @param uid		�û�ID
	 * @param pageNum	ҳ��
	 * @param pageSize	ҳ���С
	 * @return
	 */
	public SverResponse<PageBean<ActionOrderVo>> findOrders(Integer uid,int pageNum,int pageSize);
	/**
	 * ȡ������
	 * @param uid		�û�Id
	 * @param orderNo	�������
	 * @return
	 */
	public SverResponse<String> cancelOrder(Integer uid, Long orderNo);
	/**
	 * ���ݶ�����Ż�ȡ��������
	 * @param orderNo	�������
	 * @return
	 */
	public SverResponse<ActionOrderVo> findOrderDetail(Integer uid,Long orderNo);
	
	
	/******************************��̨����ҵ�񷽷�******************************************/
	/**
	 * ����Ա�û���ѯ������ҳ�б�
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public SverResponse<PageBean<ActionOrderVo>> mgrOrders(int pageNum,int pageSize);
	/**
	 * ����Ա�û���ѯ����������ҳ
	 * @param orderNo  ������ţ�Ϊnull�����ѯȫ��
	 * @return
	 */
	public SverResponse<List<ActionOrderVo>> findOrdersForNoPages(Long orderNo);
	/**
	 * ����Ա��ѯ�������鷽��
	 * @param orderNo
	 * @return
	 */
	public SverResponse<ActionOrderVo> mgrDetail(Long orderNo);
	/**
	 * ����Ա���ݶ����Ų�ѯ����
	 * @param orderNo
	 * @return
	 */
	public SverResponse<PageBean<ActionOrderVo>> searchOrderByNo(Long orderNo,int pageNum,int pageSize);
	/**
	 * ����Ա����
	 * @param orderNo
	 * @return
	 */
	public SverResponse<String> deliverGoods(Long orderNo);
}
