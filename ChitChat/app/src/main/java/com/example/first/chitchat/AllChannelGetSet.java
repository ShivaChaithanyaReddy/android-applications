package com.example.first.chitchat;

import java.util.List;

/**
 * Created by Chaithanya on 3/27/2017.
 */

public class AllChannelGetSet {

    private String status;
    private String message;
    private List<ChannelBean> data;



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ChannelBean> getData() {
        return data;
    }

    public void setData(List<ChannelBean> data) {
        this.data = data;
    }

    public static class ChannelBean {

            private String channel_id;
            private String channel_name;

            public String getChannel_id() {
                return channel_id;
            }

            public void setChannel_id(String channel_id) {
                this.channel_id = channel_id;
            }

            public String getChannel_name() {
                return channel_name;
            }

            public void setChannel_name(String channel_name) {
                this.channel_name = channel_name;
            }
        }

        /* private String channelId;
    private String channelName;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }*/

}
