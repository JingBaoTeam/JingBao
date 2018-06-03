package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionParam;

public interface ActionParamsDao {

	/**
	 * 新增参数
	 * @param param		参数对象
	 * @return
	 */
	public int insertParam(ActionParam param);
	/**
	 * 更新参数
	 * @param param		参数对象
	 * @return
	 */
	public int updateParam(ActionParam param);
	/**
	 * 根据父节点id查找子节点参数
	 * @param parentId		父节点ID
	 * @return
	 */
	public List<ActionParam> findParamsByParentId(Integer parentId);
	/**
	 * 根据节点Id查找参数对象
	 * @param id		节点ID
	 * @return
	 */
	public ActionParam findParamById(Integer id);
	/**
	 * 删除节点参数
	 * @param id	节点ID
	 * @return
	 */
	public int deleteParam(Integer id);
}
