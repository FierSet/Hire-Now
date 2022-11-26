package com.aoz.hire_now;

import android.content.Context;
import android.graphics.Color;
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

public class ListAdapter_reseñas extends RecyclerView.Adapter<ListAdapter_reseñas.ViewHolder>
{
    private List<ListElement_reseñas> mData;
    private LayoutInflater mInflate;
    private Context context;
    final ListAdapter_reseñas.OnItemClickListener listener;
    int skin_list;

    float reseñas, exitorango;

    public interface OnItemClickListener
    {
        void onItemClick(ListElement_reseñas item);
    }

    public ListAdapter_reseñas(int skin_list, List<ListElement_reseñas> itemList, Context context, ListAdapter_reseñas.OnItemClickListener listener)
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
    public ListAdapter_reseñas.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflate.inflate(skin_list, null);
        return new ListAdapter_reseñas.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter_reseñas.ViewHolder holder, final  int position)
    {
        holder.bindData(mData.get(position));
    }

    public void setItem(List<ListElement_reseñas> items)
    {
        mData = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView titulo, Descripcion;
        RatingBar Reseñas;
        ImageView Imagenes_list2;

        ViewHolder(View itemView)
        {
            super(itemView);
        }

        void bindData(final ListElement_reseñas item)
        {
            titulo = itemView.findViewById(R.id.titulo2);
            Reseñas = itemView.findViewById(R.id.Calificacion_resenas);
            Descripcion = itemView.findViewById(R.id.Descripcion2);
            Imagenes_list2 = itemView.findViewById(R.id.Imagenes_list2);

            titulo.setText(item.getUser());
            float reseñaval = Float.parseFloat(item.getReseña());
            Reseñas.setRating(reseñaval);

            if(item.getComentario_reseña().isEmpty())
            {
                Descripcion.setTextColor(Color.parseColor("#8E8888"));
                Descripcion.setText("Sin comentarios");
            }
            else
            {
                Descripcion.setTextColor(Color.parseColor("#000000"));
                Descripcion.setText(item.getComentario_reseña());
            }

            accederalDB("cargar_perfil.php", Imagenes_list2, item.getUser());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });

        }

        private void accederalDB(String accion, ImageView imagen, String user)
        {
            String link = global_var.getLink();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, link + accion, new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response)
                {
                    if(!response.isEmpty())
                    {
                        extraerdatos(response, imagen);
                    }
                    else
                    {
                        mensajes("Error" + response);
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

                    parametros.put("user", user);

                    return parametros;
                }
            };
            RequestQueue rq = Volley.newRequestQueue(context);
            rq.add(stringRequest);
        }

        public void extraerdatos( String dato, ImageView imagen )
        {
            try
            {
                JSONObject response = new JSONObject(dato);
                JSONArray jsonArray = response.optJSONArray("datos");
                JSONObject jsonObject = null;
                jsonObject = jsonArray.getJSONObject(0);

                /*
                jsonObject.optString("id");
                jsonObject.optString("nombres");
                jsonObject.optString("apellidoP");
                jsonObject.optString("apellidoM");
                jsonObject.optString("profesion");
                jsonObject.optString("RFC");
                jsonObject.optString("comprobado");
                */

                Uri urlparse = Uri.parse(jsonObject.optString("imagen"));
                Glide.with(context).load(urlparse).error(R.drawable.user_vector).into(imagen);


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
