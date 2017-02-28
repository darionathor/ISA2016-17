package proj.beans.service;

import java.util.Collection;

import proj.beans.domain.RasporedRada;

public interface RasporedRadaService {

	Collection<RasporedRada> findAll();

	RasporedRada findOne(String id);

	RasporedRada create(RasporedRada restoran) throws Exception;

	RasporedRada update(RasporedRada restoran) throws Exception;

	void delete(String id);
}
