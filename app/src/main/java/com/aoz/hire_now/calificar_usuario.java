package com.aoz.hire_now;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
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

import java.util.HashMap;
import java.util.Map;


public class calificar_usuario extends Fragment implements View.OnClickListener
{
    View rootView;

    SeekBar valoracion_seek, rango_exito_seek;

    EditText comentario;

    TextView valoracion, progreso;

    Button Varora, cerrar;

    String user = global_var.getUser(), link = global_var.getLink();

    String id_contrato, usuario_a_reseñar;

    String reseñas = "Resenar.php";

    LinearLayout base;

    boolean finalizado;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_calificar_usuario, container, false);

        valoracion_seek = (SeekBar) rootView.findViewById(R.id.valoracion);
        rango_exito_seek = (SeekBar) rootView.findViewById(R.id.rango_exito);

        comentario = (EditText) rootView.findViewById(R.id.comentario_varoracion);

        valoracion = (TextView) rootView.findViewById(R.id.porsentaje_valoracion);
        progreso = (TextView) rootView.findViewById(R.id.progreso);

        Varora = (Button) rootView.findViewById(R.id.Valorar_botton);               Varora.setOnClickListener((View.OnClickListener) this);
        cerrar = (Button) rootView.findViewById(R.id.botoncerrar_crear_ofertas3);   cerrar.setOnClickListener((View.OnClickListener) this);
        base = rootView.findViewById(R.id.calificar_usuario_fondo);                 base.setOnClickListener(this);

        id_contrato = getArguments().getString("id_contrato");
        usuario_a_reseñar = getArguments().getString("usuario_reseñar");
        finalizado = getArguments().getBoolean("finalizado");


        valoracion_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                float valor = (5 * progress);
                valor = valor / 100;
                valoracion.setText(Float.toString(valor));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        rango_exito_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                float valor = (5 * progress);
                valor = valor / 100;
                progreso.setText(Float.toString(valor));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        return rootView;
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.Valorar_botton:
                basededatos(reseñas);
                getFragmentManager().beginTransaction().remove(this).commit();
            break;

            case R.id.botoncerrar_crear_ofertas3:
                getFragmentManager().beginTransaction().remove(this).commit();
            break;

            case  R.id.calificar_usuario_fondo:
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
                    mensajes(response);
                //else cabecera.setText("0 Contratos.");
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

                parametros.put("user", user);
                parametros.put("id_contrato", id_contrato);
                parametros.put("a_sereñar", usuario_a_reseñar);
                parametros.put("comentario_resenas", comentario.getText().toString());
                parametros.put("resenas", valoracion.getText().toString());
                parametros.put("progreso", progreso.getText().toString());

                return parametros;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(getActivity());
        rq.add(stringRequest);
    }


    public void mensajes(String mensaje)
    {
        Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
    }

}