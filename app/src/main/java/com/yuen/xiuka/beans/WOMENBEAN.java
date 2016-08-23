package com.yuen.xiuka.beans;

import com.yuen.xiuka.utils.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by Administrator on 2016/8/22.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class WOMENBEAN {

    /**
     * code : 0
     * msg : 成功
     * data : {"aboutus":"地址：上海市嘉定区南翔中冶祥腾2号楼1016-1017\n\n电话：222222\n\n手机：18512012354\n\n联系人：程先生\n\n邮政编号：410001\n\n网站：www.yuenkeji.com\n\n邮箱：3333@qq.com                                                        "}
     */

    private String code;
    private String msg;
    /**
     * aboutus : 地址：上海市嘉定区南翔中冶祥腾2号楼1016-1017

     电话：222222

     手机：18512012354

     联系人：程先生

     邮政编号：410001

     网站：www.yuenkeji.com

     邮箱：3333@qq.com
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
        private String aboutus;

        public String getAboutus() {
            return aboutus;
        }

        public void setAboutus(String aboutus) {
            this.aboutus = aboutus;
        }
    }
}
