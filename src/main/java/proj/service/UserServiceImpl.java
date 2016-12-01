package proj.service;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proj.domain.User;
import proj.repository.InMemoryUserRepository;

@Service
public class UserServiceImpl implements UserService {

private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private InMemoryUserRepository userRepository;

	@Override
	public Collection<User> findAll() {
		// TODO Auto-generated method stub
		logger.info("> findAll");
        Collection<User> users = userRepository.findAll();
        logger.info("< findAll");
        return users;
	}

	@Override
	public User findOne(Long id) {
		// TODO Auto-generated method stub
		logger.info("> findOne id:{}", id);
		User user = userRepository.findOne(id);
        logger.info("< findOne id:{}", id);
        return user;
	}

	@Override
	public User create(User user) throws Exception {
		// TODO Auto-generated method stub
		logger.info("> create");
        if (user.getId() != null) {
            logger.error(
                    "Pokusaj kreiranja novog entiteta, ali Id nije null.");
            throw new Exception(
                    "Id mora biti null prilikom perzistencije novog entiteta.");
        }
        User savedUser = userRepository.create(user);
        logger.info("< create");
        return savedUser;
	}

	@Override
	public User update(User user) throws Exception {
		// TODO Auto-generated method stub
		logger.info("> update id:{}", user.getId());
		User userToUpdate = findOne(user.getId());
        if (userToUpdate == null) {
            logger.error(
                    "Pokusaj azuriranja entiteta, ali je on nepostojeci.");
            throw new Exception("Trazeni entitet nije pronadjen.");
        }
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setEmail(user.getEmail());
        
        User updatedUser = userRepository.create(userToUpdate);
        logger.info("< update id:{}", user.getId());
        return updatedUser;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		logger.info("> delete id:{}", id);
		userRepository.delete(id);
        logger.info("< delete id:{}", id);
	}

}
