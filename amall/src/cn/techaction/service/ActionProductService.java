package cn.techaction.service;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.utils.PageBean;

public interface ActionProductService {
	/**
	 * 查询商品信息
	 * 
	 * @param productId
	 *            产品编号
	 * @param partsId
	 *            配件编号
	 * @param pageNum
	 *            当前页码
	 * @param pageSize
	 * @return
	 */
	public SverResponse<PageBean<ActionProduct>> findProducts(Integer productId, Integer partsId, Integer pageNum,
			Integer pageSize);
}
