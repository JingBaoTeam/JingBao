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
			//�û�δ��½
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "�����Ա��¼���ڽ��в�����");
		}
		//�ж��Ƿ��ǹ���Ա
		SverResponse<String> response = userService.isAdmin(user);
		if(response.isSuccess()) {
			//��ѯ��������ҳ
			return aOrderService.mgrOrders(pageNum, pageSize);
		}else {
			return SverResponse.createByErrorMessage("�޲���Ȩ�ޣ�");
		}
	}
	
	@RequestMapping("/findorders_nopages.do")
	@ResponseBody
	public SverResponse<List<ActionOrderVo>> findOrders(HttpSession session,Long orderNo) {
		User user = (User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			//�û�δ��½
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "�����Ա��¼���ڽ��в�����");
		}
		//�ж��Ƿ��ǹ���Ա
		SverResponse<String> response = userService.isAdmin(user);
		if(response.isSuccess()) {
			//��ѯ��������ҳ
			return aOrderService.findOrdersForNoPages(orderNo);
		}else {
			return SverResponse.createByErrorMessage("�޲���Ȩ�ޣ�");
		}
	}
	
	@RequestMapping("/getdetail.do")
	@ResponseBody
	public SverResponse<ActionOrderVo> getDetail(HttpSession session, Long orderNo) {
		User user = (User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			//�û�δ��½
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "�����Ա��¼���ڽ��в�����");
		}
		//�ж��Ƿ��ǹ���Ա
		SverResponse<String> response = userService.isAdmin(user);
		if(response.isSuccess()) {
			return aOrderService.mgrDetail(orderNo);
		}else {
			return SverResponse.createByErrorMessage("�޲���Ȩ�ޣ�");
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
			//�û�δ��½
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "�����Ա��¼���ڽ��в�����");
		}
		//�ж��Ƿ��ǹ���Ա
		SverResponse<String> response = userService.isAdmin(user);
		if(response.isSuccess()) {
			return aOrderService.searchOrderByNo(orderNo,pageNum,pageSize);
		}else {
			return SverResponse.createByErrorMessage("�޲���Ȩ�ޣ�");
		}
	}
	
	@RequestMapping("/delivergoods.do")
	@ResponseBody
	public SverResponse<String> delivergoods(HttpSession session, Long orderNo) {
		User user = (User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			//�û�δ��½
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "�����Ա��¼���ڽ��в�����");
		}
		//�ж��Ƿ��ǹ���Ա
		SverResponse<String> response = userService.isAdmin(user);
		if(response.isSuccess()) {
			return aOrderService.deliverGoods(orderNo);
		}else {
			return SverResponse.createByErrorMessage("�޲���Ȩ�ޣ�");
		}
	}
}
