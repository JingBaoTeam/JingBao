package cn.techaction.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class ActionProduct {
	private Integer id;
	private String name;
<<<<<<< HEAD
	private Integer productId; //²úÆ·ÀàÐÍID
	private Integer partsId;	//Åä¼þÀàÐÍID
	private String iconUrl;		//ÉÌÆ·Ö÷Í¼
	private String subImages;	//ÉÌÆ·×éÍ¼ µØÖ·
	private String detail ;		//ÉÌÆ·ÏêÇé
	private String specParam;	//¹æ¸ñ²ÎÊý
	private BigDecimal price;	//ÉÌÆ·¼Û¸ñ
	private Integer stock;		//¿â´æ
	private Integer status;		//ÉÌÆ·×´Ì¬
	private Integer hot;		//ÊÇ·ñÈÈÏú
=======
	private Integer productId; //äº§å“ç±»åž‹ID
	private Integer partsId;	//é…ä»¶ç±»åž‹ID
	private String iconUrl;		//å•†å“ä¸»å›¾
	private String subImages;	//å•†å“ç»„å›¾ åœ°å€
	private String detail ;		//å•†å“è¯¦æƒ…
	private String specParam;	//è§„æ ¼å‚æ•°
	private BigDecimal price;	//å•†å“ä»·æ ¼
	private Integer stock;		//åº“å­˜
	private Integer status;		//å•†å“çŠ¶æ€
	private Integer hot;		//æ˜¯å¦çƒ­é”€
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
