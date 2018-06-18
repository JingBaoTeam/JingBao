package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionProduct;

public interface ActionProductDao {
	/**
	 * 查询符合条件的总记录数
	 * 
	 * @param productId
	 * @param partsId
	 * @return
	 */
	public int getTotalCount(Integer productId, Integer partsId);

	/**
	 * 分页查询
	 * 
	 * @param productId
	 * @param partsId
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<ActionProduct> findProductsByTypeId(Integer productId, Integer partsId, Integer startIndex,
			Integer pageSize);

	//@author ireson
	/**
	 * 根据商品编号查找商品信息
	 * @param id		商品编号
	 * @return
	 */
	public ActionProduct findProductById(Integer id);
	/**
	 * 新增商品信息
	 * @param product	商品对象
	 * @return
	 */
	public int insertProduct(ActionProduct product);
	/**
	 * 更新商品信息
	 * @param product	商品对象
	 * @return
	 */
	public int updateProduct(ActionProduct product) ;
	/**
	 * 删除商品信息
	 * @param id	商品ID
	 * @return
	 */
	public int deleteProductById(Integer id);
	//读取总记录数，为分页做准备
	/**
	 * 根据条件查询总记录数
	 * @param condition
	 * @return
	 */
	public Integer getTotalCount(ActionProduct condition);
	/**
	 * 根据条件分页查询
	 * @param condition
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List<ActionProduct> findProducts(ActionProduct condition,int offset,int pageSize);
	/**
	 * 根据条件查询商品信息，不分页
	 * @param condition
	 * @return
	 */
	public List<ActionProduct> findProductsNoPage(ActionProduct conditon);
	
	/**
	 * 查找热门商品
	 * @return
	 */
	public List<ActionProduct> findHotProducts(Integer num);
	/**
	 * 根据产品类型查询商品信息
	 * @return
	 */
	public List<ActionProduct> findProductsByProductCategory(Integer categoryId);
}
