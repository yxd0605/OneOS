package com.eli.oneos.db.greendao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.
/**
 * Entity mapped to table USER_HISTORY.
 */
public class UserHistory {

    /** Not-null value. */
    private String name;
    /** Not-null value. */
    private String pwd;
    /** Not-null value. */
    private String mac;
    private Long time;

    public UserHistory() {
    }

    public UserHistory(String name) {
        this.name = name;
    }

    public UserHistory(String name, String pwd, String mac, Long time) {
        this.name = name;
        this.pwd = pwd;
        this.mac = mac;
        this.time = time;
    }

    /** Not-null value. */
    public String getName() {
        return name;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setName(String name) {
        this.name = name;
    }

    /** Not-null value. */
    public String getPwd() {
        return pwd;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    /** Not-null value. */
    public String getMac() {
        return mac;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setMac(String mac) {
        this.mac = mac;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

}
