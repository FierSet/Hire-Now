package com.aoz.hire_now;

import java.io.Serializable;

public class ListElement implements Serializable //ofertas
{
    public String ID, user, imagen, titulo, descripcion, info_genera, profecion_req, costo, vacantes;

    public Boolean es_usuario;

    public ListElement(String ID, String user ,String imagen, String titulo, String descripcion,String profecion_req, String info_genera,String costo, String vacantes, Boolean es_usuario)
    {
        this.ID = ID;
        this.user = user;
        this.imagen = imagen;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.profecion_req = profecion_req;
        this.info_genera = info_genera;
        this.costo = costo;
        this.vacantes = vacantes;
        this.es_usuario = es_usuario;
    }

    public ListElement()
    {

    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public String getVacantes() {
        return vacantes;
    }

    public void setVacantes(String vacantes) {
        this.vacantes = vacantes;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getInfo_genera() {
        return info_genera;
    }

    public String getProfecion_req() {
        return profecion_req;
    }

    public void setProfecion_req(String profecion_req) {
        this.profecion_req = profecion_req;
    }

    public void setInfo_genera(String info_genera) {
        this.info_genera = info_genera;
    }

    public String getImagen()
    {
        return imagen;
    }

    public void setImagen(String imagen)
    {
        this.imagen = imagen;
    }

    public String getTitulo()
    {
        return titulo;
    }

    public void setTitulo(String titulo)
    {
        this.titulo = titulo;
    }

    public String getDescripcion()
    {
        return descripcion;
    }

    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }

    public Boolean getEs_usuario() {
        return es_usuario;
    }

    public void setEs_usuario(Boolean es_usuario) {
        this.es_usuario = es_usuario;
    }
}
