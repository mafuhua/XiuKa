package com.yuen.xiuka.beans;

import java.util.List;

/**
 * Created by Administrator on 2016/8/5.
 */
public class BianQianBean {

    /**
     * code : 0
     * msg : 成功
     * data : [{"cid":"5","pid":"0","name":"女神","type":"n","type_id":"0","pai":"0","lang":"zh-cn"},{"cid":"6","pid":"0","name":"摄影","type":"n","type_id":"0","pai":"0","lang":"zh-cn"},{"cid":"7","pid":"0","name":"旅行","type":"n","type_id":"0","pai":"0","lang":"zh-cn"}]
     */

    private String code;
    private String msg;
    /**
     * cid : 5
     * pid : 0
     * name : 女神
     * type : n
     * type_id : 0
     * pai : 0
     * lang : zh-cn
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
        private String cid;
        private String pid;
        private String name;
        private String type;
        private String type_id;
        private String pai;
        private String lang;

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getType_id() {
            return type_id;
        }

        public void setType_id(String type_id) {
            this.type_id = type_id;
        }

        public String getPai() {
            return pai;
        }

        public void setPai(String pai) {
            this.pai = pai;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }
    }
}
