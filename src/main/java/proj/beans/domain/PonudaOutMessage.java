package proj.beans.domain;

import java.util.ArrayList;

public class PonudaOutMessage {
	
	private String id;
	
	private String Od;
	private String Do;
	private ArrayList<Jelo> jelo;
	private ArrayList<Pice> pice;
	private boolean prihvacen;
	public PonudaOutMessage(Ponuda a, Restoran restorani) {
		if(a.getPrihvacenaPonuda()!=null)setPrihvacen(true);
		else setPrihvacen(false);
		id=a.getId();
		Od=a.getOd();
		Do=a.getDo();
		ponude=new ArrayList<PonudaPonudjaca>();
		restoran=restorani.getNaziv();
		jelo=new ArrayList<Jelo>();
		if(a.getJelo()!=null)
		for(String j :a.getJelo()){
			for(Jelo jelo:restorani.getJelovnik()){
				if(jelo.getId().equals(j)){
					this.jelo.add(jelo);
					break;
				}
			}
		}pice=new ArrayList<Pice>();
		if(a.getPice()!=null)
		for(String j :a.getPice()){
			for(Pice jelo:restorani.getKartaPica()){
				if(jelo.getId().equals(j)){
					this.pice.add(jelo);
					break;
				}
			}
		}
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOd() {
		return Od;
	}
	public void setOd(String od) {
		Od = od;
	}
	public String getDo() {
		return Do;
	}
	public void setDo(String do1) {
		Do = do1;
	}
	public ArrayList<Jelo> getJelo() {
		return jelo;
	}
	public void setJelo(ArrayList<Jelo> jelo) {
		this.jelo = jelo;
	}
	public ArrayList<Pice> getPice() {
		return pice;
	}
	public void setPice(ArrayList<Pice> pice) {
		this.pice = pice;
	}
	public String getRestoran() {
		return restoran;
	}
	public void setRestoran(String restoran) {
		this.restoran = restoran;
	}
	public ArrayList<PonudaPonudjaca> getPonude() {
		return ponude;
	}
	public void setPonude(ArrayList<PonudaPonudjaca> ponude) {
		this.ponude = ponude;
	}
	public boolean isPrihvacen() {
		return prihvacen;
	}
	public void setPrihvacen(boolean prihvacen) {
		this.prihvacen = prihvacen;
	}
	private String restoran;
	private ArrayList<PonudaPonudjaca> ponude;
}
