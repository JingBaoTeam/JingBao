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
	 * ĞÂÔö»ò¸üĞÂÉÌÆ·ĞÅÏ¢
	 * @throws Exception 
	 */
	@Override
	public SverResponse<String> saveOrUpdateProduct(ActionProduct product)  {
		if(product!=null) {
			//½«Í¼×éÖĞµÚÒ»¸öÖÃÎ»Ö÷Í¼
			if(StringUtils.isNoneBlank(product.getSubImages())) {
				String[] array = product.getSubImages().split(",");
				//JSONArray jsonArray = new JSONArray(product.getSubImages());
				product.setIconUrl(array[0]);
			}
			
			if(product.getId() != null) {
				product.setUpdated(new Date());
				int rs = aProductDao.updateProduct(product);
				if(rs >0) {
					return SverResponse.createRespBySuccessMessage("²úÆ·¸üĞÂ³É¹¦£¡");
				}
				return SverResponse.createByErrorMessage("²úÆ·¸üĞÂÊ§°Ü£¡");
			}else {
				//ĞÂÔö
				product.setStatus(1);  //ÉÌÆ·µÄ×´Ì¬£º1-´ıÊÛ£¬¸Õ±£´æ£»2-ÉÏ¼Ü£¬ÔÚÊÛ£»3-ÏÂ¼Ü£¬Í£ÊÛ
				product.setHot(2);   //ÊÇ·ñÈÈÏú£¬1-ÊÇ£¬2-·ñ
				product.setUpdated(new Date());
				product.setCreated(new Date());
				int rs = aProductDao.insertProduct(product);
				if(rs >0) {
					return SverResponse.createRespBySuccessMessage("²úÆ·ĞÂÔö³É¹¦£¡");
				}
				return SverResponse.createByErrorMessage("²úÆ·ĞÂÔöÊ§°Ü£¡");
			}
		}
		return SverResponse.createByErrorMessage("²úÆ·²ÎÊı´íÎó£¡");
	}

	
	/**
	 * ¸ü¸ÄÉÌÆ·×´Ì¬ĞÅÏ¢
	 * @throws Exception 
	 */
	@Override
	public SverResponse<String> updateStatus(Integer productId, Integer status) {
		if(productId==null || status==null) {
			return SverResponse.createByErrorMessage("·Ç·¨²ÎÊı£¡");
		}
		ActionProduct product = new ActionProduct();
		product.setId(productId);
		product.setStatus(status);
		product.setUpdated(new Date());
		int rs = aProductDao.updateProduct(product);
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("ĞŞ¸Ä²úÆ·×´Ì¬³É¹¦£¡");
		}
		return SverResponse.createByErrorMessage("ĞŞ¸Ä²úÆ·×´Ì¬Ê§°Ü£¡");
	}

	

	@Override
	public SverResponse<String> updateHotStatus(Integer productId, Integer isHot) {
		if(productId==null || isHot==null) {
			return SverResponse.createByErrorMessage("·Ç·¨²ÎÊı£¡");
		}
		ActionProduct product = new ActionProduct();
		product.setId(productId);
		product.setHot(isHot);
		product.setUpdated(new Date());
		int rs = aProductDao.updateProduct(product);
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("ĞŞ¸Ä²úÆ·×´Ì¬³É¹¦£¡");
		}
		return SverResponse.createByErrorMessage("ĞŞ¸Ä²úÆ·×´Ì¬Ê§°Ü£¡");
	}

=======
	
	
	@Autowired
	private ActionProductDao productDao;
	@Autowired
	private ActionParamsDao aParamsDao;
>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77

	/**
	 * ²éÕÒÉÌÆ·ÏêÇéĞÅÏ¢
	 */
	@Override
<<<<<<< HEAD
	public SverResponse<ActionProduct> findProductDetailById(Integer productId) {
		if(productId==null) {
			return SverResponse.createByErrorMessage("²úÆ·±àºÅ²»ÄÜÎª¿Õ£¡");
		}
		ActionProduct product = aProductDao.findProductById(productId);
		if(product==null) {
			return SverResponse.createByErrorMessage("²úÆ·ÒÑ¾­ÏÂ¼Ü£¡");
		}
		return SverResponse.createRespBySuccess(product);
	}


	//·â×°VO¶ÔÏó
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
		// æŸ¥æ‰¾ç¬¦åˆæ¡ä»¶çš„æ€»è®°å½•æ•°
		int totalRecord = productDao.getTotalCount(productId, partsId);
		PageBean<ActionProduct> pageBean = new PageBean<>(pageNum, pageSize, totalRecord);
		// æŸ¥è¯¢ç¬¦åˆæ¡ä»¶çš„æ•°æ®
		List<ActionProduct> data = productDao.findProductsByTypeId(productId, partsId, pageBean.getStartIndex(),
				pageBean.getPageSize());
		 pageBean.setData(data);
