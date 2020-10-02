package com.vias.accountantlalaji;

public class Listitem_announcement {

    String sno;
    String idd;
    String supera_id;
    String admin_id;
    String messages;
    String created_at;

    public Listitem_announcement(String sno, String idd, String supera_id, String admin_id, String messages, String created_at) {
        this.sno = sno;
        this.idd = idd;
        this.supera_id = supera_id;
        this.admin_id = admin_id;
        this.messages = messages;
        this.created_at = created_at;
    }

    public String getSno() {
        return sno;
    }

    public String getIdd() {
        return idd;
    }

    public String getSupera_id() {
        return supera_id;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public String getMessages() {
        return messages;
    }

    public String getCreated_at() {
        return created_at;
    }
}
