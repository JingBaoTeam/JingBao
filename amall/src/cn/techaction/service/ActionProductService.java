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
	 * 将商品加入购物车
	 * @param product
	 * @return
	 */
	public SverResponse<String> saveOrUpdateProduct(ActionProduct product);
	/**
	 * 更新商品的状态信息
	 * @param productId	商品编号
	 * @param status	状态：1-代售，2上架，3下架
	 * @return
	 */
	public SverResponse<String> updateStatus(Integer productId,Integer status);
	
	/**
	 * 更新商品是否为热销状态
	 * @param productId 商品编号
	 * @param isHot		热销状态：1-热销，2-非热销
	 * @return
	 */
	public SverResponse<String> updateHotStatus(Integer productId,Integer isHot);
	/**
	 * 读取商品详情信息
	 * @param productId	商品编号
	 * @return
	 */
	public SverResponse<ActionProduct> findProductDetailById(Integer productId);
	/**
	 * 分页查询商品信息
	 * @param product	查询条件
	 * @param pageNum	当前页码
	 * @param pageSize	页面大小
	 * @return
	 */
	public SverResponse<PageBean<ActionProductListVo>> findProductsByCondition(ActionProduct product,int pageNum,int pageSize);
	
	/**
	 * 查询符合条件的商品信息，不分页
	 * @param product
	 * @return
	 */
	public SverResponse<List<ActionProductListVo>> findProduts(ActionProduct product);
	
	/**
	 * 图片上传
	 * @param file
	 * @param path
	 * @return
	 */
	public SverResponse<Map<String, String>> uploadFile(MultipartFile file,String path);
	
	//前台商城需要的商品详情信息
	/**
	 * 门户：根据商品编号查找商品详情
	 * @param productId
	 * @return
	 */
	public SverResponse<ActionProduct> findProductDetailForPortal(Integer productId);
	/**
	 * 门户：根据产品类型和配件类型查找商品信息
	 * @param productTypeId
	 * @param partsId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
<<<<<<< HEAD
	public SverResponse<PageBean<ActionProductListVo>> findProductsForPortal(Integer productTypeId,Integer partsId, int pageNum,int pageSize);
	/**
	 * 门户：查找热门商品
	 * @param num    查询商品的数量
	 * @return
	 */
	public SverResponse<List<ActionProduct>> findHotProducts(Integer num);
	/**
	 * 门户：获取首页所有楼层数据
=======
	public SverResponse<PageBean<ActionProduct>> findProducts(Integer productId, Integer partsId, Integer pageNum,
			Integer pageSize);
	
	/**
	 * 灏嗗晢鍝佸姞鍏ヨ喘鐗╄溅
	 * @param product
	 * @return
	 */
	public SverResponse<String> saveOrUpdateProduct(ActionProduct product);
	/**
	 * 鏇存柊鍟嗗搧鐨勭姸鎬佷俊鎭�
	 * @param productId	鍟嗗搧缂栧彿
	 * @param status	鐘舵�侊細1-浠ｅ敭锛�2涓婃灦锛�3涓嬫灦
	 * @return
	 */
	public SverResponse<String> updateStatus(Integer productId,Integer status);
	
	/**
	 * 鏇存柊鍟嗗搧鏄惁涓虹儹閿�鐘舵��
	 * @param productId 鍟嗗搧缂栧彿
	 * @param isHot		鐑攢鐘舵�侊細1-鐑攢锛�2-闈炵儹閿�
	 * @return
	 */
	public SverResponse<String> updateHotStatus(Integer productId,Integer isHot);
	/**
	 * 璇诲彇鍟嗗搧璇︽儏淇℃伅
	 * @param productId	鍟嗗搧缂栧彿
	 * @return
	 */
	public SverResponse<ActionProduct> findProductDetailById(Integer productId);
	/**
	 * 鍒嗛〉鏌ヨ鍟嗗搧淇℃伅
	 * @param product	鏌ヨ鏉′欢
	 * @param pageNum	褰撳墠椤电爜
	 * @param pageSize	椤甸潰澶у皬
	 * @return
	 */
	public SverResponse<PageBean<ActionProductListVo>> findProductsByCondition(ActionProduct product,int pageNum,int pageSize);
	
	/**
	 * 鏌ヨ绗﹀悎鏉′欢鐨勫晢鍝佷俊鎭紝涓嶅垎椤�
	 * @param product
	 * @return
	 */
	public SverResponse<List<ActionProductListVo>> findProduts(ActionProduct product);
	
	/**
	 * 鍥剧墖涓婁紶
	 * @param file
	 * @param path
	 * @return
	 */
	public SverResponse<Map<String, String>> uploadFile(MultipartFile file,String path);
	
	//鍓嶅彴鍟嗗煄闇�瑕佺殑鍟嗗搧璇︽儏淇℃伅
	/**
	 * 闂ㄦ埛锛氭牴鎹晢鍝佺紪鍙锋煡鎵惧晢鍝佽鎯�
	 * @param productId
	 * @return
	 */
	public SverResponse<ActionProduct> findProductDetailForPortal(Integer productId);
	/**
	 * 闂ㄦ埛锛氭牴鎹骇鍝佺被鍨嬪拰閰嶄欢绫诲瀷鏌ユ壘鍟嗗搧淇℃伅
	 * @param productTypeId
	 * @param partsId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public SverResponse<PageBean<ActionProductListVo>> findProductsForPortal(Integer productTypeId,Integer partsId, int pageNum,int pageSize);
	/**
	 * 闂ㄦ埛锛氭煡鎵剧儹闂ㄥ晢鍝�
	 * @param num    鏌ヨ鍟嗗搧鐨勬暟閲�
	 * @return
	 */
	public SverResponse<List<ActionProduct>> findHotProducts(String num);
	/**
	 * 闂ㄦ埛锛氳幏鍙栭椤垫墍鏈夋ゼ灞傛暟鎹�
>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77
	 * @return
	 */
	public SverResponse<ActionProductFloorVo> findFloorProducts();
}
