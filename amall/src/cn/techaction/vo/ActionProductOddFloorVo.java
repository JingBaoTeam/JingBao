package cn.techaction.vo;

import java.util.List;

import cn.techaction.pojo.ActionProduct;

public class ActionProductOddFloorVo {
	private ActionProduct mt;		//�м��ϲ�����
	private ActionProduct mb;		//�м��²�����
	private List<ActionProduct> list;	//�Ҳ��б�����
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
