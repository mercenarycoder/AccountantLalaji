package com.vias.accountantlalaji;

public class CustomerList {

    String customerId;
    String customerName;
    String customerMob;
    String customerType;

    String login_id;
    String cmp_id;
    String dob;
    String email;
    String billing_address;
    String shipping_address	;
    String state_id;
    String pincode;
    String customer_gst;
    String status;
    String buss_name;
    String opening_account;
    String account_name;
    String account_number;
    String bank_ifsc_code;
    String consumer_no;
    String created_at;
    String state_name;

    public CustomerList(String customerId, String customerName, String customerMob, String customerType, String login_id, String cmp_id, String dob, String email, String billing_address, String shipping_address, String state_id, String pincode, String customer_gst, String status, String buss_name, String opening_account, String account_name, String account_number, String bank_ifsc_code, String consumer_no, String created_at, String state_name) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerMob = customerMob;
        this.customerType = customerType;
        this.login_id = login_id;
        this.cmp_id = cmp_id;
        this.dob = dob;
        this.email = email;
        this.billing_address = billing_address;
        this.shipping_address = shipping_address;
        this.state_id = state_id;
        this.pincode = pincode;
        this.customer_gst = customer_gst;
        this.status = status;
        this.buss_name = buss_name;
        this.opening_account = opening_account;
        this.account_name = account_name;
        this.account_number = account_number;
        this.bank_ifsc_code = bank_ifsc_code;
        this.consumer_no = consumer_no;
        this.created_at = created_at;
        this.state_name = state_name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerMob() {
        return customerMob;
    }

    public String getCustomerType() {
        return customerType;
    }

    public String getLogin_id() {
        return login_id;
    }

    public String getCmp_id() {
        return cmp_id;
    }

    public String getDob() {
        return dob;
    }

    public String getEmail() {
        return email;
    }

    public String getBilling_address() {
        return billing_address;
    }

    public String getShipping_address() {
        return shipping_address;
    }

    public String getState_id() {
        return state_id;
    }

    public String getPincode() {
        return pincode;
    }

    public String getCustomer_gst() {
        return customer_gst;
    }

    public String getStatus() {
        return status;
    }

    public String getBuss_name() {
        return buss_name;
    }

    public String getOpening_account() {
        return opening_account;
    }

    public String getAccount_name() {
        return account_name;
    }

    public String getAccount_number() {
        return account_number;
    }

    public String getBank_ifsc_code() {
        return bank_ifsc_code;
    }

    public String getConsumer_no() {
        return consumer_no;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getState_name() {
        return state_name;
    }
}
