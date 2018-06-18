package cn.techaction.vo;

import java.math.BigDecimal;
/**
 * 用于在后台商品列表中展示的Vo对象
 * @author ireson
 *
 */
public class ActionProductListVo {
	private Integer id;
	private String name;
	private BigDecimal price;
	private Integer status;
	private String statusDesc;
	private String productCategory;
	private String partsCategory;
	private String iconUrl;
	private Integer hot;
	private String hotStatus;
	
	
	public Integer getHot() {
		return hot;
	}
	public void setHot(Integer hot) {
		this.hot = hot;
	}
	public String getHotStatus() {
		return hotStatus;
	}
	public void setHotStatus(String hotStatus) {
		this.hotStatus = hotStatus;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
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
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	public String getPartsCategory() {
		return partsCategory;
	}
	public void setPartsCategory(String partsCategory) {
		this.partsCategory = partsCategory;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	
}
