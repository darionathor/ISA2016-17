package proj.beans.service;

import java.util.Collection;

import proj.beans.domain.Poseta;

public interface PosetaService {

	Collection<Poseta> findAll();

	Poseta findOne(String id);

	Poseta create(Poseta restoran) throws Exception;

	Poseta update(Poseta restoran) throws Exception;

	void delete(String id);
}
