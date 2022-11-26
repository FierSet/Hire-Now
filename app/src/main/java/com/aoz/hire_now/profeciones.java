package com.aoz.hire_now;

public class profeciones
{
    public String profecion;

    public profeciones() { }


    public void setProfecion(String profecion)
    {
        this.profecion = profecion;
    }

    public String getProfecion()
    {
        return profecion.toString();
    }


    @Override
    public String toString()
    {
        return profecion;
    }

}
