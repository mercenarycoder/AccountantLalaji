package com.vias.accountantlalaji;

public class Listitem_Account {

    String sno;
    String idd;
    String login_idd;
    String cmp_idd;
    String opening_account;
    String account_type;
    String account_name;
    String status;
    String created_at;
    String bankName;

    public Listitem_Account(String sno, String idd, String login_idd, String cmp_idd, String opening_account, String account_type, String account_name, String status, String created_at, String bankName) {
        this.sno = sno;
        this.idd = idd;
        this.login_idd = login_idd;
        this.cmp_idd = cmp_idd;
        this.opening_account = opening_account;
        this.account_type = account_type;
        this.account_name = account_name;
        this.status = status;
        this.created_at = created_at;
        this.bankName = bankName;
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

    public String getOpening_account() {
        return opening_account;
    }

    public String getAccount_type() {
        return account_type;
    }

    public String getAccount_name() {
        return account_name;
    }

    public String getStatus() {
        return status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getBankName() {
        return bankName;
    }
}
