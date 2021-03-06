package cn.techaction.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
<<<<<<< HEAD
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
=======
>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionParamsDao;
import cn.techaction.dao.ActionProductDao;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.service.ActionProductService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionProductEvenFloorVo;
import cn.techaction.vo.ActionProductFloorVo;
import cn.techaction.vo.ActionProductListVo;
import cn.techaction.vo.ActionProductOddFloorVo;

@Service
public class ActionProductServiceImpl implements ActionProductService {

<<<<<<< HEAD
	@Autowired
	private ActionProductDao aProductDao;
	@Autowired
	private ActionParamsDao aParamsDao;
	
	/**
	 * 新增或更新商品信息
	 * @throws Exception 
	 */
	@Override
	public SverResponse<String> saveOrUpdateProduct(ActionProduct product)  {
		if(product!=null) {
			//将图组中第一个置位主图
			if(StringUtils.isNoneBlank(product.getSubImages())) {
				String[] array = product.getSubImages().split(",");
				//JSONArray jsonArray = new JSONArray(product.getSubImages());
				product.setIconUrl(array[0]);
			}
			
			if(product.getId() != null) {
				product.setUpdated(new Date());
				int rs = aProductDao.updateProduct(product);
				if(rs >0) {
					return SverResponse.createRespBySuccessMessage("产品更新成功！");
				}
				return SverResponse.createByErrorMessage("产品更新失败！");
			}else {
				//新增
				product.setStatus(1);  //商品的状态：1-待售，刚保存；2-上架，在售；3-下架，停售
				product.setHot(2);   //是否热销，1-是，2-否
				product.setUpdated(new Date());
				product.setCreated(new Date());
				int rs = aProductDao.insertProduct(product);
				if(rs >0) {
					return SverResponse.createRespBySuccessMessage("产品新增成功！");
				}
				return SverResponse.createByErrorMessage("产品新增失败！");
			}
		}
		return SverResponse.createByErrorMessage("产品参数错误！");
	}

	
	/**
	 * 更改商品状态信息
	 * @throws Exception 
	 */
	@Override
	public SverResponse<String> updateStatus(Integer productId, Integer status) {
		if(productId==null || status==null) {
			return SverResponse.createByErrorMessage("非法参数！");
		}
		ActionProduct product = new ActionProduct();
		product.setId(productId);
		product.setStatus(status);
		product.setUpdated(new Date());
		int rs = aProductDao.updateProduct(product);
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("修改产品状态成功！");
		}
		return SverResponse.createByErrorMessage("修改产品状态失败！");
	}

	

	@Override
	public SverResponse<String> updateHotStatus(Integer productId, Integer isHot) {
		if(productId==null || isHot==null) {
			return SverResponse.createByErrorMessage("非法参数！");
		}
		ActionProduct product = new ActionProduct();
		product.setId(productId);
		product.setHot(isHot);
		product.setUpdated(new Date());
		int rs = aProductDao.updateProduct(product);
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("修改产品状态成功！");
		}
		return SverResponse.createByErrorMessage("修改产品状态失败！");
	}

=======
	
	
	@Autowired
	private ActionProductDao productDao;
	@Autowired
	private ActionParamsDao aParamsDao;
>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77

	/**
	 * 查找商品详情信息
	 */
	@Override
<<<<<<< HEAD
	public SverResponse<ActionProduct> findProductDetailById(Integer productId) {
		if(productId==null) {
			return SverResponse.createByErrorMessage("产品编号不能为空！");
		}
		ActionProduct product = aProductDao.findProductById(productId);
		if(product==null) {
			return SverResponse.createByErrorMessage("产品已经下架！");
		}
		return SverResponse.createRespBySuccess(product);
	}


	//封装VO对象
	private ActionProductListVo createProductListVo(ActionProduct product){
		ActionProductListVo vo = new ActionProductListVo();
		vo.setId(product.getId());
		vo.setName(product.getName());
		vo.setPartsCategory(aParamsDao.findParamById(product.getPartsId()).getName());
		vo.setProductCategory(aParamsDao.findParamById(product.getProductId()).getName());
		vo.setPrice(product.getPrice());
		vo.setStatus(product.getStatus());
		vo.setIconUrl(product.getIconUrl());
		vo.setStatusDesc(ConstUtil.ProductStatus.getStatusDesc(product.getStatus()));
		vo.setHotStatus(ConstUtil.HotStatus.getHotDesc(product.getHot()));
		vo.setHot(product.getHot());
		return vo;
	}
