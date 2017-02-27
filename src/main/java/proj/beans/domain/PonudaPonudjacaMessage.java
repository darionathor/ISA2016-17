package proj.beans.domain;

import java.util.ArrayList;

public class PonudaPonudjacaMessage {
	private String idPonude;
	private ArrayList<ArrayList<String>> idArtikla;
	public String getIdPonude() {
		return idPonude;
	}
	public void setIdPonude(String idPonude) {
		this.idPonude = idPonude;
	}
	public ArrayList<ArrayList<String>> getIdArtikla() {
		return idArtikla;
	}
	public void setIdArtikla(ArrayList<ArrayList<String>> idArtikla) {
		this.idArtikla = idArtikla;
	}
}
