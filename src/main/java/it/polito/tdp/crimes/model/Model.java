package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	Graph<String, DefaultWeightedEdge> grafo;
	List<EdgeAndWeight> overAvarage;
	int nMax;
	List<String> listaRisRicorsione;
	
	public String doCreaGrafo(int m, String catID) {
		EventsDao dao = new EventsDao();
		List<String> vertici = new ArrayList<>(dao.getVertex(m, catID));
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, vertici);
		List<EdgeAndWeight> edge = new ArrayList<>(dao.getArchiPeso(m, catID));
		double avg = 0.0;
		Collections.sort(edge);
		for(EdgeAndWeight e: edge) {
			Graphs.addEdgeWithVertices(grafo, e.getOffenseID1(), e.getOffenseID2(), e.getPeso());
			avg+=e.getPeso();
		}
		avg=avg/edge.size();
		String result = "";
		if(this.grafo==null) {
			result ="Grafo non creato";
			return result;
		}
		overAvarage = new ArrayList<>();
		result = "Grafo creato con :\n# "+this.grafo.vertexSet().size()+" VERTICI\n# "+this.grafo.edgeSet().size()+" ARCHI\n";
		result += "\nGli archi cui il peso è maggiore rispetto alla media dei pesi degli archi presenti nel grafo sono: \n";
		for(EdgeAndWeight e: edge) {
			if(e.getPeso()>avg) {
				result+= e.getOffenseID1()+" | "+ e.getOffenseID2()+ "    PESO : "+e.getPeso()+"\n";
				overAvarage.add(e);
			}
		}
		return result;
		
	}
	
	public String doCalcolaPercorso(EdgeAndWeight arco) {
		this.nMax = 0;
		this.listaRisRicorsione = new ArrayList<>();
		String partenza = arco.getOffenseID1();
		String arrivo = arco.getOffenseID2();
		List<String> parziale = new ArrayList<>();
		parziale.add(partenza);
		cerca(0,parziale,arrivo);
		String result="";
		if(listaRisRicorsione.isEmpty()) {
			result+="Ricorsione non avvenuta!";
			return result;
		}
		result+="\nRicorsione avvenuta con successo!!!\n\nIl percoso che tocca il numero massimo di vertici e che inizia con "+ partenza+" e si conclude con "+ arrivo+" è il seguente :\n";
		for(String vertici:listaRisRicorsione)
			result+=vertici+"\n";
		return result;
		
		
	}
	
	public void cerca(int n, List<String> parziale, String arrivo) {
		if(parziale.get(n).equals(arrivo)) {
			if(n>nMax) {
				nMax=n;
				listaRisRicorsione = new ArrayList<>(parziale);
			}
		}else {
			String ultimo = parziale.get(n);
			for(String vicino: Graphs.neighborListOf(grafo, ultimo)) {
				if(!parziale.contains(vicino)) {
					parziale.add(vicino);
					cerca(n+1,parziale,arrivo);
					parziale.remove(parziale.size()-1);
				}
			}
		}
	}
	public List<EdgeAndWeight> getEdgeOverAvg(){
		return overAvarage;
	}
	
	public List<Integer> getMonth(){
		EventsDao dao = new EventsDao();
		return dao.listAllMonth();
	}
	public List<String> getCategoryID(){
		EventsDao dao = new EventsDao();
		return dao.listCategoryID();
	}
}
