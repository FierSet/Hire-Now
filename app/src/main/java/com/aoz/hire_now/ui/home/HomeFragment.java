package com.aoz.hire_now.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.aoz.hire_now.ui.ver_oferta.Ver_oferta;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment implements View.OnClickListener
{
    View rootView;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    List<ListElement> elementos;
    EditText busqueda;

    Boolean Editor = false;

    String link = global_var.getLink(), user = global_var.getUser();

    String cargar_todas_ofertas = "cargar_ofertas.php", buscar = "Buscar_home.php", verifica_usuario = "verificar_perfil.php";
    String verificado;

    final int busqueda_automatica = 0, busqueda_normal = 1, comprobar_usuario = 2;
    Button Oferta;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        busqueda = (EditText) rootView.findViewById(R.id.busqueda);

        Oferta = (Button) rootView.findViewById(R.id.Ofertar);  Oferta.setOnClickListener((View.OnClickListener) this);

        basededatos(verifica_usuario, comprobar_usuario);
        basededatos(cargar_todas_ofertas, busqueda_automatica);

        busqueda.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                buscar();
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        return  rootView;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.Ofertar:
                if("1".equals(verificado))
                    abrirfragent_crearoferta();
                else
                    mensajes("Usuario no verificado.");
            break;
        }
    }

    public void buscar()
    {
        if(!busqueda.getText().toString().isEmpty())
        {
            basededatos(buscar, busqueda_normal);
        }
        else
            basededatos(cargar_todas_ofertas, busqueda_automatica);
    }

    public void basededatos(String accion, int estado) //0 busqueda automatica, 1 busqueda normal, 2 comprobar usuario
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link + accion, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                if(!response.isEmpty())
                {
                    switch (estado)
                    {
                        case busqueda_normal:
                            extraerdatos_busqueda(response);
                        break;

                        case comprobar_usuario:
                            comprobado(response);
                        break;

                        default:
                            extraerdatos( response );
                        break;
                    }
                }
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
                switch (estado)
                {
                    case busqueda_normal:
                        parametros.put("dato", busqueda.getText().toString());
                    break;
                    case comprobar_usuario:
                        parametros.put("user", user);
                    break;
                }
                return parametros;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(getActivity());
        rq.add(stringRequest);
    }

    public void comprobado(String dato) //verifica que los usuarios esta verificado
    {
        try
        {
            JSONObject response = new JSONObject(dato);
            JSONArray ofertas = response.getJSONArray("dato");
            JSONObject jsonObject = ofertas.getJSONObject(0);
            verificado = jsonObject.optString("comprobado");
        }
        catch (Exception e)
        {
            mensajes("Error de Jason: " + e.toString());
        }
    }

    public void extraerdatos( String dato )
    {
        //elementos.clear();
        //ListAdapter.notifyDataSetChanged();
        elementos = new ArrayList<>();
        try
        {
            //UPDATE usuarios SET apellidoP = "ortega", apellidoM = "zacarias" WHERE user = "miguel";
            JSONObject response = new JSONObject(dato);
            JSONArray ofertas = response.getJSONArray("datos");
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
                                                jsonObject.optString("Informacion_general"),    //String info_genera
                                                jsonObject.optString("pago"),                   //string costos
                                                jsonObject.optString("vacantes"),               //string vacantes
                                               false
                                              )
                             );

                ListAdapter listAdapter = new ListAdapter(R.layout.modelo_de_lista, elementos, getContext(), new ListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(ListElement item)
                    {
                        abrirfragent_veroferta(item);
                    }
                });

                RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.Lista_ofertas);
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

    public void extraerdatos_busqueda(String dato)
    {
        elementos = new ArrayList<>();
        try
        {
            JSONObject response = new JSONObject(dato);
            JSONArray ofertas = response.getJSONArray("ofertas");
            JSONArray usuarios = response.getJSONArray("usuarios");
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
                                jsonObject.optString("Informacion_general"),     //String info_genera
                                jsonObject.optString("pago"),                   //string costos
                                jsonObject.optString("vacantes"),               //string vacantes
                                false
                        )
                );

                ListAdapter listAdapter = new ListAdapter(R.layout.modelo_de_lista, elementos, getContext(), new ListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(ListElement item)
                    {
                        abrirfragent_veroferta(item);
                    }
                });

                RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.Lista_ofertas);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(listAdapter);
            }

            for(int i = 0; i < usuarios.length(); i++)
            {
                JSONObject jsonObject = null;
                jsonObject = usuarios.getJSONObject(i);

                elementos.add( new ListElement(
                                     jsonObject.optString("user"),                  //String ID,

                                jsonObject.optString("nombres") + " " +
                                     jsonObject.optString("apellidoP") + " " +     //String user,
                                     jsonObject.optString("apellidoM"),

                                     jsonObject.optString("imagen"),              // String imagen,

                                jsonObject.optString("nombres") + " " +
                                        jsonObject.optString("apellidoP") + " " +         //String user,
                                        jsonObject.optString("apellidoM"),                //String titulo,

                                "coming song",                                      // String descripcion,

                                jsonObject.optString("profesion"),                      //String profecion_req,

                                "coming song",                                      //String info_genera
                                "",
                                "",
                                true
                                )
                    );

                ListAdapter listAdapter = new ListAdapter(R.layout.modelo_de_lista, elementos, getContext(), new ListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(ListElement item)
                    {
                        abrirfragent_veroferta(item);
                    }
                });

                RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.Lista_ofertas);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(listAdapter);
            }
        }
        catch (Exception e)
        {
            //mensajes(e.getMessage());
        }

    }

    public void abrirfragent_crearoferta()
    {
        Bundle datos = new Bundle();
        datos.putSerializable("user", user);
        datos.putSerializable("link", link);
        datos.putSerializable("Editor", Editor);

        Crear_ofertas crear_ofertas = new Crear_ofertas();
        crear_ofertas.setArguments(datos);

        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.Contrain_home, crear_ofertas);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public void abrirfragent_veroferta(ListElement item)
    {
        Bundle datos = new Bundle();
        datos.putSerializable("objeto", item);

        Ver_oferta ver_oferta = new Ver_oferta();
        ver_oferta.setArguments(datos);

        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.Contrain_home, ver_oferta);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void mensajes(String mensaje)
    {
        Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
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

}