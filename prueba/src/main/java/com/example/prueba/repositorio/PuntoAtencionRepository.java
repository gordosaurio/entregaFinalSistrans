package com.example.prueba.repositorio;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import com.example.prueba.modelo.PuntoAtencion;
import com.example.prueba.modelo.Usuario;

@Repository
public interface PuntoAtencionRepository extends MongoRepository<PuntoAtencion, ObjectId>{
    @Query("{}")
    List<PuntoAtencion> darPuntosAtencion();

    PuntoAtencion save(PuntoAtencion puntoAtencion);

    @Query(value = "{_id : ?0}", exists = true)
    @Update("{$set: {nombre : ?1, tipoPuntoAtencion : ?2, locacion : ?3, horarioApertura : ?4, horarioCierre : ?5, idOficina : ?6}}")
    void editarPuntoAtencion(ObjectId idPuntoAtencion, String nombre, String tipoPuntoAtencion, String locacion, String horarioApertura, String horarioCierre, ObjectId idOficina);

    @Query("{_id: ?0}")
    PuntoAtencion buscarporId(ObjectId id);

    @Query(value="{_id : ?0}", delete = true)
    void deleteById(ObjectId id);
}
