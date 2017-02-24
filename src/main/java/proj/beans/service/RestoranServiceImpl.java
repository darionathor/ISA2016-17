package proj.beans.service;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proj.beans.domain.Restoran;
import proj.beans.domain.User;
import proj.beans.repository.InMemoryRestoranRepository;
import proj.beans.repository.InMemoryUserRepository;

@Service
public class RestoranServiceImpl implements RestoranService{
	

		private Logger logger = LoggerFactory.getLogger(this.getClass());
	
		@Autowired
	    private InMemoryRestoranRepository restoranRepository;

		@Override
		public Collection<Restoran> findAll() {
			// TODO Auto-generated method stub
			logger.info("> findAll");
	        Collection<Restoran> users = restoranRepository.findAll();
	        logger.info("< findAll");
	        return users;
		}

		@Override
		public Restoran findOne(Long id) {
			// TODO Auto-generated method stub
			logger.info("> findOne id:{}", id);
			Restoran user = restoranRepository.findOne(id);
	        logger.info("< findOne id:{}", id);
	        return user;
		}

		@Override
		public Restoran create(Restoran restoran) throws Exception {
			// TODO Auto-generated method stub
			logger.info("> create");
	        if (restoran.getId() != null) {
	            logger.error(
	                    "Pokusaj kreiranja novog entiteta, ali Id nije null.");
	            throw new Exception(
	                    "Id mora biti null prilikom perzistencije novog entiteta.");
	        }
	        Restoran savedrestoran = restoranRepository.create(restoran);
	        logger.info("< create");
	        return savedrestoran;
		}

		@Override
		public Restoran update(Restoran restoran) throws Exception {
			// TODO Auto-generated method stub
			logger.info("> update id:{}", restoran.getId());
			Restoran restoranToUpdate = findOne(restoran.getId());
	        if (restoranToUpdate == null) {
	            logger.error(
	                    "Pokusaj azuriranja entiteta, ali je on nepostojeci.");
	            throw new Exception("Trazeni entitet nije pronadjen.");
	        }
	        restoranToUpdate.setNaziv(restoran.getNaziv());
	        restoranToUpdate.setVrsta(restoran.getVrsta());
	        
	        Restoran updatedRestoran= restoranRepository.create(restoranToUpdate);
	        logger.info("< update id:{}", restoran.getId());
	        return updatedRestoran;
		}

		@Override
		public void delete(Long id) {
			// TODO Auto-generated method stub
			logger.info("> delete id:{}", id);
			restoranRepository.delete(id);
	        logger.info("< delete id:{}", id);
		}
}
