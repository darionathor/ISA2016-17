package proj.beans.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import proj.beans.domain.RasporedRada;

public interface MongoRasporedRadaRepository extends MongoRepository<RasporedRada, String>{

}