>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77

	//ÉÌÆ·ÁĞ±í£¬·ÖÒ³
	@Override
	public SverResponse<PageBean<ActionProductListVo>> findProductsByCondition(ActionProduct product, int pageNum,
			int pageSize) {
		//²éÕÒ·ûºÏÌõ¼şµÄ×Ü¼ÇÂ¼Êı
		if(product.getName()!=null) {
			product.setName(new StringBuilder().append("%").append(product.getName()).append("%").toString());
		}
		int totalRecord = aProductDao.getTotalCount(product);
		//´´½¨·ÖÒ³·â×°¶ÔÏó
		PageBean<ActionProductListVo> pageBean = new PageBean<>(pageNum, pageSize, totalRecord);
		//¶ÁÈ¡Êı¾İ
		List<ActionProduct> products = aProductDao.findProducts(product,pageBean.getStartIndex(), pageSize);
		//·â×°VO
		List<ActionProductListVo> voList = Lists.newArrayList();
		for(ActionProduct p:products) {
			voList.add(createProductListVo(p));
		}
		pageBean.setData(voList);
				
		return SverResponse.createRespBySuccess(pageBean);
	}
	

	
	/**
	 * æ–°å¢æˆ–æ›´æ–°å•†å“ä¿¡æ¯
	 * @throws Exception 
	 */
	@Override
	public SverResponse<String> saveOrUpdateProduct(ActionProduct product)  {
		if(product!=null) {
			//å°†å›¾ç»„ä¸­ç¬¬ä¸€ä¸ªç½®ä½ä¸»å›¾
			if(StringUtils.isNoneBlank(product.getSubImages())) {
				String[] array = product.getSubImages().split(",");
				//JSONArray jsonArray = new JSONArray(product.getSubImages());
				product.setIconUrl(array[0]);
			}
			
			if(product.getId() != null) {
				product.setUpdated(new Date());
				int rs = productDao.updateProduct(product);
				if(rs >0) {
					return SverResponse.createRespBySuccessMessage("äº§å“æ›´æ–°æˆåŠŸï¼");
				}
				return SverResponse.createByErrorMessage("äº§å“æ›´æ–°å¤±è´¥ï¼");
			}else {
				//æ–°å¢
				product.setStatus(1);  //å•†å“çš„çŠ¶æ€ï¼š1-å¾…å”®ï¼Œåˆšä¿å­˜ï¼›2-ä¸Šæ¶ï¼Œåœ¨å”®ï¼›3-ä¸‹æ¶ï¼Œåœå”®
				product.setHot(2);   //æ˜¯å¦çƒ­é”€ï¼Œ1-æ˜¯ï¼Œ2-å¦
				product.setUpdated(new Date());
				product.setCreated(new Date());
				int rs = productDao.insertProduct(product);
				if(rs >0) {
					return SverResponse.createRespBySuccessMessage("äº§å“æ–°å¢æˆåŠŸï¼");
				}
				return SverResponse.createByErrorMessage("äº§å“æ–°å¢å¤±è´¥ï¼");
			}
		}
		return SverResponse.createByErrorMessage("äº§å“å‚æ•°é”™è¯¯ï¼");
	}

	
	/**
	 * æ›´æ”¹å•†å“çŠ¶æ€ä¿¡æ¯
	 * @throws Exception 
	 */
	@Override
	public SverResponse<String> updateStatus(Integer productId, Integer status) {
		if(productId==null || status==null) {
			return SverResponse.createByErrorMessage("éæ³•å‚æ•°ï¼");
		}
		ActionProduct product = new ActionProduct();
		product.setId(productId);
		product.setStatus(status);
		product.setUpdated(new Date());
		int rs = productDao.updateProduct(product);
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("ä¿®æ”¹äº§å“çŠ¶æ€æˆåŠŸï¼");
		}
		return SverResponse.createByErrorMessage("ä¿®æ”¹äº§å“çŠ¶æ€å¤±è´¥ï¼");
	}

	

	@Override
	public SverResponse<String> updateHotStatus(Integer productId, Integer isHot) {
		if(productId==null || isHot==null) {
			return SverResponse.createByErrorMessage("éæ³•å‚æ•°ï¼");
		}
		ActionProduct product = new ActionProduct();
		product.setId(productId);
		product.setHot(isHot);
		product.setUpdated(new Date());
		int rs = productDao.updateProduct(product);
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("ä¿®æ”¹äº§å“çŠ¶æ€æˆåŠŸï¼");
		}
		return SverResponse.createByErrorMessage("ä¿®æ”¹äº§å“çŠ¶æ€å¤±è´¥ï¼");
	}


	/**
	 * æŸ¥æ‰¾å•†å“è¯¦æƒ…ä¿¡æ¯
	 */
	@Override
	public SverResponse<ActionProduct> findProductDetailById(Integer productId) {
		if(productId==null) {
			return SverResponse.createByErrorMessage("äº§å“ç¼–å·ä¸èƒ½ä¸ºç©ºï¼");
		}
		ActionProduct product = productDao.findProductById(productId);
		if(product==null) {
			return SverResponse.createByErrorMessage("äº§å“å·²ç»ä¸‹æ¶ï¼");
		}
		return SverResponse.createRespBySuccess(product);
	}


	//å°è£…VOå¯¹è±¡
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

	//å•†å“åˆ—è¡¨ï¼Œåˆ†é¡µ
	@Override
	public SverResponse<PageBean<ActionProductListVo>> findProductsByCondition(ActionProduct product, int pageNum,
			int pageSize) {
		//æŸ¥æ‰¾ç¬¦åˆæ¡ä»¶çš„æ€»è®°å½•æ•°
		if(product.getName()!=null) {
			product.setName(new StringBuilder().append("%").append(product.getName()).append("%").toString());
		}
		int totalRecord = productDao.getTotalCount(product);
		//åˆ›å»ºåˆ†é¡µå°è£…å¯¹è±¡
		PageBean<ActionProductListVo> pageBean = new PageBean<>(pageNum, pageSize, totalRecord);
		//è¯»å–æ•°æ®
		List<ActionProduct> products = productDao.findProducts(product,pageBean.getStartIndex(), pageSize);
		//å°è£…VO
		List<ActionProductListVo> voList = Lists.newArrayList();
		for(ActionProduct p:products) {
			voList.add(createProductListVo(p));
		}
		pageBean.setData(voList);
				
		return SverResponse.createRespBySuccess(pageBean);
	}


	/**
	 * æ–‡ä»¶ä¸Šä¼ æ–¹æ³•
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
	            //æ–‡ä»¶å·²ç»ä¸Šä¼ æˆåŠŸäº†
	     } catch (IOException e) {
	        return SverResponse.createByErrorMessage("æ–‡ä»¶ä¸Šä¼ é”™è¯¯ï¼");
	     }
	     Map<String,String> fileMap = Maps.newHashMap();
         fileMap.put("url",ConstUtil.UPLOAD_IMAGES_PATH+targetFile.getName());
         System.out.println(fileMap.get("url"));
         return SverResponse.createRespBySuccess(fileMap);
	}


	/**
	 * ç”¨äºå•†åŸæ˜¾ç¤ºå•†å“è¯¦æƒ…
	 */
	@Override
	public SverResponse<ActionProduct> findProductDetailForPortal(Integer productId) {
		if(productId==null) {
			return SverResponse.createByErrorMessage("äº§å“ç¼–å·ä¸èƒ½ä¸ºç©ºï¼");
		}
		ActionProduct product = productDao.findProductById(productId);
		if(product==null) {
			return SverResponse.createByErrorMessage("äº§å“å·²ç»ä¸‹æ¶ï¼");
		}
		if(product.getStatus()==ConstUtil.ProductStatus.STATUS_OFF_SALE) {
			return SverResponse.createByErrorMessage("äº§å“å·²ç»ä¸‹æ¶ï¼");
		}
		return SverResponse.createRespBySuccess(product);
	}


	@Override
	public SverResponse<PageBean<ActionProductListVo>> findProductsForPortal(Integer productTypeId, Integer partsId,
			int pageNum, int pageSize) {
		//æŸ¥æ‰¾ç¬¦åˆæ¡ä»¶çš„æ€»è®°å½•æ•°
		ActionProduct product = new ActionProduct();
		product.setProductId(productTypeId);
		product.setPartsId(partsId);
		int totalRecord = productDao.getTotalCount(product);
		//åˆ›å»ºåˆ†é¡µå°è£…å¯¹è±¡
		PageBean<ActionProductListVo> pageBean = new PageBean<>(pageNum, pageSize, totalRecord);
		//è¯»å–æ•°æ®
		List<ActionProduct> products = productDao.findProducts(product,pageBean.getStartIndex(), pageSize);
		//å°è£…VO
		List<ActionProductListVo> voList = Lists.newArrayList();
		for(ActionProduct p:products) {
			voList.add(createProductListVo(p));
		}
		pageBean.setData(voList);
		return SverResponse.createRespBySuccess(pageBean);
	}


