package com.aoz.hire_now.ui.gallery;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.bumptech.glide.Glide;
//import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GalleryFragment extends Fragment {

    View rootView;
    ImageView imagen_user;
    ImageView verificado_imagen;

    TextView FULL_NAMES, PROFECION, USER, RFC;
    String user, link;

    String cargarp = "cargar_perfil.php";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.fragment_gallery, container, false);

        FULL_NAMES = rootView.findViewById(R.id.FULL_NAME);
        PROFECION = rootView.findViewById(R.id.PROFECION);
        USER = rootView.findViewById(R.id.ID_user);
        imagen_user = rootView.findViewById(R.id.user_imagen);
        verificado_imagen = rootView.findViewById(R.id.verificado);
        RFC = rootView.findViewById(R.id.RFC_LABEL);

        global_var global_var = (global_var) getActivity().getApplicationContext();
        link = global_var.getLink();
        user = global_var.getUser();

        imprime();
        return  rootView;
    }

    public void imprime()
    {
        accederalDB(cargarp);
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
                else
                {
                    mensajes("Error" + response);
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
            //UPDATE usuarios SET apellidoP = "ortega", apellidoM = "zacarias" WHERE user = "miguel";

            JSONObject response = new JSONObject(dato);
            JSONArray jsonArray = response.optJSONArray("datos");
            JSONObject jsonObject = null;
            jsonObject = jsonArray.getJSONObject(0);

            datos_c(jsonObject.optString("id"),
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
            //e.printStackTrace();
            mensajes("Error de Jason: " + e.toString());
        }
    }

    public void datos_c(String id, String nombre, String APELLIDO_P, String APELLIDO_M, String PROFECIONES, String IMAGEN, String rfc, String verificado)
    {
        if(!"null".equals(PROFECIONES))
            PROFECION.setText(PROFECIONES);
        else
            PROFECION.setText("Sin profecion");
        if(!"null".equals(APELLIDO_P) && !"null".equals(APELLIDO_M) && !"null".equals(nombre))
            FULL_NAMES.setText(APELLIDO_P + " " + APELLIDO_M + " " + nombre);
        else
            FULL_NAMES.setText("Sin nombre.");
        USER.setText(user + ":" + id);
        if(!"null".equals(rfc))
            RFC.setText(rfc);
        else
            RFC.setText("Sin RFC");

        //_______la_mayoria_de_imagense______________________________
        Uri urlparse = Uri.parse(IMAGEN);
        Glide.with(this).load(urlparse).error(R.drawable.user_vector).into(imagen_user);
        //____________________________________________________________

        if("0".equals(verificado))
            verificado_imagen.setImageResource(R.drawable.verifique_reed);
        else
            verificado_imagen.setImageResource(R.drawable.verifique_green);
    }


    public void mensajes(String mensaje)
    {
        Toast.makeText(getActivity(), mensaje, Toast.LENGTH_LONG).show();
    }

}