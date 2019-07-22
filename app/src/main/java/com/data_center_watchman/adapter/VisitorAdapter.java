package com.data_center_watchman.adapter;

import com.data_center_watchman.model.OauthToken;
import com.data_center_watchman.model.Remedy;
import com.data_center_watchman.model.Visitor;
import com.data_center_watchman.test.Crq;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface VisitorAdapter {

    @POST("postVisitor")
    Call<Visitor> sendRequest(@Body Visitor visitor);
    @GET("getVisitor")
    Call<List<Visitor>> getAll();
    @GET("getCheckedIn")
    Call<List<Visitor>> getAllCheckedIn();
    @GET("getRequests")
    Call<List<Visitor>> getAllRequests();
    @GET("getCheckedOut")
    Call<List<Visitor>> getAllCheckedOut();
    @POST("checkin")
    Call<Visitor> checkin(@Body Visitor visitor);
    @POST("checkout")
    Call<Visitor> checkout(@Body Visitor visitor);
    @POST("crqtestapi/crqQuery")
    @FormUrlEncoded
    Call<Remedy> getStatus(@Field("CRQNumber") String crq);
    @GET("oauth/v1/generate?grant_type=client_credentials")
    Call<OauthToken> getToken();
}
