package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionProduct;

public interface ActionProductDao {
	/**
	 * 根据商品编号查找商品信息
	 * @param id		商品编号
	 * @return
	 */
<<<<<<< HEAD
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
=======
	public int getTotalCount(Integer productId, Integer partsId);/////////////////////////////////

>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77
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
<<<<<<< HEAD
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
=======
	public List<ActionProduct> findProductsByTypeId(Integer productId, Integer partsId, Integer startIndex,
			Integer pageSize);///////////////////////////////////////////////////////////


	/**
	 * 鏍规嵁鍟嗗搧缂栧彿鏌ユ壘鍟嗗搧淇℃伅
	 * @param productId		鍟嗗搧缂栧彿
	 * @return
	 */
	public ActionProduct findProductById(Integer productId);///////////////////////////////////////////
	/**
	 * 鏂板鍟嗗搧淇℃伅
	 * @param product	鍟嗗搧瀵硅薄
	 * @return
	 */
	public int insertProduct(ActionProduct product);//////////////////////////////
	/**
	 * 鏇存柊鍟嗗搧淇℃伅
	 * @param product	鍟嗗搧瀵硅薄
	 * @return
	 */
	public int updateProduct(ActionProduct product) ;/////////////////////////////////
	/**
	 * 鍒犻櫎鍟嗗搧淇℃伅
	 * @param id	鍟嗗搧ID
	 * @return
	 */
	public int deleteProductById(Integer id);///////////////////////////////
	//璇诲彇鎬昏褰曟暟锛屼负鍒嗛〉鍋氬噯澶�
	/**
	 * 鏍规嵁鏉′欢鏌ヨ鎬昏褰曟暟
	 * @param condition
	 * @return
	 */
	public Integer getTotalCount(ActionProduct condition);//////////////////////////
	/**
	 * 鏍规嵁鏉′欢鍒嗛〉鏌ヨ
	 * @param condition
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List<ActionProduct> findProducts(ActionProduct condition,int offset,int pageSize);/////////////////////////////
	/**
	 * 鏍规嵁鏉′欢鏌ヨ鍟嗗搧淇℃伅锛屼笉鍒嗛〉
	 * @param condition
	 * @return
	 */
	public List<ActionProduct> findProductsNoPage(ActionProduct conditon);/////////////////////////////////////////////////
	
	/**
	 * 鏌ユ壘鐑棬鍟嗗搧
	 * @return
	 */
	public List<ActionProduct> findHotProducts(String num);////////////////////////////////////////////////////////
	/**
	 * 鏍规嵁浜у搧绫诲瀷鏌ヨ鍟嗗搧淇℃伅
	 * @return
	 */
	public List<ActionProduct> findProductsByProductCategory(Integer categoryId);/////////////////////////////////////
>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77
}
