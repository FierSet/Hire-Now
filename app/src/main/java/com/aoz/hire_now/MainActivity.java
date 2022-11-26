package com.aoz.hire_now;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    Button ingresa, registarse;
    EditText user, password, password2;
    ImageView icono;

    CheckBox crear;

    String link = "https://prieto-chan.000webhostapp.com/Hire_now/", Ingresar = "Acceso_Android.php", Crear = "Crear_Android.php";
    boolean login = false;
    public String usuarios;
    protected String contraseñas, contraseñas2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        crear = findViewById(R.id.crearchec);       crear.setOnClickListener(this);

        ingresa = findViewById(R.id.ingresar);      ingresa.setOnClickListener(this);
        registarse = findViewById(R.id.crear);      registarse.setOnClickListener(this);
        icono = findViewById(R.id.avatar);

        user = findViewById(R.id.user);             user.setOnClickListener(this);
        password = findViewById(R.id.password);     password.setOnClickListener(this);
        password2 = findViewById(R.id.password2);   password2.setOnClickListener(this);


        registarse.setVisibility(View.INVISIBLE);
        password2.setVisibility(View.INVISIBLE);

        recuperarcuenta();
    }

    @Override
    public void onClick(View v)
    {
        usuarios = user.getText().toString();
        contraseñas = password.getText().toString();
        contraseñas2 = password2.getText().toString();

        if(crear.isChecked())
            visible(1,0,0);
        else
            visible(0,1,1);

        switch (v.getId())
        {
            case R.id.user:
                marcaerror(true, 0,false,0, false, 0);
                user.setText("");
                break;

            case R.id.password:
                marcaerror(false, 0,true,0, false, 0);
                password.setText("");
                break;

            case R.id.password2:
                marcaerror(false, 0,false,0, true, 0);
                password2.setText("");
                break;

            case R.id.ingresar:
                login = true;
                verificadatos(Ingresar);
                break;

            case R.id.crear:
                login = false;
                if(contraseñas.equals(contraseñas2))
                    verificadatos(Crear);
                else
                {
                    marcaerror(false, 0,true,1, true, 1);
                    mensajes("Las contraseñas no coincide.");
                }
                break;
        }
    }

    public void vaciardatos()
    {
        user.setText("");
        password.setText("");
        password2.setText("");
    }

    public void verificadatos( String accion)
    {
        if("".equals(usuarios) && "".equals(contraseñas))
        {
            marcaerror(true, 1,true,1, false, 0);
            mensajes("Datos no ingresados");
        }
        else if("".equals(usuarios))
        {
            marcaerror(true, 1,false,0, false, 0);
            mensajes("No ingreso usuarios");
        }
        else if("".equals(contraseñas))
        {
            marcaerror(false, 1,false,0, false, 0);
            mensajes("No ingreso la contraseña");
        }
        else
            accederalDB(accion);
    }

    public void marcaerror(boolean usuario, int estadouser,boolean passw,int estadopass, boolean passw2, int estadopass2)
    {
        //0 normal, 1 error, 3 nada.
        if(usuario)
            switch (estadouser)
            {
                case 0:
                    user.setBackgroundResource(R.drawable.custom_input);
                    break;

                case 1:
                    user.setBackgroundResource(R.drawable.custom_input_red);
                    break;
            }
        if(passw)
            switch (estadopass)
            {
                case 0:
                    password.setBackgroundResource(R.drawable.custom_input);
                    break;

                case 1:
                    password.setBackgroundResource(R.drawable.custom_input_red);
                    break;
            }
        if(passw2)
            switch (estadopass2)
            {
                case 0:
                    password2.setBackgroundResource(R.drawable.custom_input);
                    break;

                case 1:
                    password2.setBackgroundResource(R.drawable.custom_input_red);
                    break;
            }
    }

    private void accederalDB(String accion)
    {
        imagengift(true, "https://acegif.com/wp-content/uploads/loading-25.gif");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, link + accion, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                if(!response.isEmpty() && login)
                {
                    guardarcuenta();
                    login = false;
                    global_var global_var = (global_var) getApplicationContext();
                    global_var.setUser(user.getText().toString());
                    global_var.setLink(link);

                    mensajes("conectado");
                    cambiarVentana();
                }
                else if("\"duplicados\"".equals(response) && !login)
                {
                    marcaerror(true, 1,false,0, false, 0);
                    mensajes("Cuenta ya existente");
                }
                else if("\"noduplicados\"".equals(response.toString()) && !login)
                {
                    imagengift(false, null);
                    mensajes("Cuenta creada");
                    vaciardatos();
                    crear.setChecked(false);
                    visible(0,1,1);
                    marcaerror(true, 0, true, 0, true, 0);
                }
                else
                {
                    imagengift(false, null);
                    mensajes("usuario o contraseña incorrectas");
                    marcaerror(true, 1,true,1, false, 0);
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

                parametros.put("user", usuarios);
                parametros.put("pwd", contraseñas);
                return parametros;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(stringRequest);
    }

    public void visible( int estadoingresa, int estadoregistrar, int estadopass)
    {
        switch (estadoingresa)
        {
            case 0:
                ingresa.setVisibility(View.VISIBLE);
                break;

            case 1:
                ingresa.setVisibility(View.INVISIBLE);
                break;
        }

        switch (estadoregistrar)
        {
            case 0:
                registarse.setVisibility(View.VISIBLE);
                break;

            case 1:
                registarse.setVisibility(View.INVISIBLE);
                break;
        }

        switch (estadopass)
        {
            case 0:
                password2.setVisibility(View.VISIBLE);
                break;

            case 1:
                password2.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public void imagengift(boolean gift, String URL)
    {
        if(gift)
        {
            Uri urlparse = Uri.parse(URL);
            Glide.with(this).load(urlparse).into(icono);
        }
        else
            icono.setImageResource(R.drawable.user_vector);
    }

    public void cambiarVentana()
    {
        //Bundle datos = new Bundle();
        //datos.putString("user", usuarios);
        //datos.putString("link", link);
        Intent j = new Intent(getApplicationContext(), Main_Menu.class);
        //j.putExtras(datos);
        startActivity(j);
        imagengift(false, null);
        finish();
    }
    public void guardarcuenta()
    {
        SharedPreferences preferences = getSharedPreferences("preferencialogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Usuario", usuarios);
        editor.putString("password", contraseñas);
        editor.putBoolean("Sesion", true);
        editor.commit();
    }

    private void recuperarcuenta()
    {
        SharedPreferences preferences = getSharedPreferences("preferencialogin", Context.MODE_PRIVATE);
        user.setText(preferences.getString("Usuario", ""));
        password.setText(preferences.getString("password", ""));
    }

    public void mensajes(String mensaje)
    {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

}