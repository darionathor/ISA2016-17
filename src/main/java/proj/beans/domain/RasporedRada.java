package proj.beans.domain;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;

public class RasporedRada {
	@Id
	private String id;
	private String radnik;
	private String odVreme;
	private String doVreme;
	private String datum;
	private ArrayList<String> reoni;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRadnik() {
		return radnik;
	}
	public void setRadnik(String radnik) {
		this.radnik = radnik;
	}
	public String getOdVreme() {
		return odVreme;
	}
	public void setOdVreme(String odVreme) {
		this.odVreme = odVreme;
	}
	public String getDoVreme() {
		return doVreme;
	}
	public void setDoVreme(String doVreme) {
		this.doVreme = doVreme;
	}
	public String getDatum() {
		return datum;
	}
	public void setDatum(String datum) {
		this.datum = datum;
	}
	public ArrayList<String> getReoni() {
		return reoni;
	}
	public void setReoni(ArrayList<String> reoni) {
		this.reoni = reoni;
	}
}
