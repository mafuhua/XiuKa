package com.yuen.xiuka.beans;

import java.util.List;

/**
 * Created by Administrator on 2016/8/17.
 */
public class ImgBeans {

    /**
     * code : 0
     * data : [{"ad_img":"201608/57b410265df8f.jpg","ad_link":"","ad_name":"test2","id":"18","lang":"zh-cn","position":"index","sort":"50"},{"ad_img":"201608/57b410265df8f.jpg","ad_link":"","ad_name":"test3","id":"19","lang":"zh-cn","position":"index","sort":"50"},{"ad_img":"201608/57b410265df8f.jpg","ad_link":"","ad_name":"test1","id":"16","lang":"zh-cn","position":"index","sort":"0"}]
     * msg : 成功
     */

    private String code;
    private String msg;
    /**
     * ad_img : 201608/57b410265df8f.jpg
     * ad_link :
     * ad_name : test2
     * id : 18
     * lang : zh-cn
     * position : index
     * sort : 50
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
        private String ad_img;
        private String ad_link;
        private String ad_name;
        private String id;
        private String lang;
        private String position;
        private String sort;

        public String getAd_img() {
            return ad_img;
        }

        public void setAd_img(String ad_img) {
            this.ad_img = ad_img;
        }

        public String getAd_link() {
            return ad_link;
        }

        public void setAd_link(String ad_link) {
            this.ad_link = ad_link;
        }

        public String getAd_name() {
            return ad_name;
        }

        public void setAd_name(String ad_name) {
            this.ad_name = ad_name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }
    }
}
