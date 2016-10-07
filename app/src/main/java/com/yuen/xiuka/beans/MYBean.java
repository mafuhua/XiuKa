package com.yuen.xiuka.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/21.
 */
public class MYBean implements Serializable {


    /**
     * code : 0
     * data : {"add":"","age":"0","constellation":"","fensi":0,"guangzhu":0,"image":"","label":"","login_time":"1466488627","name":"","platform":"","qianming":"","sex":"1","tel":"15136105241","time":"1466474556","type":"0","uid":"10000005","zhiye":""}
     * msg : 成功
     */

    private String code;
    /**
     * add :
     * age : 0
     * constellation :
     * fensi : 0
     * guangzhu : 0
     * image :
     * label :
     * login_time : 1466488627
     * name :
     * platform :
     * qianming :
     * sex : 1
     * tel : 15136105241
     * time : 1466474556
     * type : 0
     * uid : 10000005
     * zhiye :
     */

    private DataBean data;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean implements Serializable {
        private String add;
        private String age;
        private String constellation;
        private int fensi;
        private int guanzhu;
        private String image;

        public String getGhtype() {
            return ghtype;
        }

        public void setGhtype(String ghtype) {
            this.ghtype = ghtype;
        }

        private String ghtype;
        private String label;
        private String login_time;
        private String name;
        private String platform;
        private String qianming;
        private String note_name;
        private String sex;
        private String tel;
        private String time;
        private String type;
        private String uid;
        private String zhiye;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        private String token;

        public String getNote_name() {
            return note_name;
        }

        public void setNote_name(String note_name) {
            this.note_name = note_name;
        }

        public String getAdd() {
            return add;
        }

        public void setAdd(String add) {
            this.add = add;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getConstellation() {
            return constellation;
        }

        public void setConstellation(String constellation) {
            this.constellation = constellation;
        }

        public int getFensi() {
            return fensi;
        }

        public void setFensi(int fensi) {
            this.fensi = fensi;
        }

        public int getGuanzhu() {
            return guanzhu;
        }

        public void setGuanzhu(int guanzhu) {
            this.guanzhu = guanzhu;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getLogin_time() {
            return login_time;
        }

        public void setLogin_time(String login_time) {
            this.login_time = login_time;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public String getQianming() {
            return qianming;
        }

        public void setQianming(String qianming) {
            this.qianming = qianming;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getZhiye() {
            return zhiye;
        }

        public void setZhiye(String zhiye) {
            this.zhiye = zhiye;
        }
    }
}
