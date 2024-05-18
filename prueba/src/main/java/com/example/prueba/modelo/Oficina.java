package com.example.prueba.modelo;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection="oficina")
public class Oficina {
    @Id
    private String id;
    private String nombre;
    private String direccion;
    private int numeroPuntosDisponibles;
    private ObjectId idGerente;

    public Oficina(){}

    public Oficina(String id,String nombre,String direccion,int numeroPuntosDisponibles, ObjectId idGerente){
        this.id=id;
        this.nombre=nombre;
        this.direccion=direccion;
        this.numeroPuntosDisponibles=numeroPuntosDisponibles;
        this.idGerente=idGerente;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public ObjectId getIdGerente() {
        return idGerente;
    }
    public void setIdGerente(ObjectId idGerente) {
        this.idGerente = idGerente;
    }
    public int getNumeroPuntosDisponibles() {
        return numeroPuntosDisponibles;
    }
    public void setNumeroPuntosDisponibles(int numeroPuntosDisponibles) {
        this.numeroPuntosDisponibles = numeroPuntosDisponibles;
    }
}
