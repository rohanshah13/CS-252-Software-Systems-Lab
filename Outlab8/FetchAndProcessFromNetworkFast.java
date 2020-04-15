import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/* add some more imports */
import java.lang.Thread;
import java.io.*;
import java.sql.*;
import java.util.*;

class MultithreadingDemo extends Thread 
{ 
    public static Map<String, String> datax;
    String url;
    MultithreadingDemo(String p){
        url=p;
    }

    public void run() 
    { 
        try
        {  
            datax=new HashMap<String,String>();
            //System.out.println(url);
            URL u =new URL(url);
            URLConnection x = u.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(x.getInputStream()));
            String pokemon;
            while((pokemon = in.readLine()) != null){
                if(!pokemon.isBlank()){
                    //System.out.println(pokemon);
                    if(datax.containsKey(pokemon)){
                        String old =datax.get(pokemon);
                        String modified = old + ","+url;
       
                        datax.replace(pokemon,modified);
                    }
                    else{
                        datax.put(pokemon,url);
                    }
                }
            }
  
        } 
        catch (Exception e) 
        { 
            // Throwing an exception 
            //System.out.println (); 
        } 
    } 
}


public class FetchAndProcessFromNetworkFast implements FetchAndProcess {
    private Map<String, String> data;

    @Override
    public Map<String, String> exposeData() {
	return data;
    }

    @Override
    public void fetch(List<String> paths) {	
    // Implement here, just do it parallely!
        int n = paths.size(); 
        MultithreadingDemo obj[]= new MultithreadingDemo[n];// Number of threads 
        for (int i=0; i<n; i++) 
        { 
            MultithreadingDemo object = new MultithreadingDemo(paths.get(i)); 
            object.start();
            obj[i]=object;

        } 
        for(int i=0;i<n;i++){
            try{obj[i].join();}
            catch(Exception e){}
        }
        //try{Thread.sleep(4000);}
        //catch(Exception e){}
        data=MultithreadingDemo.datax;
        /*for(Map.Entry<String,String> entry: data.entrySet()){
            System.out.println(entry.getKey()+":"+entry.getValue());
        }*/
    }

    @Override
    public List<String> process() {
	// Implement here
	// Can you make use of the default implementation here?
	// See https://dzone.com/articles/interface-default-methods-java
        FetchAndProcess.super.process();
        Connection conn = null;
        String sql1="SELECT pokemon_name, COUNT(*) c FROM pokemon GROUP BY pokemon_name HAVING c > 1";
        String sql2="SELECT pokemon_name FROM pokemon WHERE source_path LIKE '%,%' ";
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
