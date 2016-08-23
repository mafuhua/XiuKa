package com.yuen.xiuka.utils;

import java.util.List;

import io.rong.imlib.model.Conversation;

/**
 * Created by Administrator on 2016/8/8.
 */
public class MyEvent {
    public Event event;

    public String getmTargetId() {
        return mTargetId;
    }

    public void setmTargetId(String mTargetId) {
        this.mTargetId = mTargetId;
    }

    public String mTargetId;

    public String getmPush() {
        return mPush;
    }

    public void setmPush(String mPush) {
        this.mPush = mPush;
    }

    public String mPush;

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String add;

    public List<Conversation> getGuanzhuList() {
        return guanzhuList;
    }

    public void setGuanzhuList(List<Conversation> guanzhuList) {
        this.guanzhuList = guanzhuList;
    }

    private List<Conversation> guanzhuList;
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public MyEvent(Event event) {
        this.event = event;
    }

    public enum Event{
        REFRESH_XIUQUAN,
        GET_TOKEN,
        REFRESH_LIAOTIAN,
        REFRESH_DIAN,
        REFRESH_ADD,
        NOTIFICATION_PINGLUN,
        NOTIFICATION_PINGLUNDIAN,
        REFRESH_HOUTAIDIAN

    }

}
