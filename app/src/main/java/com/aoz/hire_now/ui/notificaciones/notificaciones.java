package com.aoz.hire_now.ui.notificaciones;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aoz.hire_now.ListAdapter_Solicitud;
import com.aoz.hire_now.ListElement_Solicitud;
import com.aoz.hire_now.R;
import com.aoz.hire_now.global_var;
import com.aoz.hire_now.ui.solicitud_de_oferta.solicitud_de_ofertas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class notificaciones extends Fragment {

    View rootView;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    List<ListElement_Solicitud> elemento;

    TextView encabezado;

    String user = global_var.getUser(), link = global_var.getLink(), user_solicitud;

    String cargar_solicitud = "extraer_solicitud.php";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_notificaciones, container, false);

        encabezado = rootView.findViewById(R.id.notificaciones_encabezado);

        basededatos(cargar_solicitud);

        return  rootView;
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
                else
                    encabezado.setText("0 Solicitudes");
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                mensajes("error de conexion" + error.getMessage());
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
        elemento = new ArrayList<>();
        try
        {
            JSONObject response = new JSONObject(dato);
            JSONArray solicitud = response.getJSONArray("datos");

            if(solicitud.length() != 1)
                encabezado.setText(solicitud.length() + " Solicitud");
            else
                encabezado.setText(solicitud.length() + " Solicitudes");

            for(int i = 0; i < solicitud.length(); i++)
            {
                JSONObject jsonObject = null;
                jsonObject = solicitud.getJSONObject(i);

                String tipo = "oferta";
                if("0".equals(jsonObject.optString("tipo")))
                    tipo = "Solicitud";

                elemento.add( new ListElement_Solicitud(
                                    jsonObject.optString("id_user_notificacion"),
                                    jsonObject.optString("id_oferta"),
                                    jsonObject.optString("imagen"),
                                    jsonObject.optString("titulo"),
                                    tipo
                                    )
                            );

                ListAdapter_Solicitud listAdapter = new ListAdapter_Solicitud(R.layout.modelo_de_lista_solicitud, elemento, getContext(), new ListAdapter_Solicitud.OnItemClickListener() {
                    @Override
                    public void onItemClick(ListElement_Solicitud item)
                    {
                        abrirfragent_solicitud(item);
                    }
                });

                RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.Lista_notificaciones);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(listAdapter);
            }
        }
        catch (JSONException e)
        {
            //e.printStackTrace();
            mensajes("Error de Jason: " + e.toString());
        }
    }

    public void abrirfragent_solicitud(ListElement_Solicitud item)
    {
        Bundle datos = new Bundle();
        datos.putSerializable("objeto", item);

        solicitud_de_ofertas solicitud = new solicitud_de_ofertas();
        solicitud.setArguments(datos);

        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_notificaciones, solicitud);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void mensajes(String mensaje)
    {
        Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
    }
}