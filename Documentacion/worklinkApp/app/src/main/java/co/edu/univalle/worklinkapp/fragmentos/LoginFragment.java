package co.edu.univalle.worklinkapp.fragmentos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import co.edu.univalle.worklinkapp.MainActivity;
import co.edu.univalle.worklinkapp.R;
import co.edu.univalle.worklinkapp.api.AuthService;
import co.edu.univalle.worklinkapp.api.UserService;
import co.edu.univalle.worklinkapp.model.Usuario;
import co.edu.univalle.worklinkapp.model.dto.LoginRequest;
import co.edu.univalle.worklinkapp.model.dto.LoginResponse;
import co.edu.univalle.worklinkapp.utils.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment {
    EditText emailEditText;
    EditText passwordEditText;
    Button btnEnviar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emailEditText = view.findViewById(R.id.editTextEmail);
        passwordEditText = view.findViewById(R.id.editTextPassword);
        btnEnviar = view.findViewById(R.id.buttonEnviarLogin);

        btnEnviar.setOnClickListener(v -> procesarLogin());
    }

    private void procesarLogin(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setCorreo(emailEditText.getText().toString());
        loginRequest.setPassword(passwordEditText.getText().toString());

        AuthService authService =AuthService.retrofit.create(AuthService.class);
        Call<LoginResponse> call = authService.login(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Snackbar.make(getView(),response.body().getToken(), Snackbar.LENGTH_SHORT).show();
                Log.i("token", response.body().getToken());
                obtenerUser(response.body().getToken());
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("Error", t.toString());

            }
        });

    }
    private void obtenerUser(String token){
        UserService userService = UserService.retrofit.create(UserService.class);
        Call<Usuario> call = userService.obtenerUser("Bearer "+token);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Usuario usuario = response.body();
                SessionManager session = new SessionManager(getContext());
                session.createSession(
                        usuario.getCorreo(),
                        usuario.getNombre() + " " +usuario.getApellido()
                        , token, usuario.getRol()
                );
                getParentFragmentManager().popBackStack();

            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("Error", t.toString());
            }
        });

    }
}