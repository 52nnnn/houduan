package org.example.User;

public class divData {
    private int dvi_id;
    private String dvi_name;
    private String dvi_website;  // 官网地址
    private String dvi_createTime;
    private String dvi_updateTime;
    private String dvi_queryUrl; // 快递查询API地址

    // 构造方法
    public divData(int id, String name, String website, String createTime,
                   String updateTime, String queryUrl) {
        this.dvi_id = id;
        this.dvi_name = name;
        this.dvi_website = website;
        this.dvi_createTime = createTime;
        this.dvi_updateTime = updateTime;
        this.dvi_queryUrl = queryUrl;
    }

    public int getDvi_id() {
        return dvi_id;
    }

    public int setDvi_id(int dvi_id) {
        return this.dvi_id = dvi_id;
    }

    public String getDvi_name() {
        return dvi_name;
    }

    public void setDvi_name(String dvi_name) {
        this.dvi_name = dvi_name;
    }

    public String getDvi_createTime() {
        return dvi_createTime;
    }

    public void setDvi_createTime(String dvi_createTime) {
        this.dvi_createTime = dvi_createTime;
    }

    public String getDvi_website() {
        return dvi_website;
    }

    public void setDvi_website(String dvi_website) {
        this.dvi_website = dvi_website;
    }

    public String getDvi_updateTime() {
        return dvi_updateTime;
    }

    public void setDvi_updateTime(String dvi_updateTime) {
        this.dvi_updateTime = dvi_updateTime;
    }

    public String getDvi_queryUrl() {
        return dvi_queryUrl;
    }

    public void setDvi_queryUrl(String dvi_queryUrl) {
        this.dvi_queryUrl = dvi_queryUrl;
    }
// Getter和Setter方法（省略）
}
