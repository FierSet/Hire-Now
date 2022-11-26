package com.aoz.hire_now.ui.solicitud_de_oferta;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
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
import com.aoz.hire_now.ListElement_Solicitud;
import com.aoz.hire_now.R;
import com.aoz.hire_now.global_var;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class solicitud_de_ofertas extends Fragment implements View.OnClickListener
{

    View rootView;

    LinearLayout base;

    ImageView usuario, oferta;

    ListElement_Solicitud listElement_solicitud;

    Button cerrar, Rechazar, Aceptar;

    int vacantes;

    RatingBar Reseñas_solicitud, rangoExito_solicitud;

    String user = global_var.getUser(), link = global_var.getLink();

    String cargar_perfil = "cargar_perfil.php", cargar_ofertas = "cargar_oferta.php", crear_contrato = "Crear_contrato.php", elimiar_solicitud = "Borrar_solicitud.php",
            extraer_reseñas = "extraer_reseñas.php", actualizar_vacantes = "actualizar_vacantes.php";

    String pagos;

    final int cargar_usuario = 0, cargar_oferta = 1, Rechaza_solicitud = 2, Acepta_solicitud = 3, extraer_reseñas_tipo = 4, actualizar_vacantes_tipo = 5;

    TextView nombre_usuario, profecion_usuario, titulo_tipo, titulo_oferta, id_oferta, descripcion, pago;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_solicitud_de_ofertas, container, false);

        Reseñas_solicitud = rootView.findViewById(R.id.Reseñas_solicitud);
        rangoExito_solicitud = rootView.findViewById(R.id.rangoExito_solicitud);

        usuario = rootView.findViewById(R.id.imagen_usuario_solicitud);
        oferta = rootView.findViewById(R.id.imagen_oferta_solicitud);

        nombre_usuario = rootView.findViewById(R.id.nombre_usuario);
        profecion_usuario = rootView.findViewById(R.id.profecion_solicitud);
        pago = rootView.findViewById(R.id.pago_solicitud);

        titulo_tipo = rootView.findViewById(R.id.tipo_titulo);
        titulo_oferta = rootView.findViewById(R.id.titulo_oferta_solicitud);
        id_oferta = rootView.findViewById(R.id.id_oferta_silicitud);
        descripcion = rootView.findViewById(R.id.descripcion_solicitud);

        cerrar = rootView.findViewById(R.id.botoncerrar_crear_ofertas4);        cerrar.setOnClickListener((View.OnClickListener) this);
        Rechazar = rootView.findViewById(R.id.Recharzar_solicitud);             Rechazar.setOnClickListener((View.OnClickListener) this);
        Aceptar = rootView.findViewById(R.id.Aceptar_solicitud);                Aceptar.setOnClickListener((View.OnClickListener) this);
        base = rootView.findViewById(R.id.solicitar_oferta_base);               base.setOnClickListener(this);

        listElement_solicitud = (ListElement_Solicitud) getArguments().getSerializable("objeto");

        titulo_tipo.setText(listElement_solicitud.getTipo());



        basededatos(extraer_reseñas, extraer_reseñas_tipo);
        basededatos(cargar_perfil, cargar_usuario);
        basededatos(cargar_ofertas, cargar_oferta);


        return  rootView;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.botoncerrar_crear_ofertas4:
                getFragmentManager().beginTransaction().remove(this).commit();
            break;

            case R.id.Aceptar_solicitud:
                basededatos(crear_contrato, Acepta_solicitud);
                vacantes--;
                basededatos(actualizar_vacantes, actualizar_vacantes_tipo);
                getFragmentManager().beginTransaction().remove(this).commit();
            break;

            case R.id.Recharzar_solicitud:
                basededatos(elimiar_solicitud, Rechaza_solicitud);
                getFragmentManager().beginTransaction().remove(this).commit();
            break;

            case R.id.solicitar_oferta_base:
                getFragmentManager().beginTransaction().remove(this).commit();
            break;
        }
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
                        case cargar_usuario:
                            extraerdatos_usuario(response);
                        break;

                        case cargar_oferta:
                            extraerdatos_oferta(response);
                        break;

                        case Acepta_solicitud:
                            mensajes(response);
                            basededatos(elimiar_solicitud, Rechaza_solicitud);
                        break;

                        case extraer_reseñas_tipo:
                            extraereseña(response);
                        break;

                        default:
                            mensajes(response);
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
                    case extraer_reseñas_tipo:
                    case cargar_usuario:
                        parametros.put("user", listElement_solicitud.getID_user_notificacion());
                    break;

                    case cargar_oferta:
                        parametros.put("id_oferta", listElement_solicitud.getID_iferta());
                    break;

                    case Acepta_solicitud:
                        parametros.put("id_oferta", listElement_solicitud.getID_iferta());
                        parametros.put("Contratante", user);
                        parametros.put("contratista", listElement_solicitud.getID_user_notificacion());
                        parametros.put("pago", pagos);
                    break;

                    case Rechaza_solicitud:
                        parametros.put("user", user);
                        parametros.put("id_user_notificacion", listElement_solicitud.getID_user_notificacion());
                        parametros.put("id_oferta", listElement_solicitud.getID_iferta());
                    break;

                    case actualizar_vacantes_tipo:
                        parametros.put("ID", listElement_solicitud.getID_iferta());
                        parametros.put("user", user);
                        parametros.put("vacantes", "" + vacantes);
                    break;
                }

                return parametros;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(getActivity());
        rq.add(stringRequest);
    }

    public void extraerdatos_usuario(String dato)
    {
        try
        {
            JSONObject response = new JSONObject(dato);
            JSONArray usuarios = response.getJSONArray("datos");
            JSONObject jsonObject = null;
            jsonObject = usuarios.getJSONObject(0);

            nombre_usuario.setText(jsonObject.getString("nombres") + " " + jsonObject.getString("apellidoP") + " " + jsonObject.getString("apellidoM") );
            profecion_usuario.setText(jsonObject.getString("profesion"));

            Uri urlparse = Uri.parse(jsonObject.getString("imagen"));
            Glide.with(getContext()).load(urlparse).error(R.drawable.user_vector).into(usuario);

        }
        catch (Exception e)
        {
            //mensajes(e.getMessage());
        }

    }

    public void extraereseña(String dato)
    {
        try
        {
            JSONObject response = new JSONObject(dato);
            JSONArray jsonArray = response.optJSONArray("datos");
            JSONObject jsonObject = null;
            jsonObject = jsonArray.getJSONObject(0);

            String reseñas = (jsonObject.optString("AVG(resenas)")),
                    exito = (jsonObject.optString("AVG(exito)"));

            if(!"null".equals(reseñas) && !"null".equals(exito))
            {
                float reseñasv = Float.parseFloat(reseñas),
                        exitov = Float.parseFloat(exito);

                Reseñas_solicitud.setRating(reseñasv);
                rangoExito_solicitud.setRating(exitov);
            }

        }
        catch (JSONException e)
        {
            //e.printStackTrace();
            mensajes("Error de Jason: " + e.toString());
        }
    }

    public void extraerdatos_oferta(String dato)
    {
        try
        {
            JSONObject response = new JSONObject(dato);
            JSONArray ofertas = response.getJSONArray("datos");
            JSONObject jsonObject = null;
            jsonObject = ofertas.getJSONObject(0);

            titulo_oferta.setText(jsonObject.getString("Titulo"));
            id_oferta.setText(jsonObject.getString("ID"));
            descripcion.setText(jsonObject.getString("Informacion_general"));

            pagos = jsonObject.getString("pago");
            pago.setText(pago.getText() + pagos);

            vacantes =  Integer.parseInt(jsonObject.getString("vacantes"));

            Uri urlparse = Uri.parse(jsonObject.getString("imagen"));
            Glide.with(getContext()).load(urlparse).error(R.drawable.work).into(oferta);

        }
        catch (Exception e)
        {
            //mensajes("ERROR JS: " + e.getMessage());
        }

    }

    public void mensajes(String mensaje)
    {
        Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
    }

}