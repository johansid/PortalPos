package cn.burgeon.core.bean;

public class Order {
	private int id;
	private String cardNum;
	private String zheKou;
	private String kuanHao;
	private String tiaoMa;
	private String saleAsistant;
	private String orderDate;
	private String orderState;
	public Order() {
		super();
	}
	public Order(String cardNum, String zheKou, String kuanHao, String tiaoMa,
			String saleAsistant, String orderDate, String orderState) {
		super();
		this.cardNum = cardNum;
		this.zheKou = zheKou;
		this.kuanHao = kuanHao;
		this.tiaoMa = tiaoMa;
		this.saleAsistant = saleAsistant;
		this.orderDate = orderDate;
		this.orderState = orderState;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public String getZheKou() {
		return zheKou;
	}
	public void setZheKou(String zheKou) {
		this.zheKou = zheKou;
	}
	public String getKuanHao() {
		return kuanHao;
	}
	public void setKuanHao(String kuanHao) {
		this.kuanHao = kuanHao;
	}
	public String getTiaoMa() {
		return tiaoMa;
	}
	public void setTiaoMa(String tiaoMa) {
		this.tiaoMa = tiaoMa;
	}
	public String getSaleAsistant() {
		return saleAsistant;
	}
	public void setSaleAsistant(String saleAsistant) {
		this.saleAsistant = saleAsistant;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getOrderState() {
		return orderState;
	}
	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}
}
