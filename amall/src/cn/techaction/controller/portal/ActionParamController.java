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
import cn.techaction.pojo.ActionParam;
import cn.techaction.service.ActionParamsService;

@Controller
@RequestMapping("/param")
public class ActionParamController {

	@Autowired
	private ActionParamsService aParamService;
	
	@RequestMapping(value="/findallparams.do",method=RequestMethod.GET)
	@ResponseBody
	public SverResponse<List<ActionParam>> saveAddress(HttpSession session,ActionAddress addr){
		
		return aParamService.findAllParams();
	} 
}
