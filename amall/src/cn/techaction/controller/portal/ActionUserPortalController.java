package cn.techaction.controller.portal;

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
@RequestMapping("/user")
public class ActionUserPortalController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/login.do",method=RequestMethod.POST)
	@ResponseBody
	public User login(String account,String password,HttpSession session) {
		User user = userService.login(account, password);
		return user;
	}
	
	/**
	 * 前端登陆方法
	 * @param account     	账号
	 * @param password		密码
	 * @param session		会话
	 * @return
	 */
	@RequestMapping(value="/do_login.do",method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<User> doLogin(String account,String password,HttpSession session){
		SverResponse<User> response = userService.doLogin(account, password);
		if(response.isSuccess()) {
			//登陆成功，将用户信息存入session
			session.setAttribute(ConstUtil.CUR_USER, response.getData());
		}
		return response;
	}
	
	/**
	 * 用户登出
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/do_logout.do")
	@ResponseBody
	public SverResponse<String> loginOut(HttpSession session){
		session.removeAttribute(ConstUtil.CUR_USER);
		return SverResponse.createRespBySuccess();
	}
	/**
	 * 用户注册
	 * @param user		用户信息
	 * @return
	 */
	@RequestMapping(value="/do_register.do",method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<String> registerUser(User user){
		return userService.doRegister(user);
	}
	
	/**
	 * 用于检测用户名、邮箱、手机是否已被注册
	 * @param info
	 * @param type
	 * @return
	 */
	@RequestMapping(value="/do_check_info.do",method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<String> checkValidUserInfo(String info,String type){
		return userService.checkValidation(info, type);
	}
	
	
	/**
	 * 获取登用用户信息
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/getuserinfo.do",method=RequestMethod.GET)
	@ResponseBody
	public SverResponse<User> getUserInfo(HttpSession session){
		User user = (User)session.getAttribute(ConstUtil.CUR_USER);
		if(user !=null) {
			return SverResponse.createRespBySuccess(user);
		}
		return SverResponse.createByErrorMessage("无法获取用户信息！");
	}
	/**
	 * 获取用户密码提示问题
	 * @param account
	 * @return
	 */
	@RequestMapping(value="/getuserquestion.do",method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<String> getUserQuestion(String account){
		return userService.findUserQuestion(account);
	}
	
	
	/**
	 * 验证用户密码提示问题答案
	 * @param account
	 * @param question
	 * @param asw
	 * @return
	 */
	@RequestMapping(value="/checkuserasw.do",method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<String> checkUserAnswer(String account,String question,String asw){
		return userService.checkUserAnswer(account, question, asw);
	}
	
	
	/**
	 * 根据问题及答案重置密码
	 * @param account
	 * @param newPwd
	 * @param token
	 * @return
	 */
	@RequestMapping(value="/resetpassword.do",method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<String> resetPassword(String account,String newpwd,String token){
		return userService.resetPassword(account, newpwd, token);
	}
	
	
	/**
	 * 登陆用户修改密码
	 * @param account
	 * @param newPwd
	 * @param oldpwd
	 * @return
	 */
	@RequestMapping(value="/updatepassword.do",method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<String> updatePassword(HttpSession session,String newpwd,String oldpwd){
		User user = (User)session.getAttribute(ConstUtil.CUR_USER);
		return userService.updatePassword(user, newpwd, oldpwd);
	}
	
	
	
	@RequestMapping(value="/updateuserinfo.do",method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<User> updateUserInfo(HttpSession session,User user){
		User curUser = (User)session.getAttribute(ConstUtil.CUR_USER);
	    if(curUser == null){
	       return SverResponse.createByErrorMessage("用户尚未登录");
	    }
	    user.setId(curUser.getId());
	    user.setAccount(curUser.getAccount());
	    user.setRole(curUser.getRole());
	    user.setCreate_time(curUser.getCreate_time());
	    SverResponse<User> resp= userService.updateUserInfo(user);
	    if(resp.isSuccess()) {
	    	session.setAttribute(ConstUtil.CUR_USER, resp.getData());
	    }
		return resp;
	}
	
	

}
