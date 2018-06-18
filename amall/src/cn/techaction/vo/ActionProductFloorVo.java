package cn.techaction.vo;

public class ActionProductFloorVo {
	private ActionProductOddFloorVo oneFloor;	//1楼数据
	private ActionProductEvenFloorVo twoFloor;	//2楼数据
	private ActionProductOddFloorVo threeFloor;	//3楼数据
	private ActionProductEvenFloorVo fourFloor;	//4楼数据
	public ActionProductOddFloorVo getOneFloor() {
		return oneFloor;
	}
	public void setOneFloor(ActionProductOddFloorVo oneFloor) {
		this.oneFloor = oneFloor;
	}
	public ActionProductEvenFloorVo getTwoFloor() {
		return twoFloor;
	}
	public void setTwoFloor(ActionProductEvenFloorVo twoFloor) {
		this.twoFloor = twoFloor;
	}
	public ActionProductOddFloorVo getThreeFloor() {
		return threeFloor;
	}
	public void setThreeFloor(ActionProductOddFloorVo threeFloor) {
		this.threeFloor = threeFloor;
	}
	public ActionProductEvenFloorVo getFourFloor() {
		return fourFloor;
	}
	public void setFourFloor(ActionProductEvenFloorVo fourFloor) {
		this.fourFloor = fourFloor;
	}
}
