package proj.beans.domain;

public class PonudaPica {

	private String idPica;
	private String kol;
	private String cena;
	public PonudaPica(String idPica, String kol, String cena) {
		// TODO Auto-generated constructor stub
		this.idPica=idPica;
		this.kol=kol;
		this.cena=cena;
	}
	public String getKol() {
		return kol;
	}
	public void setKol(String kol) {
		this.kol = kol;
	}
	public String getCena() {
		return cena;
	}
	public void setCena(String cena) {
		this.cena = cena;
	}
	public String getIdPica() {
		return idPica;
	}
	public void setIdPica(String idPica) {
		this.idPica = idPica;
	}
}
