package co.edu.univalle.worklinkapp.api;

import co.edu.univalle.worklinkapp.model.dto.LoginRequest;
import co.edu.univalle.worklinkapp.model.dto.LoginResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AuthService {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.74.184:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);


}
