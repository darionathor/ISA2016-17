package proj.beans.domain;

import java.util.ArrayList;

public class RasporedRadaMessage {
	private String datum;
	private String odTime;
	private String doTime;
	private String selekt;
	public String getDatum() {
		return datum;
	}
	public void setDatum(String datum) {
		this.datum = datum;
	}
	public String getOdTime() {
		return odTime;
	}
	public void setOdTime(String odTime) {
		this.odTime = odTime;
	}
	public String getDoTime() {
		return doTime;
	}
	public void setDoTime(String doTime) {
		this.doTime = doTime;
	}
	public String getSelekt() {
		return selekt;
	}
	public void setSelekt(String selekt) {
		this.selekt = selekt;
	}
	public ArrayList<String> getReon() {
		return reon;
	}
	public void setReon(ArrayList<String> reon) {
		this.reon = reon;
	}
	private ArrayList<String> reon;
}
