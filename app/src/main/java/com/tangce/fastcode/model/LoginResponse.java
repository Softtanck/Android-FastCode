package com.tangce.fastcode.model;

/**
 * Created by Tanck on 11/3/2016.
 * <p>
 * Describe:
 */
public class LoginResponse {

    /**
     * defaultVehiclePlate : 川A88888
     * signature : 请输入您的签名
     * defaultVehicleIcon : /upload/vehicleIcon/2016-05-14/src_de8ec8de-1da9-4115-94cf-8d6e1cefdd4f.jpg
     * nickName : Tanck
     * defaultVehicleId : 1
     * photo : /upload/endUser/photo/src_b3a7508e-8c95-4d21-a265-5376a2108927.jpeg
     * id : 1
     * userName : 18380473706
     * defaultVehicle : 进口Artega-Artega GT
     * defaultDeviceNo : 3333333333
     */

    private String defaultVehiclePlate;
    private String signature;
    private String defaultVehicleIcon;
    private String nickName;
    private int defaultVehicleId;
    private String photo;
    private int id;
    private String userName;
    private String defaultVehicle;
    private String defaultDeviceNo;

    public String getDefaultVehiclePlate() {
        return defaultVehiclePlate;
    }

    public void setDefaultVehiclePlate(String defaultVehiclePlate) {
        this.defaultVehiclePlate = defaultVehiclePlate;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getDefaultVehicleIcon() {
        return defaultVehicleIcon;
    }

    public void setDefaultVehicleIcon(String defaultVehicleIcon) {
        this.defaultVehicleIcon = defaultVehicleIcon;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getDefaultVehicleId() {
        return defaultVehicleId;
    }

    public void setDefaultVehicleId(int defaultVehicleId) {
        this.defaultVehicleId = defaultVehicleId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDefaultVehicle() {
        return defaultVehicle;
    }

    public void setDefaultVehicle(String defaultVehicle) {
        this.defaultVehicle = defaultVehicle;
    }

    public String getDefaultDeviceNo() {
        return defaultDeviceNo;
    }

    public void setDefaultDeviceNo(String defaultDeviceNo) {
        this.defaultDeviceNo = defaultDeviceNo;
    }
}
