package com.vias.accountantlalaji;

public class Listitem_brand {
    String sno;
    String idd;
    String login_idd	;
    String cmp_idd;
    String hsn_id;
    String brand_name;
    String status;
    String created_at;

    public Listitem_brand(String sno, String idd, String login_idd, String cmp_idd, String hsn_id, String brand_name, String status, String created_at) {
        this.sno = sno;
        this.idd = idd;
        this.login_idd = login_idd;
        this.cmp_idd = cmp_idd;
        this.hsn_id = hsn_id;
        this.brand_name = brand_name;
        this.status = status;
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

    public String getHsn_id() {
        return hsn_id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public String getStatus() {
        return status;
    }

    public String getCreated_at() {
        return created_at;
    }
}
