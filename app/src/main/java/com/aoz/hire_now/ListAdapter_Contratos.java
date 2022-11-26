package com.aoz.hire_now;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter_Contratos extends RecyclerView.Adapter<ListAdapter_Contratos.ViewHolder>
{
    private List<ListElement_Contratos> mData;
    private LayoutInflater mInflate;
    private Context context;
    final ListAdapter_Contratos.OnItemClickListener listener;
    int skin_list;

    public interface OnItemClickListener
    {
        void onItemClick(ListElement_Contratos item);
    }

    public ListAdapter_Contratos(int skin_list, List<ListElement_Contratos> itemList, Context context, ListAdapter_Contratos.OnItemClickListener listener)
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
    public ListAdapter_Contratos.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflate.inflate(skin_list, null);
        return new ListAdapter_Contratos.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter_Contratos.ViewHolder holder, final  int position)
    {
        holder.bindData(mData.get(position));
    }

    public void setItem(List<ListElement_Contratos> items)
    {
        mData = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView titulo, contratante, contratista, pago, estado;

        ViewHolder(View itemView)
        {
            super(itemView);
        }

        void bindData(final ListElement_Contratos item)
        {

            try
            {
                titulo = itemView.findViewById(R.id.titulo2);
                contratante = itemView.findViewById(R.id.contratante_lista_contrato);
                contratista = itemView.findViewById(R.id.contratista_lista_contrato);
                pago = itemView.findViewById(R.id.pago_lista_contrato);
                estado = itemView.findViewById(R.id.estado_lista_contratos);

                titulo.setText(item.getId_contrato() + " : " + item.getId_oferta());
                contratante.setText(item.getContratante());
                contratista.setText(item.getContratista());
                pago.setText(item.getPago());

                if("1".equals(item.getEstado()))
                {
                    estado.setTextColor(Color.parseColor("#4CAF0F"));
                    estado.setText("Vigente. ");
                }
                else if("0".equals(item.getEstado()))
                {
                    estado.setTextColor(Color.parseColor("#E40B0B"));
                    estado.setText("Cancelado. ");
                }
                else if("2".equals(item.getEstado()))
                {
                    estado.setText("Filalizado. ");
                }
            }
            catch (Exception e)
            {
                mensajes(e.toString());
            }

            /*
            Uri urlparse = Uri.parse(item.getImagen());
            Glide.with(context).load(urlparse).error(R.drawable.work).into(imagen);
            */

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
