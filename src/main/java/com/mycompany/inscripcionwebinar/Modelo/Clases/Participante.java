package com.mycompany.inscripcionwebinar.Modelo.Clases;

public class Participante {

    private int idparticipante;
    private String nombre;
    private String correo;
    private String empresa;

    public Participante(String nombre, String correo, String empresa) {
        this.nombre = nombre;
        this.correo = correo;
        this.empresa = empresa;
    }

    public Participante(int idparticipante, String nombre, String correo, String empresa) {
        this.idparticipante = idparticipante;
        this.nombre = nombre;
        this.correo = correo;
        this.empresa = empresa;
    }

    public int getIdparticipante() {
        return idparticipante;
    }

    public void setIdparticipante(int idparticipante) {
        this.idparticipante = idparticipante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    @Override
    public String toString() {
        return "Participante{" + "id=" + idparticipante + ", nombre=" + nombre
                + ", correo=" + correo + ", empresa=" + empresa + '}';
    }

}
