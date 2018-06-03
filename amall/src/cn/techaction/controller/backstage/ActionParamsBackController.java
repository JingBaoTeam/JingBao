package cn.techaction.controller.backstage;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.techaction.common.ResponseCode;
import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionParam;
import cn.techaction.pojo.User;
import cn.techaction.service.ActionParamsService;
import cn.techaction.service.UserService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.vo.ActionParamVo;

@Controller
@RequestMapping("/mgr/param")
public class ActionParamsBackController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ActionParamsService aParamService;
	
	
	@RequestMapping(value="/saveparam.do",method=RequestMethod.POST)
    @ResponseBody
	public SverResponse<String> saveParam(HttpSession session,ActionParam param){
		User user = (User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			//用户未登陆
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后在进行操作！");
		}
		//判断是否是管理员
		SverResponse<String> response = userService.isAdmin(user);
		if(response.isSuccess()) {
			//新增类型业务
			return aParamService.addParam(param);
		}else {
			return SverResponse.createByErrorMessage("无操作权限！");
		}
	}
	
	
	@RequestMapping(value="/updateparam.do",method=RequestMethod.POST)
    @ResponseBody
	public SverResponse<String> updateCategory(HttpSession session,ActionParam param){
		User user = (User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			//用户未登陆
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后在进行操作！");
		}
		//判断是否是管理员
		SverResponse<String> response = userService.isAdmin(user);
		if(response.isSuccess()) {
			//新增类型业务
			return aParamService.updateParam(param);
		}else {
			return SverResponse.createByErrorMessage("无操作权限！");
		}
	}
	
	
	@RequestMapping("/findchildren.do")
    @ResponseBody
	public SverResponse<List<ActionParam>> getChildrenParam(HttpSession session,@RequestParam(value="id",defaultValue="0")Integer id){
		User user = (User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			//用户未登陆
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后在进行操作！");
		}
		//判断是否是管理员
		SverResponse<String> response = userService.isAdmin(user);
		if(response.isSuccess()) {
			return aParamService.findParamChildren(id);
		}else {
			return SverResponse.createByErrorMessage("无操作权限！");
		}
	}
	
	@RequestMapping("/findallchildren.do")
    @ResponseBody
	public SverResponse<List<ActionParam>> getParamAndAllChildren(HttpSession session,@RequestParam(value="id",defaultValue="0")Integer id){
		User user = (User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			//用户未登陆
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后在进行操作！");
		}
		//判断是否是管理员
		SverResponse<String> response = userService.isAdmin(user);
		if(response.isSuccess()) {
			return aParamService.findParamAndAllChildrenById(id);
		}else {
			return SverResponse.createByErrorMessage("无操作权限！");
		}
	}
	
	@RequestMapping("/findptype.do")
    @ResponseBody
	public SverResponse<List<ActionParam>> getParamByParentId(HttpSession session){
		User user = (User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			//用户未登陆
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后在进行操作！");
		}
		//判断是否是管理员
		SverResponse<String> response = userService.isAdmin(user);
		if(response.isSuccess()) {
			return aParamService.findProdutTypeParams();
		}else {
			return SverResponse.createByErrorMessage("无操作权限！");
		}
	}
	
	@RequestMapping("/findpartstype.do")
    @ResponseBody
	public SverResponse<List<ActionParamVo>> getPartsParam(HttpSession session,Integer productTypeId){
		User user = (User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			//用户未登陆
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后在进行操作！");
		}
		//判断是否是管理员
		SverResponse<String> response = userService.isAdmin(user);
		if(response.isSuccess()) {
			return aParamService.findPartsTypeParamsByProductTypeId(productTypeId);
		}else {
			return SverResponse.createByErrorMessage("无操作权限！");
		}
	}
	
	
	@RequestMapping("/findpathparam.do")
    @ResponseBody
	public SverResponse<List<ActionParam>> getPathParam(HttpSession session){
		User user = (User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			//用户未登陆
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后在进行操作！");
		}
		//判断是否是管理员
		SverResponse<String> response = userService.isAdmin(user);
		if(response.isSuccess()) {
			return aParamService.findAllPathParams();
		}else {
			return SverResponse.createByErrorMessage("无操作权限！");
		}
	}
}
