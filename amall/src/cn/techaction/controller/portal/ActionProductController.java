package cn.techaction.controller.portal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.service.ActionProductService;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionProductFloorVo;
import cn.techaction.vo.ActionProductListVo;

@Controller
@RequestMapping("/product")
public class ActionProductController {

	@Autowired
	private ActionProductService aProductService;
	
	/**
	 * 根据商品编号，获取商品详情
	 * @param session
	 * @param productId
	 * @return
	 */
	@RequestMapping("/getdetail.do")
    @ResponseBody
	public SverResponse<ActionProduct> getProductDetail(Integer productId){
		return aProductService.findProductDetailForPortal(productId);
	}
	
	@RequestMapping("/findproducts.do")
    @ResponseBody
	public SverResponse<PageBean<ActionProductListVo>> searchProducts(Integer productTypeId,
			Integer partsId, 
			@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
		return aProductService.findProductsForPortal(productTypeId,partsId, pageNum, pageSize);
	}
	
	
	@RequestMapping("/findhotproducts.do")
    @ResponseBody
	public SverResponse<List<ActionProduct>> findHotProducts(Integer num){
		return aProductService.findHotProducts(num);
	}
	
	
	@RequestMapping("/findfloors.do")
    @ResponseBody
	public SverResponse<ActionProductFloorVo> findFloorProducts(){
		return aProductService.findFloorProducts();
	}
	
}
