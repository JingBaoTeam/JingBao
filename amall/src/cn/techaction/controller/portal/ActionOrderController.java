package cn.techaction.controller.portal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.User;
import cn.techaction.service.ActionOrderService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionOrderVo;

@Controller
@RequestMapping("/order")
public class ActionOrderController {
	
	@Autowired
	private ActionOrderService aOrderService;
	
	
	
	@RequestMapping(value="/createorder.do",method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<ActionOrderVo> createOrder(HttpSession session,Integer addrId){
		User user = (User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后在进行操作！");
		}
		return aOrderService.generateOrder(user.getId(), addrId);
	}
	
	
	
    @RequestMapping("/getlist.do")
    @ResponseBody
    public SverResponse<PageBean<ActionOrderVo>> list(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,@RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
    	User user = (User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后在进行操作！");
		}
        return aOrderService.findOrders(user.getId(), pageNum, pageSize);
    }
    
    @RequestMapping("/cancelorder.do")
    @ResponseBody
    public SverResponse<String> cancelOrder(HttpSession session, Long orderNo){
    	User user = (User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后在进行操作！");
		}
        return aOrderService.cancelOrder(user.getId(),orderNo);
    }
    
    
    @RequestMapping("/getdetail.do")
    @ResponseBody
    public SverResponse<ActionOrderVo> getDetail(HttpSession session,Long orderNo){
    	User user = (User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后在进行操作！");
		}
        return aOrderService.findOrderDetail(user.getId(), orderNo);
    }
}
