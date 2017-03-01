package proj.beans.domain;

public class OceneKonobaraMessage {
	private String ime;
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
	public String getOcena() {
		return ocena;
	}
	public void setOcena(String ocena) {
		this.ocena = ocena;
	}
	private String prezime;
	private String ocena;
	private String prihod;
	public String getPrihod() {
		return prihod;
	}
	public void setPrihod(String prihod) {
		this.prihod = prihod;
	}
}
