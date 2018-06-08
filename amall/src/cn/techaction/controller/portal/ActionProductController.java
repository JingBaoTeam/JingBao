package cn.techaction.controller.portal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.service.ActionProductService;
import cn.techaction.utils.PageBean;

@Controller
@RequestMapping("/products")
public class ActionProductController {
	@Autowired
	private ActionProductService productService;

	@RequestMapping("/find_products.do")
	@ResponseBody
	public SverResponse<PageBean<ActionProduct>> searchProducts(Integer productId, Integer partsId, Integer pageNum,
			Integer pageSize) {

		return productService.findProducts(productId, partsId, pageNum, pageSize);

	}

}
