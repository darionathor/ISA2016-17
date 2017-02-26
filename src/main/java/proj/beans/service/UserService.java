package proj.beans.service;

import java.util.Collection;

import proj.beans.domain.User;

public interface UserService {
	Collection<User> findAll();

	User findOne(String id);

	User create(User user) throws Exception;

	User update(User user) throws Exception;

	void delete(String id);
}
