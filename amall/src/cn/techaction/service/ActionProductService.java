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
	 * ����Ʒ���빺�ﳵ
	 * @param product
	 * @return
	 */
	public SverResponse<String> saveOrUpdateProduct(ActionProduct product);
	/**
	 * ������Ʒ��״̬��Ϣ
	 * @param productId	��Ʒ���
	 * @param status	״̬��1-���ۣ�2�ϼܣ�3�¼�
	 * @return
	 */
	public SverResponse<String> updateStatus(Integer productId,Integer status);
	
	/**
	 * ������Ʒ�Ƿ�Ϊ����״̬
	 * @param productId ��Ʒ���
	 * @param isHot		����״̬��1-������2-������
	 * @return
	 */
	public SverResponse<String> updateHotStatus(Integer productId,Integer isHot);
	/**
	 * ��ȡ��Ʒ������Ϣ
	 * @param productId	��Ʒ���
	 * @return
	 */
	public SverResponse<ActionProduct> findProductDetailById(Integer productId);
	/**
	 * ��ҳ��ѯ��Ʒ��Ϣ
	 * @param product	��ѯ����
	 * @param pageNum	��ǰҳ��
	 * @param pageSize	ҳ���С
	 * @return
	 */
	public SverResponse<PageBean<ActionProductListVo>> findProductsByCondition(ActionProduct product,int pageNum,int pageSize);
	
	/**
	 * ��ѯ������������Ʒ��Ϣ������ҳ
	 * @param product
	 * @return
	 */
	public SverResponse<List<ActionProductListVo>> findProduts(ActionProduct product);
	
	/**
	 * ͼƬ�ϴ�
	 * @param file
	 * @param path
	 * @return
	 */
	public SverResponse<Map<String, String>> uploadFile(MultipartFile file,String path);
	
	//ǰ̨�̳���Ҫ����Ʒ������Ϣ
	/**
	 * �Ż���������Ʒ��Ų�����Ʒ����
	 * @param productId
	 * @return
	 */
	public SverResponse<ActionProduct> findProductDetailForPortal(Integer productId);
	/**
	 * �Ż������ݲ�Ʒ���ͺ�������Ͳ�����Ʒ��Ϣ
	 * @param productTypeId
	 * @param partsId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public SverResponse<PageBean<ActionProductListVo>> findProductsForPortal(Integer productTypeId,Integer partsId, int pageNum,int pageSize);
	/**
	 * �Ż�������������Ʒ
	 * @param num    ��ѯ��Ʒ������
	 * @return
	 */
	public SverResponse<List<ActionProduct>> findHotProducts(Integer num);
	/**
	 * �Ż�����ȡ��ҳ����¥������
	 * @return
	 */
	public SverResponse<ActionProductFloorVo> findFloorProducts();
}
