package cn.techaction.service;

import java.util.List;

import cn.techaction.common.SverResponse;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionOrderVo;

public interface ActionOrderService {
	/**
	 * 生成订单
	 * @param uid		用户ID
	 * @param addrId	地址编号
	 * @return
	 */
	public SverResponse<ActionOrderVo> generateOrder(Integer uid,Integer addrId);
	/**
	 * 订单分页列表
	 * @param uid		用户ID
	 * @param pageNum	页码
	 * @param pageSize	页面大小
	 * @return
	 */
	public SverResponse<PageBean<ActionOrderVo>> findOrders(Integer uid,int pageNum,int pageSize);
	/**
	 * 取消订单
	 * @param uid		用户Id
	 * @param orderNo	订单编号
	 * @return
	 */
	public SverResponse<String> cancelOrder(Integer uid, Long orderNo);
	/**
	 * 根据订单编号获取订单详情
	 * @param orderNo	订单编号
	 * @return
	 */
	public SverResponse<ActionOrderVo> findOrderDetail(Integer uid,Long orderNo);
	
	
	/******************************后台管理业务方法******************************************/
	/**
	 * 管理员用户查询订单分页列表
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public SverResponse<PageBean<ActionOrderVo>> mgrOrders(int pageNum,int pageSize);
	/**
	 * 管理员用户查询订单，不分页
	 * @param orderNo  订单编号，为null，则查询全部
	 * @return
	 */
	public SverResponse<List<ActionOrderVo>> findOrdersForNoPages(Long orderNo);
	/**
	 * 管理员查询订单详情方法
	 * @param orderNo
	 * @return
	 */
	public SverResponse<ActionOrderVo> mgrDetail(Long orderNo);
	/**
	 * 管理员根据订单号查询订单
	 * @param orderNo
	 * @return
	 */
	public SverResponse<PageBean<ActionOrderVo>> searchOrderByNo(Long orderNo,int pageNum,int pageSize);
	/**
	 * 管理员发货
	 * @param orderNo
	 * @return
	 */
	public SverResponse<String> deliverGoods(Long orderNo);
}
