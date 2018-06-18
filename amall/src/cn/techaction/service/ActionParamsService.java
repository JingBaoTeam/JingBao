package cn.techaction.service;

import java.util.List;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionParam;
import cn.techaction.vo.ActionParamVo;

public interface ActionParamsService {
	//新增品类
	/**
	 * 新增商品参数信息
	 * @param param
	 * @return
	 */
	public SverResponse<String> addParam(ActionParam param);
	/**
	 * 更新商品参数
	 * @param param
	 * @return
	 */
	public SverResponse<String> updateParam(ActionParam param);
	
	/**
	 * 根据参数ID查找其子参数
	 * @param id
	 * @return
	 */
	public SverResponse<List<ActionParam>> findParamChildren(Integer id);
	/**
	 * 递归查找某个节点的所有子节点信息
	 * @param id
	 * @return
	 */
	public SverResponse<List<ActionParam>> findParamAndAllChildrenById(Integer id);
	
	/**
	 * 查询产品类型参数，即一级节点
	 * @return
	 */
	public SverResponse<List<ActionParam>> findProdutTypeParams();
	/**
	 * 查询配件类型参数，即三级节点
	 * @param productTypeId 产品类型参数
	 * @return
	 */
	public SverResponse<List<ActionParamVo>> findPartsTypeParamsByProductTypeId(Integer productTypeId);
	/**
	 * 读取全路径类型参数
	 * @return
	 */
	public SverResponse<List<ActionParam>> findAllPathParams();
	
	
	/************************前台用到的方法*********************************/
	
	/**
	 * 获取全部分类信息
	 * @return
	 */
	public SverResponse<List<ActionParam>> findAllParams();
}
