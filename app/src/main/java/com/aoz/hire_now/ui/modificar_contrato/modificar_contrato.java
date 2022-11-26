package com.aoz.hire_now.ui.modificar_contrato;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import com.aoz.hire_now.ListElement_Contratos;
import com.aoz.hire_now.R;
import com.aoz.hire_now.calificar_usuario;
import com.aoz.hire_now.global_var;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class modificar_contrato extends Fragment implements View.OnClickListener
{

    View rootView;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    TextView id_del_contrato, id_oferta, contratante, contratista, descripcion, estado;

    EditText pago;

    ListElement_Contratos elementContratos;

    String user = global_var.getUser(), link = global_var.getLink();

    String actializar_contrato = "Actualizar_contrato.php", extraer_oferta = "cargar_oferta.php", estado_contrato = "estado_contrato.php";
    final int actualizar_contrato_tipo = 0, extraert_oferta_tipo = 1, estado_contrato_tipo = 3;

    Button cancelar, finalizar, actualizar, cerrar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_modificar_contrato, container, false);

        id_del_contrato = rootView.findViewById(R.id.id_contrato_editar_contrato);
        id_oferta = rootView.findViewById(R.id.id_oferta_editar_contrato);
        contratante = rootView.findViewById(R.id.Contratante_editar_contrato);
        contratista = rootView.findViewById(R.id.contratista_editar_contrato);
        descripcion = rootView.findViewById(R.id.Descripcion_editar_contrato);
        estado = rootView.findViewById(R.id.Estado_editar_contrato);

        cancelar = rootView.findViewById(R.id.cancelar_contrato);           cancelar.setOnClickListener((View.OnClickListener) this);
        finalizar = rootView.findViewById(R.id.finalizar_contrato);         finalizar.setOnClickListener((View.OnClickListener) this);
        actualizar = rootView.findViewById(R.id.actualizar_contrato);       actualizar.setOnClickListener((View.OnClickListener) this);
        cerrar = rootView.findViewById(R.id.botoncerrar_crear_ofertas2);    cerrar.setOnClickListener((View.OnClickListener) this);

        pago = rootView.findViewById(R.id.pago_editar_contratos);

        elementContratos = (ListElement_Contratos) getArguments().getSerializable("objeto");

        id_del_contrato.setText(elementContratos.getId_contrato());
        id_oferta.setText(elementContratos.getId_oferta());
        contratista.setText(elementContratos.getContratista());
        contratante.setText(elementContratos.getContratante());
        pago.setText(elementContratos.getPago());

        if("1".equals(elementContratos.getEstado()))
        {
            estado.setTextColor(Color.parseColor("#4CAF0F"));
            estado.setText("Vigente. ");
        }
        else if("0".equals(elementContratos.getEstado()))
        {
            estado.setTextColor(Color.parseColor("#E40B0B"));
            estado.setText("Expirado. ");
        }
        else if("2".equals(elementContratos.getEstado()))
        {
            estado.setText("Filalizado. ");
        }

        basededatos(extraer_oferta, extraert_oferta_tipo, 0);
        return rootView;
    }

    @Override
    public void onClick(View v)
    {
        int cancelar = 0, finalizar = 2;
        switch (v.getId())
        {
            case R.id.cancelar_contrato:
                basededatos(estado_contrato, estado_contrato_tipo, cancelar);
                abrir_fragment(false);
                getFragmentManager().beginTransaction().remove(this).commit();
            break;

            case R.id.finalizar_contrato:
                basededatos(estado_contrato, estado_contrato_tipo, finalizar);
                abrir_fragment(true);
                getFragmentManager().beginTransaction().remove(this).commit();
            break;

            case R.id.actualizar_contrato:
                if(user.equals(elementContratos.getContratante()))
                {
                    basededatos(actializar_contrato, actualizar_contrato_tipo, 0);
                    getFragmentManager().beginTransaction().remove(this).commit();
                }
                else
                    mensajes("Solo el contratante puede modificarlo");
            break;

            case R.id.botoncerrar_crear_ofertas2:
                getFragmentManager().beginTransaction().remove(this).commit();
            break;

        }
    }

    public void abrir_fragment(boolean finalizar)
    {
        Bundle datos = new Bundle();

        datos.putSerializable("id_contrato", elementContratos.getId_contrato());
        if(user.equals(elementContratos.getContratante()))
            datos.putSerializable("usuario_reseñar", elementContratos.getContratista());
        else
            datos.putSerializable("usuario_reseñar", elementContratos.getContratante());

        datos.putSerializable("finalizado", finalizar);

        calificar_usuario calificar_usuario = new calificar_usuario();
        calificar_usuario.setArguments(datos);

        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.administrar_contratos, calificar_usuario);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void basededatos(String accion, int tipo, int estado)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link + accion, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                if(!response.isEmpty())
                    switch (tipo)
                    {
                        case estado_contrato_tipo:
                        case actualizar_contrato_tipo:
                            mensajes(response);
                        break;

                        case extraert_oferta_tipo:
                            extraerdatos(response);
                        break;
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

                switch (tipo)
                {
                    case extraert_oferta_tipo:
                        parametros.put("id_oferta", elementContratos.getId_oferta());
                    break;

                    case actualizar_contrato_tipo:
                        parametros.put("id_contrato", elementContratos.getId_contrato());
                        parametros.put("pago", pago.getText().toString());
                   break;

                    case estado_contrato_tipo:
                        parametros.put("id_contrato", elementContratos.getId_contrato());
                        parametros.put("estado", Integer.toString(estado));
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
            //UPDATE usuarios SET apellidoP = "ortega", apellidoM = "zacarias" WHERE user = "miguel";
            JSONObject response = new JSONObject(dato);
            JSONArray contratos = response.getJSONArray("datos");
            JSONObject jsonObject = null;
            jsonObject = contratos.getJSONObject(0);

            descripcion.setText(jsonObject.optString("descripcion"));

        }
        catch (JSONException e)
        {
            //e.printStackTrace();
            mensajes("Error de Jason: " + e.toString());
        }
    }

    public void mensajes(String mensaje)
    {
        Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
    }

}