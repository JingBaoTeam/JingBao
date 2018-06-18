package cn.techaction.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.lang3.StringUtils;
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

	private String alias = "id,name,product_id as productId,parts_id as partsId,icon_url as iconUrl"
			+ ",sub_images as subImages,detail,spec_param as specParam,price,stock,status,is_hot as hot"
			+ ",created,updated ";

	@Override
	public int insertProduct(ActionProduct product) {
		String sql = "insert into action_products(name,product_id ,parts_id ,icon_url,sub_images ,detail,spec_param,price,stock,status,is_hot,created,updated ) values "
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] params = { product.getName(),product.getProductId(),product.getPartsId(),product.getIconUrl(),
				product.getSubImages(),product.getDetail(),
				product.getSpecParam(),product.getPrice(),
				product.getStock(),product.getStatus(),
				product.getHot(),product.getCreated(),product.getUpdated()
				};
		try {
			return queryRunner.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int deleteProductById(Integer id) {
		String sql = "delete from action_products where id = ? ";
		try {
			return queryRunner.update(sql, id);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int updateProduct(ActionProduct product){
		String sql = "update action_products set updated=? ";
		List<Object> params = new ArrayList<>();
		params.add(product.getUpdated());
		if(!StringUtils.isEmpty(product.getName())) {
			sql +=",name = ?";
			params.add(product.getName());
		}
		if(product.getPrice()!=null) {
			sql +=",price = ?";
			params.add(product.getPrice());
		}
		if(product.getStock()!=null) {
			sql +=",stock = ?";
			params.add(product.getStock());
		}
		if(!StringUtils.isEmpty(product.getIconUrl())) {
			sql +=",icon_url = ?";
			params.add(product.getIconUrl());
		}
		if(!StringUtils.isEmpty(product.getSubImages())) {
			sql +=",sub_images = ?";
			params.add(product.getSubImages());
		}
		if(product.getStatus()!=null) {
			sql +=",status = ?";
			params.add(product.getStatus());
		}
		if(!StringUtils.isEmpty(product.getDetail())) {
			sql +=",detail = ?";
			params.add(product.getDetail());
		}
		if(!StringUtils.isEmpty(product.getSpecParam())) {
			sql +=",spec_param = ?";
			params.add(product.getSpecParam());
		}
		if(product.getHot()!=null) {
			sql +=",is_hot = ?";
			params.add(product.getHot());
		}
		
		sql += " where id = ?";
		params.add(product.getId());
	
		try {
			return queryRunner.update(sql, params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	//读取总记录数
	@Override
	public Integer getTotalCount(ActionProduct conditon) {
		String sql = "select count(id) as num from action_products where 1=1 ";
		List<Object> params = new ArrayList<>();
		if(conditon.getId()!=null) {
			sql+=" and id = ? ";
			params.add(conditon.getId());
		}
		if(conditon.getName()!=null) {
			sql+=" and name like ? ";
			params.add(conditon.getName());
		}
		if(conditon.getStatus()!=null) {
			sql+=" and status = ? ";
			params.add(conditon.getStatus());
		}
		if(conditon.getProductId()!=null) {
			sql+=" and product_id = ? ";
			params.add(conditon.getProductId());
		}
		if(conditon.getPartsId()!=null) {
			sql+=" and parts_id = ? ";
			params.add(conditon.getPartsId());
		}
		try {
			return queryRunner.query(sql, new ColumnListHandler<Long>("num"),params.toArray()).get(0).intValue();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	//读取分页数据
	@Override
	public List<ActionProduct> findProducts(ActionProduct conditon,int offset, int pageSize) {
		String sql = "select " + this.alias + " from action_products where 1=1 ";
		List<Object> params = new ArrayList<>();
		if(conditon.getId()!=null) {
			sql+=" and id = ? ";
			params.add(conditon.getId());
		}
		if(conditon.getName()!=null) {
			sql+=" and name like ? ";
			params.add(conditon.getName());
		}
		if(conditon.getStatus()!=null) {
			sql+=" and status = ? ";
			params.add(conditon.getStatus());
		}
		if(conditon.getProductId()!=null) {
			sql+=" and product_id = ? ";
			params.add(conditon.getProductId());
		}
		if(conditon.getPartsId()!=null) {
			sql+=" and parts_id = ? ";
			params.add(conditon.getPartsId());
		}
		sql+= " limit ?,?";
		params.add(offset);
		params.add(pageSize);
		try {
			return queryRunner.query(sql, new BeanListHandler<ActionProduct>(ActionProduct.class), params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ActionProduct> findProductsNoPage(ActionProduct conditon) {
		String sql = "select " + this.alias + " from action_products where 1=1 ";
		List<Object> params = new ArrayList<>();
		if(conditon.getId()!=null) {
			sql+=" and id = ? ";
			params.add(conditon.getId());
		}
		if(conditon.getName()!=null) {
			sql+=" and name like ? ";
			params.add(conditon.getName());
		}
		if(conditon.getStatus()!=null) {
			sql+=" and status = ? ";
			params.add(conditon.getStatus());
		}
		if(conditon.getProductId()!=null) {
			sql+=" and product_id = ? ";
			params.add(conditon.getProductId());
		}
		if(conditon.getPartsId()!=null) {
			sql+=" and parts_id = ? ";
			params.add(conditon.getPartsId());
		}
		sql+=" order by created,id desc";
		try {
			return queryRunner.query(sql, new BeanListHandler<ActionProduct>(ActionProduct.class), params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ActionProduct> findHotProducts(Integer num) {
		String sql = "select " + this.alias + " from action_products where is_hot=1 ";
		sql+=" order by updated,id desc ";
		if(num !=null) {
			sql+=" limit 0 , ?";
		}
		try {
			if(num!=null) {
				return queryRunner.query(sql, new BeanListHandler<ActionProduct>(ActionProduct.class),num);
			}else {
				return queryRunner.query(sql, new BeanListHandler<ActionProduct>(ActionProduct.class));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<ActionProduct> findProductsByProductCategory(Integer categoryId) {
		String sql = "select " + this.alias + " from action_products where product_id= ? order by updated desc ";
		try {
			return queryRunner.query(sql, new BeanListHandler<ActionProduct>(ActionProduct.class),categoryId);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
