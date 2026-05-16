package co.edu.univalle.worklinkapp.api;

import co.edu.univalle.worklinkapp.model.Usuario;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface UserService {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.74.184:8080/user/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("get")
    Call<Usuario> obtenerUser(@Header("Authorization") String token);

}
