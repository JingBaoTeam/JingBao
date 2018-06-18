package cn.techaction.common;

public enum ResponseCode {
	SUCCESS(0,"SUCCESS"),
<<<<<<< HEAD
	ERROR(1,"ERROR"),
	UNLOGIN(2,"UNLOGIN");
	
=======
	ERROR(1,"ERROR");
>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77
	private final int code;
	private final String desc;
	
	private ResponseCode(int code,String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	public int getCode() {
		return code;
	}
	public String getDesc() {
		return desc;
	}

}
