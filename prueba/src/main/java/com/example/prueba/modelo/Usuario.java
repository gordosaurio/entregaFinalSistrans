package com.example.prueba.modelo;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection="usuario")
public class Usuario {
    @Id
    private ObjectId id;
    
    private String tipoDocumento;
    private int numeroDocumento;
    private String nombre;
    private String direccionFisica;
    private String direccionDigital;
    private int telefono;
    private int codigoPostal;
    private String tipoUsuario;
    private String nacionalidad;

    public Usuario() {}

    public Usuario(String tipoDocumento, int numeroDocumento, String nombre, String direccionFisica,
            String direccionDigital, int telefono, int codigoPostal, String tipoUsuario, String nacionalidad) {
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.nombre = nombre;
        this.direccionFisica = direccionFisica;
        this.direccionDigital = direccionDigital;
        this.telefono = telefono;
        this.codigoPostal = codigoPostal;
        this.tipoUsuario = tipoUsuario;
        this.nacionalidad = nacionalidad;
    }
    public ObjectId getId() {
        return id;
    }
    public void setId(ObjectId id) {
        this.id = id;
    }
    public String getTipoDocumento() {
        return tipoDocumento;
    }
    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
    public int getNumeroDocumento() {
        return numeroDocumento;
    }
    public void setNumeroDocumento(int numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDireccionFisica() {
        return direccionFisica;
    }
    public void setDireccionFisica(String direccionFisica) {
        this.direccionFisica = direccionFisica;
    }
    public String getDireccionDigital() {
        return direccionDigital;
    }
    public void setDireccionDigital(String direccionDigital) {
        this.direccionDigital = direccionDigital;
    }
    public int getTelefono() {
        return telefono;
    }
    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
    public int getCodigoPostal() {
        return codigoPostal;
    }
    public void setCodigoPostal(int codigoPostal) {
        this.codigoPostal = codigoPostal;
    }
    public String getTipoUsuario() {
        return tipoUsuario;
    }
    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    public String getNacionalidad() {
        return nacionalidad;
    }
    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getIdString() {
        return id.toHexString();
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", tipoDocumento=" + tipoDocumento + ", numeroDocumento=" + numeroDocumento
                + ", nombre=" + nombre + ", direccionFisica=" + direccionFisica + ", direccionDigital="
                + direccionDigital + ", telefono=" + telefono + ", codigoPostal=" + codigoPostal + ", tipoUsuario="
                + tipoUsuario + ", nacionalidad=" + nacionalidad + "]";
    }
    
}
