package com.yuen.xiuka.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/6/29.
 */
public class XIUQUANBean implements Serializable{


    /**
     * code : 0
     * data : [{"add":"上海市","comments":"2","content":"回家咯","id":"192","image":[{"img":""}],"img":"avatar/201608/1471069687-72731.jpg","name":"你是谁","platform":"","share":"2","time":"08-13 15:20","uid":"10000042","zan":"10","zhibo_time":""}]
     * datas : {"bj_image":"","fensi":"3","guanzhu":"3","image":"avatar/201608/1471069687-72731.jpg","name":"你是谁","platform":"","shifou":0,"shifou_ren":0,"type":"0","uid":"10000042"}
     * msg : 成功
     */

    private String code;
    /**
     * bj_image :
     * fensi : 3
     * guanzhu : 3
     * image : avatar/201608/1471069687-72731.jpg
     * name : 你是谁
     * platform :
     * shifou : 0
     * shifou_ren : 0
     * type : 0
     * uid : 10000042
     */

    private DatasBean datas;
    private String msg;
    /**
     * add : 上海市
     * comments : 2
     * content : 回家咯
     * id : 192
     * image : [{"img":""}]
     * img : avatar/201608/1471069687-72731.jpg
     * name : 你是谁
     * platform :
     * share : 2
     * time : 08-13 15:20
     * uid : 10000042
     * zan : 10
     * zhibo_time :
     */

    private List<XiuQuanDataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<XiuQuanDataBean> getData() {
        return data;
    }

    public void setData(List<XiuQuanDataBean> data) {
        this.data = data;
    }

    public static class DatasBean {
        private String bj_image;
        private String fensi;
        private String guanzhu;
        private String image;
        private String name;
        private String platform;
        private String platformid;

        public String getPlatformname() {
            return platformname;
        }

        public void setPlatformname(String platformname) {
            this.platformname = platformname;
        }

        public String getPlatformid() {
            return platformid;
        }

        public void setPlatformid(String platformid) {
            this.platformid = platformid;
        }

        private String platformname;
        private int shifou;
        private int shifou_ren;
        private String type;
        private String uid;

        public String getBj_image() {
            return bj_image;
        }

        public void setBj_image(String bj_image) {
            this.bj_image = bj_image;
        }

        public String getFensi() {
            return fensi;
        }

        public void setFensi(String fensi) {
            this.fensi = fensi;
        }

        public String getGuanzhu() {
            return guanzhu;
        }

        public void setGuanzhu(String guanzhu) {
            this.guanzhu = guanzhu;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
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

        public int getShifou() {
            return shifou;
        }

        public void setShifou(int shifou) {
            this.shifou = shifou;
        }

        public int getShifou_ren() {
            return shifou_ren;
        }

        public void setShifou_ren(int shifou_ren) {
            this.shifou_ren = shifou_ren;
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
    }

}