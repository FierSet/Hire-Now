package com.aoz.hire_now.ui.slideshow;

import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aoz.hire_now.R;
import com.aoz.hire_now.global_var;
import com.aoz.hire_now.profeciones;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SlideshowFragment extends Fragment implements View.OnClickListener
{
    View rootView;
    Button Guardar;
    CheckBox cambiar_pass, cambiar_prof;
    ImageView vista_imagen;
    EditText imagen_search, nombres, apellidop, apellidom, rfc, nuevacontraseña, confirmarnuevacontraseña, contraseña_actual;

    String user, link;

    static final int  profecion = 1, datos = 2, escribe = 3;
    String cargar_prof ="lista_profeciones.php", cargar_datos = "cargar_perfil.php", actualizar = "actualiza_d.php";

    Spinner lista_profecion;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.fragment_slideshow, container, false);
        lista_profecion = rootView.findViewById(R.id.profeciones);
        vista_imagen =rootView.findViewById(R.id.imagen_check);
        imagen_search = rootView.findViewById(R.id.imagen_search);   imagen_search.setOnClickListener((View.OnClickListener) this);
        nombres = rootView.findViewById(R.id.Nombres);
        apellidop = rootView.findViewById(R.id.APELLIDOP);
        apellidom = rootView.findViewById(R.id.APELLIDOM);
        rfc = rootView.findViewById(R.id.RFC);
        nuevacontraseña = rootView.findViewById(R.id.nueva_contraseña);                       nuevacontraseña.setOnClickListener((View.OnClickListener) this);
        confirmarnuevacontraseña = rootView.findViewById(R.id.confirmar_nueva_contraseña); confirmarnuevacontraseña.setOnClickListener((View.OnClickListener) this);
        contraseña_actual = rootView.findViewById(R.id.contraseña_actual);                   contraseña_actual.setOnClickListener((View.OnClickListener) this);

        Guardar = rootView.findViewById(R.id.Guardar); Guardar.setOnClickListener((View.OnClickListener) this);
        cambiar_pass = rootView.findViewById(R.id.cambiar_contraseña); cambiar_pass.setOnClickListener((View.OnClickListener) this);
        cambiar_prof = rootView.findViewById(R.id.cambiar_profecion); cambiar_prof.setOnClickListener((View.OnClickListener) this);

        global_var global_var = (global_var) getActivity().getApplicationContext();
        link = global_var.getLink();
        user = global_var.getUser();

        activardessac_texvypin();

        acciones();

        imagen_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                Uri urlparse = Uri.parse(imagen_search.getText().toString());
                Glide.with(getContext()).load(urlparse).error(R.drawable.user_vector).into(vista_imagen);
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        return  rootView;
    }

    @Override
    public void onClick(View v)
    {
        if(cambiar_pass.isChecked())
            activardessac_texvypin();
        else
            activardessac_texvypin();


        if(cambiar_prof.isChecked())
            activardessac_texvypin();
        else
            activardessac_texvypin();

        switch (v.getId())
        {
            case R.id.Guardar:
                if(cambiar_pass.isChecked())
                {
                    if(nuevacontraseña.getText().toString().equals(confirmarnuevacontraseña.getText().toString()))
                        basededatos(actualizar, escribe);
                    else
                    {
                        mensajes("Las contraseñas no coinciden");
                        nuevacontraseña.setBackgroundResource(R.drawable.custom_input_red);
                        confirmarnuevacontraseña.setBackgroundResource(R.drawable.custom_input_red);
                    }
                }
                else
                    basededatos(actualizar, escribe);
            break;

            case R.id.nueva_contraseña:
                nuevacontraseña.setBackgroundResource(R.drawable.custom_input);
                nuevacontraseña.setText("");
            break;

            case R.id.confirmar_nueva_contraseña:
                confirmarnuevacontraseña.setBackgroundResource(R.drawable.custom_input);
                confirmarnuevacontraseña.setText("");
            break;

            case R.id.contraseña_actual:
                contraseña_actual.setBackgroundResource(R.drawable.custom_input);
                contraseña_actual.setText("");
            break;

            case R.id.imagen_search:
                imagen_search.setText("");
            break;
        }
    }

    public void acciones()
    {
        basededatos(cargar_prof, profecion);
        basededatos(cargar_datos, datos);
    }

    public void activardessac_texvypin()
    {
        nuevacontraseña.setEnabled(cambiar_pass.isChecked());
        confirmarnuevacontraseña.setEnabled(cambiar_pass.isChecked());
        lista_profecion.setEnabled(cambiar_prof.isChecked());
    }

    public void basededatos(String accion, int tipo) //1 profecion, 2 datos, 3 cargar
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link + accion, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                if(!response.isEmpty())
                {
                    switch (tipo)
                    {
                        case profecion:
                            cargarspin(response);
                        break;

                        case datos:
                            extraerdatos(response);
                        break;

                        case escribe:
                            mensajes(response);
                            if("contraseña incorrecta".equals(response))
                                contraseña_actual.setBackgroundResource(R.drawable.custom_input_red);

                        break;
                    }

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
                switch (tipo)
                {
                    case datos:
                        parametros.put("user", user);
                    break;
                    case escribe:
                        parametros.put("user", user);
                        parametros.put("pwd", contraseña_actual.getText().toString());

                        parametros.put("nombres", nombres.getText().toString());
                        parametros.put("apellido_p", apellidop.getText().toString());
                        parametros.put("apellido_m", apellidom.getText().toString());
                        parametros.put("RFC", rfc.getText().toString());
                        parametros.put("IMAGEN", imagen_search.getText().toString());

                        if(cambiar_prof.isChecked())
                            parametros.put("profecion", lista_profecion.getSelectedItem().toString());

                        if(cambiar_pass.isChecked())
                            parametros.put("nuevo_pass", nuevacontraseña.getText().toString());
                    break;
                }

                return parametros;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(getActivity());
        rq.add(stringRequest);
    }

    public void extraerdatos( String dato )
    {
        try
        {
            JSONObject response = new JSONObject(dato);
            JSONArray jsonArray = response.optJSONArray("datos");
            JSONObject jsonObject = null;
            jsonObject = jsonArray.getJSONObject(0);

            datos_c(
                    jsonObject.optString("nombres"),
                    jsonObject.optString("apellidoP"),
                    jsonObject.optString("apellidoM"),
                    jsonObject.optString("imagen"),
                    jsonObject.optString("RFC")
            );

        }
        catch (JSONException e)
        {
            mensajes("Error de Jason: " + e.toString());
        }
    }

    public void datos_c( String nombre, String apellido_p, String apellido_m, String IMAGEN, String RFC)
    {
        if(!"".equals(nombre) && !"null".equals(nombre))
            nombres.setText(nombre);
        if(!"".equals(apellido_p) && !"null".equals(apellido_p))
            apellidop.setText(apellido_p);
        if(!"".equals(apellido_m) && !"null".equals(apellido_m))
            apellidom.setText(apellido_m);
        if(!"".equals(IMAGEN) && !"null".equals(IMAGEN))
            imagen_search.setText(IMAGEN);
        if(!"".equals(RFC) && !"null".equals(RFC))
            rfc.setText(RFC);
    }

    public void cargarspin(String response)
    {
        ArrayList<profeciones> lista = new ArrayList<profeciones>();
        try
        {
            JSONArray JsonArreglo = new JSONArray(response);
            for(int i = 0; i < JsonArreglo.length(); i++)
            {
                profeciones t = new profeciones();
                t.setProfecion(JsonArreglo.getJSONObject(i).getString("profesion"));
                lista.add(t);
            }
            ArrayAdapter<profeciones> a = new ArrayAdapter<profeciones>(getContext(), R.layout.spinner_item_custom1, lista);
            lista_profecion.setAdapter(a);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            mensajes("error carga: " + e.toString());
        }
    }

    public void iniciarhilo()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                //delay(1);
            }
        }).start();
    }

    public void delay(int time)
    {
        try
        {
            Thread.sleep(time * 500);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public void mensajes(String mensaje)
    {
        Toast.makeText(getActivity(), mensaje, Toast.LENGTH_LONG).show();
    }

}