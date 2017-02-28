package proj.beans.domain;

import java.util.ArrayList;

public class Raspored {
	private ArrayList<Sto> stolovi;
	private ArrayList<Segment> segmenti;
	public ArrayList<Sto> getStolovi() {
		return stolovi;
	}
	public void setStolovi(ArrayList<Sto> stolovi) {
		this.stolovi = stolovi;
	}
	public ArrayList<Segment> getSegmenti() {
		return segmenti;
	}
	public void setSegmenti(ArrayList<Segment> segmenti) {
		this.segmenti = segmenti;
	}
}
