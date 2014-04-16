package cn.burgeon.core.bean;

public class Member {
	private int id;
	private String name;
	private char sex;
	private String cardNum;
	private String iDentityCardNum;
	private String phoneNum;
	private int type;
	private String birthday;
	private String email;
	private String createCardDate;
	public Member() {
		super();
	}
	public Member(String name, char sex, String cardNum,
			String iDentityCardNum, String phoneNum, int type, String birthday,
			String email, String createCardDate) {
		super();
		this.name = name;
		this.sex = sex;
		this.cardNum = cardNum;
		this.iDentityCardNum = iDentityCardNum;
		this.phoneNum = phoneNum;
		this.type = type;
		this.birthday = birthday;
		this.email = email;
		this.createCardDate = createCardDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public char getSex() {
		return sex;
	}
	public void setSex(char sex) {
		this.sex = sex;
	}
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public String getiDentityCardNum() {
		return iDentityCardNum;
	}
	public void setiDentityCardNum(String iDentityCardNum) {
		this.iDentityCardNum = iDentityCardNum;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCreateCardDate() {
		return createCardDate;
	}
	public void setCreateCardDate(String createCardDate) {
		this.createCardDate = createCardDate;
	}
}
