package cn.techaction.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionParamsDao;
import cn.techaction.pojo.ActionParam;
import cn.techaction.service.ActionParamsService;
import cn.techaction.vo.ActionParamVo;

@Service
public class ActionParamsServiceImpl implements ActionParamsService {

	@Autowired
	private ActionParamsDao aParamDao;
	
	@Override
	public SverResponse<String> addParam(ActionParam param) {
		if(StringUtils.isBlank(param.getName())) {
			SverResponse.createByErrorMessage("��Ʒ�������Ʋ���Ϊ�գ�");
		}
		param.setStatus(true);
		param.setCreated(new Date());
		param.setUpdated(new Date());
		param.setLevel(getParamLevel(param.getParent_id()));
		int rs = aParamDao.insertParam(param);
		if(rs >0) {
			return SverResponse.createRespBySuccessMessage("��Ʒ���������ɹ���");
		}
		return  SverResponse.createByErrorMessage("��Ʒ��������ʧ�ܣ�");
	}
	
	//���������ڵ��Leve���丸�ڵ��level=1
	private int getParamLevel(int parentId) {
		//��ȡ���׽ڵ�
		ActionParam parentParam= aParamDao.findParamById(parentId);
		if(parentParam!=null) {
			int count = parentParam.getLevel();
			return count+1;
		}else {
			return 0;
		}
	}

	@Override
	public SverResponse<String> updateParam(ActionParam param) {
		if(param.getId()==0 || StringUtils.isBlank(param.getName())) {
			SverResponse.createByErrorMessage("��������");
		}
		param.setUpdated(new Date());
		int rs= aParamDao.updateParam(param);
		if(rs >0) {
			return SverResponse.createRespBySuccessMessage("��Ʒ�����޸ĳɹ���");
		}
		return  SverResponse.createByErrorMessage("��Ʒ�����޸�ʧ�ܣ�");
	}

	@Override
	public SverResponse<List<ActionParam>> findParamChildren(Integer id) {
		List<ActionParam> list = aParamDao.findParamsByParentId(id);
		return SverResponse.createRespBySuccess(list);
	}

	
	@Override
	public SverResponse<List<ActionParam>> findParamAndAllChildrenById(Integer id) {
		Set<ActionParam> paramSet = Sets.newHashSet();
		//�ݹ�����ӽڵ�
		findChildren(paramSet, id);
		List<ActionParam> paramsList = Lists.newArrayList();
		if(id!=null) {
			for(ActionParam param: paramSet) {
				paramsList.add(param);
			}
		}
		return SverResponse.createRespBySuccess(paramsList);
	}

	
	//�ݹ��㷨
	private Set<ActionParam> findChildren(Set<ActionParam> paramSet,Integer id){
		ActionParam param = aParamDao.findParamById(id);
		if(param!=null) {
			paramSet.add(param);
		}
		//�����ӽڵ�
		List<ActionParam> paramList = aParamDao.findParamsByParentId(id);
		for(ActionParam p:paramList) {
			findChildren(paramSet, p.getId());
		}
		return paramSet;
	}
	
	/**
	 * ��ѯһ���ڵ�
	 */
	@Override
	public SverResponse<List<ActionParam>> findProdutTypeParams() {
		List<ActionParam> list = aParamDao.findParamsByParentId(0);
		return SverResponse.createRespBySuccess(list);
	}
	
	
	@Override
	public SverResponse<List<ActionParamVo>> findPartsTypeParamsByProductTypeId(Integer productTypeId) {
		//�����ڵ�
		List<ActionParam> paramList = aParamDao.findParamsByParentId(productTypeId);
		//�ݹ��ѯÿ�������ڵ�������ӽڵ�
		for(ActionParam param : paramList) {
			findDirectChildren(param);
		}
		//��װVO
		List<ActionParamVo> volist = Lists.newArrayList();
		for(ActionParam secondLevel : paramList) {
			//����
			if(secondLevel.getChildren().size()>0) {
				//����
				for(ActionParam thirdLevel: secondLevel.getChildren()) {
					ActionParamVo vo = new ActionParamVo();
					vo.setId(thirdLevel.getId());
					vo.setName(secondLevel.getName()+"/"+thirdLevel.getName());
					volist.add(vo);
				}
			}else {
				ActionParamVo vo = new ActionParamVo();
				vo.setId(secondLevel.getId());
				vo.setName(secondLevel.getName());
				volist.add(vo);
			}
		}
		return SverResponse.createRespBySuccess(volist);
	}
	
	/**
	 * �ݹ����
	 * @param parentParam
	 */
	private void findDirectChildren(ActionParam parentParam){
		//�����ӽڵ�
		List<ActionParam> paramList = aParamDao.findParamsByParentId(parentParam.getId());
		parentParam.setChildren(paramList);
		for(ActionParam p:paramList) {
			findDirectChildren(p);
		}
	}
	
	
	@Override
	public SverResponse<List<ActionParam>> findAllPathParams() {
		//����һ���ӽڵ�
		List<ActionParam> paramList = aParamDao.findParamsByParentId(0);
		for(ActionParam param : paramList) {
			findDirectChildren(param);
		}
		//��װVO
		List<ActionParam> list = Lists.newArrayList();
		for(ActionParam param : paramList) {
			createDeepParam(list, param);
		}
		return SverResponse.createRespBySuccess(list);
	}

	private void createDeepParam(List<ActionParam> list,ActionParam parent) {
		list.add(parent);
		for(ActionParam p : parent.getChildren()) {
			p.setName(parent.getName()+"/"+p.getName());
			createDeepParam(list, p);
		}
	}
	
	
	/************************ǰ̨��������ȡ���еķ�����Ϣ*******************************/
	
	
	@Override
	public SverResponse<List<ActionParam>> findAllParams() {
		//����һ���ӽڵ�
		List<ActionParam> paramList = aParamDao.findParamsByParentId(0);
		for(ActionParam param : paramList) {
			findDirectChildren(param);
		}
		return SverResponse.createRespBySuccess(paramList);
	}
	
	
	
}
