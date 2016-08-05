package com.yuen.xiuka.beans;

import java.util.List;

/**
 * Created by Administrator on 2016/8/5.
 */
public class ZhuBoListBean {

    /**
     * code : 0
     * data : [{"add":"上海","age":"24","bj_image":"avatar/201607/1469514852-59443.3","constellation":"双子座","image":"avatar/201608/1470305648-34065.jpg","label":"女神摄影旅行","login_time":"1470306078","name":"温暖","platform":"虎牙","qianming":"Xixixii","sex":"1","tel":"15136105241","time":"1466474556","token":"POpEdjT+X/AUAnOJSMvkl3kc4LfKU+jyYNzyw9WjpbjUiT/MUTdPbwd0ZxYoz5OnMO+9vVTWQMCFAaerqtiKbg==","type":"1","uid":"10000005","zhiye":"程序猿"},{"add":"","age":"0","bj_image":"","constellation":"","image":"avatar/201606/1466666600-56376.jpg","label":"摄影","login_time":"1469685854","name":"呵呵","platform":"熊猫","qianming":"","sex":"1","tel":"15821972616","time":"1466219039","token":"9xexf7CAuS7aW/8fCrJhAnkc4LfKU+jyYNzyw9WjpbjUiT/MUTdPb3uN3fWuDpKCMO+9vVTWQMCaQWAxIuOegg==","type":"1","uid":"10000002","zhiye":""},{"add":"咸宁市","age":"24","bj_image":"","constellation":"天蝎座","image":"avatar/201606/1466666600-56376.jpg","label":"生活","login_time":"1469780479","name":"234","platform":"YY","qianming":"你就是你","sex":"2","tel":"15721972613","time":"1466474556","token":"vKRsODdOuVnTUXXIUV7Cknkc4LfKU+jyYNzyw9WjpbjUiT/MUTdPbzqDPFj+DBc8MO+9vVTWQMBx8Sfw5jm62A==","type":"1","uid":"10000001","zhiye":"自由职业"},{"add":"","age":"0","bj_image":"","constellation":"","image":"avatar/201606/1466666600-56376.jpg","label":"摄影","login_time":"1466219640","name":"236","platform":"斗鱼","qianming":"","sex":"1","tel":"15821972610","time":"1466219552","token":"J/muBg5awWyhOfIJ/ZBrPlVlIYAMg9cSUUBiSLWpp1elSEKRtI5uqleLG/Aj+qtmc5zqW9TqNpY1exK0FoKMLromu+ObgbAa","type":"1","uid":"10000003","zhiye":""},{"add":"","age":"0","bj_image":"","constellation":"","image":"","label":"摄影","login_time":"1467448340","name":"","platform":"一直播","qianming":"","sex":"1","tel":"15514736764","time":"1467428304","token":"sk4TCtIOlf9qQTQx3sxRcFVlIYAMg9cSUUBiSLWpp1elSEKRtI5uqkHm7EQaIVem2T6nb6lFvcTxE3kpsocUR7omu+ObgbAa","type":"1","uid":"10000007","zhiye":""},{"add":"","age":"24","bj_image":"avatar/201607/1469439799-78653.","constellation":"","image":"avatar/201608/1470274537-65989.","label":"摄影","login_time":"1470380448","name":"Vayne","platform":"映客","qianming":"顶焦度计大喊大叫风景","sex":"1","tel":"15900655030","time":"1466477885","token":"8QqQ2JB1nVnOOrL+7tXIaF/uuvPevau0BUTYJBH18oZDxKNppjjXCe/KESkAVUgYQ5WDNSSduCQXH/WQFjo+jrTPr3MANtL6","type":"1","uid":"10000006","zhiye":"呵呵呵呵呵"},{"add":"123","age":"25","bj_image":"","constellation":"123","image":"avatar/201606/1466234501-27067.jpg","label":"摄影","login_time":"1469611663","name":"123","platform":"花椒","qianming":"123","sex":"1","tel":"15821972611","time":"1466474556","token":"PjBMRtgxJJbgtizvr4/tM1/uuvPevau0BUTYJBH18oZDxKNppjjXCc8LlS9v4+gL1HMoOLC+iENqXeWtsC87vLTPr3MANtL6","type":"1","uid":"10000000","zhiye":"123"}]
     * msg : 成功
     */

    private String code;
    private String msg;
    /**
     * add : 上海
     * age : 24
     * bj_image : avatar/201607/1469514852-59443.3
     * constellation : 双子座
     * image : avatar/201608/1470305648-34065.jpg
     * label : 女神摄影旅行
     * login_time : 1470306078
     * name : 温暖
     * platform : 虎牙
     * qianming : Xixixii
     * sex : 1
     * tel : 15136105241
     * time : 1466474556
     * token : POpEdjT+X/AUAnOJSMvkl3kc4LfKU+jyYNzyw9WjpbjUiT/MUTdPbwd0ZxYoz5OnMO+9vVTWQMCFAaerqtiKbg==
     * type : 1
     * uid : 10000005
     * zhiye : 程序猿
     */

    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String add;
        private String age;
        private String bj_image;
        private String constellation;
        private String image;
        private String label;
        private String login_time;
        private String name;
        private String platform;
        private String qianming;
        private String sex;
        private String tel;
        private String time;
        private String token;
        private String type;
        private String uid;
        private String zhiye;

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

        public String getBj_image() {
            return bj_image;
        }

        public void setBj_image(String bj_image) {
            this.bj_image = bj_image;
        }

        public String getConstellation() {
            return constellation;
        }

        public void setConstellation(String constellation) {
            this.constellation = constellation;
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

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
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
