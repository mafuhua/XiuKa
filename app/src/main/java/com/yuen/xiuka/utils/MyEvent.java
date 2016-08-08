package com.yuen.xiuka.utils;

/**
 * Created by Administrator on 2016/8/8.
 */
public class MyEvent {
    public Event event;

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
        GET_FILE_MESSAGE_FAILD
    }

}
