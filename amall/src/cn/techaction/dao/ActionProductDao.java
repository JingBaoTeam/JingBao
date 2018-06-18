package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionProduct;

public interface ActionProductDao {
	/**
	 * ������Ʒ��Ų�����Ʒ��Ϣ
	 * @param id		��Ʒ���
	 * @return
	 */
<<<<<<< HEAD
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
=======
	public int getTotalCount(Integer productId, Integer partsId);/////////////////////////////////

>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77
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
<<<<<<< HEAD
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
=======
	public List<ActionProduct> findProductsByTypeId(Integer productId, Integer partsId, Integer startIndex,
			Integer pageSize);///////////////////////////////////////////////////////////


	/**
	 * 根据商品编号查找商品信息
	 * @param productId		商品编号
	 * @return
	 */
	public ActionProduct findProductById(Integer productId);///////////////////////////////////////////
	/**
	 * 新增商品信息
	 * @param product	商品对象
	 * @return
	 */
	public int insertProduct(ActionProduct product);//////////////////////////////
	/**
	 * 更新商品信息
	 * @param product	商品对象
	 * @return
	 */
	public int updateProduct(ActionProduct product) ;/////////////////////////////////
	/**
	 * 删除商品信息
	 * @param id	商品ID
	 * @return
	 */
	public int deleteProductById(Integer id);///////////////////////////////
	//读取总记录数，为分页做准备
	/**
	 * 根据条件查询总记录数
	 * @param condition
	 * @return
	 */
	public Integer getTotalCount(ActionProduct condition);//////////////////////////
	/**
	 * 根据条件分页查询
	 * @param condition
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List<ActionProduct> findProducts(ActionProduct condition,int offset,int pageSize);/////////////////////////////
	/**
	 * 根据条件查询商品信息，不分页
	 * @param condition
	 * @return
	 */
	public List<ActionProduct> findProductsNoPage(ActionProduct conditon);/////////////////////////////////////////////////
	
	/**
	 * 查找热门商品
	 * @return
	 */
	public List<ActionProduct> findHotProducts(String num);////////////////////////////////////////////////////////
	/**
	 * 根据产品类型查询商品信息
	 * @return
	 */
	public List<ActionProduct> findProductsByProductCategory(Integer categoryId);/////////////////////////////////////
>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77
}
