package com.lexing360.app.lexingupdate.model;

/**
 * Created by fenglingfeng on 2018/2/2.
 */

public class JwtModel {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private String jwt;

        public String getJwt() {
            return jwt;
        }

        public void setJwt(String jwt) {
            this.jwt = jwt;
        }

    }
}
