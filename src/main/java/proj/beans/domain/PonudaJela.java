package proj.beans.domain;

public class PonudaJela {
	private String idJela;
	private String kol;
	private String cena;
	public PonudaJela(String idJela, String kol, String cena) {
		// TODO Auto-generated constructor stub
		this.idJela=idJela;
		this.kol=kol;
		this.cena=kol;
	}
	public String getIdJela() {
		return idJela;
	}
	public void setIdJela(String idJela) {
		this.idJela = idJela;
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
}
