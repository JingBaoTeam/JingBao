package cn.techaction.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import cn.techaction.dao.UserDao;
import cn.techaction.pojo.User;

@Repository
public class UserDaoImpl implements UserDao {
	
	@Resource
	private QueryRunner queryRunner;

	@Override
	public User findUserByAccountAndPwd(String account, String password) {
		String sql = "select * from action_users where account = ? and password=?";
		try {
			return queryRunner.query(sql, new BeanHandler<User>(User.class), account,password);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int checkUserByAccount(String account) {
		String sql="select count(account) as num from action_users where account = ?";
		try {
			List<Long> rs = queryRunner.query(sql, new ColumnListHandler<Long>("num"),account);
			return rs.size()>0?rs.get(0).intValue():0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int checkUserByEmail(String email) {
		String sql="select count(account) as num from action_users where email = ?";
		try {
			List<Long> rs = queryRunner.query(sql, new ColumnListHandler<Long>("num"),email);
			return rs.size()>0?rs.get(0).intValue():0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int checkUserByPhone(String phone) {
		String sql="select count(account) as num from action_users where phone = ?";
		try {
			List<Long> rs = queryRunner.query(sql, new ColumnListHandler<Long>("num"),phone);
			return rs.size()>0?rs.get(0).intValue():0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int insertUser(User user) {
		String sql="insert into action_users(account,password,email,phone,question,asw,role,create_time,update_time) "
				+ "values(?,?,?,?,?,?,?,?,?)";
		Object[] params= {user.getAccount(),user.getPassword(),user.getEmail(),user.getPhone(),user.getQuestion(),user.getAsw(),user.getRole(),user.getCreate_time(),user.getUpdate_time()};
		try {
			return queryRunner.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public String findUserQuestion(String account) {
		String sql="select question from action_users where account = ?";
		try {
			List<String> questions = queryRunner.query(sql, new ColumnListHandler<String>("question"),account);
			return questions.get(0);
		} catch (SQLException e) {
			return null;
		}
	}

	@Override
	public int checkUserAnswer(String account, String question, String asw) {
		String sql="select count(account) as num from action_users where account = ? and question = ? and asw = ?";
		try {
			List<Long> rs = queryRunner.query(sql, new ColumnListHandler<Long>("num"),account,question,asw);
			return rs.size()>0?rs.get(0).intValue():0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int updatePasswordByAccount(String account, String password) {
		String sql="update action_users set password=? where account = ?";
		try {
			return queryRunner.update(sql, password,account);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int checkPassword(String account, String password) {
		String sql = "select count(account) as num from action_users where account = ? and password=?";
		try {
			List<Long> rs = queryRunner.query(sql, new ColumnListHandler<Long>("num"),account,password);
			return rs.size()>0?rs.get(0).intValue():0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int updateUserInfo(User user) {
		String sql=" update action_users set update_time = ? ";
		List<Object> params = new ArrayList<>();
		params.add(user.getUpdate_time());
		if(!StringUtils.isEmpty(user.getPassword())) {
			sql+=" ,password = ?";
			params.add(user.getPassword());
		}
		if(!StringUtils.isEmpty(user.getEmail())) {
			sql+=" ,email = ?";
			params.add(user.getEmail());
		}
		if(!StringUtils.isEmpty(user.getPhone())) {
			sql+=" ,phone = ?";
			params.add(user.getPhone());
		}
		if(!StringUtils.isEmpty(user.getQuestion())) {
			sql+=" ,question = ?";
			params.add(user.getQuestion());
		}
		if(!StringUtils.isEmpty(user.getAsw())) {
			sql+=" ,asw = ?";
			params.add(user.getAsw());
		}
		sql+=" where id = ?";
		params.add(user.getId());
		try {
		
			return queryRunner.update(sql, params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	
	
}
