package com.aoz.hire_now.ui.Administrar_contratos;

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
import com.aoz.hire_now.ListAdapter_Contratos;
import com.aoz.hire_now.ListElement_Contratos;
import com.aoz.hire_now.R;
import com.aoz.hire_now.global_var;
import com.aoz.hire_now.ui.modificar_contrato.modificar_contrato;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Administrar_Contratos extends Fragment
{

    View rootView;

    String link = global_var.getLink(), user = global_var.getUser();

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    TextView cabecera;

    String extraer_contratos = "extraer_contrato.php";

    List<ListElement_Contratos> elementos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_administrar__contratos, container, false);
        cabecera = rootView.findViewById(R.id.cabecera_contratos);

        basededatos(extraer_contratos);
        return rootView;
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
                    cabecera.setText("0 Contratos.");
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

        elementos = new ArrayList<>();
        try
        {
            //UPDATE usuarios SET apellidoP = "ortega", apellidoM = "zacarias" WHERE user = "miguel";
            JSONObject response = new JSONObject(dato);
            JSONArray contratos = response.getJSONArray("CONTRATOS");

            if(contratos.length() != 1)
                cabecera.setText(contratos.length() + " Contratos.");
            else
                cabecera.setText(contratos.length() + " Contrato.");

            for(int i = 0; i < contratos.length(); i++)
            {
                JSONObject jsonObject = null;
                jsonObject = contratos.getJSONObject(i);

                elementos.add( new ListElement_Contratos(
                                jsonObject.optString("id_contrato"),
                                jsonObject.optString("id_oferta"),
                                jsonObject.optString("Contratante"),
                                jsonObject.optString("contratista"),
                                jsonObject.optString("pago"),
                                jsonObject.optString("estado"),
                                jsonObject.optString("permiso_contratante"),
                                jsonObject.optString("permiso_contratista")
                        )
                );

                ListAdapter_Contratos listAdapter_contratos = new ListAdapter_Contratos(R.layout.modelo_de_lista_de_contrato, elementos, getContext(), new ListAdapter_Contratos.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(ListElement_Contratos item)
                    {
                        abrir_ventana(item);
                    }
                });

                RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.lista_contratos);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(listAdapter_contratos );
            }
        }
        catch (JSONException e)
        {
            //e.printStackTrace();
            mensajes("Error de Jason: " + e.toString());
        }
    }

    public void abrir_ventana(ListElement_Contratos item)
    {
        Bundle datos = new Bundle();

        datos.putSerializable("objeto", item);

        modificar_contrato modificar_contrato = new modificar_contrato();
        modificar_contrato.setArguments(datos);

        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.administrar_contratos, modificar_contrato);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void mensajes(String mensaje)
    {
        Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
    }

}