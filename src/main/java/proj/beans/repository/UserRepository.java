package proj.beans.repository;

import java.util.Collection;

import proj.beans.domain.User;


public interface UserRepository {
	Collection<User> findAll();

	User create(User user);

	User findOne(Long id);
	
	User update(User user);

	void delete(Long id);
}
