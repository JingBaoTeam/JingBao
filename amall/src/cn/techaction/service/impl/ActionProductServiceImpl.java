package cn.techaction.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionProductDao;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.service.ActionProductService;
import cn.techaction.utils.PageBean;

@Service
public class ActionProductServiceImpl implements ActionProductService {
	@Autowired
	private ActionProductDao productDao;

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

}
