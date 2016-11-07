package com.tangce.fastcode.api;

import com.tangce.fastcode.model.BaseResponse;
import com.tangce.fastcode.model.ModifyUserPhotoResponse;
import com.tangce.fastcode.network.FastHttp;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import rx.Observable;

/**
 * Created by Tanck on 11/7/2016.
 * <p>
 * Describe:
 */
public class EditUserInfoApi {
    private interface EditUserInfoService {
        @Multipart
        @POST("csh-interface/endUser/editUserPhoto.jhtml")
        Observable<BaseResponse<ModifyUserPhotoResponse>> modifyUserPhoto(@PartMap Map<String, RequestBody> params);
    }

    public static Observable<BaseResponse<ModifyUserPhotoResponse>> modifyUserPhoto(Map<String, RequestBody> params) {
        return FastHttp.create(EditUserInfoService.class).modifyUserPhoto(params);
    }
}
