package cn.burgeon.core.bean;

/**
 * Created by Simon on 2014/4/22.
 */
public class AllotInQuery {
	private String ID; // 调拨表单ID
	private String DOCNO; // 单据编号
	private String BILLDATE; // 单据日期
	private String C_ORIG_ID; // 出货店仓
	private String TOT_QTYOUT; // 出库数量

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getDOCNO() {
		return DOCNO;
	}

	public void setDOCNO(String DOCNO) {
		this.DOCNO = DOCNO;
	}

	public String getBILLDATE() {
		return BILLDATE;
	}

	public void setBILLDATE(String BILLDATE) {
		this.BILLDATE = BILLDATE;
	}

	public String getC_ORIG_ID() {
		return C_ORIG_ID;
	}

	public void setC_ORIG_ID(String c_ORIG_ID) {
		C_ORIG_ID = c_ORIG_ID;
	}

	public String getTOT_QTYOUT() {
		return TOT_QTYOUT;
	}

	public void setTOT_QTYOUT(String TOT_QTYOUT) {
		this.TOT_QTYOUT = TOT_QTYOUT;
	}
}
