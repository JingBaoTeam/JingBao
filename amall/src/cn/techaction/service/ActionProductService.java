package cn.techaction.service;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.utils.PageBean;

public interface ActionProductService {
	/**
	 * ��ѯ��Ʒ��Ϣ
	 * 
	 * @param productId
	 *            ��Ʒ���
	 * @param partsId
	 *            ������
	 * @param pageNum
	 *            ��ǰҳ��
	 * @param pageSize
	 * @return
	 */
	public SverResponse<PageBean<ActionProduct>> findProducts(Integer productId, Integer partsId, Integer pageNum,
			Integer pageSize);
}
