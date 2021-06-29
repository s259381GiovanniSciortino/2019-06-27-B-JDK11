package it.polito.tdp.crimes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import it.polito.tdp.crimes.model.Event;
import it.polito.tdp.crimes.model.EdgeAndWeight;


public class EventsDao {
	
	public List<EdgeAndWeight> getArchiPeso(int month, String catID){
		String sql="SELECT e1.offense_type_id as id1,e2.offense_type_id as id2, count(distinct e1.neighborhood_id) as peso "
				+ "from events e1, events e2 "
				+ "where month(e1.reported_date)=? "
				+ "and e1.offense_category_id=? "
				+ "and month(e2.reported_date)=? "
				+ "and e2.offense_category_id=? "
				+ "and e1.offense_type_id>e2.offense_type_id "
				+ "and e1.neighborhood_id=e2.neighborhood_id "
				+ "group by e1.offense_type_id,e2.offense_type_id";
		List<EdgeAndWeight> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, month);
			st.setString(2, catID);
			st.setInt(3, month);
			st.setString(4, catID);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				EdgeAndWeight e= new EdgeAndWeight(res.getString("id1"), res.getString("id2"),res.getInt("peso"));
				result.add(e);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	public List<String> getVertex(int month, String catID){
		String sql="SELECT distinct offense_type_id as id "
				+ "from events "
				+ "where month(reported_date)=? "
				+ "and offense_category_id=? ";
		List<String> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, month);
			st.setString(2, catID);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				result.add(res.getString("id"));
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Integer> listAllMonth(){
		String sql="SELECT distinct month(reported_date) as m "
				+ "from events "
				+ "order by month(reported_date) asc ";
		List<Integer> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				result.add(res.getInt("m"));
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<String> listCategoryID(){
		String sql="SELECT distinct offense_category_id as id "
				+ "from events";
		List<String> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				result.add(res.getString("id"));
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Event> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

}
