package com.aoz.hire_now.ui.Creat_oferta;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aoz.hire_now.ListElement;
import com.aoz.hire_now.R;
import com.aoz.hire_now.profeciones;
import com.bumptech.glide.Glide;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Crear_ofertas extends Fragment implements View.OnClickListener
{

    TextView caracteres_descripcion, caracteres_info_general, titulo_label;
    EditText titulo, link_imagen, Descripcion, inform_general, costo, vacantes;

    ListElement elemento;

    Spinner lista_profecion;

    Button Crear, Borrar, cerrar;

    ImageView Imagen_oferta;

    String link , user;

    Boolean Editor;

    int caracteres_desc_max = 100, caracteres_inf_general_max = 1000;

    static final int Cargar_prof_tipo = 0, crear_oferta_tipo = 1, actualizar_oferta_typo = 2, Borrar_oferta_type = 3;

    String cargar_prof ="lista_profeciones.php", Crear_oferta = "Crear_oferta.php", Actualizar_oferta = "actualizar_oferta.php", Borrar_oferta = "eliminar_oferta.php";;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_crear_ofertas, container, false);

        titulo_label = view.findViewById(R.id.titulo_crear_oferta);
        caracteres_descripcion =  view.findViewById(R.id.caracteres_descripcion);
        caracteres_info_general = view.findViewById(R.id.caracteres_info_general);

        titulo = view.findViewById(R.id.Titulo_de_oferta);
        link_imagen = view.findViewById(R.id.Link_imagen_oferta);               link_imagen.setOnClickListener((View.OnClickListener) this);
        Descripcion = view.findViewById(R.id.Descripcion_de_oferta);
        inform_general = view.findViewById(R.id.Info_general);
        Imagen_oferta = view.findViewById(R.id.Imagen_oferta);
        vacantes = view.findViewById(R.id.no_vacantes);
        costo = view.findViewById(R.id.pago);

        lista_profecion = view.findViewById(R.id.profeciones_requeridas);

        Crear = view.findViewById(R.id.crear_oferta);                           Crear.setOnClickListener((View.OnClickListener) this);
        cerrar = view.findViewById(R.id.botoncerrar_crear_ofertas);             cerrar.setOnClickListener((View.OnClickListener) this);
        Borrar = view.findViewById(R.id.Borrar_oferta);                         Borrar.setOnClickListener((View.OnClickListener) this);

        Uri urlparse = Uri.parse(link_imagen.getText().toString());
        Glide.with(getContext()).load(urlparse).error(R.drawable.work).into(Imagen_oferta);

        link = (String) getArguments().getSerializable("link");
        Editor = (Boolean) getArguments().getSerializable("Editor");

        caracteres();
        editor();
        basededatos(cargar_prof, 0);

        link_imagen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                Uri urlparse = Uri.parse(link_imagen.getText().toString());
                Glide.with(getContext()).load(urlparse).error(R.drawable.work).into(Imagen_oferta);
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        Descripcion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                caracteres();
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        inform_general.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                caracteres();
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        return view;
    }

    public void editor()
    {
        if (Editor)
        {
            Borrar.setVisibility(View.VISIBLE);
            Crear.setText("Actualizar Oferta");
            titulo_label.setText("Actualizar Oferta");
            elemento = (ListElement) getArguments().getSerializable("objeto");
            user = elemento.getUser();
            editordatos();
        }
        else
        {
            Borrar.setVisibility(View.INVISIBLE);
            user = (String) getArguments().getSerializable("user");
        }
    }

    @Override
    public void onClick(View v)
    {
        int fondo_rojo = R.drawable.custom_input_red, fondo_normal = R.drawable.custom_input2;
        Descripcion.setBackgroundResource(fondo_normal);
        titulo.setBackgroundResource(fondo_normal);
        inform_general.setBackgroundResource(fondo_normal);

        switch (v.getId())
        {
            case R.id.crear_oferta:
                if(titulo.getText().length() == 0)
                {
                    titulo.setBackgroundResource(fondo_rojo);
                    mensajes("El titulo no deve estar vacia");
                }
                else if(Descripcion.getText().length() == 0 || Descripcion.getText().length() >= caracteres_desc_max)
                {
                    Descripcion.setBackgroundResource(fondo_rojo);
                    mensajes("La descripcion no deve estar vacia y no deve pasar el maximo");
                }
                else if(inform_general.getText().length() == 0 || inform_general.getText().length() >= caracteres_inf_general_max)
                {
                    inform_general.setBackgroundResource(fondo_rojo);
                    mensajes("La informacion general no deve estar vacia y no deve pasar el maximo");
                }
                else if(costo.getText().length() == 0)
                {
                    costo.setBackgroundResource(fondo_rojo);
                    mensajes("La informacion pago no deve estar vacia y no deve pasar el maximo");
                }
                else if(vacantes.getText().length() == 0)
                {
                    vacantes.setBackgroundResource(fondo_rojo);
                    mensajes("La informacion vacantes no deve estar vacia y no deve pasar el maximo");
                }
                else
                {
                    if(!Editor)
                        basededatos(Crear_oferta, crear_oferta_tipo);
                    else
                        basededatos(Actualizar_oferta, actualizar_oferta_typo);
                    getFragmentManager().beginTransaction().remove(this).commit();
                }
            break;

            case R.id.Link_imagen_oferta:

                link_imagen.setText("");

            break;

            case R.id.botoncerrar_crear_ofertas:
                getFragmentManager().beginTransaction().remove(this).commit();
            break;

            case R.id.Borrar_oferta:

                    basededatos(Borrar_oferta, Borrar_oferta_type);
                    getFragmentManager().beginTransaction().remove(this).commit();
            break;

        }
    }

    public void editordatos()
    {
        titulo.setText(elemento.getTitulo());
        link_imagen.setText(elemento.getImagen());
        Descripcion.setText(elemento.getDescripcion());
        inform_general.setText(elemento.getInfo_genera());
        costo.setText(elemento.getCosto());
        vacantes.setText(elemento.getVacantes());
    }

    public void caracteres()
    {
        int caracteres_descripciones = Descripcion.getText().length();
        int caracteres_info_generales = inform_general.getText().length();

        if(caracteres_descripciones <= caracteres_desc_max)
        {
            caracteres_descripcion.setTextColor(Color.parseColor("#000000"));
            caracteres_descripcion.setText(( caracteres_desc_max - (caracteres_desc_max - caracteres_descripciones)) + "/" + caracteres_desc_max );
        }
        else
        {
            caracteres_descripcion.setTextColor(Color.parseColor("#FF0000"));
            caracteres_descripcion.setText( caracteres_descripciones + "/" + caracteres_desc_max );
        }

        if(caracteres_info_generales <= caracteres_inf_general_max)
        {
            caracteres_info_general.setTextColor(Color.parseColor("#000000"));
            caracteres_info_general.setText( ( caracteres_inf_general_max - (caracteres_inf_general_max - caracteres_info_generales) ) + "/" + caracteres_inf_general_max );
        }
        else
        {
            caracteres_info_general.setTextColor(Color.parseColor("#FF0000"));
            caracteres_info_general.setText( caracteres_info_generales + "/" + caracteres_inf_general_max );
        }
    }

    public void basededatos(String accion, int tipo)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link + accion, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                caracteres();
                if(!response.isEmpty())
                {
                    switch (tipo)
                    {
                        case Cargar_prof_tipo:
                            cargarspin(response);
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

                switch (tipo)
                {
                    case crear_oferta_tipo:

                        parametros.put("user", user);
                        parametros.put("imagen", link_imagen.getText().toString());
                        parametros.put("titulo", titulo.getText().toString());
                        parametros.put("descripcion", Descripcion.getText().toString());
                        parametros.put("info_general", inform_general.getText().toString());
                        parametros.put("profecion_general", lista_profecion.getSelectedItem().toString());
                        parametros.put("costo", costo.getText().toString());
                        parametros.put("vacantes", vacantes.getText().toString());

                    break;

                    case actualizar_oferta_typo:

                        parametros.put("ID", elemento.getID());
                        parametros.put("user", user);
                        parametros.put("imagen", link_imagen.getText().toString());
                        parametros.put("titulo", titulo.getText().toString());
                        parametros.put("descripcion", Descripcion.getText().toString());
                        parametros.put("info_general", inform_general.getText().toString());
                        parametros.put("profecion_general", lista_profecion.getSelectedItem().toString());
                        parametros.put("costo", costo.getText().toString());
                        parametros.put("vacantes", vacantes.getText().toString());

                    break;

                    case Borrar_oferta_type:

                        parametros.put("ID", elemento.getID());

                    break;
                }

                return parametros;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(getActivity());
        rq.add(stringRequest);
    }

    public void cargarspin(String response)
    {
        ArrayList<profeciones> lista = new ArrayList<profeciones>();
        try
        {
            JSONArray JsonArreglo = new JSONArray(response);
            for(int i = 0; i < JsonArreglo.length(); i++)
            {
                profeciones t = new profeciones();
                t.setProfecion(JsonArreglo.getJSONObject(i).getString("profesion"));
                lista.add(t);
            }
            ArrayAdapter<profeciones> a = new ArrayAdapter<profeciones>(getContext(), R.layout.spinner_item_custom1, lista);
            lista_profecion.setAdapter(a);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            mensajes("error carga: " + e.toString());
        }
    }

    public void mensajes(String mensaje)
    {
        Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
    }
}