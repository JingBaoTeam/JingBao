package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionAddress;

public interface ActionAddressDao {

	/**
	 * �����ջ��˵�ַ��Ϣ
	 * @param address	��ַ����
	 * @return
	 */
	public int insertAddress(ActionAddress address);
	/**
	 * ��ѯ�û����ռ��˵�ַ��Ϣ
	 * @param userId	�û�ID
	 * @return
	 */
	public List<ActionAddress> findAddrsByUserId(Integer userId);
	/**
	 * �����ռ��˵�ַ��Ϣ
	 * @param address	��ַ����
	 * @return
	 */
	public int updateAddress(ActionAddress address);
	/**
	 * ɾ���ռ��˵�ַ��Ϣ
	 * @param id	��ַ���
	 * @return
	 */
	public int deleteAddress(Integer id);
	/**
	 * ���ݵ�ַId��ѯ�ջ��˵�ַ��Ϣ
	 * @param addrId
	 * @return
	 */
	public ActionAddress findAddrsById(Integer addrId);
	/**
	 * ��ѯ�Ƿ����Ĭ�ϵ�ַ
	 * @param addrId
	 * @return
	 */
	public int findDefaultAddrByUserId(Integer userId);
	/**
	 * ��ȡ�û���Ĭ�ϵ�ַ
	 * @param userId
	 * @return
	 */
	public ActionAddress findDefaultAddr(Integer userId);
}
