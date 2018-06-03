package cn.techaction.controller.backend;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionUser;
import cn.techaction.service.ActionUserService;
import cn.techaction.utils.ConstUtil;

@Controller
@RequestMapping("/mgr/user")
public class ActionUserBackController {

	@Autowired
	private ActionUserService userService;

	/**
	 * 前台用户登录
	 * @param account
	 * @param password
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	@ResponseBody
	public SverResponse<ActionUser> doLogin(String account, String password, HttpSession session) {
		SverResponse<ActionUser> response = userService.doLogin(account, password);
		if (response.isSuccess()) {
			ActionUser user = response.getData();
			if (user.getRole() == ConstUtil.Role.ROLE_ADMIN) {
				//将用户信息保存到Session
				session.setAttribute(ConstUtil.CUR_USER, user);
				return response;
			} else {
				return SverResponse.createByErrorMessage("");
			}
		}
		return response;
	}

}
