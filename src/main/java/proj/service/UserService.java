package proj.service;

import java.util.Collection;

import proj.domain.User;

public interface UserService {
	Collection<User> findAll();

	User findOne(Long id);

	User create(User user) throws Exception;

	User update(User user) throws Exception;

	void delete(Long id);
}
