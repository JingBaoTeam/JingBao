package cn.techaction.controller.portal;

<<<<<<< HEAD
=======


>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77
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
<<<<<<< HEAD
import cn.techaction.vo.ActionProductListVo;
=======
>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77

@Controller
@RequestMapping("/product")
public class ActionProductController {
<<<<<<< HEAD
=======
	@Autowired
	private ActionProductService productService;

	@RequestMapping(value="/findproducts.do")
	@ResponseBody
	public SverResponse<PageBean<ActionProduct>> searchProducts(Integer productId, Integer partsId, Integer pageNum,
			Integer pageSize) {
		return productService.findProducts(productId, partsId, pageNum, pageSize);
>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77

	@Autowired
	private ActionProductService aProductService;
	
	/**
	 * ∏˘æ›…Ã∆∑±‡∫≈£¨ªÒ»°…Ã∆∑œÍ«È
	 * @param session
	 * @param productId
	 * @return
	 */
	@RequestMapping("/getdetail.do")
    @ResponseBody
	public SverResponse<ActionProduct> getProductDetail(Integer productId){
		return aProductService.findProductDetailForPortal(productId);
	}
	
<<<<<<< HEAD
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
=======
	/**
	 * Ê†πÊçÆÂïÜÂìÅÁºñÂè∑ÔºåËé∑ÂèñÂïÜÂìÅËØ¶ÊÉÖ
	 * @param session
	 * @param productId
	 * @return
	 */
	@RequestMapping("/getdetail.do")
    @ResponseBody
	public SverResponse<ActionProduct> getProductDetail(Integer productId){
		return productService.findProductDetailForPortal(productId);
	}
<<<<<<< HEAD

=======
	@RequestMapping("/findhotproducts.do")
    @ResponseBody
	public SverResponse<List<ActionProduct>> findHotProducts(Integer num){
		return productService.findHotProducts(num);
	}
>>>>>>> cf09ffecd11d957bc038a14f9595dc154faacb5e
>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77
	
	
	@RequestMapping("/findfloors.do")
    @ResponseBody
	public SverResponse<ActionProductFloorVo> findFloorProducts(){
<<<<<<< HEAD
		return aProductService.findFloorProducts();
=======
		return productService.findFloorProducts();
	}
	


	@RequestMapping(value="/findhotproducts.do")
	@ResponseBody
	public SverResponse<List<ActionProduct>> findHotProducts(String num) {
		return productService.findHotProducts(num);

>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77
	}
	
}