=======
	public SverResponse<PageBean<ActionProduct>> findProducts(Integer productId, Integer partsId, Integer pageNum,
			Integer pageSize) {

		// TODO Auto-generated method stub
		// 鏌ユ壘绗﹀悎鏉′欢鐨勬�昏褰曟暟
		int totalRecord = productDao.getTotalCount(productId, partsId);
		PageBean<ActionProduct> pageBean = new PageBean<>(pageNum, pageSize, totalRecord);
		// 鏌ヨ绗﹀悎鏉′欢鐨勬暟鎹�
		List<ActionProduct> data = productDao.findProductsByTypeId(productId, partsId, pageBean.getStartIndex(),
				pageBean.getPageSize());
		 pageBean.setData(data);
>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77

	//商品列表，分页
	@Override
	public SverResponse<PageBean<ActionProductListVo>> findProductsByCondition(ActionProduct product, int pageNum,
			int pageSize) {
		//查找符合条件的总记录数
		if(product.getName()!=null) {
			product.setName(new StringBuilder().append("%").append(product.getName()).append("%").toString());
		}
		int totalRecord = aProductDao.getTotalCount(product);
		//创建分页封装对象
		PageBean<ActionProductListVo> pageBean = new PageBean<>(pageNum, pageSize, totalRecord);
		//读取数据
		List<ActionProduct> products = aProductDao.findProducts(product,pageBean.getStartIndex(), pageSize);
		//封装VO
		List<ActionProductListVo> voList = Lists.newArrayList();
		for(ActionProduct p:products) {
			voList.add(createProductListVo(p));
		}
		pageBean.setData(voList);
				
		return SverResponse.createRespBySuccess(pageBean);
	}
	

	
	/**
	 * 鏂板鎴栨洿鏂板晢鍝佷俊鎭�
	 * @throws Exception 
	 */
	@Override
	public SverResponse<String> saveOrUpdateProduct(ActionProduct product)  {
		if(product!=null) {
			//灏嗗浘缁勪腑绗竴涓疆浣嶄富鍥�
			if(StringUtils.isNoneBlank(product.getSubImages())) {
				String[] array = product.getSubImages().split(",");
				//JSONArray jsonArray = new JSONArray(product.getSubImages());
				product.setIconUrl(array[0]);
			}
			
			if(product.getId() != null) {
				product.setUpdated(new Date());
				int rs = productDao.updateProduct(product);
				if(rs >0) {
					return SverResponse.createRespBySuccessMessage("浜у搧鏇存柊鎴愬姛锛�");
				}
				return SverResponse.createByErrorMessage("浜у搧鏇存柊澶辫触锛�");
			}else {
				//鏂板
				product.setStatus(1);  //鍟嗗搧鐨勭姸鎬侊細1-寰呭敭锛屽垰淇濆瓨锛�2-涓婃灦锛屽湪鍞紱3-涓嬫灦锛屽仠鍞�
				product.setHot(2);   //鏄惁鐑攢锛�1-鏄紝2-鍚�
				product.setUpdated(new Date());
				product.setCreated(new Date());
				int rs = productDao.insertProduct(product);
				if(rs >0) {
					return SverResponse.createRespBySuccessMessage("浜у搧鏂板鎴愬姛锛�");
				}
				return SverResponse.createByErrorMessage("浜у搧鏂板澶辫触锛�");
			}
		}
		return SverResponse.createByErrorMessage("浜у搧鍙傛暟閿欒锛�");
	}

	
	/**
	 * 鏇存敼鍟嗗搧鐘舵�佷俊鎭�
	 * @throws Exception 
	 */
	@Override
	public SverResponse<String> updateStatus(Integer productId, Integer status) {
		if(productId==null || status==null) {
			return SverResponse.createByErrorMessage("闈炴硶鍙傛暟锛�");
		}
		ActionProduct product = new ActionProduct();
		product.setId(productId);
		product.setStatus(status);
		product.setUpdated(new Date());
		int rs = productDao.updateProduct(product);
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("淇敼浜у搧鐘舵�佹垚鍔燂紒");
		}
		return SverResponse.createByErrorMessage("淇敼浜у搧鐘舵�佸け璐ワ紒");
	}

	

	@Override
	public SverResponse<String> updateHotStatus(Integer productId, Integer isHot) {
		if(productId==null || isHot==null) {
			return SverResponse.createByErrorMessage("闈炴硶鍙傛暟锛�");
		}
		ActionProduct product = new ActionProduct();
		product.setId(productId);
		product.setHot(isHot);
		product.setUpdated(new Date());
		int rs = productDao.updateProduct(product);
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("淇敼浜у搧鐘舵�佹垚鍔燂紒");
		}
		return SverResponse.createByErrorMessage("淇敼浜у搧鐘舵�佸け璐ワ紒");
	}


	/**
	 * 鏌ユ壘鍟嗗搧璇︽儏淇℃伅
	 */
	@Override
	public SverResponse<ActionProduct> findProductDetailById(Integer productId) {
		if(productId==null) {
			return SverResponse.createByErrorMessage("浜у搧缂栧彿涓嶈兘涓虹┖锛�");
		}
		ActionProduct product = productDao.findProductById(productId);
		if(product==null) {
			return SverResponse.createByErrorMessage("浜у搧宸茬粡涓嬫灦锛�");
		}
		return SverResponse.createRespBySuccess(product);
	}


	//灏佽VO瀵硅薄
	private ActionProductListVo createProductListVo(ActionProduct product){
		ActionProductListVo vo = new ActionProductListVo();
		vo.setId(product.getId());
		vo.setName(product.getName());
		vo.setPartsCategory(aParamsDao.findParamById(product.getPartsId()).getName());
		vo.setProductCategory(aParamsDao.findParamById(product.getProductId()).getName());
		vo.setPrice(product.getPrice());
		vo.setStatus(product.getStatus());
		vo.setIconUrl(product.getIconUrl());
		vo.setStatusDesc(ConstUtil.ProductStatus.getStatusDesc(product.getStatus()));
		vo.setHotStatus(ConstUtil.HotStatus.getHotDesc(product.getHot()));
		vo.setHot(product.getHot());
		return vo;
	}

	//鍟嗗搧鍒楄〃锛屽垎椤�
	@Override
	public SverResponse<PageBean<ActionProductListVo>> findProductsByCondition(ActionProduct product, int pageNum,
			int pageSize) {
		//鏌ユ壘绗﹀悎鏉′欢鐨勬�昏褰曟暟
		if(product.getName()!=null) {
			product.setName(new StringBuilder().append("%").append(product.getName()).append("%").toString());
		}
		int totalRecord = productDao.getTotalCount(product);
		//鍒涘缓鍒嗛〉灏佽瀵硅薄
		PageBean<ActionProductListVo> pageBean = new PageBean<>(pageNum, pageSize, totalRecord);
		//璇诲彇鏁版嵁
		List<ActionProduct> products = productDao.findProducts(product,pageBean.getStartIndex(), pageSize);
		//灏佽VO
		List<ActionProductListVo> voList = Lists.newArrayList();
		for(ActionProduct p:products) {
			voList.add(createProductListVo(p));
		}
		pageBean.setData(voList);
				
		return SverResponse.createRespBySuccess(pageBean);
	}


	/**
	 * 鏂囦欢涓婁紶鏂规硶
	 */
	
	@Override
	public SverResponse<Map<String, String>> uploadFile(MultipartFile file, String path) {
		 String fileName = file.getOriginalFilename();
	     String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
	     String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;
	     File fileDir = new File(path);
	     if(!fileDir.exists()){
	        fileDir.setWritable(true);
	        fileDir.mkdirs();
	     }
	     File targetFile = new File(path,uploadFileName);
	     try {
	         file.transferTo(targetFile);
	            //鏂囦欢宸茬粡涓婁紶鎴愬姛浜�
	     } catch (IOException e) {
	        return SverResponse.createByErrorMessage("鏂囦欢涓婁紶閿欒锛�");
	     }
	     Map<String,String> fileMap = Maps.newHashMap();
         fileMap.put("url",ConstUtil.UPLOAD_IMAGES_PATH+targetFile.getName());
         System.out.println(fileMap.get("url"));
         return SverResponse.createRespBySuccess(fileMap);
	}


	/**
	 * 鐢ㄤ簬鍟嗗煄鏄剧ず鍟嗗搧璇︽儏
	 */
	@Override
	public SverResponse<ActionProduct> findProductDetailForPortal(Integer productId) {
		if(productId==null) {
			return SverResponse.createByErrorMessage("浜у搧缂栧彿涓嶈兘涓虹┖锛�");
		}
		ActionProduct product = productDao.findProductById(productId);
		if(product==null) {
			return SverResponse.createByErrorMessage("浜у搧宸茬粡涓嬫灦锛�");
		}
		if(product.getStatus()==ConstUtil.ProductStatus.STATUS_OFF_SALE) {
			return SverResponse.createByErrorMessage("浜у搧宸茬粡涓嬫灦锛�");
		}
		return SverResponse.createRespBySuccess(product);
	}


	@Override
	public SverResponse<PageBean<ActionProductListVo>> findProductsForPortal(Integer productTypeId, Integer partsId,
			int pageNum, int pageSize) {
		//鏌ユ壘绗﹀悎鏉′欢鐨勬�昏褰曟暟
		ActionProduct product = new ActionProduct();
		product.setProductId(productTypeId);
		product.setPartsId(partsId);
		int totalRecord = productDao.getTotalCount(product);
		//鍒涘缓鍒嗛〉灏佽瀵硅薄
		PageBean<ActionProductListVo> pageBean = new PageBean<>(pageNum, pageSize, totalRecord);
		//璇诲彇鏁版嵁
		List<ActionProduct> products = productDao.findProducts(product,pageBean.getStartIndex(), pageSize);
		//灏佽VO
		List<ActionProductListVo> voList = Lists.newArrayList();
		for(ActionProduct p:products) {
			voList.add(createProductListVo(p));
		}
		pageBean.setData(voList);
		return SverResponse.createRespBySuccess(pageBean);
	}


