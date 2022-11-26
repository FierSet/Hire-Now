package com.aoz.hire_now;

import java.io.Serializable;

public class ListElement_Solicitud implements Serializable //ofertas
{
    public String ID_user_notificacion, ID_iferta, imagen, titulo, tipo;

    public ListElement_Solicitud(String ID_user_notificacion, String ID_iferta, String imagen, String titulo, String tipo) {
        this.ID_user_notificacion = ID_user_notificacion;
        this.ID_iferta = ID_iferta;
        this.imagen = imagen;
        this.titulo = titulo;
        this.tipo = tipo;
    }

    public String getID_user_notificacion() {
        return ID_user_notificacion;
    }

    public void setID_user_notificacion(String ID_user_notificacion) {
        this.ID_user_notificacion = ID_user_notificacion;
    }

    public String getID_iferta() {
        return ID_iferta;
    }

    public void setID_iferta(String ID_iferta) {
        this.ID_iferta = ID_iferta;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
