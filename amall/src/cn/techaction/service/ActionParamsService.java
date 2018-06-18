package cn.techaction.service;

import java.util.List;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionParam;
import cn.techaction.vo.ActionParamVo;

public interface ActionParamsService {
	//����Ʒ��
	/**
	 * ������Ʒ������Ϣ
	 * @param param
	 * @return
	 */
	public SverResponse<String> addParam(ActionParam param);
	/**
	 * ������Ʒ����
	 * @param param
	 * @return
	 */
	public SverResponse<String> updateParam(ActionParam param);
	
	/**
	 * ���ݲ���ID�������Ӳ���
	 * @param id
	 * @return
	 */
	public SverResponse<List<ActionParam>> findParamChildren(Integer id);
	/**
	 * �ݹ����ĳ���ڵ�������ӽڵ���Ϣ
	 * @param id
	 * @return
	 */
	public SverResponse<List<ActionParam>> findParamAndAllChildrenById(Integer id);
	
	/**
	 * ��ѯ��Ʒ���Ͳ�������һ���ڵ�
	 * @return
	 */
	public SverResponse<List<ActionParam>> findProdutTypeParams();
	/**
	 * ��ѯ������Ͳ������������ڵ�
	 * @param productTypeId ��Ʒ���Ͳ���
	 * @return
	 */
	public SverResponse<List<ActionParamVo>> findPartsTypeParamsByProductTypeId(Integer productTypeId);
	/**
	 * ��ȡȫ·�����Ͳ���
	 * @return
	 */
	public SverResponse<List<ActionParam>> findAllPathParams();
	
	
	/************************ǰ̨�õ��ķ���*********************************/
	
	/**
	 * ��ȡȫ��������Ϣ
	 * @return
	 */
	public SverResponse<List<ActionParam>> findAllParams();
}
