package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionParam;

public interface ActionParamsDao {

	/**
	 * ��������
	 * @param param		��������
	 * @return
	 */
	public int insertParam(ActionParam param);
	/**
	 * ���²���
	 * @param param		��������
	 * @return
	 */
	public int updateParam(ActionParam param);
	/**
	 * ���ݸ��ڵ�id�����ӽڵ����
	 * @param parentId		���ڵ�ID
	 * @return
	 */
	public List<ActionParam> findParamsByParentId(Integer parentId);
	/**
	 * ���ݽڵ�Id���Ҳ�������
	 * @param id		�ڵ�ID
	 * @return
	 */
	public ActionParam findParamById(Integer id);
	/**
	 * ɾ���ڵ����
	 * @param id	�ڵ�ID
	 * @return
	 */
	public int deleteParam(Integer id);
}
