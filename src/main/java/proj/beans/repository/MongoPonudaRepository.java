package proj.beans.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import proj.beans.domain.Ponuda;

public interface MongoPonudaRepository extends MongoRepository<Ponuda, String>{

}
