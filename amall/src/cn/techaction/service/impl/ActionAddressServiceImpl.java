package cn.techaction.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionAddressDao;
import cn.techaction.pojo.ActionAddress;
import cn.techaction.service.ActionAddrService;

@Service
public class ActionAddressServiceImpl implements ActionAddrService {

	@Autowired
	private ActionAddressDao aAddressDao;
	
	@Override
	public SverResponse<List<ActionAddress>> findAddrsByUserId(Integer userId) {
		if(userId==null) {
			return SverResponse.createByErrorMessage("��������");
		}
		List<ActionAddress> list= aAddressDao.findAddrsByUserId(userId);
		return SverResponse.createRespBySuccess(list);
	}

	@Override
	public SverResponse<String> addAddress(ActionAddress addr) {
		if(addr==null) {
			return SverResponse.createByErrorMessage("��������");
		}
		//�ж����е�ַ���Ƿ���Ĭ�ϵ�ַ�����û��������¼ΪĬ�ϵ�ַ������Ϊһ���ַ
		int count= aAddressDao.findDefaultAddrByUserId(addr.getUid());
		if(count==0) {
			addr.setDfault(true);
		}else {
			addr.setDfault(false); 
		}
		addr.setCreated(new Date());
		addr.setUpdated(new Date());
		int rs = aAddressDao.insertAddress(addr);
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("��ַ�����ɹ���");
		}
		return SverResponse.createRespBySuccessMessage("��ַ����ʧ�ܣ�");
	}

	@Override
	public SverResponse<String> delAddress(Integer userId,Integer id) {
		if(id==null) {
			return SverResponse.createByErrorMessage("��������");
		}
		int rs = aAddressDao.deleteAddress(id);
		//�鿴�Ƿ����Ĭ�ϵ�ַ
		int count= aAddressDao.findDefaultAddrByUserId(userId);
		if(count<=0) {
			//����Ĭ�ϵ�ַ
			List<ActionAddress> lists= aAddressDao.findAddrsByUserId(userId);
			if(lists.size()>0) {
				ActionAddress addr = lists.get(0);
				addr.setDfault(true);
				aAddressDao.updateAddress(addr);
			}
		}
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("��ַɾ���ɹ���");
		}
		return SverResponse.createRespBySuccessMessage("��ַɾ��ʧ�ܣ�");
	}

	@Override
	public SverResponse<String> updateAddress(ActionAddress addr) {
		if(addr==null) {
			return SverResponse.createByErrorMessage("��������");
		}
		addr.setUpdated(new Date());
		int rs = aAddressDao.updateAddress(addr);
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("��ַ���³ɹ���");
		}
		return SverResponse.createRespBySuccessMessage("��ַ����ʧ�ܣ�");
	}

	
	@Override
	public SverResponse<String> updateAddrDefaultStatus(Integer userId, Integer id) {
		if(id==null || userId==null) {
			return SverResponse.createByErrorMessage("��������");
		}
		//��ȡԭ��Ĭ�ϵĵ�ַ
		ActionAddress oldAddr = aAddressDao.findDefaultAddr(userId);
		if(oldAddr!=null) {
			//ȡ��ԭ����Ĭ�ϵ�ַ
			oldAddr.setDfault(false);
			oldAddr.setUpdated(new Date());
			if(aAddressDao.updateAddress(oldAddr)<=0) {
				return SverResponse.createRespBySuccessMessage("Ĭ�ϵ�ַ����ʧ�ܣ�");
			}
		}
		//�����µ�Ĭ�ϵ�ַ
		ActionAddress newAddr = new ActionAddress();
		newAddr.setDfault(true);
		newAddr.setId(id);
		newAddr.setUpdated(new Date());
		if(aAddressDao.updateAddress(newAddr)<=0) {
			return SverResponse.createRespBySuccessMessage("Ĭ�ϵ�ַ����ʧ�ܣ�");
		}
		return SverResponse.createRespBySuccessMessage("Ĭ�ϵ�ַ���óɹ���");
	}
	
	

}
