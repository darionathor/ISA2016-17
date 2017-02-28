package proj.beans.service;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proj.beans.domain.Ponuda;
import proj.beans.domain.RasporedRada;
import proj.beans.repository.MongoRasporedRadaRepository;

@Service
public class RasporedRadaServiceImpl implements RasporedRadaService{

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
  //  private InMemoryRestoranRepository restoranRepository;
	private MongoRasporedRadaRepository ponudaRepository;
	
	@Override
	public Collection<RasporedRada> findAll() {
		// TODO Auto-generated method stub
		logger.info("> findAll");
        Collection<RasporedRada> users = ponudaRepository.findAll();
        logger.info("< findAll");
        return users;
	}

	@Override
	public RasporedRada findOne(String id) {
		// TODO Auto-generated method stub
		logger.info("> findOne id:{}", id);
		RasporedRada user = ponudaRepository.findOne(id);
        logger.info("< findOne id:{}", id);
        return user;
	}

	@Override
	public RasporedRada create(RasporedRada restoran) throws Exception {
		// TODO Auto-generated method stub
		logger.info("> create");
        if (restoran.getId() != null) {
            logger.error(
                    "Pokusaj kreiranja novog entiteta, ali Id nije null.");
            throw new Exception(
                    "Id mora biti null prilikom perzistencije novog entiteta.");
        }
        RasporedRada savedrestoran = ponudaRepository.save(restoran);
        logger.info("< create");
        return savedrestoran;
	}

	@Override
	public RasporedRada update(RasporedRada restoran) throws Exception {
		// TODO Auto-generated method stub
		logger.info("> update id:{}", restoran.getId());
        RasporedRada updatedRestoran= ponudaRepository.save(restoran);
        logger.info("< update id:{}", restoran.getId());
        return updatedRestoran;
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		logger.info("> delete id:{}", id);
		ponudaRepository.delete(id);
        logger.info("< delete id:{}", id);
	}
}
