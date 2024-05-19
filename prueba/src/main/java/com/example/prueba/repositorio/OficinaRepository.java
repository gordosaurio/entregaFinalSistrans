package com.example.prueba.repositorio;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import com.example.prueba.modelo.Oficina;
import com.example.prueba.modelo.Usuario;
@Repository
public interface OficinaRepository extends MongoRepository<Oficina, ObjectId>{
    @Query("{}")
    List<Oficina> buscarOficinas();

    Oficina save(Oficina oficina);

    
}
