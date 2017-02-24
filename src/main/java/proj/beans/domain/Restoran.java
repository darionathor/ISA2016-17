package proj.beans.domain;

public class Restoran {
	private String Naziv;
	private String Vrsta;
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
}
