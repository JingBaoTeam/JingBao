package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionCart;

public interface ActionCartDao {
	
	/**
	 * �����û�id�Ͳ�Ʒid��ѯ����
	 * @param userId			�û����
	 * @param productId			��Ʒ���
	 * @return
	 */
	public ActionCart findCartByUserAndProductId(Integer userId,Integer productId);
	/**
	 * ���湺�ﳵ
	 * @param cart				���ﳵ����
	 * @return
	 */
	public int insertCart(ActionCart cart);
	/**
	 * ���¹��ﳵ����Ʒ������
	 * @param cart				���ﳵ����
	 * @return
	 */
	public int updateCartById(ActionCart cart);
	/**
	 * ���¹��ﳵ����Ʒ������
	 * @param cart				���ﳵ����
	 * @return
	 */
	public int updateCartByUserIdAndProductId(ActionCart cart);
	/**
	 * ɾ�����ﳵ�е���Ʒ
	 * @param productId			��Ʒ���
	 * @return
	 */
	public int deleteCart(Integer userId,Integer productId);
	
	/**
	 * �����û����ﳵ�е���Ʒ��Ϣ
	 * @param userId			�û����
	 * @return
	 */
	public List<ActionCart> findCartByUser(Integer userId);
	
	/**
	 * ɾ��ĳ���û����ﳵ�����е���Ʒ
	 * @param userId
	 * @return					�û����
	 */
	public int deleteCartByUserId(Integer userId);
	
	/**
	 * ��ȡ��ǰ�û����ﳵ����Ʒ������
	 * @param userId
	 * @return
	 */
	public int getCartCountByUserId(Integer userId);

}
