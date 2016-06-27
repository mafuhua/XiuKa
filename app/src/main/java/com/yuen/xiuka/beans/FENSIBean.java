package com.yuen.xiuka.beans;

import java.util.List;

/**
 * Created by Administrator on 2016/6/25.
 */
public class FENSIBean {

    /**
     * code : 0
     * msg : 成功
     * data : [{"id":"1","uid":"10000005","g_uid":"10000001","type":"2","name":"您认为是一种自信","m_uid":"10000005","qianming":"东西外婆而你自我后面","image":"avatar/201606/1466666600-56376.jpg","xianghu":1},{"id":"2","uid":"10000005","g_uid":"10000002","type":"2","name":"您认为是一种自信","m_uid":"10000005","qianming":"东西外婆而你自我后面","image":"avatar/201606/1466666600-56376.jpg","xianghu":1},{"id":"3","uid":"10000005","g_uid":"10000003","type":"1","name":"您认为是一种自信","m_uid":"10000005","qianming":"东西外婆而你自我后面","image":"avatar/201606/1466666600-56376.jpg","xianghu":0}]
     */

    private String code;
    private String msg;
    /**
     * id : 1
     * uid : 10000005
     * g_uid : 10000001
     * type : 2
     * name : 您认为是一种自信
     * m_uid : 10000005
     * qianming : 东西外婆而你自我后面
     * image : avatar/201606/1466666600-56376.jpg
     * xianghu : 1
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
        private String uid;
        private String g_uid;
        private String type;
        private String name;
        private String m_uid;
        private String qianming;
        private String image;
        private int xianghu;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getG_uid() {
            return g_uid;
        }

        public void setG_uid(String g_uid) {
            this.g_uid = g_uid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getM_uid() {
            return m_uid;
        }

        public void setM_uid(String m_uid) {
            this.m_uid = m_uid;
        }

        public String getQianming() {
            return qianming;
        }

        public void setQianming(String qianming) {
            this.qianming = qianming;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getXianghu() {
            return xianghu;
        }

        public void setXianghu(int xianghu) {
            this.xianghu = xianghu;
        }
    }
}
