package proj.beans.domain;

import java.util.Date;
import java.util.ArrayList;

import org.springframework.data.annotation.Id;

public class Poseta {
	@Id
	private String id;
	private String user;
	private Date datum;
	private ArrayList<String> narucenaJela;
	private ArrayList<String> narucenaPica;
	private String restoran;
	private String sto;
	private Ocena ocena;
	private String konobar;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public Date getDatum() {
		return datum;
	}
	public void setDatum(Date datum) {
		this.datum = datum;
	}
	public ArrayList<String> getNarucenaJela() {
		return narucenaJela;
	}
	public void setNarucenaJela(ArrayList<String> narucenaJela) {
		this.narucenaJela = narucenaJela;
	}
	public ArrayList<String> getNarucenaPica() {
		return narucenaPica;
	}
	public void setNarucenaPica(ArrayList<String> narucenaPica) {
		this.narucenaPica = narucenaPica;
	}
	public String getRestoran() {
		return restoran;
	}
	public void setRestoran(String restoran) {
		this.restoran = restoran;
	}
	public String getSto() {
		return sto;
	}
	public void setSto(String sto) {
		this.sto = sto;
	}
	public Ocena getOcena() {
		return ocena;
	}
	public void setOcena(Ocena ocena) {
		this.ocena = ocena;
	}
	public String getKonobar() {
		return konobar;
	}
	public void setKonobar(String konobar) {
		this.konobar = konobar;
	}
}
