package cn.techaction.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionProductDao;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.service.ActionProductService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.utils.PageBean;

@Service
public class ActionProductServiceImpl implements ActionProductService {
	@Autowired
	private ActionProductDao productDao;
	@Autowired
	private ActionProductDao aParamsDao;
	@Override
	public SverResponse<String> saveOrUpdateProduct(ActionProduct product)  {
		if(product!=null) {
			//将图组中第一个置位主图
			if(StringUtils.isNoneBlank(product.getSubImages())) {
				String[] array = product.getSubImages().split(",");
				//JSONArray jsonArray = new JSONArray(product.getSubImages());
				product.setIconUrl(array[0]);
			}
			
			if(product.getId() != null) {
				product.setUpdated(new Date());
				int rs = productDao.updateProduct(product);
				if(rs >0) {
					return SverResponse.createRespBySuccessMessage("产品更新成功！");
				}
				return SverResponse.createByErrorMessage("产品更新失败！");
			}else {
				//新增
				product.setStatus(1);  //商品的状态：1-待售，刚保存；2-上架，在售；3-下架，停售
				product.setHot(2);   //是否热销，1-是，2-否
				product.setUpdated(new Date());
				product.setCreated(new Date());
				int rs = productDao.insertProduct(product);
				if(rs >0) {
					return SverResponse.createRespBySuccessMessage("产品新增成功！");
				}
				return SverResponse.createByErrorMessage("产品新增失败！");
			}
		}
		return SverResponse.createByErrorMessage("产品参数错误！");
	}
	@Override
	public SverResponse<String> updateStatus(Integer productId, Integer status) {
		if(productId==null || status==null) {
			return SverResponse.createByErrorMessage("非法参数！");
		}
		ActionProduct product = new ActionProduct();
		product.setId(productId);
		product.setStatus(status);
		product.setUpdated(new Date());
		int rs = productDao.updateProduct(product);
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("修改产品状态成功！");
		}
		return SverResponse.createByErrorMessage("修改产品状态失败！");
	}

	@Override
	public SverResponse<String> updateHotStatus(Integer productId, Integer isHot) {
		if(productId==null || isHot==null) {
			return SverResponse.createByErrorMessage("非法参数！");
		}
		ActionProduct product = new ActionProduct();
		product.setId(productId);
		product.setHot(isHot);
		product.setUpdated(new Date());
		int rs = productDao.updateProduct(product);
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("修改产品状态成功！");
		}
		return SverResponse.createByErrorMessage("修改产品状态失败！");
	}

	@Override
	public SverResponse<PageBean<ActionProduct>> findProducts(Integer productId, Integer partsId, Integer pageNum,
			Integer pageSize) {
		// TODO Auto-generated method stub
		// 查找符合条件的总记录数
		int totalRecord = productDao.getTotalCount(productId, partsId);
		PageBean<ActionProduct> pageBean = new PageBean<>(pageNum, pageSize, totalRecord);
		// 查询符合条件的数据
		List<ActionProduct> data = productDao.findProductsByTypeId(productId, partsId, pageBean.getStartIndex(),
				pageBean.getPageSize());
		 pageBean.setData(data);

		return SverResponse.createRespBySuccess(pageBean);
	}

	@Override
	public SverResponse<ActionProduct> looksProducts(String productId) {
		ActionProduct product = productDao.findProductById(productId);
		return SverResponse.createRespBySuccess(product);
	}
	public SverResponse<List<ActionProduct>> gethotProducts(String num) {
		List<ActionProduct> products = productDao.findhotsProducts(num);
		if(products.size()<5) {
			return SverResponse.createByErrorMessage("尚未设置热销商品！");
		}
		return SverResponse.createRespBySuccess(products);
	}

}
