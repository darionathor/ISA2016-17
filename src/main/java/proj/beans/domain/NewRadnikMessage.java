package proj.beans.domain;

public class NewRadnikMessage {
	private String username;
	private String email;
	private String ime;
	private String prezime;
	private String vrsta;
	private String datumRodjenja;
	private int konfekcijskiBroj;
	private int velicinaObuce;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	public String getVrsta() {
		return vrsta;
	}
	public void setVrsta(String vrsta) {
		this.vrsta = vrsta;
	}
	public String getDatumRodjenja() {
		return datumRodjenja;
	}
	public void setDatumRodjenja(String datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}
	public int getKonfekcijskiBroj() {
		return konfekcijskiBroj;
	}
	public void setKonfekcijskiBroj(int konfekcijskiBroj) {
		this.konfekcijskiBroj = konfekcijskiBroj;
	}
	public int getVelicinaObuce() {
		return velicinaObuce;
	}
	public void setVelicinaObuce(int velicinaObuce) {
		this.velicinaObuce = velicinaObuce;
	}
}
