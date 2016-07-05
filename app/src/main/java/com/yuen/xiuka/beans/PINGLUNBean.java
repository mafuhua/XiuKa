package com.yuen.xiuka.beans;

import java.util.List;

/**
 * Created by Administrator on 2016/7/1.
 */
public class PINGLUNBean {


    /**
     * code : 0
     * data : {"add":" ","comments":[{"content":"kxzckjklasd","img":"avatar/201607/1467440544-11447.jpg","name":"6艘哦","time":"07-05 11:39","uid":"10000005"},{"content":"jdfkasdkfjlckxvzlxkcjvaoijfaksdfwe","img":"avatar/201607/1467440544-11447.jpg","name":"6艘哦","time":"07-05 11:39","uid":"10000005"},{"content":"","img":"avatar/201607/1467440544-11447.jpg","name":"6艘哦","time":"07-05 11:34","uid":"10000005"},{"content":"123","img":"avatar/201607/1467440544-11447.jpg","name":"6艘哦","time":"07-05 11:32","uid":"10000005"},{"content":"asjdalksjlxmvc.,xcjfiosdajfiosajdoifjoaiwejroisjoidajflkadsjlfkasdjopifjsdaoiafjaoisdfjoiasdfjoaisdjfoiasdjfoiasdjfoisadjfoisadjfoisd","img":"avatar/201607/1467440544-11447.jpg","name":"6艘哦","time":"07-05 11:21","uid":"10000005"},{"content":"asdjaskjdoasijxzklcmas","img":"avatar/201607/1467440544-11447.jpg","name":"6艘哦","time":"07-05 11:20","uid":"10000005"},{"content":"煞笔","img":"avatar/201607/1467440544-11447.jpg","name":"6艘哦","time":"07-02 16:13","uid":"10000005"}],"content":"Jkmbjkbkjb,mm Kim,hm in join notion jug Hun job hkjli ,k; o9j ink I'm jk no nk nk Klnklmlk l. I'll;';l';l'","id":"21","image":"circle/201607/1467447042-47067.jpg,circle/201607/1467447043-99382.jpg","img":"avatar/201606/1466666600-56376.jpg","name":"237","platform":"","time":"1467447040","uid":"10000006","zhibo_time":""}
     * msg : 成功
     */

    private String code;
    /**
     * add :
     * comments : [{"content":"kxzckjklasd","img":"avatar/201607/1467440544-11447.jpg","name":"6艘哦","time":"07-05 11:39","uid":"10000005"},{"content":"jdfkasdkfjlckxvzlxkcjvaoijfaksdfwe","img":"avatar/201607/1467440544-11447.jpg","name":"6艘哦","time":"07-05 11:39","uid":"10000005"},{"content":"","img":"avatar/201607/1467440544-11447.jpg","name":"6艘哦","time":"07-05 11:34","uid":"10000005"},{"content":"123","img":"avatar/201607/1467440544-11447.jpg","name":"6艘哦","time":"07-05 11:32","uid":"10000005"},{"content":"asjdalksjlxmvc.,xcjfiosdajfiosajdoifjoaiwejroisjoidajflkadsjlfkasdjopifjsdaoiafjaoisdfjoiasdfjoaisdjfoiasdjfoiasdjfoisadjfoisadjfoisd","img":"avatar/201607/1467440544-11447.jpg","name":"6艘哦","time":"07-05 11:21","uid":"10000005"},{"content":"asdjaskjdoasijxzklcmas","img":"avatar/201607/1467440544-11447.jpg","name":"6艘哦","time":"07-05 11:20","uid":"10000005"},{"content":"煞笔","img":"avatar/201607/1467440544-11447.jpg","name":"6艘哦","time":"07-02 16:13","uid":"10000005"}]
     * content : Jkmbjkbkjb,mm Kim,hm in join notion jug Hun job hkjli ,k; o9j ink I'm jk no nk nk Klnklmlk l. I'll;';l';l'
     * id : 21
     * image : circle/201607/1467447042-47067.jpg,circle/201607/1467447043-99382.jpg
     * img : avatar/201606/1466666600-56376.jpg
     * name : 237
     * platform :
     * time : 1467447040
     * uid : 10000006
     * zhibo_time :
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

    public static class DataBean {
        private String add;
        private String content;
        private String id;
        private String image;
        private String img;
        private String name;
        private String platform;
        private String time;
        private String uid;
        private String zhibo_time;
        /**
         * content : kxzckjklasd
         * img : avatar/201607/1467440544-11447.jpg
         * name : 6艘哦
         * time : 07-05 11:39
         * uid : 10000005
         */

        private List<CommentsBean> comments;

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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
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

        public String getZhibo_time() {
            return zhibo_time;
        }

        public void setZhibo_time(String zhibo_time) {
            this.zhibo_time = zhibo_time;
        }

        public List<CommentsBean> getComments() {
            return comments;
        }

        public void setComments(List<CommentsBean> comments) {
            this.comments = comments;
        }

        public static class CommentsBean {
            private String content;
            private String img;
            private String name;
            private String time;
            private String uid;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
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
        }
    }
}
