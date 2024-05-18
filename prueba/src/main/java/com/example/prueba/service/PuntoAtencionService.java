package com.example.prueba.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.prueba.modelo.PuntoAtencion;
import com.example.prueba.repositorio.PuntoAtencionRepository;

@Service
public class PuntoAtencionService {
    @Autowired
    private PuntoAtencionRepository puntoAtencionRepository;

    public void crearPuntoAtencion(String nombre,String tipoPuntoAtencion,String locacion, String horarioApertura, String horarioCierre,ObjectId idOficina){
        PuntoAtencion puntoAtencion = new PuntoAtencion();
        puntoAtencion.setNombre(nombre);
        puntoAtencion.setTipoPuntoAtencion(tipoPuntoAtencion);
        puntoAtencion.setLocacion(locacion);
        puntoAtencion.setHorarioApertura(horarioApertura);
        puntoAtencion.setHorarioCierre(horarioCierre);
        puntoAtencion.setIdOficina(idOficina);
        puntoAtencionRepository.save(puntoAtencion);
    }

    public void eliminarPuntoAtencion(ObjectId id){
        puntoAtencionRepository.deleteById(id);
    }
}
