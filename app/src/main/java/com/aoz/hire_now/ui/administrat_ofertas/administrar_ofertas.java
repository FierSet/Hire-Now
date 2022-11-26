package com.aoz.hire_now.ui.administrat_ofertas;

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
import com.aoz.hire_now.ListAdapter;
import com.aoz.hire_now.ListElement;
import com.aoz.hire_now.R;
import com.aoz.hire_now.global_var;
import com.aoz.hire_now.ui.Creat_oferta.Crear_ofertas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class administrar_ofertas extends Fragment
{

    View rootView;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    TextView encabezado;

    Boolean Editor = true;

    List<ListElement> elementos;

    String link = global_var.getLink(), user = global_var.getUser();
    String cargar_todas_ofertas = "cargar_ofertas.php";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_administrar_ofertas, container, false);

        encabezado = rootView.findViewById(R.id.ofertas_encabezado);

        basededatos(cargar_todas_ofertas);

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
                {
                    extraerdatos( response );
                }
                else
                    encabezado.setText("0 Ofertas.");

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
                parametros.put("Buscar", user);
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
            JSONObject response = new JSONObject(dato);
            JSONArray ofertas = response.getJSONArray("datos");

            if(ofertas.length() != 1)
                encabezado.setText(ofertas.length() + " Ofertas.");
            else
                encabezado.setText(ofertas.length() + " Oferta.");


            for(int i = 0; i < ofertas.length(); i++)
            {
                JSONObject jsonObject = null;
                jsonObject = ofertas.getJSONObject(i);

                elementos.add( new ListElement(
                                jsonObject.optString("ID"),                     //String ID,
                                jsonObject.optString("id_user"),                //String user,
                                jsonObject.optString("Imagen"),                 // String imagen,
                                jsonObject.optString("Titulo"),                 //String titulo,
                                jsonObject.optString("descripcion"),            // String descripcion,
                                jsonObject.optString("profecion_requerida"),    //String profecion_req,
                                jsonObject.optString("Informacion_general"),   //String info_genera
                                jsonObject.optString("pago"),                   //string costos
                                jsonObject.optString("vacantes"),               //string vacantes
                                false
                        )
                );

                ListAdapter listAdapter = new ListAdapter(R.layout.modelo_de_lista_administrar,elementos, getContext(), new ListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(ListElement item)
                    {
                       abrirfragent_crearoferta(item);
                    }
                });

                RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.lista_administrar_ofertas);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(listAdapter);
            }

        }
        catch (JSONException e)
        {
            mensajes("Error de Jason: " + e.toString());
        }
    }

    public void abrirfragent_crearoferta(ListElement item)
    {
        Bundle datos = new Bundle();

        datos.putSerializable("link", link);
        datos.putSerializable("Editor", Editor);
        datos.putSerializable("objeto", item);

        Crear_ofertas crear_ofertas = new Crear_ofertas();
        crear_ofertas.setArguments(datos);

        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.Administrar_oferta, crear_ofertas);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public void mensajes(String mensaje)
    {
        Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
    }

}