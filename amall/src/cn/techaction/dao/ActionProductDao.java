package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionProduct;

public interface ActionProductDao {
	/**
	 * ¸ù¾İÉÌÆ·±àºÅ²éÕÒÉÌÆ·ĞÅÏ¢
	 * @param id		ÉÌÆ·±àºÅ
	 * @return
	 */
<<<<<<< HEAD
	public ActionProduct findProductById(Integer id);
	/**
	 * ĞÂÔöÉÌÆ·ĞÅÏ¢
	 * @param product	ÉÌÆ·¶ÔÏó
	 * @return
	 */
	public int insertProduct(ActionProduct product);
	/**
	 * ¸üĞÂÉÌÆ·ĞÅÏ¢
	 * @param product	ÉÌÆ·¶ÔÏó
	 * @return
	 */
	public int updateProduct(ActionProduct product) ;
	/**
	 * É¾³ıÉÌÆ·ĞÅÏ¢
	 * @param id	ÉÌÆ·ID
	 * @return
	 */
	public int deleteProductById(Integer id);
	//¶ÁÈ¡×Ü¼ÇÂ¼Êı£¬Îª·ÖÒ³×ö×¼±¸
=======
	public int getTotalCount(Integer productId, Integer partsId);/////////////////////////////////

>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77
	/**
	 * ¸ù¾İÌõ¼ş²éÑ¯×Ü¼ÇÂ¼Êı
	 * @param condition
	 * @return
	 */
	public Integer getTotalCount(ActionProduct condition);
	/**
	 * ¸ù¾İÌõ¼ş·ÖÒ³²éÑ¯
	 * @param condition
	 * @param offset
	 * @param pageSize
	 * @return
	 */
<<<<<<< HEAD
	public List<ActionProduct> findProducts(ActionProduct condition,int offset,int pageSize);
	/**
	 * ¸ù¾İÌõ¼ş²éÑ¯ÉÌÆ·ĞÅÏ¢£¬²»·ÖÒ³
	 * @param condition
	 * @return
	 */
	public List<ActionProduct> findProductsNoPage(ActionProduct conditon);
	
	/**
	 * ²éÕÒÈÈÃÅÉÌÆ·
	 * @return
	 */
	public List<ActionProduct> findHotProducts(Integer num);
	/**
	 * ¸ù¾İ²úÆ·ÀàĞÍ²éÑ¯ÉÌÆ·ĞÅÏ¢
	 * @return
	 */
	public List<ActionProduct> findProductsByProductCategory(Integer categoryId);
=======
	public List<ActionProduct> findProductsByTypeId(Integer productId, Integer partsId, Integer startIndex,
			Integer pageSize);///////////////////////////////////////////////////////////


	/**
	 * æ ¹æ®å•†å“ç¼–å·æŸ¥æ‰¾å•†å“ä¿¡æ¯
	 * @param productId		å•†å“ç¼–å·
	 * @return
	 */
	public ActionProduct findProductById(Integer productId);///////////////////////////////////////////
	/**
	 * æ–°å¢å•†å“ä¿¡æ¯
	 * @param product	å•†å“å¯¹è±¡
	 * @return
	 */
	public int insertProduct(ActionProduct product);//////////////////////////////
	/**
	 * æ›´æ–°å•†å“ä¿¡æ¯
	 * @param product	å•†å“å¯¹è±¡
	 * @return
	 */
	public int updateProduct(ActionProduct product) ;/////////////////////////////////
	/**
	 * åˆ é™¤å•†å“ä¿¡æ¯
	 * @param id	å•†å“ID
	 * @return
	 */
	public int deleteProductById(Integer id);///////////////////////////////
	//è¯»å–æ€»è®°å½•æ•°ï¼Œä¸ºåˆ†é¡µåšå‡†å¤‡
	/**
	 * æ ¹æ®æ¡ä»¶æŸ¥è¯¢æ€»è®°å½•æ•°
	 * @param condition
	 * @return
	 */
	public Integer getTotalCount(ActionProduct condition);//////////////////////////
	/**
	 * æ ¹æ®æ¡ä»¶åˆ†é¡µæŸ¥è¯¢
	 * @param condition
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List<ActionProduct> findProducts(ActionProduct condition,int offset,int pageSize);/////////////////////////////
	/**
	 * æ ¹æ®æ¡ä»¶æŸ¥è¯¢å•†å“ä¿¡æ¯ï¼Œä¸åˆ†é¡µ
	 * @param condition
	 * @return
	 */
	public List<ActionProduct> findProductsNoPage(ActionProduct conditon);/////////////////////////////////////////////////
	
	/**
	 * æŸ¥æ‰¾çƒ­é—¨å•†å“
	 * @return
	 */
	public List<ActionProduct> findHotProducts(String num);////////////////////////////////////////////////////////
	/**
	 * æ ¹æ®äº§å“ç±»å‹æŸ¥è¯¢å•†å“ä¿¡æ¯
	 * @return
	 */
	public List<ActionProduct> findProductsByProductCategory(Integer categoryId);/////////////////////////////////////
>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77
}
