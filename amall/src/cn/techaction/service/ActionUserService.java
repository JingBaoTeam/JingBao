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
}
