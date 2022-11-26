package com.aoz.hire_now.ui.ver_oferta;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aoz.hire_now.ListElement;
import com.aoz.hire_now.Lista_resenas;
import com.aoz.hire_now.R;
import com.aoz.hire_now.global_var;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Ver_oferta extends Fragment implements View.OnClickListener, Serializable {

    TextView ver_ofertas_titulo, id, usuario, descripcion, profecion, info_general, profecion_label_ver_oferta, vacantes, pagos;
    ImageView imagen;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    LinearLayout pagovacantes, base;

    RatingBar Reseñas, RangoExito;

    ListElement listElement;

    String user = global_var.getUser(), link = global_var.getLink();

    String user_busqueda;

    boolean cuenta_con_comentarios = false;

    String enviar_solicitudes = "generar_solicitud.php", rating = "extraer_reseñas.php";

    final int generar_solicitud_tipo = 0, rating_tipo = 1;

    Button cerrar, notificar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_ver_oferta, container, false);

        pagovacantes = view.findViewById(R.id.Pagovacantes);

        Reseñas = view.findViewById(R.id.Resenas_ver_oferta);
        RangoExito = view.findViewById(R.id.porsentaje_de_exito_ver_oferta);

        ver_ofertas_titulo = view.findViewById(R.id.Titulo_ver_oferta);
        id = view.findViewById(R.id.id_ver_ofertas);
        usuario = view.findViewById(R.id.Usuario_ver_oferta);
        descripcion = view.findViewById(R.id.Descripcion_ver_oferta);
        profecion = view.findViewById(R.id.profecion_ver_ofertas);
        info_general = view.findViewById(R.id.info_generar_ver_ofertas);
        profecion_label_ver_oferta = view.findViewById(R.id.profecion_ver_oferta_label);
        vacantes = view.findViewById(R.id.vacantes_ver_ofertas);
        pagos = view.findViewById(R.id.pagos_ver_ofertas);

        imagen = view.findViewById(R.id.imagen_ver_oferta);

        cerrar = view.findViewById(R.id.botoncerrar_crear_ofertas);          cerrar.setOnClickListener((View.OnClickListener) this);
        notificar = view.findViewById(R.id.Notificar);                       notificar.setOnClickListener((View.OnClickListener) this);
        base = view.findViewById(R.id.ver_oferta_base);                      base.setOnClickListener(this);

        listElement = (ListElement) getArguments().getSerializable("objeto");

        ver_ofertas_titulo.setText(listElement.getTitulo());
        id.setText(listElement.getID());
        usuario.setText(listElement.getUser());
        descripcion.setText(listElement.getDescripcion());
        profecion.setText(listElement.getProfecion_req());
        info_general.setText(listElement.getInfo_genera());

        if(listElement.getEs_usuario())
        {
            profecion_label_ver_oferta.setText("Profecion:");
            ver_ofertas_titulo.setText("Usuario.");
            pagovacantes.setVisibility(View.INVISIBLE);
            user_busqueda = listElement.getID();
        }
        else
        {
            pagos.setText(pagos.getText() + "$" + listElement.getCosto());
            vacantes.setText(vacantes.getText() + listElement.getVacantes());
            user_busqueda = listElement.getUser();
        }
        accederalDB(rating, rating_tipo, user_busqueda);

        Uri urlparse = Uri.parse(listElement.getImagen());
        Glide.with(getContext()).load(urlparse).error(R.drawable.work).into(imagen);

        Reseñas.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if(event.getAction() == MotionEvent.ACTION_UP)
                    if(cuenta_con_comentarios)
                        abrirfragent_reseñas();
                    else
                        mensajes("Este usuario aun no tiene reseñas");
                return true;
            }
        });

        return view;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.botoncerrar_crear_ofertas:
                getFragmentManager().beginTransaction().remove(this).commit();
            break;

            case R.id.Notificar:

                if(!user.equals(listElement.getUser()))
                    if(!listElement.getEs_usuario())
                    {
                        accederalDB(enviar_solicitudes, generar_solicitud_tipo, null);
                        getFragmentManager().beginTransaction().remove(this).commit();
                    }
                    else
                        mensajes("no es oferta");
                else
                    mensajes("No puedes enviarte solicitud a ti mismo.");
            break;
            case  R.id.ver_oferta_base:
                getFragmentManager().beginTransaction().remove(this).commit();
            break;
        }
    }

    private void accederalDB(String accion, int tipo, String ID) //0 responder_ofertas, 1 ofercer_ofertas
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link + accion, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                if(!response.isEmpty())
                    switch (tipo)
                    {
                        case generar_solicitud_tipo:
                            mensajes(response);
                        break;

                        case rating_tipo:
                            extraerdatos(response);
                        break;
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
                    case generar_solicitud_tipo:
                        parametros.put("id_user_oferta", listElement.getUser());
                        parametros.put("user", user);
                        parametros.put("id_oferta", listElement.getID());
                        parametros.put("imagen", listElement.getImagen());
                        parametros.put("titulo", listElement.getTitulo());
                        parametros.put("tipo", "0");
                    break;
                    case rating_tipo:
                        parametros.put("user", ID);
                    break;
                }

                return parametros;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(getContext());
        rq.add(stringRequest);
    }

    public void extraerdatos( String dato)
    {
        try
        {
            JSONObject response = new JSONObject(dato);
            JSONArray jsonArray = response.optJSONArray("datos");
            JSONObject jsonObject = null;
            jsonObject = jsonArray.getJSONObject(0);

            String reseñas = jsonObject.optString("AVG(resenas)"),
                    exito = jsonObject.optString("AVG(exito)");

            if(!"null".equals(reseñas) && !"null".equals(exito))
            {
                float reseñasv = Float.parseFloat(reseñas),
                        exitov = Float.parseFloat(exito);

                Reseñas.setRating(reseñasv);
                RangoExito.setRating(exitov);
                cuenta_con_comentarios = true;
            }

        }
        catch (JSONException e)
        {
            //e.printStackTrace();
            mensajes("Error de Jason: " + e.toString());
        }
    }

    public void abrirfragent_reseñas()
    {
        Bundle datos = new Bundle();

        datos.putSerializable("user_busquerda", user_busqueda);

        Lista_resenas lista_resenas = new Lista_resenas();
        lista_resenas.setArguments(datos);

        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fondo_ver_oferta, lista_resenas);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public void mensajes(String mensaje)
    {
        Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
    }
}