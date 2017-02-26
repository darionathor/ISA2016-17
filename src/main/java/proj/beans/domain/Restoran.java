package proj.beans.domain;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;

public class Restoran {
	private String Naziv;
	private String Vrsta;
	private ArrayList<Jelo> Jelovnik;
	private ArrayList<Pice> KartaPica;
	private String Menadzer;
	private ArrayList<String> Radnici;
	
	public String getMenadzer() {
		return Menadzer;
	}

	public void setMenadzer(String menadzer) {
		Menadzer = menadzer;
	}

	public ArrayList<String> getRadnici() {
		return Radnici;
	}

	public void setRadnici(ArrayList<String> radnici) {
		Radnici = radnici;
	}

	public String getVrsta() {
		return Vrsta;
	}

	public void setVrsta(String vrsta) {
		Vrsta = vrsta;
	}
	@Id
	private String id;
	public String getNaziv() {
		return Naziv;
	}

	public void setNaziv(String naziv) {
		Naziv = naziv;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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
