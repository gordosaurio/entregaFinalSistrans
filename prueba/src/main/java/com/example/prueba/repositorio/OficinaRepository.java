package com.example.prueba.repositorio;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import com.example.prueba.modelo.Oficina;
import com.example.prueba.modelo.PuntoAtencion;
import com.example.prueba.modelo.Usuario;
@Repository
public interface OficinaRepository extends MongoRepository<Oficina, ObjectId>{
    @Query("{}")
    List<Oficina> buscarOficinas();

    Oficina save(Oficina oficina);

    @Query("{_id : ?0}")
    @Update("{'$set':{'nombre': ?1} {'direccion': ?2}, {'numeroPuntosDisponibles': ?3}, {'idGerente': ?4}}")
    void updateOficina(ObjectId id, String nombre, String direccion, int numeroPuntosDisponibles, ObjectId idGerente );    

    @Query(value="{_id : ?0}", delete = true)
    void deleteById(ObjectId id);

    @Query("{_id: ?0}")
    PuntoAtencion buscarporId(ObjectId id);
}
