package proj.beans.service;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proj.beans.domain.Ponuda;
import proj.beans.domain.Restoran;
import proj.beans.repository.MongoPonudaRepository;

@Service
public class PonudaServiceImpl implements PonudaService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
  //  private InMemoryRestoranRepository restoranRepository;
	private MongoPonudaRepository ponudaRepository;
	@Override
	public Collection<Ponuda> findAll() {
		// TODO Auto-generated method stub
		logger.info("> findAll");
        Collection<Ponuda> users = ponudaRepository.findAll();
        logger.info("< findAll");
        return users;
	}

	@Override
	public Ponuda findOne(String id) {
		// TODO Auto-generated method stub
		logger.info("> findOne id:{}", id);
		Ponuda user = ponudaRepository.findOne(id);
        logger.info("< findOne id:{}", id);
        return user;
	}

	@Override
	public Ponuda create(Ponuda restoran) throws Exception {
		// TODO Auto-generated method stub
		logger.info("> create");
        if (restoran.getId() != null) {
            logger.error(
                    "Pokusaj kreiranja novog entiteta, ali Id nije null.");
            throw new Exception(
                    "Id mora biti null prilikom perzistencije novog entiteta.");
        }
        Ponuda savedrestoran = ponudaRepository.save(restoran);
        logger.info("< create");
        return savedrestoran;
	}

	@Override
	public Ponuda update(Ponuda restoran) throws Exception {
		// TODO Auto-generated method stub
		logger.info("> update id:{}", restoran.getId());
		Ponuda restoranToUpdate = findOne(restoran.getId());
        if (restoranToUpdate == null) {
            logger.error(
                    "Pokusaj azuriranja entiteta, ali je on nepostojeci.");
            throw new Exception("Trazeni entitet nije pronadjen.");
        }
        restoranToUpdate.setDo(restoran.getDo());
        restoranToUpdate.setOd(restoran.getOd());
        restoranToUpdate.setJelo(restoran.getJelo());
        restoranToUpdate.setPice(restoran.getPice());
        restoranToUpdate.setRestoran(restoran.getRestoran());
        
        Ponuda updatedRestoran= ponudaRepository.save(restoranToUpdate);
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
