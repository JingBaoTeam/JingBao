package cn.techaction.pojo;

import java.util.Date;

public class User {
	private Integer id;
	private String account;
	private String password;
	private String email;
	private String phone;
	private String question;
	private String asw;
	private Integer role;
	private Date create_time;
	private Date update_time;
	
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public User(Integer id, String account, String password, String email, String phone, String question, String asw,
			Integer role, Date createTime, Date updateTime) {
		super();
		this.id = id;
		this.account = account;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.question = question;
		this.asw = asw;
		this.role = role;
		this.create_time = createTime;
		this.update_time = updateTime;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAsw() {
		return asw;
	}
	public void setAsw(String asw) {
		this.asw = asw;
	}
	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	
	

}