<<<<<<< HEAD
	/**
	 * ÎÄ¼şÉÏ´«·½·¨
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
	            //ÎÄ¼şÒÑ¾­ÉÏ´«³É¹¦ÁË
	     } catch (IOException e) {
	        return SverResponse.createByErrorMessage("ÎÄ¼şÉÏ´«´íÎó£¡");
	     }
	     Map<String,String> fileMap = Maps.newHashMap();
         fileMap.put("url",ConstUtil.UPLOAD_IMAGES_PATH+targetFile.getName());
         System.out.println(fileMap.get("url"));
         return SverResponse.createRespBySuccess(fileMap);
	}


	/**
	 * ÓÃÓÚÉÌ³ÇÏÔÊ¾ÉÌÆ·ÏêÇé
	 */
	@Override
	public SverResponse<ActionProduct> findProductDetailForPortal(Integer productId) {
		if(productId==null) {
			return SverResponse.createByErrorMessage("²úÆ·±àºÅ²»ÄÜÎª¿Õ£¡");
		}
		ActionProduct product = aProductDao.findProductById(productId);
		if(product==null) {
			return SverResponse.createByErrorMessage("²úÆ·ÒÑ¾­ÏÂ¼Ü£¡");
		}
		if(product.getStatus()==ConstUtil.ProductStatus.STATUS_OFF_SALE) {
			return SverResponse.createByErrorMessage("²úÆ·ÒÑ¾­ÏÂ¼Ü£¡");
		}
		return SverResponse.createRespBySuccess(product);
	}


	@Override
	public SverResponse<PageBean<ActionProductListVo>> findProductsForPortal(Integer productTypeId, Integer partsId,
			int pageNum, int pageSize) {
		//²éÕÒ·ûºÏÌõ¼şµÄ×Ü¼ÇÂ¼Êı
		ActionProduct product = new ActionProduct();
		product.setProductId(productTypeId);
		product.setPartsId(partsId);
		int totalRecord = aProductDao.getTotalCount(product);
		//´´½¨·ÖÒ³·â×°¶ÔÏó
		PageBean<ActionProductListVo> pageBean = new PageBean<>(pageNum, pageSize, totalRecord);
		//¶ÁÈ¡Êı¾İ
		List<ActionProduct> products = aProductDao.findProducts(product,pageBean.getStartIndex(), pageSize);
		//·â×°VO
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
//			return SverResponse.createByErrorMessage("ÉĞÎ´ÉèÖÃÈÈÏúÉÌÆ·£¡");
//		}
		return SverResponse.createRespBySuccess(products);
	}


	@Override
	public SverResponse<ActionProductFloorVo> findFloorProducts() {
		ActionProductFloorVo vo = new ActionProductFloorVo();
		//1Â¥Êı¾İ
		List<ActionProduct> products1 = aProductDao.findProductsByProductCategory(ConstUtil.ProductType.TYPE_HNTJX);
		if(products1.size()<8) {
			return SverResponse.createByErrorMessage("1Â¥ÉÌÆ·Êı¾İÉĞÎ´×¼±¸ÍêÕû£¡");
		}
		vo.setOneFloor(createOddFloorVo(products1));
		//2Â¥Êı¾İ
		List<ActionProduct> products2 = aProductDao.findProductsByProductCategory(ConstUtil.ProductType.TYPE_JZQZJJX);
		if(products2.size()<8) {
			return SverResponse.createByErrorMessage("2Â¥ÉÌÆ·Êı¾İÉĞÎ´×¼±¸ÍêÕû£¡");
		}
		vo.setTwoFloor(createEvenFloorVo(products2));
		//3Â¥Êı¾İ
		List<ActionProduct> products3 = aProductDao.findProductsByProductCategory(ConstUtil.ProductType.TYPE_GCQZJJX);
		if(products3.size()<8) {
			return SverResponse.createByErrorMessage("3Â¥ÉÌÆ·Êı¾İÉĞÎ´×¼±¸ÍêÕû£¡");
		}
		vo.setThreeFloor(createOddFloorVo(products3));
		//4Â¥Êı¾İ
		List<ActionProduct> products4 = aProductDao.findProductsByProductCategory(ConstUtil.ProductType.TYPE_LMJX);
		if(products4.size()<8) {
			return SverResponse.createByErrorMessage("4Â¥ÉÌÆ·Êı¾İÉĞÎ´×¼±¸ÍêÕû£¡");
=======
	@Override
	public SverResponse<List<ActionProduct>> findHotProducts(String num) {
		List<ActionProduct> products = productDao.findHotProducts(num);
	if(products.size()<5) {
		return SverResponse.createByErrorMessage("å°šæœªè®¾ç½®çƒ­é”€å•†å“ï¼");
	}
		return SverResponse.createRespBySuccess(products);
	}




	public SverResponse<ActionProductFloorVo> findFloorProducts() {
		ActionProductFloorVo vo = new ActionProductFloorVo();
		//1æ¥¼æ•°æ®
		List<ActionProduct> products1 = productDao.findProductsByProductCategory(ConstUtil.ProductType.TYPE_HNTJX);
		if(products1.size()<8) {
			return SverResponse.createByErrorMessage("1æ¥¼å•†å“æ•°æ®å°šæœªå‡†å¤‡å®Œæ•´ï¼");
		}
		vo.setOneFloor(createOddFloorVo(products1));
		//2æ¥¼æ•°æ®
		List<ActionProduct> products2 = productDao.findProductsByProductCategory(ConstUtil.ProductType.TYPE_JZQZJJX);
		if(products2.size()<8) {
			return SverResponse.createByErrorMessage("2æ¥¼å•†å“æ•°æ®å°šæœªå‡†å¤‡å®Œæ•´ï¼");
		}
		vo.setTwoFloor(createEvenFloorVo(products2));
		//3æ¥¼æ•°æ®
		List<ActionProduct> products3 = productDao.findProductsByProductCategory(ConstUtil.ProductType.TYPE_GCQZJJX);
		if(products3.size()<8) {
			return SverResponse.createByErrorMessage("3æ¥¼å•†å“æ•°æ®å°šæœªå‡†å¤‡å®Œæ•´ï¼");
		}
		vo.setThreeFloor(createOddFloorVo(products3));
		//4æ¥¼æ•°æ®
		List<ActionProduct> products4 = productDao.findProductsByProductCategory(ConstUtil.ProductType.TYPE_LMJX);
		if(products4.size()<8) {
			return SverResponse.createByErrorMessage("4æ¥¼å•†å“æ•°æ®å°šæœªå‡†å¤‡å®Œæ•´ï¼");
>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77
		}
		vo.setFourFloor(createEvenFloorVo(products4));
		return SverResponse.createRespBySuccess(vo);
	}
	/**
<<<<<<< HEAD
	 * ´´½¨ÆæÊıÂ¥²ãVO
=======
	 * åˆ›å»ºå¥‡æ•°æ¥¼å±‚VO
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
	 * ºóÌ¨¹ÜÀí¶Ë²éÑ¯·ûºÏÌõ¼şµÄÉÌÆ·ĞÅÏ¢£¬ºóÌ¨²»·ÖÒ³£¬Ç°Ì¨·ÖÒ³
=======
	 * åå°ç®¡ç†ç«¯æŸ¥è¯¢ç¬¦åˆæ¡ä»¶çš„å•†å“ä¿¡æ¯ï¼Œåå°ä¸åˆ†é¡µï¼Œå‰å°åˆ†é¡µ
>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77
	 */
	@Override
	public SverResponse<List<ActionProductListVo>> findProduts(ActionProduct product) {
		
		if(product.getName()!=null) {
			product.setName(new StringBuilder().append("%").append(product.getName()).append("%").toString());
		}
<<<<<<< HEAD
		//¶ÁÈ¡Êı¾İ
		List<ActionProduct> products = aProductDao.findProductsNoPage(product);
		//·â×°VO
=======
		//è¯»å–æ•°æ®
		List<ActionProduct> products = productDao.findProductsNoPage(product);
		//å°è£…VO
>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77
		List<ActionProductListVo> voList = Lists.newArrayList();
		for(ActionProduct p:products) {
			voList.add(createProductListVo(p));
		}	
		return SverResponse.createRespBySuccess(voList);
	}
}


