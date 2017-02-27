package proj.beans.service;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proj.beans.domain.Ponuda;
import proj.beans.domain.PonudaPonudjaca;
import proj.beans.repository.MongoPonudaPonudjacaRepository;

@Service
public class PonudaPonudjacaServiceImpl implements PonudaPonudjacaService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
  //  private InMemoryRestoranRepository restoranRepository;
	private MongoPonudaPonudjacaRepository ponudaRepository;
	@Override
	public Collection<PonudaPonudjaca> findAll() {
		// TODO Auto-generated method stub
		logger.info("> findAll");
        Collection<PonudaPonudjaca> users = ponudaRepository.findAll();
        logger.info("< findAll");
        return users;
	}

	@Override
	public PonudaPonudjaca findOne(String id) {
		// TODO Auto-generated method stub
		logger.info("> findOne id:{}", id);
		PonudaPonudjaca user = ponudaRepository.findOne(id);
        logger.info("< findOne id:{}", id);
        return user;
	}

	@Override
	public PonudaPonudjaca create(PonudaPonudjaca restoran) throws Exception {
		// TODO Auto-generated method stub
		logger.info("> create");
        if (restoran.getId() != null) {
            logger.error(
                    "Pokusaj kreiranja novog entiteta, ali Id nije null.");
            throw new Exception(
                    "Id mora biti null prilikom perzistencije novog entiteta.");
        }
        PonudaPonudjaca savedrestoran = ponudaRepository.save(restoran);
        logger.info("< create");
        return savedrestoran;
	}

	@Override
	public PonudaPonudjaca update(PonudaPonudjaca restoran) throws Exception {
		// TODO Auto-generated method stub
		logger.info("> update id:{}", restoran.getId());
		PonudaPonudjaca restoranToUpdate = findOne(restoran.getId());
        if (restoranToUpdate == null) {
            logger.error(
                    "Pokusaj azuriranja entiteta, ali je on nepostojeci.");
            throw new Exception("Trazeni entitet nije pronadjen.");
        }
        restoranToUpdate.setPonuda(restoran.getPonuda());
        restoranToUpdate.setPonudjac(restoran.getPonudjac());
        restoranToUpdate.setpJela(restoran.getpJela());
        restoranToUpdate.setpPice(restoran.getpPice());
        
        PonudaPonudjaca updatedRestoran= ponudaRepository.save(restoranToUpdate);
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
