package com.aoz.hire_now;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aoz.hire_now.databinding.ActivityMainMenuBinding;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Main_Menu extends AppCompatActivity
{

    static AppBarConfiguration mAppBarConfiguration;
    static ActivityMainMenuBinding binding;

    public String link = global_var.getLink(), user = global_var.getUser();

    //Bundle recibe;

    String cargarp = "cargar_perfil.php";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = ActivityMainMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMainMenu.toolbar);
        binding.appBarMainMenu.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_Perfil, R.id.nav_config, R.id.administrar_ofertas, R.id.notificaciones, R.id.administrar_Contratos)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_menu);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        accederalDB(cargarp);
        //navigationView1.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);

    }

    public void cargar_header(String id, String nombres, String apellido_p, String apellido_m, String profecion, String Imagen, String RFC, String comprobado)
    {
        NavigationView navigationView1;
        View header;

        navigationView1 = findViewById(R.id.nav_view);
        header = navigationView1.getHeaderView(0);

        TextView nombre = header.findViewById(R.id.nombre_header);
        TextView sub_nombre =header.findViewById(R.id.sub_nombre_header);
        ImageView imagen_header = header.findViewById(R.id.Imagen_header);

        nombre.setText(id + ":" + user);
        sub_nombre.setText(apellido_p + " " + apellido_m + " " + nombres);

        Uri urlparse = Uri.parse(Imagen);
        Glide.with(this).load(urlparse).error(R.drawable.user_vector).into(imagen_header);
    }

    public void extraerdatos( String dato )
    {
        try
        {
            JSONObject response = new JSONObject(dato);
            JSONArray jsonArray = response.optJSONArray("datos");
            JSONObject jsonObject = null;
            jsonObject = jsonArray.getJSONObject(0);

            cargar_header(  jsonObject.optString("id"),
                    jsonObject.optString("nombres"),
                    jsonObject.optString("apellidoP"),
                    jsonObject.optString("apellidoM"),
                    jsonObject.optString("profesion"),
                    jsonObject.optString("imagen"),
                    jsonObject.optString("RFC"),
                    jsonObject.optString("comprobado")
            );
        }
        catch (JSONException e)
        {
            mensajes("Error de Jason: " + e.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main__menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent j = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(j);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_menu);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void accederalDB(String accion)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link + accion, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                if(!response.isEmpty())
                {
                    extraerdatos(response);
                }

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                mensajes(error.getMessage());
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> parametros = new HashMap<String, String>();

                parametros.put("user", user);
                //parametros.put("pwd", contraseñas);
                return parametros;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(stringRequest);
    }

    public void mensajes(String mensaje)
    {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

}