<<<<<<< HEAD
	/**
	 * 文件上传方法
	 */
	
	@Override
	public SverResponse<Map<String, String>> uploadFile(MultipartFile file, String path) {
		 String fileName = file.getOriginalFilename();
	     String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
	     String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;
	     File fileDir = new File(path);
	     if(!fileDir.exists()){
	        fileDir.setWritable(true);
	        fileDir.mkdirs();
	     }
	     File targetFile = new File(path,uploadFileName);
	     try {
	         file.transferTo(targetFile);
	            //文件已经上传成功了
	     } catch (IOException e) {
	        return SverResponse.createByErrorMessage("文件上传错误！");
	     }
	     Map<String,String> fileMap = Maps.newHashMap();
         fileMap.put("url",ConstUtil.UPLOAD_IMAGES_PATH+targetFile.getName());
         System.out.println(fileMap.get("url"));
         return SverResponse.createRespBySuccess(fileMap);
	}


	/**
	 * 用于商城显示商品详情
	 */
	@Override
	public SverResponse<ActionProduct> findProductDetailForPortal(Integer productId) {
		if(productId==null) {
			return SverResponse.createByErrorMessage("产品编号不能为空！");
		}
		ActionProduct product = aProductDao.findProductById(productId);
		if(product==null) {
			return SverResponse.createByErrorMessage("产品已经下架！");
		}
		if(product.getStatus()==ConstUtil.ProductStatus.STATUS_OFF_SALE) {
			return SverResponse.createByErrorMessage("产品已经下架！");
		}
		return SverResponse.createRespBySuccess(product);
	}


	@Override
	public SverResponse<PageBean<ActionProductListVo>> findProductsForPortal(Integer productTypeId, Integer partsId,
			int pageNum, int pageSize) {
		//查找符合条件的总记录数
		ActionProduct product = new ActionProduct();
		product.setProductId(productTypeId);
		product.setPartsId(partsId);
		int totalRecord = aProductDao.getTotalCount(product);
		//创建分页封装对象
		PageBean<ActionProductListVo> pageBean = new PageBean<>(pageNum, pageSize, totalRecord);
		//读取数据
		List<ActionProduct> products = aProductDao.findProducts(product,pageBean.getStartIndex(), pageSize);
		//封装VO
		List<ActionProductListVo> voList = Lists.newArrayList();
		for(ActionProduct p:products) {
			voList.add(createProductListVo(p));
		}
		pageBean.setData(voList);
		return SverResponse.createRespBySuccess(pageBean);
	}


	@Override
	public SverResponse<List<ActionProduct>> findHotProducts(Integer num) {
		List<ActionProduct> products = aProductDao.findHotProducts(num);
//		if(products.size()<5) {
//			return SverResponse.createByErrorMessage("尚未设置热销商品！");
//		}
		return SverResponse.createRespBySuccess(products);
	}


	@Override
	public SverResponse<ActionProductFloorVo> findFloorProducts() {
		ActionProductFloorVo vo = new ActionProductFloorVo();
		//1楼数据
		List<ActionProduct> products1 = aProductDao.findProductsByProductCategory(ConstUtil.ProductType.TYPE_HNTJX);
		if(products1.size()<8) {
			return SverResponse.createByErrorMessage("1楼商品数据尚未准备完整！");
		}
		vo.setOneFloor(createOddFloorVo(products1));
		//2楼数据
		List<ActionProduct> products2 = aProductDao.findProductsByProductCategory(ConstUtil.ProductType.TYPE_JZQZJJX);
		if(products2.size()<8) {
			return SverResponse.createByErrorMessage("2楼商品数据尚未准备完整！");
		}
		vo.setTwoFloor(createEvenFloorVo(products2));
		//3楼数据
		List<ActionProduct> products3 = aProductDao.findProductsByProductCategory(ConstUtil.ProductType.TYPE_GCQZJJX);
		if(products3.size()<8) {
			return SverResponse.createByErrorMessage("3楼商品数据尚未准备完整！");
		}
		vo.setThreeFloor(createOddFloorVo(products3));
		//4楼数据
		List<ActionProduct> products4 = aProductDao.findProductsByProductCategory(ConstUtil.ProductType.TYPE_LMJX);
		if(products4.size()<8) {
			return SverResponse.createByErrorMessage("4楼商品数据尚未准备完整！");
=======
	@Override
	public SverResponse<List<ActionProduct>> findHotProducts(String num) {
		List<ActionProduct> products = productDao.findHotProducts(num);
	if(products.size()<5) {
		return SverResponse.createByErrorMessage("灏氭湭璁剧疆鐑攢鍟嗗搧锛�");
	}
		return SverResponse.createRespBySuccess(products);
	}




	public SverResponse<ActionProductFloorVo> findFloorProducts() {
		ActionProductFloorVo vo = new ActionProductFloorVo();
		//1妤兼暟鎹�
		List<ActionProduct> products1 = productDao.findProductsByProductCategory(ConstUtil.ProductType.TYPE_HNTJX);
		if(products1.size()<8) {
			return SverResponse.createByErrorMessage("1妤煎晢鍝佹暟鎹皻鏈噯澶囧畬鏁达紒");
		}
		vo.setOneFloor(createOddFloorVo(products1));
		//2妤兼暟鎹�
		List<ActionProduct> products2 = productDao.findProductsByProductCategory(ConstUtil.ProductType.TYPE_JZQZJJX);
		if(products2.size()<8) {
			return SverResponse.createByErrorMessage("2妤煎晢鍝佹暟鎹皻鏈噯澶囧畬鏁达紒");
		}
		vo.setTwoFloor(createEvenFloorVo(products2));
		//3妤兼暟鎹�
		List<ActionProduct> products3 = productDao.findProductsByProductCategory(ConstUtil.ProductType.TYPE_GCQZJJX);
		if(products3.size()<8) {
			return SverResponse.createByErrorMessage("3妤煎晢鍝佹暟鎹皻鏈噯澶囧畬鏁达紒");
		}
		vo.setThreeFloor(createOddFloorVo(products3));
		//4妤兼暟鎹�
		List<ActionProduct> products4 = productDao.findProductsByProductCategory(ConstUtil.ProductType.TYPE_LMJX);
		if(products4.size()<8) {
			return SverResponse.createByErrorMessage("4妤煎晢鍝佹暟鎹皻鏈噯澶囧畬鏁达紒");
>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77
		}
		vo.setFourFloor(createEvenFloorVo(products4));
		return SverResponse.createRespBySuccess(vo);
	}
	/**
<<<<<<< HEAD
	 * 创建奇数楼层VO
=======
	 * 鍒涘缓濂囨暟妤煎眰VO
>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77
	 * @param products
	 * @return
	 */
	private ActionProductOddFloorVo createOddFloorVo(List<ActionProduct> products) {
		ActionProductOddFloorVo floorVo = new ActionProductOddFloorVo();
		floorVo.setMt(products.get(0));
		floorVo.setMb(products.get(1));
		floorVo.setList(products.subList(2, 8));
		return floorVo;
	}
	private ActionProductEvenFloorVo createEvenFloorVo(List<ActionProduct> products) {
		ActionProductEvenFloorVo floorVo = new ActionProductEvenFloorVo();
		floorVo.setMb(products.subList(0, 2));
		floorVo.setMt(products.get(2));
		floorVo.setRt(products.subList(3, 7));
		floorVo.setRb(products.subList(7, 9));
		return floorVo;
	}

	/**
<<<<<<< HEAD
	 * 后台管理端查询符合条件的商品信息，后台不分页，前台分页
=======
	 * 鍚庡彴绠＄悊绔煡璇㈢鍚堟潯浠剁殑鍟嗗搧淇℃伅锛屽悗鍙颁笉鍒嗛〉锛屽墠鍙板垎椤�
>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77
	 */
	@Override
	public SverResponse<List<ActionProductListVo>> findProduts(ActionProduct product) {
		
		if(product.getName()!=null) {
			product.setName(new StringBuilder().append("%").append(product.getName()).append("%").toString());
		}
<<<<<<< HEAD
		//读取数据
		List<ActionProduct> products = aProductDao.findProductsNoPage(product);
		//封装VO
=======
		//璇诲彇鏁版嵁
		List<ActionProduct> products = productDao.findProductsNoPage(product);
		//灏佽VO
>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77
		List<ActionProductListVo> voList = Lists.newArrayList();
		for(ActionProduct p:products) {
			voList.add(createProductListVo(p));
		}	
		return SverResponse.createRespBySuccess(voList);
	}
}


