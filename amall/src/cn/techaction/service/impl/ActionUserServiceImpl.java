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
}
