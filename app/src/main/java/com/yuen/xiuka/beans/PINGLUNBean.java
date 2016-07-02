package com.yuen.xiuka.beans;

import java.util.List;

/**
 * Created by Administrator on 2016/7/1.
 */
public class PINGLUNBean {


    /**
     * code : 0
     * msg : 成功
     * data : {"id":"16","uid":"10000005","add":"上海市","content":"监控摄像头","image":["circle/201607/1467362747-79038.jpg","circle/201607/1467362747-14482.jpg","circle/201607/1467362747-85769.jpg"],"platform":"","zhibo_time":"","comments":[{"uid":"10000005","content":"asd","time":"1467171761","name":"您认为是一种自信","img":"avatar/201606/1466666600-56376.jpg"},{"uid":"10000005","content":"asd","time":"1467116255","name":"您认为是一种自信","img":"avatar/201606/1466666600-56376.jpg"},{"uid":"10000003","content":"哈哈哈","time":"1467010963","name":"236","img":"avatar/201606/1466666600-56376.jpg"},{"uid":"10000003","content":"嘻嘻","time":"1467010963","name":"236","img":"avatar/201606/1466666600-56376.jpg"},{"uid":"10000003","content":"啊啊啊","time":"1467010963","name":"236","img":"avatar/201606/1466666600-56376.jpg"},{"uid":"10000003","content":"嗯嗯","time":"1467010963","name":"236","img":"avatar/201606/1466666600-56376.jpg"},{"uid":"10000003","content":"天天","time":"1467010963","name":"236","img":"avatar/201606/1466666600-56376.jpg"},{"uid":"10000003","content":"从v","time":"1467010963","name":"236","img":"avatar/201606/1466666600-56376.jpg"}],"time":"1467362747","name":"您认为是一种自信","img":"avatar/201606/1466666600-56376.jpg"}
     */

    private String code;
    private String msg;
    /**
     * id : 16
     * uid : 10000005
     * add : 上海市
     * content : 监控摄像头
     * image : ["circle/201607/1467362747-79038.jpg","circle/201607/1467362747-14482.jpg","circle/201607/1467362747-85769.jpg"]
     * platform :
     * zhibo_time :
     * comments : [{"uid":"10000005","content":"asd","time":"1467171761","name":"您认为是一种自信","img":"avatar/201606/1466666600-56376.jpg"},{"uid":"10000005","content":"asd","time":"1467116255","name":"您认为是一种自信","img":"avatar/201606/1466666600-56376.jpg"},{"uid":"10000003","content":"哈哈哈","time":"1467010963","name":"236","img":"avatar/201606/1466666600-56376.jpg"},{"uid":"10000003","content":"嘻嘻","time":"1467010963","name":"236","img":"avatar/201606/1466666600-56376.jpg"},{"uid":"10000003","content":"啊啊啊","time":"1467010963","name":"236","img":"avatar/201606/1466666600-56376.jpg"},{"uid":"10000003","content":"嗯嗯","time":"1467010963","name":"236","img":"avatar/201606/1466666600-56376.jpg"},{"uid":"10000003","content":"天天","time":"1467010963","name":"236","img":"avatar/201606/1466666600-56376.jpg"},{"uid":"10000003","content":"从v","time":"1467010963","name":"236","img":"avatar/201606/1466666600-56376.jpg"}]
     * time : 1467362747
     * name : 您认为是一种自信
     * img : avatar/201606/1466666600-56376.jpg
     */

    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String id;
        private String uid;
        private String add;
        private String content;
        private String platform;
        private String zhibo_time;
        private String time;
        private String name;
        private String img;
        private List<String> image;
        /**
         * uid : 10000005
         * content : asd
         * time : 1467171761
         * name : 您认为是一种自信
         * img : avatar/201606/1466666600-56376.jpg
         */

        private List<CommentsBean> comments;

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

        public String getAdd() {
            return add;
        }

        public void setAdd(String add) {
            this.add = add;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public String getZhibo_time() {
            return zhibo_time;
        }

        public void setZhibo_time(String zhibo_time) {
            this.zhibo_time = zhibo_time;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public List<String> getImage() {
            return image;
        }

        public void setImage(List<String> image) {
            this.image = image;
        }

        public List<CommentsBean> getComments() {
            return comments;
        }

        public void setComments(List<CommentsBean> comments) {
            this.comments = comments;
        }

        public static class CommentsBean {
            private String uid;
            private String content;
            private String time;
            private String name;
            private String img;

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }
    }
}
