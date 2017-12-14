package com.zhang.retrofittest;

/**
 * Created by zhang on 2017/12/11.
 */

public class LoginBean {

    private Token token;
    private UserModel user;

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "token=" + token +
                ", user=" + user +
                '}';
    }
}
