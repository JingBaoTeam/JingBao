package cn.techaction.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import cn.techaction.dao.ActionParamsDao;
import cn.techaction.pojo.ActionParam;

//@author ireson

@Repository
public class ActionParamsDaoImpl implements ActionParamsDao {

	@Resource
	private QueryRunner queryRunner;
	
	@Override
	public int insertParam(ActionParam param) {
		String sql="insert into action_params(parent_id,name,sort_order,status,created,updated,level) "
				+ "values(?,?,?,?,?,?,?)";
		Object[] params= {param.getParent_id(),param.getName(),param.getSort_order(),param.isStatus(),param.getCreated(),param.getUpdated(),param.getLevel()};
		try {
			return queryRunner.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int updateParam(ActionParam param) {
		String sql="update action_params set name = ? ,updated = ? where id = ?";
		Object[] params= {param.getName(),param.getUpdated(),param.getId()};
		try {
			return queryRunner.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<ActionParam> findParamsByParentId(Integer parentId) {
		String sql="select id, parent_id,name,sort_order,status,created,updated,level from  action_params where parent_id = ? order by sort_order";
		try {
			return queryRunner.query(sql, new BeanListHandler<ActionParam>(ActionParam.class),parentId);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ActionParam findParamById(Integer id) {
		String sql="select id, parent_id,name,sort_order,status,created,updated,level from  action_params where id = ?";
		try {
			return queryRunner.query(sql, new BeanHandler<ActionParam>(ActionParam.class),id);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int deleteParam(Integer id) {
		String sql="delete from action_params where id = ?";
		
		try {
			return queryRunner.update(sql, id);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	
}
