package com.aoz.hire_now;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ListAdapter_Solicitud extends RecyclerView.Adapter<ListAdapter_Solicitud.ViewHolder>
{
    private List<ListElement_Solicitud> mData;
    private LayoutInflater mInflate;
    private Context context;
    final ListAdapter_Solicitud.OnItemClickListener listener;
    int skin_list;

    public interface OnItemClickListener
    {
        void onItemClick(ListElement_Solicitud item);
    }

    public ListAdapter_Solicitud(int skin_list, List<ListElement_Solicitud> itemList, Context context, ListAdapter_Solicitud.OnItemClickListener listener)
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
    public ListAdapter_Solicitud.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflate.inflate(skin_list, null);
        return new ListAdapter_Solicitud.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter_Solicitud.ViewHolder holder, final  int position)
    {
        holder.bindData(mData.get(position));
    }

    public void setItem(List<ListElement_Solicitud> items)
    {
        mData = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imagen;
        TextView id_user_notificacion, titulo, tipo;

        ViewHolder(View itemView)
        {
            super(itemView);
        }

        void bindData(final ListElement_Solicitud item)
        {
            imagen = itemView.findViewById(R.id.Imagenes_list2);
            titulo = itemView.findViewById(R.id.titulo2);
            id_user_notificacion = itemView.findViewById(R.id.usuario);
            tipo = itemView.findViewById(R.id.tipo);

            titulo.setText(item.getTitulo());
            id_user_notificacion.setText(item.getID_user_notificacion());
            tipo.setText(item.getTipo());

            Uri urlparse = Uri.parse(item.getImagen());
            Glide.with(context).load(urlparse).error(R.drawable.work).into(imagen);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }

        public void mensajes(String mensaje)
        {
            Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show();
        }

    }
}
