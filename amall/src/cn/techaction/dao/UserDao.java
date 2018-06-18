package cn.techaction.dao;

import cn.techaction.pojo.User;

public interface UserDao {

	/**
	 * �����û�������������û�
	 * @param account
	 * @param password
	 * @return
	 */
	public User findUserByAccountAndPwd(String account,String password);
	/**
	 * �����˺��ж��û��Ƿ����
	 * @param account
	 * @return
	 */
	public int checkUserByAccount(String account);
	/**
	 * ��֤���������Ƿ��Ѿ�ע��
	 * @param email
	 * @return
	 */
	public int checkUserByEmail(String email);
	/**
	 * ��֤�ֻ������Ƿ��Ѿ�ע��
	 * @param phone
	 * @return
	 */
	public int checkUserByPhone(String phone);
	
	/**
	 * �����û�
	 * @param user
	 * @return
	 */
	public int insertUser(User user);
	
	/**
	 * �����û����õ�����
	 * @param account
	 * @return
	 */
	public String findUserQuestion(String account);
	/**
	 * ����û���������Ĵ�
	 * @param account
	 * @param question
	 * @param asw
	 * @return
	 */
	public int checkUserAnswer(String account,String question,String asw);
	/**
	 * ��������
	 * @param account
	 * @param password
	 * @return
	 */
	public int updatePasswordByAccount(String account,String password);
	/**
	 * ��֤�û��Ƿ��Ѿ�����
	 * @param account
	 * @param password
	 * @return
	 */
	public int checkPassword(String account,String password);
	/**
	 * �����û���Ϣ
	 * @param user
	 * @return
	 */
	public int updateUserInfo(User user);
}

