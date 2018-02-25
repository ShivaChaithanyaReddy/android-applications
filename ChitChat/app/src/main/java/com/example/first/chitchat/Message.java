package com.example.first.chitchat;

import java.util.List;

/**
 * Created by Chaithanya on 3/27/2017.
 */


public class Message {
    private String status;
    private String message;

    private List<MessageBeam> data;

    public List<MessageBeam> getData() {
        return data;
    }

    public void setData(List<MessageBeam> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public static class MessageBeam {

        private String message_id;
        private String channel_id;
        private userclass obj;
        private String msg_time;
        private String messages_text;

        public String getChannel_id() {
            return channel_id;
        }

        public void setChannel_id(String channel_id) {
            this.channel_id = channel_id;
        }

        public String getMessage_id() {
            return message_id;
        }

        public void setMessage_id(String message_id) {
            this.message_id = message_id;
        }

        public String getMessages_text() {
            return messages_text;
        }

        public void setMessages_text(String messages_text) {
            this.messages_text = messages_text;
        }

        public String getMsg_time() {
            return msg_time;
        }

        public void setMsg_time(String msg_time) {
            this.msg_time = msg_time;
        }

        public userclass getObj() {
            return obj;
        }

        public void setObj(userclass obj) {
            this.obj = obj;
        }

        public static class  userclass {
            private String email;
            private  String fname;
            private String  lname;

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getFname() {
                return fname;
            }

            public void setFname(String fname) {
                this.fname = fname;
            }

            public String getLname() {
                return lname;
            }

            public void setLname(String lname) {
                this.lname = lname;
            }
        }
    }
}
