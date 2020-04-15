import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import java.util.Scanner;
import java.io.*;
import java.sql.*;
import java.util.*;

public class FetchAndProcessFromNetwork implements FetchAndProcess {
    private Map<String, String> data;

    @Override
    public Map<String, String> exposeData() {
	return data;
    }

    @Override
    public void fetch(List<String> paths) {
    try{
        data=new HashMap<String,String>();
        for(String p:paths){
            //System.out.println(p);
            URL u =new URL(p);
            URLConnection x = u.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(x.getInputStream()));
            String pokemon;
            while((pokemon = in.readLine()) != null){
                if(!pokemon.isBlank()){
                    if(data.containsKey(pokemon)){
                        String old =data.get(pokemon);
                        String modified = old + ","+p;
       
                        data.replace(pokemon,modified);
                    }
                    else{
                        data.put(pokemon,p);
                    }
                }
            }
        }
    }
    catch(IOException e){}
	// Implement here
    }

    @Override
    public List<String> process() {
	// Implement here
	// Can you make use of the default implementation here?
	// See https://dzone.com/articles/interface-default-methods-java
        FetchAndProcess.super.process();
        Connection conn = null;
        String sql1="SELECT pokemon_name, COUNT(*) c FROM pokemon GROUP BY pokemon_name HAVING c > 1";
        try
            {
            HashSet<String> h = new HashSet<String>();
            conn = DriverManager.getConnection("jdbc:sqlite:pokemon.db");
            Statement stmt1 = conn.createStatement();
            ResultSet rs1=stmt1.executeQuery(sql1);
            while(rs1.next()){
                h.add(rs1.getString("pokemon_name"));
            }
            Iterator<String> i = h.iterator(); 
            while (i.hasNext()){ 
                System.out.println(i.next()); 
            }
        }
        catch(SQLException e){
            //System.out.println(e.getMessage());
        }
	    return null;
    }
}
