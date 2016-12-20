package proj.beans.repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import proj.beans.domain.Restoran;

@Repository
public class InMemoryRestoranRepository  implements RestoranRepository {
	private static AtomicLong counter = new AtomicLong();

	private final ConcurrentMap<Long, Restoran> restorani= new ConcurrentHashMap<Long, Restoran>();

	@Override
	public Collection<Restoran> findAll() {
		// TODO Auto-generated method stub
		return this.restorani.values();
	}

	@Override
	public Restoran create(Restoran restoran) {
		// TODO Auto-generated method stub
		Long id = restoran.getId();
		if (id == null) {
			id = counter.incrementAndGet();
			restoran.setId(id);
		}
		this.restorani.put(id, restoran);
		return restoran;
	}

	@Override
	public Restoran findOne(Long id) {
		// TODO Auto-generated method stub
		return this.restorani.get(id);
	}

	@Override
	public Restoran update(Restoran restoran) {
		// TODO Auto-generated method stub
		Long id = restoran.getId();
		if (id != null) {
			this.restorani.put(id, restoran);
		}
		return restoran;
}
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		this.restorani.remove(id);
	}

}
