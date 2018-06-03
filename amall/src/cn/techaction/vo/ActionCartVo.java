package cn.techaction.vo;

import java.math.BigDecimal;
import java.util.List;

public class ActionCartVo {

	private List<ActionCartListVo> lists;
	private BigDecimal totalPrice;
	public List<ActionCartListVo> getLists() {
		return lists;
	}
	public void setLists(List<ActionCartListVo> lists) {
		this.lists = lists;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
}
