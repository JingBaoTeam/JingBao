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
			return SverResponse.createByErrorMessage("参数错误！");
		}
		List<ActionAddress> list= aAddressDao.findAddrsByUserId(userId);
		return SverResponse.createRespBySuccess(list);
	}

	@Override
	public SverResponse<String> addAddress(ActionAddress addr) {
		if(addr==null) {
			return SverResponse.createByErrorMessage("参数错误！");
		}
		//判断已有地址中是否有默认地址，如果没有则本条记录为默认地址，否则为一般地址
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
			return SverResponse.createRespBySuccessMessage("地址新增成功！");
		}
		return SverResponse.createRespBySuccessMessage("地址新增失败！");
	}

	@Override
	public SverResponse<String> delAddress(Integer userId,Integer id) {
		if(id==null) {
			return SverResponse.createByErrorMessage("参数错误！");
		}
		int rs = aAddressDao.deleteAddress(id);
		//查看是否存在默认地址
		int count= aAddressDao.findDefaultAddrByUserId(userId);
		if(count<=0) {
			//设置默认地址
			List<ActionAddress> lists= aAddressDao.findAddrsByUserId(userId);
			if(lists.size()>0) {
				ActionAddress addr = lists.get(0);
				addr.setDfault(true);
				aAddressDao.updateAddress(addr);
			}
		}
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("地址删除成功！");
		}
		return SverResponse.createRespBySuccessMessage("地址删除失败！");
	}

	@Override
	public SverResponse<String> updateAddress(ActionAddress addr) {
		if(addr==null) {
			return SverResponse.createByErrorMessage("参数错误！");
		}
		addr.setUpdated(new Date());
		int rs = aAddressDao.updateAddress(addr);
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("地址更新成功！");
		}
		return SverResponse.createRespBySuccessMessage("地址更新失败！");
	}

	
	@Override
	public SverResponse<String> updateAddrDefaultStatus(Integer userId, Integer id) {
		if(id==null || userId==null) {
			return SverResponse.createByErrorMessage("参数错误！");
		}
		//读取原来默认的地址
		ActionAddress oldAddr = aAddressDao.findDefaultAddr(userId);
		if(oldAddr!=null) {
			//取消原来的默认地址
			oldAddr.setDfault(false);
			oldAddr.setUpdated(new Date());
			if(aAddressDao.updateAddress(oldAddr)<=0) {
				return SverResponse.createRespBySuccessMessage("默认地址设置失败！");
			}
		}
		//设置新的默认地址
		ActionAddress newAddr = new ActionAddress();
		newAddr.setDfault(true);
		newAddr.setId(id);
		newAddr.setUpdated(new Date());
		if(aAddressDao.updateAddress(newAddr)<=0) {
			return SverResponse.createRespBySuccessMessage("默认地址设置失败！");
		}
		return SverResponse.createRespBySuccessMessage("默认地址设置成功！");
	}
	
	

}
