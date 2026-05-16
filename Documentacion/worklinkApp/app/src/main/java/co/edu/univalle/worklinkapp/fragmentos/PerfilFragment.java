package co.edu.univalle.worklinkapp.fragmentos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import co.edu.univalle.worklinkapp.MainActivity;
import co.edu.univalle.worklinkapp.R;
import co.edu.univalle.worklinkapp.utils.SessionManager;

public class PerfilFragment extends Fragment {

    private SessionManager sessionManager;
    private LinearLayout layoutLogueado;
    private LinearLayout layoutNoLogueado;
    TextView textViewNombre;
    TextView textViewRol;

    private Button loginBtn;
    private Button logoutBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sessionManager = new SessionManager(getContext());
        layoutLogueado = view.findViewById(R.id.layout_logueado);
        layoutNoLogueado = view.findViewById(R.id.layout_no_logueado);
        loginBtn = view.findViewById(R.id.btn_login);
        logoutBtn = view.findViewById(R.id.btn_logout);
        textViewNombre = view.findViewById(R.id.tv_nombre_usuario);
        textViewRol = view.findViewById(R.id.tv_rol_usuario);


        loginBtn.setOnClickListener(v -> {
            ((MainActivity) getActivity()).mostrarLogin();
        });
        logoutBtn.setOnClickListener(v -> cerrarSesion());

    }

    @Override
    public void onResume() {
        super.onResume();
        verificarSesion();
    }

    private void verificarSesion(){
        if (sessionManager.isLoggedIn()){
            textViewNombre.setText(sessionManager.getUserName());
            textViewRol.setText(sessionManager.getUserRol());
            layoutLogueado.setVisibility(View.VISIBLE);
            layoutNoLogueado.setVisibility(View.GONE);
        }else{
            layoutLogueado.setVisibility(View.GONE);
            layoutNoLogueado.setVisibility(View.VISIBLE);
        }
    }

    private void cerrarSesion(){
        sessionManager.logout();
        Log.e("logout", "se cerro");
        verificarSesion();
    }

}