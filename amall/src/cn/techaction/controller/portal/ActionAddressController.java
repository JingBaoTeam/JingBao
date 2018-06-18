package cn.techaction.controller.portal;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionAddress;
import cn.techaction.pojo.User;
import cn.techaction.service.ActionAddrService;
import cn.techaction.utils.ConstUtil;

@Controller
@RequestMapping("/addr")
public class ActionAddressController {
	
	@Autowired
	private ActionAddrService aAddrService;
	
	@RequestMapping(value="/saveaddr.do",method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<String> saveAddress(HttpSession session,ActionAddress addr){
		User user = (User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后在进行操作！");
		}
		addr.setUid(user.getId());
		return aAddrService.addAddress(addr);
	}
	
	
	@RequestMapping("/deladdr.do")
	@ResponseBody
	public SverResponse<String> deleteAddress(HttpSession session,Integer id){
		User user = (User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后在进行操作！");
		}
		return aAddrService.delAddress(user.getId(),id);
	}
	
	@RequestMapping(value="/updateaddr.do",method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<String> updateAddress(HttpSession session,ActionAddress addr){
		User user = (User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后在进行操作！");
		}
		return aAddrService.updateAddress(addr);
	}
	
	@RequestMapping("/findaddrs.do")
	@ResponseBody
	public SverResponse<List<ActionAddress>> findaddrs(HttpSession session){
		User user = (User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后在进行操作！");
		}
		return aAddrService.findAddrsByUserId(user.getId());
	}
	
	@RequestMapping("/setdefault.do")
	@ResponseBody
	public SverResponse<String> setDefault(HttpSession session,Integer id){
		User user = (User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后在进行操作！");
		}
		return aAddrService.updateAddrDefaultStatus(user.getId(), id);
	}
}
