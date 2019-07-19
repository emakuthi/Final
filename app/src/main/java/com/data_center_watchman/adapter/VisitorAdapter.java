package com.data_center_watchman.adapter;

import com.data_center_watchman.model.Visitor;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
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


}
