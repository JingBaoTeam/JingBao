package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionProduct;

public interface ActionProductDao {
	/**
	 * ��ѯ�����������ܼ�¼��
	 * 
	 * @param productId
	 * @param partsId
	 * @return
	 */
	public int getTotalCount(Integer productId, Integer partsId);

	/**
	 * ��ҳ��ѯ
	 * 
	 * @param productId
	 * @param partsId
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<ActionProduct> findProductsByTypeId(Integer productId, Integer partsId, Integer startIndex,
			Integer pageSize);

	public ActionProduct findProductById(Integer productId);
}
