package it.polito.tdp.crimes.model;


public class EdgeAndWeight implements Comparable<EdgeAndWeight>{
	String offenseID1;
	String offenseID2;
	Integer peso;
	public EdgeAndWeight(String offenseID1, String offenseID2, Integer peso) {
		super();
		this.offenseID1 = offenseID1;
		this.offenseID2 = offenseID2;
		this.peso = peso;
	}
	public String getOffenseID1() {
		return offenseID1;
	}
	public void setOffenseID1(String offenseID1) {
		this.offenseID1 = offenseID1;
	}
	public String getOffenseID2() {
		return offenseID2;
	}
	public void setOffenseID2(String offenseID2) {
		this.offenseID2 = offenseID2;
	}
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	@Override
	public int compareTo(EdgeAndWeight other) {
		return other.getPeso().compareTo(this.getPeso());
	}
	@Override
	public String toString() {
		return offenseID1 + " - " + offenseID2 ;
	}
	
	
}


