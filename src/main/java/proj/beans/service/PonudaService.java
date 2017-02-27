package proj.beans.service;

import java.util.Collection;

import proj.beans.domain.Ponuda;

public interface PonudaService {

	Collection<Ponuda> findAll();

	Ponuda findOne(String id);

	Ponuda create(Ponuda restoran) throws Exception;

	Ponuda update(Ponuda restoran) throws Exception;

	void delete(String id);
}
