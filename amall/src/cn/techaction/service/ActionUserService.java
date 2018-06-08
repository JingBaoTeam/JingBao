package cn.techaction.service;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionUser;

public interface ActionUserService {
	/**
	 * 判断用户是否是管理用户
	 * @param user
	 * @return
	 */
	public SverResponse<String> isAdmin(ActionUser user);
	/**
	 * 用户登录业务
	 * @param account	账号
	 * @param password	密码
	 * @return
	 */
	public SverResponse<ActionUser> doLogin(String account,String password);
	/**
	 * 注册新用户
	 * @param user
	 * @return
	 */
	public SverResponse<String> doRegister(ActionUser user);
	/**
	 * 注册信息检测
	 * @param info
	 * @param type
	 * @return
	 */
	public SverResponse<String> doCheckInfo(String info, String type);
	
	/**
	 * 获取用户问题
	 * @param account
	 * @return
	 */
	public SverResponse<String> getUserQue(String account);
	
	/**
	 * 验证用户问题
	 * @param account
	 * @param aws
	 * @return
	 */
	public SverResponse<String> doCheckAws(String account,String question,String asw);
	
	/**
	 * 重置用户密码
	 * @param account
	 * @param password
	 * @param token
	 * @return
	 */
	public SverResponse<String> resetPassword(String account,String password,String token);
	
	/**
	 * 修改用户密码
	 * @param user
	 * @param newPassword
	 * @param oldPassword
	 * @return
	 */
	public SverResponse<String> updatePassword(ActionUser user, String newPassword, String oldPassword);
	
	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	public SverResponse<ActionUser> updateUserInfo(ActionUser user);
}
