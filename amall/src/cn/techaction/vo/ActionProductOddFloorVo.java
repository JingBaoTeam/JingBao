package cn.techaction.vo;

import java.util.List;

import cn.techaction.pojo.ActionProduct;

public class ActionProductOddFloorVo {
<<<<<<< HEAD
	private ActionProduct mt;		//�м��ϲ�����
	private ActionProduct mb;		//�м��²�����
	private List<ActionProduct> list;	//�Ҳ��б�����
=======
	private ActionProduct mt;		//中间上部数据
	private ActionProduct mb;		//中间下部数据
	private List<ActionProduct> list;	//右侧列表数据
>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77
	public ActionProduct getMt() {
		return mt;
	}
	public void setMt(ActionProduct mt) {
		this.mt = mt;
	}
	public ActionProduct getMb() {
		return mb;
	}
	public void setMb(ActionProduct mb) {
		this.mb = mb;
	}
	public List<ActionProduct> getList() {
		return list;
	}
	public void setList(List<ActionProduct> list) {
		this.list = list;
	}
}
