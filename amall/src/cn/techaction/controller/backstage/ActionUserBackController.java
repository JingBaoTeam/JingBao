package cn.techaction.controller.backstage;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.User;
import cn.techaction.service.UserService;
import cn.techaction.utils.ConstUtil;

@Controller
@RequestMapping("/mgr/user")
public class ActionUserBackController {

	@Autowired
	private UserService userService;

	/**
	 * 前端登陆方法
	 * 
	 * @param account
	 *            账号
	 * @param password
	 *            密码
	 * @param session
	 *            会话
	 * @return
	 */
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	@ResponseBody
	public SverResponse<User> doLogin(String account, String password, HttpSession session) {
		SverResponse<User> response = userService.doLogin(account, password);
		if (response.isSuccess()) {
			User user = response.getData();
			if (user.getRole() == ConstUtil.Role.ROLE_ADMIN) {
				// 说明登录的是管理员
				session.setAttribute(ConstUtil.CUR_USER, user);
				return response;
			} else {
				return SverResponse.createByErrorMessage("不是管理员,无法登录");
			}
		}
		return response;
	}

}
