package com.vias.accountantlalaji;

public class Listitem_HSN {
    String sno;
    String idd;
    String login_idd;
    String cmp_idd;
    String hsn_description;
    String hsn_rate;
    String hsn_code;
    String created_at;

    public Listitem_HSN(String sno, String idd, String login_idd, String cmp_idd, String hsn_description, String hsn_rate, String hsn_code, String created_at) {
        this.sno = sno;
        this.idd = idd;
        this.login_idd = login_idd;
        this.cmp_idd = cmp_idd;
        this.hsn_description = hsn_description;
        this.hsn_rate = hsn_rate;
        this.hsn_code = hsn_code;
        this.created_at = created_at;
    }

    public String getSno() {
        return sno;
    }

    public String getIdd() {
        return idd;
    }

    public String getLogin_idd() {
        return login_idd;
    }

    public String getCmp_idd() {
        return cmp_idd;
    }

    public String getHsn_description() {
        return hsn_description;
    }

    public String getHsn_rate() {
        return hsn_rate;
    }

    public String getHsn_code() {
        return hsn_code;
    }

    public String getCreated_at() {
        return created_at;
    }
}
