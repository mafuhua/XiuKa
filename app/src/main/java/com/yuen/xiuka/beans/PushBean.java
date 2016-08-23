package com.yuen.xiuka.beans;

/**
 * Created by Administrator on 2016/8/20.
 */
public class PushBean {


    /**
     * bplid : 10000050
     * content : 妈妈妈妈们
     * fabucontent :
     * fabuid :
     * fabuname :
     * name : 古丽娜扎
     * plid : 10000046
     * type : 2
     * xid : 252
     */

    private TxtBean txt;
    /**
     * txt : {"bplid":"10000050","content":"妈妈妈妈们","fabucontent":"","fabuid":"","fabuname":"","name":"古丽娜扎","plid":"10000046","type":"2","xid":"252"}
     * type : array
     */

    private String type;

    public TxtBean getTxt() {
        return txt;
    }

    public void setTxt(TxtBean txt) {
        this.txt = txt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static class TxtBean {
        private String bplid;
        private String content;
        private String fabucontent;
        private String fabuid;
        private String fabuname;
        private String name;
        private String plid;
        private String type;
        private String xid;

        public String getBplid() {
            return bplid;
        }

        public void setBplid(String bplid) {
            this.bplid = bplid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getFabucontent() {
            return fabucontent;
        }

        public void setFabucontent(String fabucontent) {
            this.fabucontent = fabucontent;
        }

        public String getFabuid() {
            return fabuid;
        }

        public void setFabuid(String fabuid) {
            this.fabuid = fabuid;
        }

        public String getFabuname() {
            return fabuname;
        }

        public void setFabuname(String fabuname) {
            this.fabuname = fabuname;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPlid() {
            return plid;
        }

        public void setPlid(String plid) {
            this.plid = plid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getXid() {
            return xid;
        }

        public void setXid(String xid) {
            this.xid = xid;
        }
    }
}
