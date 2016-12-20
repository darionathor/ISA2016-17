package proj.beans.repository;

import java.util.Collection;

import proj.beans.domain.Restoran;
import proj.beans.domain.User;

public interface RestoranRepository {
	Collection<Restoran> findAll();

	Restoran create(Restoran user);

	Restoran findOne(Long id);
	
	Restoran update(Restoran user);

	void delete(Long id);
}
