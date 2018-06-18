package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionParam;

public interface ActionParamsDao {

	/**
<<<<<<< HEAD
	 * ��������
	 * @param param		��������
=======
	 * 新增参数
	 * @param param		参数对象
>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77
	 * @return
	 */
	public int insertParam(ActionParam param);
	/**
<<<<<<< HEAD
	 * ���²���
	 * @param param		��������
=======
	 * 更新参数
	 * @param param		参数对象
>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77
	 * @return
	 */
	public int updateParam(ActionParam param);
	/**
<<<<<<< HEAD
	 * ���ݸ��ڵ�id�����ӽڵ����
	 * @param parentId		���ڵ�ID
=======
	 * 根据父节点id查找子节点参数
	 * @param parentId		父节点ID
>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77
	 * @return
	 */
	public List<ActionParam> findParamsByParentId(Integer parentId);
	/**
<<<<<<< HEAD
	 * ���ݽڵ�Id���Ҳ�������
	 * @param id		�ڵ�ID
=======
	 * 根据节点Id查找参数对象
	 * @param id		节点ID
>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77
	 * @return
	 */
	public ActionParam findParamById(Integer id);
	/**
<<<<<<< HEAD
	 * ɾ���ڵ����
	 * @param id	�ڵ�ID
=======
	 * 删除节点参数
	 * @param id	节点ID
>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77
	 * @return
	 */
	public int deleteParam(Integer id);
}
