package cn.techaction.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.techaction.dao.ActionProductDao;
import cn.techaction.pojo.ActionProduct;

@Repository
public class ActionProductDaoImpl implements ActionProductDao {
	@Autowired
	private QueryRunner queryRunner;

	@Override
	public int getTotalCount(Integer productId, Integer partsId) {
		// TODO Auto-generated method stub
		String sql = "select count(id) as num from action_products where 1=1";
		List<Object> params = new ArrayList<>();
		if (productId != null) {
			sql += " and procuct_id = ?";
			params.add(productId);
		}
		if (partsId != null) {
			sql += "and parts_id = ?";
			params.add(partsId);
		}
		try {
			List<Long> rs = queryRunner.query(sql, new ColumnListHandler<Long>("num"), params.toArray());
			return rs.get(0).intValue();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<ActionProduct> findProductsByTypeId(Integer productId, Integer partsId, Integer startIndex,
			Integer pageSize) {
		// TODO Auto-generated method stub
		String sql = "select id,name,product_id as productId,parts_id as partsId,icon_url as iconUrl"
				+ ",sub_images as subImages ,detail,spec_param as specParam,price,stock,status,is_Hot as isHot"
				+ ",created,updated from action_products where 1=1";
		List<Object> params = new ArrayList<>();
		if (productId != null) {
			sql += " and procuct_id = ?";
			params.add(productId);
		}
		if (partsId != null) {
			sql += "and parts_id = ?";
			params.add(partsId);
		}
		sql+="limit ? , ?";
		params.add(startIndex);
		params.add(pageSize);
		try {
			return queryRunner.query(sql, new BeanListHandler<ActionProduct>(ActionProduct.class),params.toArray());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ActionProduct findProductById(Integer productId) {
		// TODO Auto-generated method stub
		return null;
	}

}
