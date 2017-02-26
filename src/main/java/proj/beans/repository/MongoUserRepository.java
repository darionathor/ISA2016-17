package proj.beans.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import proj.beans.domain.User;

public interface MongoUserRepository extends MongoRepository<User, String>{

}
