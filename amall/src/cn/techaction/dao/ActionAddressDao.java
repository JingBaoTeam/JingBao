package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionAddress;

public interface ActionAddressDao {

	/**
	 * 新增收货人地址信息
	 * @param address	地址对象
	 * @return
	 */
	public int insertAddress(ActionAddress address);
	/**
	 * 查询用户的收件人地址信息
	 * @param userId	用户ID
	 * @return
	 */
	public List<ActionAddress> findAddrsByUserId(Integer userId);
	/**
	 * 更新收件人地址信息
	 * @param address	地址对象
	 * @return
	 */
	public int updateAddress(ActionAddress address);
	/**
	 * 删除收件人地址信息
	 * @param id	地址编号
	 * @return
	 */
	public int deleteAddress(Integer id);
	/**
	 * 根据地址Id查询收货人地址信息
	 * @param addrId
	 * @return
	 */
	public ActionAddress findAddrsById(Integer addrId);
	/**
	 * 查询是否存在默认地址
	 * @param addrId
	 * @return
	 */
	public int findDefaultAddrByUserId(Integer userId);
	/**
	 * 读取用户的默认地址
	 * @param userId
	 * @return
	 */
	public ActionAddress findDefaultAddr(Integer userId);
}
