package proj.beans.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import proj.beans.domain.PonudaPonudjaca;

public interface MongoPonudaPonudjacaRepository extends MongoRepository<PonudaPonudjaca, String>{

}
