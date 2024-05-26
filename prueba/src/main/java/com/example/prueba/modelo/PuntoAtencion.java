package com.example.prueba.modelo;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="puntoAtencion")
public class PuntoAtencion {
    @Id
    private ObjectId id;

    private String nombre;
    private String tipoPuntoAtencion;
    private String locacion;
    private String horarioApertura;
    private String horarioCierre;
    private ObjectId idOficina;
    
    public PuntoAtencion(){}

    public PuntoAtencion(String nombre,String tipoPuntoAtencion,String locacion,String horarioApertura,String horarioCierre,ObjectId idOficina){
        this.nombre=nombre;
        this.tipoPuntoAtencion=tipoPuntoAtencion;
        this.locacion=locacion;
        this.horarioApertura=horarioApertura;
        this.horarioCierre=horarioCierre;
        this.idOficina=idOficina;
    }

    public String getHorarioApertura() {
        return horarioApertura;
    }
    public void setHorarioApertura(String horarioApertura) {
        this.horarioApertura = horarioApertura;
    }
    public String getHorarioCierre() {
        return horarioCierre;
    }
    public void setHorarioCierre(String horarioCierre) {
        this.horarioCierre = horarioCierre;
    }
    public ObjectId getId() {
        return id;
    }
    public void setId(ObjectId id) {
        this.id = id;
    }
    public ObjectId getIdOficina() {
        return idOficina;
    }
    public void setIdOficina(ObjectId idOficina) {
        this.idOficina = idOficina;
    }
    public String getLocacion() {
        return locacion;
    }
    public void setLocacion(String locacion) {
        this.locacion = locacion;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getTipoPuntoAtencion() {
        return tipoPuntoAtencion;
    }
    public void setTipoPuntoAtencion(String tipoPuntoAtencion) {
        this.tipoPuntoAtencion = tipoPuntoAtencion;
    }

    @Override
    public String toString() {
        return "PuntoAtencion [id=" + id + ", nombre=" + nombre + ", tipoPuntoAtencion=" + tipoPuntoAtencion
                + ", locacion=" + locacion + ", horarioApertura=" + horarioApertura + ", horarioCierre=" + horarioCierre
                + ", idOficina=" + idOficina + "]";
    }
    
    
    
}
