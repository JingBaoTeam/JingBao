package cn.techaction.vo;

import java.util.List;

import cn.techaction.pojo.ActionProduct;

public class ActionProductOddFloorVo {
	private ActionProduct mt;		//中间上部数据
	private ActionProduct mb;		//中间下部数据
	private List<ActionProduct> list;	//右侧列表数据
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
