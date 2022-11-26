package com.aoz.hire_now;

import java.io.Serializable;

public class ListElement_Contratos implements Serializable //ofertas
{
    public String id_contrato, id_oferta, Contratante, contratista, pago, estado, permiso_contratante, permiso_contratista;

    public ListElement_Contratos(String id_contrato, String id_oferta, String Contratante, String contratista, String pago, String estado, String permiso_contratante, String permiso_contratista)
    {
        this.id_contrato = id_contrato;
        this.id_oferta = id_oferta;
        this.Contratante = Contratante;
        this.contratista = contratista;
        this.pago = pago;
        this.estado = estado;
        this.permiso_contratante = permiso_contratante;
        this.permiso_contratista = permiso_contratista;
    }

    public String getId_contrato() {
        return id_contrato;
    }

    public void setId_contrato(String id_contrato) {
        this.id_contrato = id_contrato;
    }

    public String getId_oferta() {
        return id_oferta;
    }

    public void setId_oferta(String id_oferta) {
        this.id_oferta = id_oferta;
    }

    public String getContratante() {
        return Contratante;
    }

    public void setContratante(String contratante) {
        Contratante = contratante;
    }

    public String getContratista() {
        return contratista;
    }

    public void setContratista(String contratista) {
        this.contratista = contratista;
    }

    public String getPago() {
        return pago;
    }

    public void setPago(String pago) {
        this.pago = pago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPermiso_contratante() {
        return permiso_contratante;
    }

    public void setPermiso_contratante(String permiso_contratante) {
        this.permiso_contratante = permiso_contratante;
    }

    public String getPermiso_contratista() {
        return permiso_contratista;
    }

    public void setPermiso_contratista(String permiso_contratista) {
        this.permiso_contratista = permiso_contratista;
    }
}
