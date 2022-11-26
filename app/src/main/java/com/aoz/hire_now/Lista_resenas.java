package com.aoz.hire_now;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lista_resenas extends Fragment implements View.OnClickListener {

    View rootView;

    TextView no_reseñas, titulo;

    Button cerrar;

    List<ListElement_reseñas> elementos;

    String user = global_var.getUser(), link = global_var.getLink();

    String user_busqeda;

    String extrae_resenas_comentadas = "extrae_resenas_comentadas.php";

    LinearLayout base;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_lista_resenas, container, false);

        no_reseñas = rootView.findViewById(R.id.no_resenas);

        cerrar = rootView.findViewById(R.id.botoncerrar_crear_ofertas5);    cerrar.setOnClickListener((View.OnClickListener) this);
        base = rootView.findViewById(R.id.lista_resenas_base);              base.setOnClickListener(this);

        user_busqeda = getArguments().getString("user_busquerda");

        basededatos(extrae_resenas_comentadas);

        return rootView;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.botoncerrar_crear_ofertas5:
                getFragmentManager().beginTransaction().remove(this).commit();
            break;
            case R.id.lista_resenas_base:
                getFragmentManager().beginTransaction().remove(this).commit();
            break;
        }
    }

    public void basededatos(String accion)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link + accion, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                if(!response.isEmpty())
                    extraerdatos(response);
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                mensajes("error de conexion " + error.getLocalizedMessage());
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> parametros = new HashMap<String, String>();

                parametros.put("user", user_busqeda);

                return parametros;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(getContext());
        rq.add(stringRequest);
    }

    public void extraerdatos( String dato )
    {
        elementos = new ArrayList<>();
        try
        {
            JSONObject response = new JSONObject(dato);
            JSONArray Reseñas = response.getJSONArray("datos");

            if(Reseñas.length() != 1)
                no_reseñas.setText(Reseñas.length() + " Reseñas.");
            else
                no_reseñas.setText(Reseñas.length() + " Reseñas.");


            for(int i = 0; i < Reseñas.length(); i++)
            {
                JSONObject jsonObject = null;
                jsonObject = Reseñas.getJSONObject(i);

                elementos.add( new ListElement_reseñas(
                                jsonObject.optString("user"),                     //String ID,
                                jsonObject.optString("id_contrato"),                //String user,
                                jsonObject.optString("comentario_resenas"),                 // String imagen,
                                jsonObject.optString("resenas"),                 //String titulo,
                                jsonObject.optString("comentarios_exito"),            // String descripcion,
                                jsonObject.optString("exito")
                        )
                );

                ListAdapter_reseñas listAdapter_reseñas = new ListAdapter_reseñas(R.layout.modelo_de_lista_resenas, elementos, getContext(), new ListAdapter_reseñas.OnItemClickListener() {
                    @Override
                    public void onItemClick(ListElement_reseñas item) {

                    }
                });

                RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.lista_resenas);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(listAdapter_reseñas);
            }

        }
        catch (JSONException e)
        {
            mensajes("Error de Jason: " + e.toString());
        }
    }

    public void mensajes(String mensaje)
    {
        Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
    }

}