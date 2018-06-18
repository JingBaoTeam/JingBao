package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionProduct;

public interface ActionProductDao {
	/**
	 * ������Ʒ��Ų�����Ʒ��Ϣ
	 * @param id		��Ʒ���
	 * @return
	 */
	public ActionProduct findProductById(Integer id);
	/**
	 * ������Ʒ��Ϣ
	 * @param product	��Ʒ����
	 * @return
	 */
	public int insertProduct(ActionProduct product);
	/**
	 * ������Ʒ��Ϣ
	 * @param product	��Ʒ����
	 * @return
	 */
	public int updateProduct(ActionProduct product) ;
	/**
	 * ɾ����Ʒ��Ϣ
	 * @param id	��ƷID
	 * @return
	 */
	public int deleteProductById(Integer id);
	//��ȡ�ܼ�¼����Ϊ��ҳ��׼��
	/**
	 * ����������ѯ�ܼ�¼��
	 * @param condition
	 * @return
	 */
	public Integer getTotalCount(ActionProduct condition);
	/**
	 * ����������ҳ��ѯ
	 * @param condition
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List<ActionProduct> findProducts(ActionProduct condition,int offset,int pageSize);
	/**
	 * ����������ѯ��Ʒ��Ϣ������ҳ
	 * @param condition
	 * @return
	 */
	public List<ActionProduct> findProductsNoPage(ActionProduct conditon);
	
	/**
	 * ����������Ʒ
	 * @return
	 */
	public List<ActionProduct> findHotProducts(Integer num);
	/**
	 * ���ݲ�Ʒ���Ͳ�ѯ��Ʒ��Ϣ
	 * @return
	 */
	public List<ActionProduct> findProductsByProductCategory(Integer categoryId);
}
