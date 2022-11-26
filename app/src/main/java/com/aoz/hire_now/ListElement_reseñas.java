package com.aoz.hire_now;

import java.io.Serializable;

public class ListElement_reseñas implements Serializable //ofertas
{
    public String user, id_contrato, comentario_reseña, reseña, comentario_exito, exito;

    public ListElement_reseñas(String user, String id_contrato, String comentario_reseña, String reseña, String comentario_exito, String exito)
    {
        this.user = user;
        this.id_contrato = id_contrato;
        this.comentario_reseña = comentario_reseña;
        this.reseña = reseña;
        this.comentario_exito = comentario_exito;
        this.exito = exito;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getId_contrato() {
        return id_contrato;
    }

    public void setId_contrato(String id_contrato) {
        this.id_contrato = id_contrato;
    }

    public String getComentario_reseña() {
        return comentario_reseña;
    }

    public void setComentario_reseña(String comentario_reseña) {
        this.comentario_reseña = comentario_reseña;
    }

    public String getReseña() {
        return reseña;
    }

    public void setReseña(String reseña) {
        this.reseña = reseña;
    }

    public String getComentario_exito() {
        return comentario_exito;
    }

    public void setComentario_exito(String comentario_exito) {
        this.comentario_exito = comentario_exito;
    }

    public String getExito() {
        return exito;
    }

    public void setExito(String exito) {
        this.exito = exito;
    }
}
