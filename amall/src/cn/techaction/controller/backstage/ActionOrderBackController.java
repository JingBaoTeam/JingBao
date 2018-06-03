package cn.techaction.controller.backstage;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.techaction.common.ResponseCode;
import cn.techaction.common.SverResponse;
import cn.techaction.pojo.User;
import cn.techaction.service.ActionOrderService;
import cn.techaction.service.UserService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionOrderVo;

@Controller
@RequestMapping("/mgr/order")
public class ActionOrderBackController {

	@Autowired
	private ActionOrderService aOrderService;
	@Autowired
	private UserService userService;
	
	@RequestMapping("/findorders.do")
	@ResponseBody
	public SverResponse<PageBean<ActionOrderVo>> getOrders(HttpSession session,
			@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
		User user = (User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			//用户未登陆
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请管理员登录后在进行操作！");
		}
		//判断是否是管理员
		SverResponse<String> response = userService.isAdmin(user);
		if(response.isSuccess()) {
			//查询订单并分页
			return aOrderService.mgrOrders(pageNum, pageSize);
		}else {
			return SverResponse.createByErrorMessage("无操作权限！");
		}
	}
	
	@RequestMapping("/findorders_nopages.do")
	@ResponseBody
	public SverResponse<List<ActionOrderVo>> findOrders(HttpSession session,Long orderNo) {
		User user = (User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			//用户未登陆
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请管理员登录后在进行操作！");
		}
		//判断是否是管理员
		SverResponse<String> response = userService.isAdmin(user);
		if(response.isSuccess()) {
			//查询订单并分页
			return aOrderService.findOrdersForNoPages(orderNo);
		}else {
			return SverResponse.createByErrorMessage("无操作权限！");
		}
	}
	
	@RequestMapping("/getdetail.do")
	@ResponseBody
	public SverResponse<ActionOrderVo> getDetail(HttpSession session, Long orderNo) {
		User user = (User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			//用户未登陆
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请管理员登录后在进行操作！");
		}
		//判断是否是管理员
		SverResponse<String> response = userService.isAdmin(user);
		if(response.isSuccess()) {
			return aOrderService.mgrDetail(orderNo);
		}else {
			return SverResponse.createByErrorMessage("无操作权限！");
		}
	}
	
	
	@RequestMapping("/search.do")
	@ResponseBody
	public SverResponse<PageBean<ActionOrderVo>> searchOrder(HttpSession session, 
			Long orderNo,
			@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
		User user = (User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			//用户未登陆
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请管理员登录后在进行操作！");
		}
		//判断是否是管理员
		SverResponse<String> response = userService.isAdmin(user);
		if(response.isSuccess()) {
			return aOrderService.searchOrderByNo(orderNo,pageNum,pageSize);
		}else {
			return SverResponse.createByErrorMessage("无操作权限！");
		}
	}
	
	@RequestMapping("/delivergoods.do")
	@ResponseBody
	public SverResponse<String> delivergoods(HttpSession session, Long orderNo) {
		User user = (User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			//用户未登陆
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请管理员登录后在进行操作！");
		}
		//判断是否是管理员
		SverResponse<String> response = userService.isAdmin(user);
		if(response.isSuccess()) {
			return aOrderService.deliverGoods(orderNo);
		}else {
			return SverResponse.createByErrorMessage("无操作权限！");
		}
	}
}
