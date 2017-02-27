package proj.beans.domain;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;

public class Ponuda {
	@Id
	private String id;
	
	private String Od;
	private String Do;
	private ArrayList<String> jelo;
	private ArrayList<String> pice;
	private ArrayList<String> ponudaPonudjaca;
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
	public ArrayList<String> getJelo() {
		return jelo;
	}
	public void setJelo(ArrayList<String> jelo) {
		this.jelo = jelo;
	}
	public ArrayList<String> getPice() {
		return pice;
	}
	public void setPice(ArrayList<String> pice) {
		this.pice = pice;
	}
	public String getRestoran() {
		return restoran;
	}
	public void setRestoran(String restoran) {
		this.restoran = restoran;
	}
	public ArrayList<String> getPonudaPonudjaca() {
		return ponudaPonudjaca;
	}
	public void setPonudaPonudjaca(ArrayList<String> ponudaPonudjaca) {
		this.ponudaPonudjaca = ponudaPonudjaca;
	}
	private String restoran;
}
