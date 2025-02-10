package top.gaogle.pojo.model;

import top.gaogle.pojo.domain.EnterpriseBill;

public class EnterpriseBillModel extends EnterpriseBill {

    /**
     * 余额，单位分
     */
    private String strBalance;

    /**
     * 金额，单位分
     */
    private String strAmount;

    public String getStrBalance() {
        return strBalance;
    }

    public void setStrBalance(String strBalance) {
        this.strBalance = strBalance;
    }

    public String getStrAmount() {
        return strAmount;
    }

    public void setStrAmount(String strAmount) {
        this.strAmount = strAmount;
    }
}
