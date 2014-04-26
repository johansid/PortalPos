package cn.burgeon.core.bean;

public class Settle {
	private String payWay;
	private String payMoney;
	public Settle() {
		super();
	}
	public Settle(String payWay, String payMoney) {
		super();
		this.payWay = payWay;
		this.payMoney = payMoney;
	}
	public String getPayWay() {
		return payWay;
	}
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	public String getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(String payMoney) {
		this.payMoney = payMoney;
	}
}
