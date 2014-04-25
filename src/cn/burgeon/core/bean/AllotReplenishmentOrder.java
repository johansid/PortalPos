package cn.burgeon.core.bean;

/**
 * Created by Simon on 2014/4/22.
 */
public class AllotReplenishmentOrder {
    private String DOCNO; // 单据编号
    private String BILLDATE; // 单据日期
    private String C_DEST_ID; // 出货店仓
    private String STATUSERID; // 申请人
    private String DESCRIPTION; // 备注

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

    public String getC_DEST_ID() {
        return C_DEST_ID;
    }

    public void setC_DEST_ID(String c_DEST_ID) {
        C_DEST_ID = c_DEST_ID;
    }

    public String getSTATUSERID() {
        return STATUSERID;
    }

    public void setSTATUSERID(String STATUSERID) {
        this.STATUSERID = STATUSERID;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }
}
