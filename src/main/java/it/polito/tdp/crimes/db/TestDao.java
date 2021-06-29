package it.polito.tdp.crimes.db;

import it.polito.tdp.crimes.model.Event;

public class TestDao {

	public static void main(String[] args) {
		EventsDao dao = new EventsDao();
		
		System.out.println(dao.getVertex(12, "public-disorder").size());
	}

}
