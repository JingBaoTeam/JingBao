package cn.techaction.service;

import java.util.List;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionAddress;

public interface ActionAddrService {

	/**
	 * ����ĳ���û��������ջ���ַ
	 * @param userId
	 * @return
	 */
	public SverResponse<List<ActionAddress>> findAddrsByUserId(Integer userId);
	/**
	 * �����ռ��˵�ַ��Ϣ
	 * @param addr
	 * @return
	 */
	public SverResponse<String> addAddress(ActionAddress addr);
	/**
	 * ����Idɾ�������˵�ַ��Ϣ
	 * @param id
	 * @return
	 */
	public SverResponse<String> delAddress(Integer userId,Integer id);
	/**
	 * �����ռ��˵�ַ��Ϣ
	 * @param addr
	 * @return
	 */
	public SverResponse<String> updateAddress(ActionAddress addr);
	
	/**
	 * ���µ�ַĬ��״̬
	 * @param userId
	 * @param id
	 * @return
	 */
	public SverResponse<String> updateAddrDefaultStatus(Integer userId, Integer id);
}
