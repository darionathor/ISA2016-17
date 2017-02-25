package proj.beans.domain;

import java.util.ArrayList;

public class Restoran {
	private String Naziv;
	private String Vrsta;
	private ArrayList<Jelo> Jelovnik;
	private ArrayList<Pice> KartaPica;
	public String getVrsta() {
		return Vrsta;
	}

	public void setVrsta(String vrsta) {
		Vrsta = vrsta;
	}

	private Long id;
	public String getNaziv() {
		return Naziv;
	}

	public void setNaziv(String naziv) {
		Naziv = naziv;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ArrayList<Jelo> getJelovnik() {
		return Jelovnik;
	}

	public void setJelovnik(ArrayList<Jelo> jelovnik) {
		Jelovnik = jelovnik;
	}

	public ArrayList<Pice> getKartaPica() {
		return KartaPica;
	}

	public void setKartaPica(ArrayList<Pice> kartaPica) {
		KartaPica = kartaPica;
	}
}
