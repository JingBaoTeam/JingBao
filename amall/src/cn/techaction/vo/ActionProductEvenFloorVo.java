package cn.techaction.vo;

import java.util.List;

import cn.techaction.pojo.ActionProduct;

public class ActionProductEvenFloorVo {
	private List<ActionProduct> mb;		//中间下方
	private ActionProduct mt;			//中间上方
	private List<ActionProduct> rt;		//右-上
	private List<ActionProduct> rb;		//右-下
	
	public List<ActionProduct> getMb() {
		return mb;
	}
	public void setMb(List<ActionProduct> mb) {
		this.mb = mb;
	}
	public ActionProduct getMt() {
		return mt;
	}
	public void setMt(ActionProduct mt) {
		this.mt = mt;
	}
	public List<ActionProduct> getRt() {
		return rt;
	}
	public void setRt(List<ActionProduct> rt) {
		this.rt = rt;
	}
	public List<ActionProduct> getRb() {
		return rb;
	}
	public void setRb(List<ActionProduct> rb) {
		this.rb = rb;
	}
}
