package com.zhang.retrofittest;

/**
 * Created by 29495 on 2017/11/6.
 * 诊断单模型
 */

public class DiagnosisApplyModel {

    public enum StateEnum {
        None, Wait, Diagnosising
    }


    private StateEnum state = StateEnum.None;
    private boolean isVerify = false;
    private UserModel expert;

    public String name; //设备名称
    public String time; //创建时间
    private String identification; //蓝牙地址
    public int status; //状态
    public String statusName;
    public String description; //描述
    public String userId; //创建人id
    public String verificationCode; // 验证码
    public String serviceGrade; // 评分
    public String valuation;    // 评价
    public String company; //公司
    public String phone; //电话
    private String id; //

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getServiceGrade() {
        return serviceGrade;
    }

    public void setServiceGrade(String serviceGrade) {
        this.serviceGrade = serviceGrade;
    }

    public String getValuation() {
        return valuation;
    }

    public void setValuation(String valuation) {
        this.valuation = valuation;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public StateEnum getState() {
        return state;
    }

    public void setState(StateEnum state) {
        this.state = state;
    }

    public UserModel getExpert() {
        return expert;
    }

    public void setExpert(UserModel expert) {
        this.expert = expert;
    }

    public boolean isVerify() {
        return isVerify;
    }

    public void setVerify(boolean verify) {
        isVerify = verify;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void apply(String address) {
        this.identification = address;
        this.state = StateEnum.Wait;
    }

}
