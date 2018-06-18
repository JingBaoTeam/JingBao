package cn.techaction.service.impl;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.UserDao;
import cn.techaction.pojo.User;
import cn.techaction.service.UserService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.utils.MD5Util;
import cn.techaction.utils.TokenCache;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;

	@Override
	public User login(String account, String password) {
		return userDao.findUserByAccountAndPwd(account, password);
	}

	@Override
	public SverResponse<User> doLogin(String account, String password) {
		//�ж��û��Ƿ����
		int rs = userDao.checkUserByAccount(account);
		if(rs==0) {
			return SverResponse.createByErrorMessage("�û������ڣ�");
		}
		//�����û�������������û�
		String md5Pwd = MD5Util.MD5Encode(password, "utf-8", false);
		User user = userDao.findUserByAccountAndPwd(account, md5Pwd);
		if(user==null) {
			return SverResponse.createByErrorMessage("�������");
		}
		//�������ÿգ���ֹй��
		user.setPassword(StringUtils.EMPTY);
		return SverResponse.createRespBySuccess("��½�ɹ�", user);
	}

	@Override
	public SverResponse<String> doRegister(User user) {
		//����û����Ƿ��Ѿ�����
		SverResponse<String> resp = checkValidation(user.getAccount(), ConstUtil.TYPE_ACCOUNT);
		if(!resp.isSuccess()) {
			return resp;
		}
		//��������Ƿ��Ѿ�ע��
		resp = checkValidation(user.getEmail(), ConstUtil.TYPE_EMAIL);
		if(!resp.isSuccess()) {
			return resp;
		}
		//ָ���û���ɫ��ͨ��ǰ��ע��Ķ��ǿͻ�
		user.setRole(ConstUtil.Role.ROLE_CUSTOMER);
		//��������м��ܴ���
		user.setPassword(MD5Util.MD5Encode(user.getPassword(), "UTF-8", false));
		//ִ��ע��
		Date curDate = new Date();
		
		user.setCreate_time(curDate);
		user.setUpdate_time(curDate);
		
		int rs = userDao.insertUser(user);
		if(rs==0) {
			return SverResponse.createByErrorMessage("ע��ʧ�ܣ�");
		}
		return  SverResponse.createRespBySuccessMessage("ע��ɹ���");
	}

	/**
	 * �����Ϣ�Ƿ���Ч
	 */
	@Override
	public SverResponse<String> checkValidation(String str, String type) {
		if(StringUtils.isNotBlank(type)) {
			if(ConstUtil.TYPE_ACCOUNT.equals(type)) {
				int rs = userDao.checkUserByAccount(str);
				if(rs > 0) {
					return SverResponse.createByErrorMessage("�û����Ѿ�����");
				}
			}
			if(ConstUtil.TYPE_EMAIL.equals(type)) {
				int rs = userDao.checkUserByEmail(str);
				if(rs > 0) {
					return SverResponse.createByErrorMessage("Email�Ѿ�����");
				}
			}
			if(ConstUtil.TYPE_PHONE.equals(type)) {
				int rs = userDao.checkUserByPhone(str);
				if(rs > 0) {
					return SverResponse.createByErrorMessage("�绰�����Ѿ����ڣ�");
				}
			}
		}else {
			return SverResponse.createByErrorMessage("��Ϣ��֤������");
		}
		return SverResponse.createRespBySuccessMessage("��Ϣ��֤�ɹ���");
	}

	
	@Override
	public SverResponse<String> findUserQuestion(String account) {
		String question =userDao.findUserQuestion(account);
		if(question==null) {
			return SverResponse.createByErrorMessage("δ����������ʾ���⣡");
		}
		return SverResponse.createRespBySuccess(question);
	}

	/**
	 * ����û����������
	 */
	@Override
	public SverResponse<String> checkUserAnswer(String account, String question, String asw) {
		int rs =  userDao.checkUserAnswer(account, question, asw);
		if(rs > 0) {
			//����ȷ,����Token
			String token = UUID.randomUUID().toString();
			//���뻺��
			TokenCache.setCacheData(TokenCache.PREFIX+account, token);
			return SverResponse.createRespBySuccessMessage(token);
		}
		return SverResponse.createByErrorMessage("����𰸴���");
	}

	@Override
	public SverResponse<String> resetPassword(String account, String password, String token) {
		//���token
		if(StringUtils.isBlank(token)) {
			return SverResponse.createByErrorMessage("Token��������");
		}
		//����û���
		SverResponse<String> resp= checkValidation(account, ConstUtil.TYPE_ACCOUNT);
		if(resp.isSuccess()) {
			//�û�������
			return SverResponse.createByErrorMessage("�û�������");
		}
		//��黺���е�token
		String oldToken = TokenCache.getCacheData(TokenCache.PREFIX+account);
		if(StringUtils.isBlank(oldToken)) {
			return SverResponse.createByErrorMessage("token��Ч��");
		}
		if(StringUtils.equals(oldToken, token)) {
			String pwd = MD5Util.MD5Encode(password, "UTF-8", false);
			//��������
			int rs = userDao.updatePasswordByAccount(account,pwd);
			if(rs>0) {
				return SverResponse.createRespBySuccess("�����޸ĳɹ���");
			}
			
		}else {
			return SverResponse.createByErrorMessage("Token����,�����»�ȡ��");
		}
		return SverResponse.createByErrorMessage("�����޸�ʧ�ܣ�");
	}

	@Override
	public SverResponse<String> updatePassword(User user, String newPassword, String oldPassword) {
		//��ֹ����ԽȨ������û��ľ������Ƿ���ȷ
		oldPassword = MD5Util.MD5Encode(oldPassword, "UTF-8", false);
		int rs = userDao.checkPassword(user.getAccount(), oldPassword);
		if(rs == 0) {
			return SverResponse.createByErrorMessage("ԭʼ�������");
		}
		newPassword = MD5Util.MD5Encode(newPassword, "UTF-8", false);
		user.setPassword(newPassword);
		user.setUpdate_time(new Date());
		rs =  userDao.updateUserInfo(user);
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("�����޸ĳɹ���");
		}
		return SverResponse.createByErrorMessage("�����޸�ʧ�ܣ�");
	}

	//�޸��û���Ϣ
	@Override
	public SverResponse<User> updateUserInfo(User user) {
		User updateUser = new User();
	    updateUser.setId(user.getId());
	    updateUser.setAccount(user.getAccount());
	    updateUser.setEmail(user.getEmail());
	    updateUser.setPhone(user.getPhone());
	    updateUser.setQuestion(user.getQuestion());
	    updateUser.setAsw(user.getAsw());
	    updateUser.setRole(user.getRole());
	    updateUser.setCreate_time(user.getCreate_time());
	    updateUser.setUpdate_time(new Date());
		int rs = userDao.updateUserInfo(updateUser);
		if(rs >0) {
			return SverResponse.createRespBySuccess("�û���Ϣ�޸ĳɹ���",updateUser);
		}
		return SverResponse.createByErrorMessage("�û���Ϣ�޸�ʧ�ܣ�"); 
	}

	
	/**
	 * �жϵ�ǰ�û��Ƿ��ǹ���Ա
	 */
	@Override
	public SverResponse<String> isAdmin(User user) {
		if(user.getRole()==ConstUtil.Role.ROLE_ADMIN) {
			return SverResponse.createRespBySuccess();
		}
		return SverResponse.createRespByError();
	}
}
