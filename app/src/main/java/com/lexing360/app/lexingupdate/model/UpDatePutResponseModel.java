package com.lexing360.app.lexingupdate.model;

/**
 * Created by fenglingfeng on 2018/2/24.
 */

public class UpDatePutResponseModel {


    private String message;
    private DataBean data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private Object hide;
        private String channel;
        private String downloadUrl;
        private long id;
        private String version;

        public Object getHide() {
            return hide;
        }

        public void setHide(Object hide) {
            this.hide = hide;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
}
