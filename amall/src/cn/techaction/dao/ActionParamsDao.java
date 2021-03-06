package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionParam;

public interface ActionParamsDao {

	/**
<<<<<<< HEAD
	 * 新增参数
	 * @param param		参数对象
=======
	 * 鏂板鍙傛暟
	 * @param param		鍙傛暟瀵硅薄
>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77
	 * @return
	 */
	public int insertParam(ActionParam param);
	/**
<<<<<<< HEAD
	 * 更新参数
	 * @param param		参数对象
=======
	 * 鏇存柊鍙傛暟
	 * @param param		鍙傛暟瀵硅薄
>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77
	 * @return
	 */
	public int updateParam(ActionParam param);
	/**
<<<<<<< HEAD
	 * 根据父节点id查找子节点参数
	 * @param parentId		父节点ID
=======
	 * 鏍规嵁鐖惰妭鐐筰d鏌ユ壘瀛愯妭鐐瑰弬鏁�
	 * @param parentId		鐖惰妭鐐笽D
>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77
	 * @return
	 */
	public List<ActionParam> findParamsByParentId(Integer parentId);
	/**
<<<<<<< HEAD
	 * 根据节点Id查找参数对象
	 * @param id		节点ID
=======
	 * 鏍规嵁鑺傜偣Id鏌ユ壘鍙傛暟瀵硅薄
	 * @param id		鑺傜偣ID
>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77
	 * @return
	 */
	public ActionParam findParamById(Integer id);
	/**
<<<<<<< HEAD
	 * 删除节点参数
	 * @param id	节点ID
=======
	 * 鍒犻櫎鑺傜偣鍙傛暟
	 * @param id	鑺傜偣ID
>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77
	 * @return
	 */
	public int deleteParam(Integer id);
}
