package cn.techaction.controller.portal;

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
@RequestMapping("/user")
public class ActionUserController {
	@Autowired
	private ActionUserService userService;
	
	/**
	 * 前台用户登陆
	 * @param account
	 * @param password
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/do_login.do",method = RequestMethod.POST)
	@ResponseBody
	public SverResponse<ActionUser> dologin(String account, String password, HttpSession session){
		SverResponse<ActionUser> response = userService.doLogin(account, password);
		if(response.isSuccess()) {
			ActionUser user = response.getData();
			session.setAttribute(ConstUtil.CUR_USER, user);
			return response;
		}else {
			System.out.println("error");
			return null;
		}
	}
	
	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/do_register.do",method = RequestMethod.POST)
	@ResponseBody
	public SverResponse<String> register(String account, String password, String email, String phone, String question, String asw){
		ActionUser user = new ActionUser();
		user.setAccount(account);
		user.setAsw(asw);
		user.setEmail(email);
		user.setQuestion(question);
		user.setPassword(password);
		user.setPhone(phone);
		return userService.doRegister(user);
	}
	
	/**
	 * 注册信息检测
	 * @param info
	 * @param type
	 * @return
	 */
	@RequestMapping(value="/do_check_info.do",method = RequestMethod.POST)
	@ResponseBody
	public SverResponse<String> checkInfo(String info, String type){
		return userService.doCheckInfo(info, type);
	}
	
	/**
	 * 获取用户信息接口
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/getuserinfo.do",method = RequestMethod.GET)
	@ResponseBody
	public SverResponse<ActionUser> getUserInfo(HttpSession session){
		ActionUser user = (ActionUser)session.getAttribute(ConstUtil.CUR_USER);
		if(user !=null) {
			return SverResponse.createRespBySuccess(user);
		}
		return SverResponse.createByErrorMessage("无法获取用户信息！");
	}
	
	/**
	 * 获取用户密码问题接口
	 * @param account
	 * @return
	 */
	@RequestMapping(value="/getuserquestion.do",method = RequestMethod.POST)
	@ResponseBody
	public SverResponse<String> getUserQue(String account){
		return userService.getUserQue(account);
	}
	
	/**
	 * 验证用户密码问题答案接口
	 * @param account
	 * @param question
	 * @param asw
	 * @return
	 */
	@RequestMapping(value="/checkuserasw.do",method = RequestMethod.POST)
	@ResponseBody
	public SverResponse<String> checkUserAsw(String account,String question,String asw){
		return userService.doCheckAws(account, question, asw);
	}
	
	/**
	 * 重置密码接口
	 * @param account
	 * @param newpwd
	 * @param token
	 * @return
	 */
	@RequestMapping(value="/resetpassword.do",method = RequestMethod.POST)
	@ResponseBody
	public SverResponse<String> resetPassword(String account,String newpwd,String token){
		return userService.resetPassword(account, newpwd, token);
	}
	
	/**
	 * 修改密码
	 * @param session
	 * @param newpwd
	 * @param oldpwd
	 * @return
	 */
	@RequestMapping(value="/updatepassword.do",method = RequestMethod.POST)
	@ResponseBody
	public SverResponse<String> updatePassword(HttpSession session,String newpwd,String oldpwd){
		ActionUser user = (ActionUser)session.getAttribute(ConstUtil.CUR_USER);
		return userService.updatePassword(user, newpwd, oldpwd);
	}
	
	/**
	 * 修改用户信息
	 * @param session
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/updateuserinfo.do",method = RequestMethod.POST)
	@ResponseBody
	public SverResponse<ActionUser> updateUserInfo(HttpSession session,ActionUser user){
		ActionUser curUser = (ActionUser)session.getAttribute(ConstUtil.CUR_USER);
	    if(curUser == null){
	       return SverResponse.createByErrorMessage("用户尚未登录");
	    }
	    user.setId(curUser.getId());
	    user.setAccount(curUser.getAccount());
	    user.setRole(curUser.getRole());
	    user.setCreate_time(curUser.getCreate_time());
	    SverResponse<ActionUser> resp= userService.updateUserInfo(user);
	    if(resp.isSuccess()) {
	    	session.setAttribute(ConstUtil.CUR_USER, resp.getData());
	    }
		return resp;
	}
	
	/**
	 * 用户登出
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/do_logout.do",method = RequestMethod.GET)
	@ResponseBody
	public SverResponse<String> loginOut(HttpSession session){
		session.removeAttribute(ConstUtil.CUR_USER);
		return SverResponse.createRespBySuccess();
	}
}
