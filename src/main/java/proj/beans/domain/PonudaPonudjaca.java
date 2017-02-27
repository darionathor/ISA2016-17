package proj.beans.domain;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;

public class PonudaPonudjaca {
	@Id
	private String id;
	
	private String Ponudjac;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPonudjac() {
		return Ponudjac;
	}
	public void setPonudjac(String ponudjac) {
		Ponudjac = ponudjac;
	}
	public String getPonuda() {
		return Ponuda;
	}
	public void setPonuda(String ponuda) {
		Ponuda = ponuda;
	}
	public ArrayList<PonudaJela> getpJela() {
		return pJela;
	}
	public void setpJela(ArrayList<PonudaJela> pJela) {
		this.pJela = pJela;
	}
	public ArrayList<PonudaPica> getpPice() {
		return pPice;
	}
	public void setpPice(ArrayList<PonudaPica> pPice) {
		this.pPice = pPice;
	}
	private String Ponuda;
	private ArrayList<PonudaJela> pJela;
	private ArrayList<PonudaPica> pPice;
}
