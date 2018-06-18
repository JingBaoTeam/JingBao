package cn.techaction.service;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.User;

public interface UserService {
	/*此方法暂时不用*/
	public User login(String account,String password);
	
	public SverResponse<String> isAdmin(User user);
	
	public SverResponse<User> doLogin(String account,String password);
	public SverResponse<String> doRegister(User user);
	
	public SverResponse<String> checkValidation(String str,String type);
	
	//获取用户问题
	public SverResponse<String> findUserQuestion(String account);
	//获取用户问题密码
	public SverResponse<String> checkUserAnswer(String account,String question,String asw);

	
	public SverResponse<String> resetPassword(String account,String password,String token);
	
	public SverResponse<String> updatePassword(User user,String newPassword,String oldPassword);
	
	public SverResponse<User> updateUserInfo(User user);
}
