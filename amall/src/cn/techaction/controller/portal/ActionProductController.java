package cn.techaction.controller.portal;

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

