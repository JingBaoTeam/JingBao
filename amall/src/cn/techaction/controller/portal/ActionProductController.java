package cn.techaction.controller.portal;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.service.ActionProductService;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionProductFloorVo;

@Controller
@RequestMapping("/products")
public class ActionProductController {
	@Autowired
	private ActionProductService productService;

	@RequestMapping(value="/findproducts.do")
	@ResponseBody
	public SverResponse<PageBean<ActionProduct>> searchProducts(Integer productId, Integer partsId, Integer pageNum,
			Integer pageSize) {
		return productService.findProducts(productId, partsId, pageNum, pageSize);

	}
	
	/**
	 * 根据商品编号，获取商品详情
	 * @param session
	 * @param productId
	 * @return
	 */
	@RequestMapping("/getdetail.do")
    @ResponseBody
	public SverResponse<ActionProduct> getProductDetail(Integer productId){
		return productService.findProductDetailForPortal(productId);
	}
	@RequestMapping("/findhotproducts.do")
    @ResponseBody
	public SverResponse<List<ActionProduct>> findHotProducts(Integer num){
		return productService.findHotProducts(num);
	}
	
	
	@RequestMapping("/findfloors.do")
    @ResponseBody
	public SverResponse<ActionProductFloorVo> findFloorProducts(){
		return productService.findFloorProducts();
	}
	

	@RequestMapping(value="/getdetail.do")
	@ResponseBody
	public SverResponse<ActionProduct> lookProducts(String productId) {
		return productService.looksProducts(productId);

	}
	@RequestMapping(value="/findhotproducts.do")
	@ResponseBody
	public SverResponse<List<ActionProduct>> hotProducts(String num) {
		return productService.gethotProducts(num);

	}
	
}

