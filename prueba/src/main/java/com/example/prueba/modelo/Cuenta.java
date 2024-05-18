package com.example.prueba.modelo;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="cuenta")
public class Cuenta {
    @Id
    private ObjectId id;

    private ObjectId idUsuario;
    private String tipoCuenta;
    private String estadoCuenta;
    private Date fechaUltimaTransaccion;
    private Double saldo;
    private Date fechaCreacion;

    public Cuenta(){}

    public Cuenta(ObjectId idUsuario,String tipoCuenta,String estadoCuenta,Date fechaUltimaTransaccion,Double saldo,Date fechaCreacion){
        this.idUsuario = idUsuario;
        this.tipoCuenta = tipoCuenta;
        this.estadoCuenta = estadoCuenta;
        this.fechaUltimaTransaccion = fechaUltimaTransaccion;
        this.saldo = saldo;
        this.fechaCreacion = fechaCreacion;
    }

    public String getEstadoCuenta() {
        return estadoCuenta;
    }

    public void setEstadoCuenta(String estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
    }
    public Date getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public Date getFechaUltimaTransaccion() {
        return fechaUltimaTransaccion;
    }
    public void setFechaUltimaTransaccion(Date fechaUltimaTransaccion) {
        this.fechaUltimaTransaccion = fechaUltimaTransaccion;
    }
    public ObjectId getId() {
        return id;
    }
    public void setId(ObjectId id) {
        this.id = id;
    }
    public ObjectId getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(ObjectId idUsuario) {
        this.idUsuario = idUsuario;
    }
    public Double getSaldo() {
        return saldo;
    }
    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
    public String getTipoCuenta() {
        return tipoCuenta;
    }
    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }
    
}
