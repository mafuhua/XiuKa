package com.yuen.xiuka.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/6/29.
 */
public class XIUQUANBean implements Serializable{


    /**
     * code : 0
     * data : [{"add":"上海市","comments":"0","content":"fgh","id":"40","image":[{"img":"circle/201607/1469429715-69114.jpg"}],"img":"avatar/201607/1468836256-37916.jpg","name":"vayne","platform":"","share":"0","time":"07-25 14:55","uid":"10000005","zan":"0","zhibo_time":""},{"add":"上海市","comments":"0","content":"fgh","id":"39","image":[{"img":"circle/201607/1469429708-57114.jpg"}],"img":"avatar/201607/1468836256-37916.jpg","name":"vayne","platform":"","share":"0","time":"07-25 14:55","uid":"10000005","zan":"0","zhibo_time":""},{"add":"上海市","comments":"0","content":"fhj","id":"38","image":[{"img":"circle/201607/1469429525-86148.jpg"}],"img":"avatar/201607/1468836256-37916.jpg","name":"vayne","platform":"","share":"0","time":"07-25 14:52","uid":"10000005","zan":"0","zhibo_time":""},{"add":"上海市","comments":"0","content":"ftffyv","id":"37","image":[{"img":"circle/201607/1469429482-25702.jpg"}],"img":"avatar/201607/1468836256-37916.jpg","name":"vayne","platform":"","share":"0","time":"07-25 14:51","uid":"10000005","zan":"0","zhibo_time":""},{"add":"上海市","comments":"0","content":"ftffyv","id":"36","image":[{"img":"circle/201607/1469429374-21173.jpg"}],"img":"avatar/201607/1468836256-37916.jpg","name":"vayne","platform":"","share":"0","time":"07-25 14:49","uid":"10000005","zan":"0","zhibo_time":""}]
     * datas : {"bj_image":"avatar/201606/1466234501","fensi":"3","guanzhu":"2","image":"avatar/201607/1468836256-37916.jpg","name":"vayne","platform":"虎牙","type":"1","uid":"10000005"}
     * msg : 成功
     */

    private String code;
    /**
     * bj_image : avatar/201606/1466234501
     * fensi : 3
     * guanzhu : 2
     * image : avatar/201607/1468836256-37916.jpg
     * name : vayne
     * platform : 虎牙
     * type : 1
     * uid : 10000005
     */

    private DatasBean datas;
    private String msg;
    /**
     * add : 上海市
     * comments : 0
     * content : fgh
     * id : 40
     * image : [{"img":"circle/201607/1469429715-69114.jpg"}]
     * img : avatar/201607/1468836256-37916.jpg
     * name : vayne
     * platform :
     * share : 0
     * time : 07-25 14:55
     * uid : 10000005
     * zan : 0
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

    public  class XiuQuanDataBean implements Serializable{
        private String add;
        private String comments;
        private String content;
        private String id;
        private String img;
        private String name;
        private String platform;
        private String share;
        private String time;
        private String uid;
        private String zan;

        public boolean isZanflag() {
            return zanflag;
        }

        public void setZanflag(boolean zanflag) {
            this.zanflag = zanflag;
        }

        private boolean zanflag;
        private String zhibo_time;
        /**
         * img : circle/201607/1469429715-69114.jpg
         */

        private List<ImageBean> image;

        public String getAdd() {
            return add;
        }

        public void setAdd(String add) {
            this.add = add;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
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

        public String getShare() {
            return share;
        }

        public void setShare(String share) {
            this.share = share;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getZan() {
            return zan;
        }

        public void setZan(String zan) {
            this.zan = zan;
        }

        public String getZhibo_time() {
            return zhibo_time;
        }

        public void setZhibo_time(String zhibo_time) {
            this.zhibo_time = zhibo_time;
        }

        public List<ImageBean> getImage() {
            return image;
        }

        public void setImage(List<ImageBean> image) {
            this.image = image;
        }

        public  class ImageBean implements Serializable{
            private String img;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }
    }
}