package com.tangce.fastcode.model;

/**
 * Created by Tanck on 11/7/2016.
 * <p>
 * Describe:
 */
public class ModifyUserPhotoResponse {

    /**
     * signature : 1dfdfgdf
     * nickName : ererere
     * photo : http://127.0.0.1:8080/csh-interface/img/photo/user1.jpg
     * id : 1
     * userName : 15892999216
     */

    private String signature;
    private String nickName;
    private String photo;
    private int id;
    private String userName;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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
}
