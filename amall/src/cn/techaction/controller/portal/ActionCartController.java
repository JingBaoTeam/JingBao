package cn.techaction.controller.portal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionUser;
import cn.techaction.service.ActionCartService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.vo.ActionCartVo;

@Controller
@RequestMapping("/cart")
public class ActionCartController {
	
	@Autowired
	private ActionCartService aCartService;
	
	
	@RequestMapping(value="/savecart.do",method=RequestMethod.POST)
    @ResponseBody
	public SverResponse<String> addProduct2Cart(HttpSession session,Integer productId,Integer count){
		ActionUser user = (ActionUser)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后，在查看购物车！");
		}
		return aCartService.saveOrUpdate(user.getId(), productId, count);
	}
	
	@RequestMapping(value="/findallcarts.do")
    @ResponseBody
	public SverResponse<ActionCartVo> findAllCarts(HttpSession session){
		ActionUser user = (ActionUser)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后，在查看购物车！");
		}
		return aCartService.findAllCarts(user.getId());
	}
	
	
	@RequestMapping("/delcarts.do")
    @ResponseBody
	public SverResponse<ActionCartVo> deleteCart(HttpSession session,Integer productId){
		ActionUser user = (ActionUser)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后，在查看购物车！");
		}
		//删除购物车中的商品
		return aCartService.deleteCart(user.getId(), productId);
	}
	
	
	
	
	@RequestMapping("/updatecarts.do")
    @ResponseBody
	public SverResponse<ActionCartVo> updateCarts(HttpSession session,Integer productId,Integer count){
		ActionUser user = (ActionUser)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后，在查看购物车！");
		}
		//删除购物车中的商品
		return aCartService.updateCart(user.getId(), productId, count);
	}
	
	
	@RequestMapping("/clearcarts.do")
    @ResponseBody
	public SverResponse<String> clearCarts(HttpSession session){
		ActionUser user = (ActionUser)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后，在查看购物车！");
		}
		//清空购物车
		return aCartService.clearCart(user.getId());
	}
	
	@RequestMapping("/getcartcount.do")
    @ResponseBody
	public SverResponse<Integer> getCartsCount(HttpSession session){
		ActionUser user = (ActionUser)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后，在查看购物车！");
		}
		return aCartService.getCartCount(user.getId());
	}
}
