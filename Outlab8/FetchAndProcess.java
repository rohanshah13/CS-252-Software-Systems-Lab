import java.util.List;
import java.util.Map;

import java.sql.*;

public interface FetchAndProcess {
    static String DB_NAME = "pokemon.db";
    static String TABLE_NAME = "pokemon";

    /* The map populated by fetch */
    // public Map<String, String> data = new HashMap<String, String>();
    
    // no default implementation
    void fetch(List<String> paths);

    // no default implementation
    Map<String, String> exposeData();
    
    /* Provides a default implementation that does a lot of work:
     * 1. Create the `TABLE_NAME` table if it does not exist (along with a uniqueness constraint).
     * 2. Inserts data into the table, safely. ensuring no duplication.
     * 3. Returns the Connection (useful for the FetchAndProcessNetwork* classes)
     */
    default List<String> process() {
	// you can use exposeData() here.
        Connection conn = null;
        String sql = "CREATE TABLE IF NOT EXISTS pokemon (\n" 
           +" pokemon_name TEXT,\n"
           +"source_path TEXT,\n"
           +"UNIQUE(pokemon_name,source_path));";
        Map<String,String> data =exposeData();
        String sql1="INSERT OR REPLACE INTO pokemon (pokemon_name,source_path) VALUES(?,?)";        

        
        try
            {
            conn = DriverManager.getConnection("jdbc:sqlite:pokemon.db");
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            

            
            for(Map.Entry<String,String> entry : data.entrySet()){
                String value=entry.getValue();
                String arr[]=value.split(",");
                for(int i=0;i<arr.length;i++){
                    PreparedStatement pstmt = conn.prepareStatement(sql1);
                    pstmt.setString(1, entry.getKey());
                    pstmt.setString(2, arr[i]);
                    //pstmt.setString(3, entry.getKey());
                    //pstmt.setString(4, entry.getValue());
                    //pstmt.setString(4, entry.getValue());
                    //pstmt.setString(5, entry.getKey());
                    pstmt.executeUpdate();
                }

            }
        }
        catch(SQLException e){
            //System.out.println(e.getMessage());
        }



	return null;
    }
}
