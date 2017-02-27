package proj.beans.service;

import java.util.Collection;

import proj.beans.domain.Ponuda;
import proj.beans.domain.PonudaPonudjaca;

public interface PonudaPonudjacaService {

	Collection<PonudaPonudjaca> findAll();

	PonudaPonudjaca findOne(String id);

	PonudaPonudjaca create(PonudaPonudjaca restoran) throws Exception;

	PonudaPonudjaca update(PonudaPonudjaca restoran) throws Exception;

	void delete(String id);
}
