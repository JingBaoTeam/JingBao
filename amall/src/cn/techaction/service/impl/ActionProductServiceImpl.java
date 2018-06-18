package cn.techaction.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
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

	@Autowired
	private ActionProductDao aProductDao;
	@Autowired
	private ActionParamsDao aParamsDao;
	
	/**
	 * �����������Ʒ��Ϣ
	 * @throws Exception 
	 */
	@Override
	public SverResponse<String> saveOrUpdateProduct(ActionProduct product)  {
		if(product!=null) {
			//��ͼ���е�һ����λ��ͼ
			if(StringUtils.isNoneBlank(product.getSubImages())) {
				String[] array = product.getSubImages().split(",");
				//JSONArray jsonArray = new JSONArray(product.getSubImages());
				product.setIconUrl(array[0]);
			}
			
			if(product.getId() != null) {
				product.setUpdated(new Date());
				int rs = aProductDao.updateProduct(product);
				if(rs >0) {
					return SverResponse.createRespBySuccessMessage("��Ʒ���³ɹ���");
				}
				return SverResponse.createByErrorMessage("��Ʒ����ʧ�ܣ�");
			}else {
				//����
				product.setStatus(1);  //��Ʒ��״̬��1-���ۣ��ձ��棻2-�ϼܣ����ۣ�3-�¼ܣ�ͣ��
				product.setHot(2);   //�Ƿ�������1-�ǣ�2-��
				product.setUpdated(new Date());
				product.setCreated(new Date());
				int rs = aProductDao.insertProduct(product);
				if(rs >0) {
					return SverResponse.createRespBySuccessMessage("��Ʒ�����ɹ���");
				}
				return SverResponse.createByErrorMessage("��Ʒ����ʧ�ܣ�");
			}
		}
		return SverResponse.createByErrorMessage("��Ʒ��������");
	}

	
	/**
	 * ������Ʒ״̬��Ϣ
	 * @throws Exception 
	 */
	@Override
	public SverResponse<String> updateStatus(Integer productId, Integer status) {
		if(productId==null || status==null) {
			return SverResponse.createByErrorMessage("�Ƿ�������");
		}
		ActionProduct product = new ActionProduct();
		product.setId(productId);
		product.setStatus(status);
		product.setUpdated(new Date());
		int rs = aProductDao.updateProduct(product);
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("�޸Ĳ�Ʒ״̬�ɹ���");
		}
		return SverResponse.createByErrorMessage("�޸Ĳ�Ʒ״̬ʧ�ܣ�");
	}

	

	@Override
	public SverResponse<String> updateHotStatus(Integer productId, Integer isHot) {
		if(productId==null || isHot==null) {
			return SverResponse.createByErrorMessage("�Ƿ�������");
		}
		ActionProduct product = new ActionProduct();
		product.setId(productId);
		product.setHot(isHot);
		product.setUpdated(new Date());
		int rs = aProductDao.updateProduct(product);
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("�޸Ĳ�Ʒ״̬�ɹ���");
		}
		return SverResponse.createByErrorMessage("�޸Ĳ�Ʒ״̬ʧ�ܣ�");
	}


	/**
	 * ������Ʒ������Ϣ
	 */
	@Override
	public SverResponse<ActionProduct> findProductDetailById(Integer productId) {
		if(productId==null) {
			return SverResponse.createByErrorMessage("��Ʒ��Ų���Ϊ�գ�");
		}
		ActionProduct product = aProductDao.findProductById(productId);
		if(product==null) {
			return SverResponse.createByErrorMessage("��Ʒ�Ѿ��¼ܣ�");
		}
		return SverResponse.createRespBySuccess(product);
	}


	//��װVO����
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

	//��Ʒ�б���ҳ
	@Override
	public SverResponse<PageBean<ActionProductListVo>> findProductsByCondition(ActionProduct product, int pageNum,
			int pageSize) {
		//���ҷ����������ܼ�¼��
		if(product.getName()!=null) {
			product.setName(new StringBuilder().append("%").append(product.getName()).append("%").toString());
		}
		int totalRecord = aProductDao.getTotalCount(product);
		//������ҳ��װ����
		PageBean<ActionProductListVo> pageBean = new PageBean<>(pageNum, pageSize, totalRecord);
		//��ȡ����
		List<ActionProduct> products = aProductDao.findProducts(product,pageBean.getStartIndex(), pageSize);
		//��װVO
		List<ActionProductListVo> voList = Lists.newArrayList();
		for(ActionProduct p:products) {
			voList.add(createProductListVo(p));
		}
		pageBean.setData(voList);
				
		return SverResponse.createRespBySuccess(pageBean);
	}


	/**
	 * �ļ��ϴ�����
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
	            //�ļ��Ѿ��ϴ��ɹ���
	     } catch (IOException e) {
	        return SverResponse.createByErrorMessage("�ļ��ϴ�����");
	     }
	     Map<String,String> fileMap = Maps.newHashMap();
         fileMap.put("url",ConstUtil.UPLOAD_IMAGES_PATH+targetFile.getName());
         System.out.println(fileMap.get("url"));
         return SverResponse.createRespBySuccess(fileMap);
	}


	/**
	 * �����̳���ʾ��Ʒ����
	 */
	@Override
	public SverResponse<ActionProduct> findProductDetailForPortal(Integer productId) {
		if(productId==null) {
			return SverResponse.createByErrorMessage("��Ʒ��Ų���Ϊ�գ�");
		}
		ActionProduct product = aProductDao.findProductById(productId);
		if(product==null) {
			return SverResponse.createByErrorMessage("��Ʒ�Ѿ��¼ܣ�");
		}
		if(product.getStatus()==ConstUtil.ProductStatus.STATUS_OFF_SALE) {
			return SverResponse.createByErrorMessage("��Ʒ�Ѿ��¼ܣ�");
		}
		return SverResponse.createRespBySuccess(product);
	}


	@Override
	public SverResponse<PageBean<ActionProductListVo>> findProductsForPortal(Integer productTypeId, Integer partsId,
			int pageNum, int pageSize) {
		//���ҷ����������ܼ�¼��
		ActionProduct product = new ActionProduct();
		product.setProductId(productTypeId);
		product.setPartsId(partsId);
		int totalRecord = aProductDao.getTotalCount(product);
		//������ҳ��װ����
		PageBean<ActionProductListVo> pageBean = new PageBean<>(pageNum, pageSize, totalRecord);
		//��ȡ����
		List<ActionProduct> products = aProductDao.findProducts(product,pageBean.getStartIndex(), pageSize);
		//��װVO
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
//			return SverResponse.createByErrorMessage("��δ����������Ʒ��");
//		}
		return SverResponse.createRespBySuccess(products);
	}


	@Override
	public SverResponse<ActionProductFloorVo> findFloorProducts() {
		ActionProductFloorVo vo = new ActionProductFloorVo();
		//1¥����
		List<ActionProduct> products1 = aProductDao.findProductsByProductCategory(ConstUtil.ProductType.TYPE_HNTJX);
		if(products1.size()<8) {
			return SverResponse.createByErrorMessage("1¥��Ʒ������δ׼��������");
		}
		vo.setOneFloor(createOddFloorVo(products1));
		//2¥����
		List<ActionProduct> products2 = aProductDao.findProductsByProductCategory(ConstUtil.ProductType.TYPE_JZQZJJX);
		if(products2.size()<8) {
			return SverResponse.createByErrorMessage("2¥��Ʒ������δ׼��������");
		}
		vo.setTwoFloor(createEvenFloorVo(products2));
		//3¥����
		List<ActionProduct> products3 = aProductDao.findProductsByProductCategory(ConstUtil.ProductType.TYPE_GCQZJJX);
		if(products3.size()<8) {
			return SverResponse.createByErrorMessage("3¥��Ʒ������δ׼��������");
		}
		vo.setThreeFloor(createOddFloorVo(products3));
		//4¥����
		List<ActionProduct> products4 = aProductDao.findProductsByProductCategory(ConstUtil.ProductType.TYPE_LMJX);
		if(products4.size()<8) {
			return SverResponse.createByErrorMessage("4¥��Ʒ������δ׼��������");
		}
		vo.setFourFloor(createEvenFloorVo(products4));
		return SverResponse.createRespBySuccess(vo);
	}
	/**
	 * ��������¥��VO
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
	 * ��̨����˲�ѯ������������Ʒ��Ϣ����̨����ҳ��ǰ̨��ҳ
	 */
	@Override
	public SverResponse<List<ActionProductListVo>> findProduts(ActionProduct product) {
		
		if(product.getName()!=null) {
			product.setName(new StringBuilder().append("%").append(product.getName()).append("%").toString());
		}
		//��ȡ����
		List<ActionProduct> products = aProductDao.findProductsNoPage(product);
		//��װVO
		List<ActionProductListVo> voList = Lists.newArrayList();
		for(ActionProduct p:products) {
			voList.add(createProductListVo(p));
		}	
		return SverResponse.createRespBySuccess(voList);
	}
}
