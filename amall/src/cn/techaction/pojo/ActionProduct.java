package cn.techaction.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class ActionProduct {
	private Integer id;
	private String name;
<<<<<<< HEAD
	private Integer productId; //产品类型ID
	private Integer partsId;	//配件类型ID
	private String iconUrl;		//商品主图
	private String subImages;	//商品组图 地址
	private String detail ;		//商品详情
	private String specParam;	//规格参数
	private BigDecimal price;	//商品价格
	private Integer stock;		//库存
	private Integer status;		//商品状态
	private Integer hot;		//是否热销
=======
	private Integer productId; //浜у搧绫诲瀷ID
	private Integer partsId;	//閰嶄欢绫诲瀷ID
	private String iconUrl;		//鍟嗗搧涓诲浘
	private String subImages;	//鍟嗗搧缁勫浘 鍦板潃
	private String detail ;		//鍟嗗搧璇︽儏
	private String specParam;	//瑙勬牸鍙傛暟
	private BigDecimal price;	//鍟嗗搧浠锋牸
	private Integer stock;		//搴撳瓨
	private Integer status;		//鍟嗗搧鐘舵��
	private Integer hot;		//鏄惁鐑攢
>>>>>>> 73cc0dba33a6db2d8b90fd9b40996deba7203e77
	private Date created;
	private Date updated;
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getPartsId() {
		return partsId;
	}
	public void setPartsId(Integer partsId) {
		this.partsId = partsId;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public String getSubImages() {
		return subImages;
	}
	public void setSubImages(String subImages) {
		this.subImages = subImages;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getSpecParam() {
		return specParam;
	}
	public void setSpecParam(String specParam) {
		this.specParam = specParam;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getHot() {
		return hot;
	}
	public void setHot(Integer hot) {
		this.hot = hot;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
	
}
