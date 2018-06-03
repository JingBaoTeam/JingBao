package cn.techaction.service.impl;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.UserDao;
import cn.techaction.pojo.User;
import cn.techaction.service.UserService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.utils.MD5Util;
import cn.techaction.utils.TokenCache;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;

	@Override
	public User login(String account, String password) {
		return userDao.findUserByAccountAndPwd(account, password);
	}

	@Override
	public SverResponse<User> doLogin(String account, String password) {
		//判断用户是否存在
		int rs = userDao.checkUserByAccount(account);
		if(rs==0) {
			return SverResponse.createByErrorMessage("用户不存在！");
		}
		//根据用户名和密码查找用户
		String md5Pwd = MD5Util.MD5Encode(password, "utf-8", false);
		User user = userDao.findUserByAccountAndPwd(account, md5Pwd);
		if(user==null) {
			return SverResponse.createByErrorMessage("密码错误！");
		}
		//将密码置空，防止泄密
		user.setPassword(StringUtils.EMPTY);
		return SverResponse.createRespBySuccess("登陆成功", user);
	}

	@Override
	public SverResponse<String> doRegister(User user) {
		//检测用户名是否已经存在
		SverResponse<String> resp = checkValidation(user.getAccount(), ConstUtil.TYPE_ACCOUNT);
		if(!resp.isSuccess()) {
			return resp;
		}
		//检测邮箱是否已经注册
		resp = checkValidation(user.getEmail(), ConstUtil.TYPE_EMAIL);
		if(!resp.isSuccess()) {
			return resp;
		}
		//指定用户角色，通过前段注册的都是客户
		user.setRole(ConstUtil.Role.ROLE_CUSTOMER);
		//对密码进行加密处理
		user.setPassword(MD5Util.MD5Encode(user.getPassword(), "UTF-8", false));
		//执行注册
		Date curDate = new Date();
		
		user.setCreate_time(curDate);
		user.setUpdate_time(curDate);
		
		int rs = userDao.insertUser(user);
		if(rs==0) {
			return SverResponse.createByErrorMessage("注册失败！");
		}
		return  SverResponse.createRespBySuccessMessage("注册成功！");
	}

	/**
	 * 检测信息是否有效
	 */
	@Override
	public SverResponse<String> checkValidation(String str, String type) {
		if(StringUtils.isNotBlank(type)) {
			if(ConstUtil.TYPE_ACCOUNT.equals(type)) {
				int rs = userDao.checkUserByAccount(str);
				if(rs > 0) {
					return SverResponse.createByErrorMessage("用户名已经存在");
				}
			}
			if(ConstUtil.TYPE_EMAIL.equals(type)) {
				int rs = userDao.checkUserByEmail(str);
				if(rs > 0) {
					return SverResponse.createByErrorMessage("Email已经存在");
				}
			}
			if(ConstUtil.TYPE_PHONE.equals(type)) {
				int rs = userDao.checkUserByPhone(str);
				if(rs > 0) {
					return SverResponse.createByErrorMessage("电话号码已经存在！");
				}
			}
		}else {
			return SverResponse.createByErrorMessage("信息验证类别错误！");
		}
		return SverResponse.createRespBySuccessMessage("信息验证成功！");
	}

	
	@Override
	public SverResponse<String> findUserQuestion(String account) {
		String question =userDao.findUserQuestion(account);
		if(question==null) {
			return SverResponse.createByErrorMessage("未设置密码提示问题！");
		}
		return SverResponse.createRespBySuccess(question);
	}

	/**
	 * 检查用户密码问题答案
	 */
	@Override
	public SverResponse<String> checkUserAnswer(String account, String question, String asw) {
		int rs =  userDao.checkUserAnswer(account, question, asw);
		if(rs > 0) {
			//答案正确,生成Token
			String token = UUID.randomUUID().toString();
			//放入缓存
			TokenCache.setCacheData(TokenCache.PREFIX+account, token);
			return SverResponse.createRespBySuccessMessage(token);
		}
		return SverResponse.createByErrorMessage("问题答案错误！");
	}

	@Override
	public SverResponse<String> resetPassword(String account, String password, String token) {
		//检查token
		if(StringUtils.isBlank(token)) {
			return SverResponse.createByErrorMessage("Token参数错误！");
		}
		//检查用户名
		SverResponse<String> resp= checkValidation(account, ConstUtil.TYPE_ACCOUNT);
		if(resp.isSuccess()) {
			//用户不存在
			return SverResponse.createByErrorMessage("用户名错误！");
		}
		//检查缓存中的token
		String oldToken = TokenCache.getCacheData(TokenCache.PREFIX+account);
		if(StringUtils.isBlank(oldToken)) {
			return SverResponse.createByErrorMessage("token无效！");
		}
		if(StringUtils.equals(oldToken, token)) {
			String pwd = MD5Util.MD5Encode(password, "UTF-8", false);
			//更新密码
			int rs = userDao.updatePasswordByAccount(account,pwd);
			if(rs>0) {
				return SverResponse.createRespBySuccess("密码修改成功！");
			}
			
		}else {
			return SverResponse.createByErrorMessage("Token错误,请重新获取！");
		}
		return SverResponse.createByErrorMessage("密码修改失败！");
	}

	@Override
	public SverResponse<String> updatePassword(User user, String newPassword, String oldPassword) {
		//防止横向越权，检测用户的旧密码是否正确
		oldPassword = MD5Util.MD5Encode(oldPassword, "UTF-8", false);
		int rs = userDao.checkPassword(user.getAccount(), oldPassword);
		if(rs == 0) {
			return SverResponse.createByErrorMessage("原始密码错误！");
		}
		newPassword = MD5Util.MD5Encode(newPassword, "UTF-8", false);
		user.setPassword(newPassword);
		user.setUpdate_time(new Date());
		rs =  userDao.updateUserInfo(user);
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("密码修改成功！");
		}
		return SverResponse.createByErrorMessage("密码修改失败！");
	}

	//修改用户信息
	@Override
	public SverResponse<User> updateUserInfo(User user) {
		User updateUser = new User();
	    updateUser.setId(user.getId());
	    updateUser.setAccount(user.getAccount());
	    updateUser.setEmail(user.getEmail());
	    updateUser.setPhone(user.getPhone());
	    updateUser.setQuestion(user.getQuestion());
	    updateUser.setAsw(user.getAsw());
	    updateUser.setRole(user.getRole());
	    updateUser.setCreate_time(user.getCreate_time());
	    updateUser.setUpdate_time(new Date());
		int rs = userDao.updateUserInfo(updateUser);
		if(rs >0) {
			return SverResponse.createRespBySuccess("用户信息修改成功！",updateUser);
		}
		return SverResponse.createByErrorMessage("用户信息修改失败！"); 
	}

	
	/**
	 * 判断当前用户是否是管理员
	 */
	@Override
	public SverResponse<String> isAdmin(User user) {
		if(user.getRole()==ConstUtil.Role.ROLE_ADMIN) {
			return SverResponse.createRespBySuccess();
		}
		return SverResponse.createRespByError();
	}
}
