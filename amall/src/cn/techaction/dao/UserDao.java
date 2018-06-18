package cn.techaction.dao;

import cn.techaction.pojo.User;

public interface UserDao {

	/**
	 * 根据用户名和密码查找用户
	 * @param account
	 * @param password
	 * @return
	 */
	public User findUserByAccountAndPwd(String account,String password);
	/**
	 * 根据账号判断用户是否存在
	 * @param account
	 * @return
	 */
	public int checkUserByAccount(String account);
	/**
	 * 验证电子邮箱是否已经注册
	 * @param email
	 * @return
	 */
	public int checkUserByEmail(String email);
	/**
	 * 验证手机号码是否已经注册
	 * @param phone
	 * @return
	 */
	public int checkUserByPhone(String phone);
	
	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	public int insertUser(User user);
	
	/**
	 * 查找用户设置的问题
	 * @param account
	 * @return
	 */
	public String findUserQuestion(String account);
	/**
	 * 检查用户密码问题的答案
	 * @param account
	 * @param question
	 * @param asw
	 * @return
	 */
	public int checkUserAnswer(String account,String question,String asw);
	/**
	 * 更新密码
	 * @param account
	 * @param password
	 * @return
	 */
	public int updatePasswordByAccount(String account,String password);
	/**
	 * 验证用户是否已经存在
	 * @param account
	 * @param password
	 * @return
	 */
	public int checkPassword(String account,String password);
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	public int updateUserInfo(User user);
}

