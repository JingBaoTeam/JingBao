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
	 * 查询商品信息
	 * 
	 * @param productId
	 *            产品编号
	 * @param partsId
	 *            配件编号
	 * @param pageNum
	 *            当前页码
	 * @param pageSize
	 * @return
	 */
	public SverResponse<PageBean<ActionProduct>> findProducts(Integer productId, Integer partsId, Integer pageNum,
			Integer pageSize);
	
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
	public SverResponse<PageBean<ActionProductListVo>> findProductsForPortal(Integer productTypeId,Integer partsId, int pageNum,int pageSize);
	/**
	 * 门户：查找热门商品
	 * @param num    查询商品的数量
	 * @return
	 */
	public SverResponse<List<ActionProduct>> findHotProducts(Integer num);
	/**
	 * 门户：获取首页所有楼层数据
	 * @return
	 */
	public SverResponse<ActionProductFloorVo> findFloorProducts();
}
