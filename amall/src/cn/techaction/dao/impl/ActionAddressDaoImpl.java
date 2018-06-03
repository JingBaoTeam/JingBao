package cn.techaction.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import cn.techaction.dao.ActionAddressDao;
import cn.techaction.pojo.ActionAddress;

@Repository
public class ActionAddressDaoImpl implements ActionAddressDao {

	@Resource
	private QueryRunner queryRunner;
	
	@Override
	public int insertAddress(ActionAddress address) {
		String sql = "insert into action_address(user_id,name,phone,mobile,province,city,district,addr,zip,dfault,created,updated) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] param = {
				address.getUid(),address.getName(),address.getPhone(),address.getMobile(),
				address.getProvince(),address.getCity(),address.getDistrict(),address.getAddr(),address.getZip(),
				address.getDfault(),address.getCreated(),address.getUpdated()
		};
		try {
			return queryRunner.update(sql,param);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<ActionAddress> findAddrsByUserId(Integer userId) {
		String sql="select id ,user_id as uid,name,phone,mobile,province,city,district,addr,zip,dfault,created,updated from action_address where user_id=? order by updated desc";
		try {
			return queryRunner.query(sql, new BeanListHandler<ActionAddress>(ActionAddress.class), userId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ActionAddress findAddrsById(Integer addrId) {
		String sql="select id ,user_id as uid,name,phone,mobile,province,city,district,addr,zip,dfault,created,updated from action_address where id=?";
		try {
			return queryRunner.query(sql, new BeanHandler<ActionAddress>(ActionAddress.class), addrId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int updateAddress(ActionAddress address) {
		String sql="update action_address set updated = ?";
		List<Object> params = new ArrayList<>();
		params.add(address.getUpdated());
		if(!StringUtils.isEmpty(address.getName())) {
			sql+=",name = ?";
			params.add(address.getName());
		}
		if(!StringUtils.isEmpty(address.getPhone())) {
			sql+=",phone = ?";
			params.add(address.getPhone());
		}
		if(!StringUtils.isEmpty(address.getMobile())) {
			sql+=",mobile = ?";
			params.add(address.getMobile());
		}
		if(!StringUtils.isEmpty(address.getProvince())) {
			sql+=",province = ?";
			params.add(address.getProvince());
		}
		if(!StringUtils.isEmpty(address.getCity())) {
			sql+=",city = ?";
			params.add(address.getCity());
		}
		if(!StringUtils.isEmpty(address.getDistrict())) {
			sql+=",district = ?";
			params.add(address.getDistrict());
		}
		if(!StringUtils.isEmpty(address.getAddr())) {
			sql+=",addr = ?";
			params.add(address.getAddr());
		}
		if(!StringUtils.isEmpty(address.getZip())) {
			sql+=",zip = ?";
			params.add(address.getZip());
		}
		if(address.getDfault()!=null) {
			sql+=",dfault = ?";
			params.add(address.getDfault());
		}
		sql+=" where id = ?";
		params.add(address.getId());
		
		try {
			return queryRunner.update(sql,params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int deleteAddress(Integer id) {
		String sql = "delete from action_address where id = ?";
		try {
			return queryRunner.update(sql,id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int findDefaultAddrByUserId(Integer userId) {
		String sql="select count(id) as num from action_address where user_id=? and dfault=1";
		try {
			return queryRunner.query(sql, new ColumnListHandler<Long>("num"), userId).get(0).intValue();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public ActionAddress findDefaultAddr(Integer userId) {
		String sql="select id ,user_id as uid,name,phone,mobile,province,city,district,addr,zip,dfault,created,updated from action_address  where user_id=? and dfault=1";
		try {
			return queryRunner.query(sql, new BeanHandler<ActionAddress>(ActionAddress.class), userId);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	

}
