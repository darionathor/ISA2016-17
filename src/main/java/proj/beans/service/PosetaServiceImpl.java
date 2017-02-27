package proj.beans.service;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proj.beans.domain.Poseta;
import proj.beans.repository.MongoPosetaRepository;

@Service
public class PosetaServiceImpl implements PosetaService{

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
  //  private InMemoryRestoranRepository restoranRepository;
	private MongoPosetaRepository ponudaRepository;
	@Override
	public Collection<Poseta> findAll() {
		// TODO Auto-generated method stub
		logger.info("> findAll");
        Collection<Poseta> users = ponudaRepository.findAll();
        logger.info("< findAll");
        return users;
	}

	@Override
	public Poseta findOne(String id) {
		// TODO Auto-generated method stub
		logger.info("> findOne id:{}", id);
		Poseta user = ponudaRepository.findOne(id);
        logger.info("< findOne id:{}", id);
        return user;
	}

	@Override
	public Poseta create(Poseta restoran) throws Exception {
		// TODO Auto-generated method stub
		logger.info("> create");
        if (restoran.getId() != null) {
            logger.error(
                    "Pokusaj kreiranja novog entiteta, ali Id nije null.");
            throw new Exception(
                    "Id mora biti null prilikom perzistencije novog entiteta.");
        }
        Poseta savedrestoran = ponudaRepository.save(restoran);
        logger.info("< create");
        return savedrestoran;
	}

	@Override
	public Poseta update(Poseta restoran) throws Exception {
		// TODO Auto-generated method stub
		logger.info("> update id:{}", restoran.getId());
		Poseta restoranToUpdate = findOne(restoran.getId());
        if (restoranToUpdate == null) {
            logger.error(
                    "Pokusaj azuriranja entiteta, ali je on nepostojeci.");
            throw new Exception("Trazeni entitet nije pronadjen.");
        }
        restoranToUpdate.setRestoran(restoran.getRestoran());
        restoranToUpdate.setDatum(restoran.getDatum());
        restoranToUpdate.setSto(restoran.getSto());
        restoranToUpdate.setUser(restoran.getUser());
        restoranToUpdate.setNarucenaJela(restoran.getNarucenaJela());
        restoranToUpdate.setNarucenaPica(restoran.getNarucenaPica());
    
        Poseta updatedRestoran= ponudaRepository.save(restoranToUpdate);
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
