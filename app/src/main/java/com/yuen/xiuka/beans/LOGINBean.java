package com.yuen.xiuka.beans;

/**
 * Created by Administrator on 2016/6/21.
 */
public class LOGINBean {

    /**
     * code : 0
     * msg : 成功
     * tel : 15136105241
     * uid : 10000005
     */

    private String code;
    private String msg;
    private String tel;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;
    private int uid;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
