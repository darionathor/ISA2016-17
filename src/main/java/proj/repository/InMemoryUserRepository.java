package proj.repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import proj.domain.User;

@Repository
public class InMemoryUserRepository implements UserRepository  {
	private static AtomicLong counter = new AtomicLong();

	private final ConcurrentMap<Long, User> users= new ConcurrentHashMap<Long, User>();

	@Override
	public Collection<User> findAll() {
		// TODO Auto-generated method stub
		return this.users.values();
	}

	@Override
	public User create(User user) {
		// TODO Auto-generated method stub
		Long id = user.getId();
		if (id == null) {
			id = counter.incrementAndGet();
			user.setId(id);
		}
		this.users.put(id, user);
		return user;
	}

	@Override
	public User findOne(Long id) {
		// TODO Auto-generated method stub
		return this.users.get(id);
	}

	@Override
	public User update(User user) {
		// TODO Auto-generated method stub
		Long id = user.getId();
		if (id != null) {
			this.users.put(id, user);
		}
		return user;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		this.users.remove(id);
	}

}
