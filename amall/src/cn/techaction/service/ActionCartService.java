package cn.techaction.service;

import cn.techaction.common.SverResponse;
import cn.techaction.vo.ActionCartVo;

public interface ActionCartService {

	/**
	 * ������Ʒ��Ϣ�����ﳵ��
	 * @param userId		�û����
	 * @param productId		��Ʒ���
	 * @param count			��Ʒ������
	 * @return
	 */
	public SverResponse<String> saveOrUpdate(Integer userId,Integer productId,Integer count);
	/**
	 * ��ѯ�û����ﳵ����Ʒ��Ϣ
	 * @param userId		�û����
	 * @return
	 */
	public SverResponse<ActionCartVo> findAllCarts(Integer userId);
	/**
	 * ɾ�����ﳵ�е���Ʒ��Ϣ
	 * @param productId		��Ʒ���
	 * @return
	 */
	public SverResponse<ActionCartVo> deleteCart(Integer userId,Integer productId);
	
	/**
	 * ���¹��ﳵ����Ʒ������
	 * @param userId
	 * @param productId
	 * @param count
	 * @return
	 */
	public SverResponse<ActionCartVo> updateCart(Integer userId,Integer productId,Integer count); 
	/**
	 * ��չ��ﳵ
	 * @param userId
	 * @return
	 */
	public SverResponse<String> clearCart(Integer userId);
	
	/**
	 * ��ȡ��½�û����ﳵ����Ʒ�ĸ���
	 * @param userId
	 * @return
	 */
	public SverResponse<Integer> getCartCount(Integer userId);
}