package cn.techaction.controller.backstage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.techaction.common.ResponseCode;
import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.pojo.User;
import cn.techaction.service.ActionProductService;
import cn.techaction.service.UserService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionProductListVo;

@Controller
@RequestMapping("/mgr/product")
public class ActionProductBackController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ActionProductService aProductService;
	
	
	/**
	 * 新增商品信息
	 * @param session
	 * @param product
	 * @return
	 */
	@RequestMapping(value="/saveproduct.do",method=RequestMethod.POST)
    @ResponseBody
	public SverResponse<String> saveProduct(HttpSession session,ActionProduct product){
		User user = (User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			//用户未登陆
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后在进行操作！");
		}
		//判断是否是管理员
		SverResponse<String> response = userService.isAdmin(user);
		if(response.isSuccess()) {
			//新增类型业务
			return aProductService.saveOrUpdateProduct(product);
		}else {
			return SverResponse.createByErrorMessage("无操作权限！");
		}
	}

	/**
	 * 修改商品状态：上架/下架
	 * @param session
	 * @param productId
	 * @param status
	 * @return
	 */
	@RequestMapping("/setstatus.do")
    @ResponseBody
	public SverResponse<String> modifyStatus(HttpSession session,Integer productId,Integer status){
		User user = (User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			//用户未登陆
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后在进行操作！");
		}
		//判断是否是管理员
		SverResponse<String> response = userService.isAdmin(user);
		if(response.isSuccess()) {
			//新增类型业务
			try {
				return aProductService.updateStatus(productId, status);
			} catch (Exception e) {
				e.printStackTrace();
				return SverResponse.createByErrorMessage("商品更新失败！");
			}
		}else {
			return SverResponse.createByErrorMessage("无操作权限！");
		}
	}
	/**
	 * 设置商品为热销商品
	 * @param session
	 * @param product
	 * @return
	 */
	@RequestMapping(value="/sethot.do")
    @ResponseBody
	public SverResponse<String> setHot(HttpSession session,Integer productId,Integer hot){
		User user = (User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			//用户未登陆
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后在进行操作！");
		}
		//判断是否是管理员
		SverResponse<String> response = userService.isAdmin(user);
		if(response.isSuccess()) {
			//新增类型业务
			return aProductService.updateHotStatus(productId, hot);
		}else {
			return SverResponse.createByErrorMessage("无操作权限！");
		}
	}
	
	/**
	 * 根据商品编号，获取商品详情
	 * @param session
	 * @param productId
	 * @return
	 */
	@RequestMapping("/getdetail.do")
    @ResponseBody
	public SverResponse<ActionProduct> getProductDetail(HttpSession session,Integer productId){
		User user = (User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			//用户未登陆
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后在进行操作！");
		}
		//判断是否是管理员
		SverResponse<String> response = userService.isAdmin(user);
		if(response.isSuccess()) {
			//新增类型业务
			return aProductService.findProductDetailById(productId);
		}else {
			return SverResponse.createByErrorMessage("无操作权限！");
		}
	}
	
	
	
	
	
	@RequestMapping("/searchproducts.do")
    @ResponseBody
	public SverResponse<PageBean<ActionProductListVo>> searchProducts(HttpSession session,ActionProduct product , @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,@RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
		User user = (User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			//用户未登陆
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后在进行操作！");
		}
		//判断是否是管理员
		SverResponse<String> response = userService.isAdmin(user);
		if(response.isSuccess()) {
			//新增类型业务
			return aProductService.findProductsByCondition(product, pageNum, pageSize);
		}else {
			return SverResponse.createByErrorMessage("无操作权限！");
		}
	}
	
	
	@RequestMapping(value="/upload.do",method=RequestMethod.POST)
    @ResponseBody
	public SverResponse<Map<String, String>> uploadFiles(HttpSession session,@RequestParam(value = "file",required = false) MultipartFile file,HttpServletRequest request){
		User user = (User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			//用户未登陆
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后在进行操作！");
		}
		//判断是否是管理员
		SverResponse<String> response = userService.isAdmin(user);
		if(response.isSuccess()) {
			  String path = request.getSession().getServletContext().getRealPath(ConstUtil.UPLOAD_IMAGES_PATH);
	          System.out.println(request.getContextPath());
	          return aProductService.uploadFile(file, path);
		}else {
			return SverResponse.createByErrorMessage("无操作权限！");
		}
	}
	
	
	@RequestMapping(value="/pic_upload.do",method=RequestMethod.POST)
    @ResponseBody
	public Map<String, String> editorUploadFiles(HttpSession session,@RequestParam(value = "files",required = false) MultipartFile file,HttpServletRequest request){
		User user = (User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			//用户未登陆
			return null;
		}
		//判断是否是管理员
		SverResponse<String> response = userService.isAdmin(user);
		Map<String, String> result = new HashMap<>();
		result.put("success", "false");
		if(response.isSuccess()) {
			String path = request.getSession().getServletContext().getRealPath(ConstUtil.UPLOAD_IMAGES_PATH);
	        SverResponse<Map<String, String>> resp= aProductService.uploadFile(file, path);
	        
	        if(resp.isSuccess()) {
	        	result.put("success", "true");
	        	result.put("file_path", request.getContextPath()+resp.getData().get("url"));
	        }
	        return result;
		}else {
			return result;
		}
	}
	
	@RequestMapping("/productlist.do")
    @ResponseBody
	public SverResponse<List<ActionProductListVo>> findProducts(HttpSession session,ActionProduct product){
		User user = (User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			//用户未登陆
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后在进行操作！");
		}
		//判断是否是管理员
		SverResponse<String> response = userService.isAdmin(user);
		if(response.isSuccess()) {
			//新增类型业务
			return aProductService.findProduts(product);
		}else {
			return SverResponse.createByErrorMessage("无操作权限！");
		}
	}
	
}
