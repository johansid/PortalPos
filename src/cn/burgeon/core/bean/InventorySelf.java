package cn.burgeon.core.bean;

public class InventorySelf {
	
	private String styleNumber;
	private int styleCount;
	private String styleName;
	
	public InventorySelf() {
		super();
	}
	
	public InventorySelf(String styleNumber, int styleCount, String styleName) {
		super();
		this.styleNumber = styleNumber;
		this.styleCount = styleCount;
		this.styleName = styleName;
	}
	
	public String getStyleNumber() {
		return styleNumber;
	}
	
	public void setStyleNumber(String styleNumber) {
		this.styleNumber = styleNumber;
	}
	
	public int getStyleCount() {
		return styleCount;
	}
	
	public void setStyleCount(int styleCount) {
		this.styleCount = styleCount;
	}
	
	public String getStyleName() {
		return styleName;
	}
	
	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}
}
