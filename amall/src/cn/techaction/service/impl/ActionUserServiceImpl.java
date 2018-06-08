package cn.techaction.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionUserDao;
import cn.techaction.pojo.ActionUser;
import cn.techaction.service.ActionUserService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.utils.MD5Util;
import cn.techaction.utils.TokenCache;

@Service
public class ActionUserServiceImpl implements ActionUserService {
	
	@Autowired
	private ActionUserDao userDao;


	@Override
	public SverResponse<ActionUser> doLogin(String account, String password) {
		//判断用户是否存在
		int rs = userDao.checkUserByAccount(account);
		if(rs==0) {
			return SverResponse.createByErrorMessage("用户不存在！");
		}
		//根据用户名和密码查找用户
		String md5Pwd = MD5Util.MD5Encode(password, "utf-8", false);
		ActionUser user = userDao.findUserByAccountAndPwd(account, md5Pwd);
		if(user==null) {
			return SverResponse.createByErrorMessage("密码错误！");
		}
		//将密码置空，防止泄密
		user.setPassword(StringUtils.EMPTY);
		return SverResponse.createRespBySuccess("登陆成功", user);
	}

	@Override
	public SverResponse<String> isAdmin(ActionUser user) {
		if(user.getRole()==ConstUtil.Role.ROLE_ADMIN) {
			return SverResponse.createRespBySuccess();
		}
		return SverResponse.createRespByError();
	}

	@Override
	public SverResponse<String> doRegister(ActionUser user) {
		int rs = userDao.checkUserByAccount(user.getAccount());
		if(rs==0) {
			return SverResponse.createByErrorMessage("用户不存在！");
		}
		user.setRole(ConstUtil.Role.ROLE_CUSTOMER);
		user.setPassword(MD5Util.MD5Encode(user.getPassword(), "UTF-8", false));
		Date curDate = new Date();
		user.setCreate_time(curDate);
		user.setUpdate_time(curDate);
		rs = userDao.insertUser(user);
		if(rs==0) {
			return SverResponse.createByErrorMessage("注册失败！");
		}
		return  SverResponse.createRespBySuccessMessage("注册成功！");
	}

	@Override
	public SverResponse<String> doCheckInfo(String info, String type) {
		if(type.equals("account")) {
			int rs = userDao.checkUserByAccount(info);
			if(rs == 0) return SverResponse.createRespBySuccess("信息验证成功");
			else return SverResponse.createByErrorMessage("用户名已经存在");
		}else if(type.equals("email")) {
			int rs = userDao.checkUserByEmail(info);
			if(rs == 0) return SverResponse.createRespBySuccess("信息验证成功");
			else return SverResponse.createByErrorMessage("Email已经存在");
		}else if(type.equals("phone")) {
			if(userDao.checkUserByPhone(info) == 0) return SverResponse.createRespBySuccess("信息验证成功");
			else return SverResponse.createByErrorMessage("电话号码已经存在！");
		}else return SverResponse.createByErrorMessage("信息验证类别错误！");
	}

	@Override
	public SverResponse<String> getUserQue(String account) {
		String mess = userDao.findUserQuestion(account);
		if(mess == null) return SverResponse.createByErrorMessage("未设置密码提示问题！");
		else return SverResponse.createRespBySuccess(mess);
	}

	@Override
	public SverResponse<String> doCheckAws(String account, String question, String asw) {
		if(userDao.checkUserAnswer(account, question, asw) == 0) {
			return SverResponse.createByErrorMessage("问题回答错误");
		}else {
			return SverResponse.createRespBySuccess("问题回答正确");
		}
	}

	@Override
	public SverResponse<String> resetPassword(String account, String password, String token) {
		if(StringUtils.isBlank(token)) {
			return SverResponse.createByErrorMessage("Token参数错误！");
		}
		SverResponse<String> resp= doCheckInfo(account, ConstUtil.TYPE_ACCOUNT);
		if(resp.isSuccess()) {
			return SverResponse.createByErrorMessage("用户名错误！");
		}
		String oldToken = TokenCache.getCacheData(TokenCache.PREFIX+account);
		if(StringUtils.isBlank(oldToken)) {
			return SverResponse.createByErrorMessage("token无效！");
		}
		if(StringUtils.equals(oldToken, token)) {
			String pwd = MD5Util.MD5Encode(password, "UTF-8", false);
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
	public SverResponse<String> updatePassword(ActionUser user, String newPassword, String oldPassword) {
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

	@Override
	public SverResponse<ActionUser> updateUserInfo(ActionUser user) {
		ActionUser updateUser = new ActionUser();
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
}
