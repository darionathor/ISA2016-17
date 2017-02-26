package proj.beans.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import proj.beans.domain.Restoran;

public interface MongoRestoranRepository extends MongoRepository<Restoran, String>{

}
