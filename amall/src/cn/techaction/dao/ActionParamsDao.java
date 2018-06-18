package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionParam;

public interface ActionParamsDao {

	/**
<<<<<<< HEAD
	 * ÐÂÔö²ÎÊý
	 * @param param		²ÎÊý¶ÔÏó
=======
	 * æ–°å¢žå‚æ•°
	 * @param param		å‚æ•°å¯¹è±¡
>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77
	 * @return
	 */
	public int insertParam(ActionParam param);
	/**
<<<<<<< HEAD
	 * ¸üÐÂ²ÎÊý
	 * @param param		²ÎÊý¶ÔÏó
=======
	 * æ›´æ–°å‚æ•°
	 * @param param		å‚æ•°å¯¹è±¡
>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77
	 * @return
	 */
	public int updateParam(ActionParam param);
	/**
<<<<<<< HEAD
	 * ¸ù¾Ý¸¸½Úµãid²éÕÒ×Ó½Úµã²ÎÊý
	 * @param parentId		¸¸½ÚµãID
=======
	 * æ ¹æ®çˆ¶èŠ‚ç‚¹idæŸ¥æ‰¾å­èŠ‚ç‚¹å‚æ•°
	 * @param parentId		çˆ¶èŠ‚ç‚¹ID
>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77
	 * @return
	 */
	public List<ActionParam> findParamsByParentId(Integer parentId);
	/**
<<<<<<< HEAD
	 * ¸ù¾Ý½ÚµãId²éÕÒ²ÎÊý¶ÔÏó
	 * @param id		½ÚµãID
=======
	 * æ ¹æ®èŠ‚ç‚¹IdæŸ¥æ‰¾å‚æ•°å¯¹è±¡
	 * @param id		èŠ‚ç‚¹ID
>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77
	 * @return
	 */
	public ActionParam findParamById(Integer id);
	/**
<<<<<<< HEAD
	 * É¾³ý½Úµã²ÎÊý
	 * @param id	½ÚµãID
=======
	 * åˆ é™¤èŠ‚ç‚¹å‚æ•°
	 * @param id	èŠ‚ç‚¹ID
>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77
	 * @return
	 */
	public int deleteParam(Integer id);
}
