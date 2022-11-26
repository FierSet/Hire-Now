package com.aoz.hire_now;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>
{
    private List<ListElement> mData;
    private LayoutInflater mInflate;
    private Context context;
    final ListAdapter.OnItemClickListener listener;
    int skin_list;

    float reseñas, exitorango;

    public interface OnItemClickListener
    {
        void onItemClick(ListElement item);
    }

    public ListAdapter(int skin_list, List<ListElement> itemList, Context context, ListAdapter.OnItemClickListener listener)
    {
        this.mInflate = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.listener = listener;
        this.skin_list = skin_list;
    }

    @Override
    public int getItemCount()
    {
        return mData.size();
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflate.inflate(skin_list, null);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final  int position)
    {
        holder.bindData(mData.get(position));
    }

    public void setItem(List<ListElement> items)
    {
        mData = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imagen;
        TextView titulo, descripcion;
        RatingBar Reseñas, RangoExito;

        ViewHolder(View itemView)
        {
            super(itemView);
        }

        void bindData(final ListElement item)
        {

            Reseñas = itemView.findViewById(R.id.Reseñas);
            RangoExito = itemView.findViewById(R.id.RangoExito);

            imagen = itemView.findViewById(R.id.Imagenes_list2);
            titulo = itemView.findViewById(R.id.titulo2);
            descripcion = itemView.findViewById(R.id.Descripcion2);

            titulo.setText(item.getTitulo());
            descripcion.setText(item.getDescripcion());

            if(skin_list != R.layout.modelo_de_lista_administrar)
                if(item.getEs_usuario())
                    basededatos("extraer_reseñas.php", item.getID());
                else
                    basededatos("extraer_reseñas.php", item.getUser());

            Uri urlparse = Uri.parse(item.getImagen());
            Glide.with(context).load(urlparse).error(R.drawable.work).into(imagen);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }

        public void basededatos(String accion, String ID)
        {
            String link = global_var.getLink();

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

                    parametros.put("user", ID);

                    return parametros;
                }
            };
            RequestQueue rq = Volley.newRequestQueue(context);
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

                }

            }
            catch (JSONException e)
            {
                //e.printStackTrace();
                mensajes("Error de Jason: " + e.toString());
            }
        }

        public void mensajes(String mensaje)
        {
            Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show();
        }

    }
}
