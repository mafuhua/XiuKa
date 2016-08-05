package com.yuen.xiuka.utils;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2016/7/23.
 */

@Table(name = "person")
public class PersonTable {
    @Column(name = "id", isId = true, autoGen = false)
    private int id;
    //姓名
    @Column(name = "name")
    private String name;
    //头像
    @Column(name = "img")
    private String img;
    //uid
    @Column(name = "uid")
    private int uid;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PersonTable{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", img='" + img + '\'' +
                ", uid=" + uid +
                '}';
    }
}