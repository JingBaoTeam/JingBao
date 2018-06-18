package cn.techaction.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionProductFloorVo;
import cn.techaction.vo.ActionProductListVo;

public interface ActionProductService {

	/**
	 * ½«ÉÌÆ·¼ÓÈë¹ºÎï³µ
	 * @param product
	 * @return
	 */
	public SverResponse<String> saveOrUpdateProduct(ActionProduct product);
	/**
	 * ¸üĞÂÉÌÆ·µÄ×´Ì¬ĞÅÏ¢
	 * @param productId	ÉÌÆ·±àºÅ
	 * @param status	×´Ì¬£º1-´úÊÛ£¬2ÉÏ¼Ü£¬3ÏÂ¼Ü
	 * @return
	 */
	public SverResponse<String> updateStatus(Integer productId,Integer status);
	
	/**
	 * ¸üĞÂÉÌÆ·ÊÇ·ñÎªÈÈÏú×´Ì¬
	 * @param productId ÉÌÆ·±àºÅ
	 * @param isHot		ÈÈÏú×´Ì¬£º1-ÈÈÏú£¬2-·ÇÈÈÏú
	 * @return
	 */
	public SverResponse<String> updateHotStatus(Integer productId,Integer isHot);
	/**
	 * ¶ÁÈ¡ÉÌÆ·ÏêÇéĞÅÏ¢
	 * @param productId	ÉÌÆ·±àºÅ
	 * @return
	 */
	public SverResponse<ActionProduct> findProductDetailById(Integer productId);
	/**
	 * ·ÖÒ³²éÑ¯ÉÌÆ·ĞÅÏ¢
	 * @param product	²éÑ¯Ìõ¼ş
	 * @param pageNum	µ±Ç°Ò³Âë
	 * @param pageSize	Ò³Ãæ´óĞ¡
	 * @return
	 */
	public SverResponse<PageBean<ActionProductListVo>> findProductsByCondition(ActionProduct product,int pageNum,int pageSize);
	
	/**
	 * ²éÑ¯·ûºÏÌõ¼şµÄÉÌÆ·ĞÅÏ¢£¬²»·ÖÒ³
	 * @param product
	 * @return
	 */
	public SverResponse<List<ActionProductListVo>> findProduts(ActionProduct product);
	
	/**
	 * Í¼Æ¬ÉÏ´«
	 * @param file
	 * @param path
	 * @return
	 */
	public SverResponse<Map<String, String>> uploadFile(MultipartFile file,String path);
	
	//Ç°Ì¨ÉÌ³ÇĞèÒªµÄÉÌÆ·ÏêÇéĞÅÏ¢
	/**
	 * ÃÅ»§£º¸ù¾İÉÌÆ·±àºÅ²éÕÒÉÌÆ·ÏêÇé
	 * @param productId
	 * @return
	 */
	public SverResponse<ActionProduct> findProductDetailForPortal(Integer productId);
	/**
	 * ÃÅ»§£º¸ù¾İ²úÆ·ÀàĞÍºÍÅä¼şÀàĞÍ²éÕÒÉÌÆ·ĞÅÏ¢
	 * @param productTypeId
	 * @param partsId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
<<<<<<< HEAD
	public SverResponse<PageBean<ActionProductListVo>> findProductsForPortal(Integer productTypeId,Integer partsId, int pageNum,int pageSize);
	/**
	 * ÃÅ»§£º²éÕÒÈÈÃÅÉÌÆ·
	 * @param num    ²éÑ¯ÉÌÆ·µÄÊıÁ¿
	 * @return
	 */
	public SverResponse<List<ActionProduct>> findHotProducts(Integer num);
	/**
	 * ÃÅ»§£º»ñÈ¡Ê×Ò³ËùÓĞÂ¥²ãÊı¾İ
=======
	public SverResponse<PageBean<ActionProduct>> findProducts(Integer productId, Integer partsId, Integer pageNum,
			Integer pageSize);
	
	/**
	 * å°†å•†å“åŠ å…¥è´­ç‰©è½¦
	 * @param product
	 * @return
	 */
	public SverResponse<String> saveOrUpdateProduct(ActionProduct product);
	/**
	 * æ›´æ–°å•†å“çš„çŠ¶æ€ä¿¡æ¯
	 * @param productId	å•†å“ç¼–å·
	 * @param status	çŠ¶æ€ï¼š1-ä»£å”®ï¼Œ2ä¸Šæ¶ï¼Œ3ä¸‹æ¶
	 * @return
	 */
	public SverResponse<String> updateStatus(Integer productId,Integer status);
	
	/**
	 * æ›´æ–°å•†å“æ˜¯å¦ä¸ºçƒ­é”€çŠ¶æ€
	 * @param productId å•†å“ç¼–å·
	 * @param isHot		çƒ­é”€çŠ¶æ€ï¼š1-çƒ­é”€ï¼Œ2-éçƒ­é”€
	 * @return
	 */
	public SverResponse<String> updateHotStatus(Integer productId,Integer isHot);
	/**
	 * è¯»å–å•†å“è¯¦æƒ…ä¿¡æ¯
	 * @param productId	å•†å“ç¼–å·
	 * @return
	 */
	public SverResponse<ActionProduct> findProductDetailById(Integer productId);
	/**
	 * åˆ†é¡µæŸ¥è¯¢å•†å“ä¿¡æ¯
	 * @param product	æŸ¥è¯¢æ¡ä»¶
	 * @param pageNum	å½“å‰é¡µç 
	 * @param pageSize	é¡µé¢å¤§å°
	 * @return
	 */
	public SverResponse<PageBean<ActionProductListVo>> findProductsByCondition(ActionProduct product,int pageNum,int pageSize);
	
	/**
	 * æŸ¥è¯¢ç¬¦åˆæ¡ä»¶çš„å•†å“ä¿¡æ¯ï¼Œä¸åˆ†é¡µ
	 * @param product
	 * @return
	 */
	public SverResponse<List<ActionProductListVo>> findProduts(ActionProduct product);
	
	/**
	 * å›¾ç‰‡ä¸Šä¼ 
	 * @param file
	 * @param path
	 * @return
	 */
	public SverResponse<Map<String, String>> uploadFile(MultipartFile file,String path);
	
	//å‰å°å•†åŸéœ€è¦çš„å•†å“è¯¦æƒ…ä¿¡æ¯
	/**
	 * é—¨æˆ·ï¼šæ ¹æ®å•†å“ç¼–å·æŸ¥æ‰¾å•†å“è¯¦æƒ…
	 * @param productId
	 * @return
	 */
	public SverResponse<ActionProduct> findProductDetailForPortal(Integer productId);
	/**
	 * é—¨æˆ·ï¼šæ ¹æ®äº§å“ç±»å‹å’Œé…ä»¶ç±»å‹æŸ¥æ‰¾å•†å“ä¿¡æ¯
	 * @param productTypeId
	 * @param partsId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public SverResponse<PageBean<ActionProductListVo>> findProductsForPortal(Integer productTypeId,Integer partsId, int pageNum,int pageSize);
	/**
	 * é—¨æˆ·ï¼šæŸ¥æ‰¾çƒ­é—¨å•†å“
	 * @param num    æŸ¥è¯¢å•†å“çš„æ•°é‡
	 * @return
	 */
	public SverResponse<List<ActionProduct>> findHotProducts(String num);
	/**
	 * é—¨æˆ·ï¼šè·å–é¦–é¡µæ‰€æœ‰æ¥¼å±‚æ•°æ®
>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77
	 * @return
	 */
	public SverResponse<ActionProductFloorVo> findFloorProducts();
}
