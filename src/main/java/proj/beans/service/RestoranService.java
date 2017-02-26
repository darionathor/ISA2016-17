package proj.beans.service;

import java.util.Collection;

import proj.beans.domain.Restoran;
import proj.beans.domain.User;

public interface RestoranService {

	Collection<Restoran> findAll();

	Restoran findOne(String id);

	Restoran create(Restoran restoran) throws Exception;

	Restoran update(Restoran restoran) throws Exception;

	void delete(String id);
}
