package com.data_center_watchman.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.data_center_watchman.Constants;
import com.data_center_watchman.R;
import com.data_center_watchman.adapter.VisitorAdapter;
import com.data_center_watchman.model.OauthToken;
import com.data_center_watchman.model.Remedy;
import com.data_center_watchman.test.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ValidateCrq extends AppCompatActivity {

    VisitorAdapter adapter;

    Retrofit.Builder builder = new Retrofit.Builder().baseUrl(Constants.REMEDY_URL)
            .addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit = builder.build();
    VisitorAdapter remedyService = retrofit.create(VisitorAdapter.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_crq);

        Button btn = findViewById(R.id.btnValidate);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticate();
            }
        });
    }
    private static String token;

    public void authenticate(){
        ApiUtils.getAuthenticationAPI(Constants.RMEDEDY_API_KEY, Constants.REMEDY_SECRET, Constants.REMEDY_URL).getToken().enqueue(new Callback<OauthToken>() {
            @Override
            public void onResponse(@NonNull Call<OauthToken> call, @NonNull Response<OauthToken> response) {
                if (response.isSuccessful()) {
                    OauthToken accessToken = response.body();
                    if (accessToken != null) {

                        Log.i(ValidateCrq.class.getSimpleName(), response.body().getToken());
                        token=response.body().getToken();

                        getCrq("CRQ000000369301", token);

                        return;
                    }
                }
                Log.e(ValidateCrq.class.getSimpleName(), "Authentication Failed: " + "Authentication Failed: "+response.raw());
            }
            @Override
            public void onFailure(@NonNull Call<OauthToken> call, @NonNull Throwable t) {
                Log.e(ValidateCrq.class.getSimpleName(), "Authentication Failed: " + t.getLocalizedMessage());
            }
        });
    }
    public void getCrq(String crq, String authToken){

        ApiUtils.getCRQStatus(authToken).getStatus(crq).enqueue(new Callback<Remedy>() {
            @Override
            public void onResponse(@NonNull Call<Remedy> call, @NonNull Response<Remedy> response) {

                if (response.isSuccessful()) {
                        Log.i(ValidateCrq.class.getSimpleName(), response.body().getFirstName());
                        return;
                }
                Log.e(ValidateCrq.class.getSimpleName(), "Failed: "+response.raw());
            }
            @Override
            public void onFailure(@NonNull Call<Remedy> call, @NonNull Throwable t) {
                Log.e(ValidateCrq.class.getSimpleName(), "Failed: " + t.getLocalizedMessage());
            }
        });

//        adapter = VisitorService.getRetrofitInstance().create(VisitorAdapter.class);
//        Call<Remedy> call = adapter.getStatus(crq);
////        isOnline();
//        final ProgressDialog progressDialog;
//        progressDialog = new ProgressDialog(ValidateCrq.this);
//        progressDialog.setMessage("Loading.Please Wait.......");
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.show();
//
//        call.enqueue(new Callback<Remedy>() {
//            @Override
//            public void onResponse(Call<Remedy> call, Response<Remedy> response) {
//
//                Log.d("Test", response.body().toString());
//
//                progressDialog.dismiss();
//            }
//            @Override
//            public void onFailure(Call<Remedy> call, Throwable t) {
//                Toast.makeText(ValidateCrq.this, "Something went wrong......try again", Toast.LENGTH_SHORT).show();
//                Log.d("Test", t.toString());
//                progressDialog.dismiss();
//            }
//        });

    }

}
