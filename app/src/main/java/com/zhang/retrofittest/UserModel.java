package com.zhang.retrofittest;

import com.zhang.retrofittest.utils.StringUtil;

import java.util.List;

/**
 * Created by 29495 on 2017/11/6.
 */

public class UserModel {

    public enum RoleEnum {
        Ordinary, Expert,APPOINT
    }

    private String id;
    private String userNo;
    private String displayName;
    private String gender;
    private String birthday;
    private String country;
    private String state;
    private String address;
    private String zip;
    private String remark;
    private boolean isEnable;
    private List<RolesBean> roles;

    private RoleEnum role;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public List<RolesBean> getRoles() {
        return roles;
    }

    public void setRoles(List<RolesBean> roles) {
        this.roles = roles;
    }

    public String getUserNo() {

        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public boolean isLogin() {
        return !StringUtil.isEmptyOrNull(this.id);
    }
    public static class RolesBean {
        /**
         * id : 797b19d6-a931-c3e1-64d1-08d52fddc8f1
         * name : 系统管理员
         * description : 整个系统的管理人员，对系统中所有资源均有操作访问的权限
         * createTime : 0001-01-01T00:00:00
         */

        private String id;
        private String name;
        private String description;
        private String createTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
