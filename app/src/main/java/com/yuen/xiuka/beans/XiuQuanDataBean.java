package com.yuen.xiuka.beans;

import java.io.Serializable;
import java.util.List;

public  class XiuQuanDataBean implements Serializable {
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