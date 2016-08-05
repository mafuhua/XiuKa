package com.yuen.xiuka.beans;

import java.util.List;

/**
 * Created by Administrator on 2016/8/5.
 */
public class GongGaoBean {

    /**
     * code : 0
     * msg : 成功
     * data : [{"id":"4","content":"哈哈哈","time":"1470293683"},{"id":"1","content":"欢迎来到秀咖","time":"1470293257"}]
     */

    private String code;
    private String msg;
    /**
     * id : 4
     * content : 哈哈哈
     * time : 1470293683
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
        private String id;
        private String content;
        private String time;

        public String getData_time() {
            return data_time;
        }

        public void setData_time(String data_time) {
            this.data_time = data_time;
        }

        private String data_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
