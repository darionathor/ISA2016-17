package proj.beans.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import proj.beans.domain.Poseta;

public interface MongoPosetaRepository extends MongoRepository<Poseta, String>{

}
