package com.example.prueba.repositorio;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.prueba.modelo.PuntoAtencion;

@Repository
public interface PuntoAtencionRepository extends MongoRepository<PuntoAtencion, ObjectId>{
    @Query("{}")
    List<PuntoAtencion> darPuntosAtencion();

    PuntoAtencion save(PuntoAtencion puntoAtencion);

    void deleteById(ObjectId id);
}
