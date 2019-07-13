package com.data_center_watchman.adapter;

import com.data_center_watchman.model.Visitor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface VisitorAdapter {

    @POST("postVisitor")
    Call<Visitor> createVisitor(@Body Visitor visitor);

    @GET("getVisitor")
    Call<List<Visitor>> getAll();

}
