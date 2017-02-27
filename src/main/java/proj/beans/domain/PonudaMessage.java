package proj.beans.domain;

import java.util.ArrayList;

public class PonudaMessage {
	private String doDatuma;
	private String odDatuma;
	private ArrayList<String> jelo;
	private ArrayList<String> pice;
	public ArrayList<String> getJelo() {
		return jelo;
	}
	public void setJelo(ArrayList<String> jelo) {
		this.jelo = jelo;
	}
	public ArrayList<String> getPice() {
		return pice;
	}
	public void setPice(ArrayList<String> pice) {
		this.pice = pice;
	}
	public String getOdDatuma() {
		return odDatuma;
	}
	public void setOdDatuma(String odDatuma) {
		this.odDatuma = odDatuma;
	}
	public String getDoDatuma() {
		return doDatuma;
	}
	public void setDoDatuma(String doDatuma) {
		this.doDatuma = doDatuma;
	}
}
