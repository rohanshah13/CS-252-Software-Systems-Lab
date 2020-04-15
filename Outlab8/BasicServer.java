import java.net.*; 
import java.io.*;
import java.util.*;
  
public class BasicServer 
{ 
    //initialize socket and input stream 
    private Socket          socket   = null; 
    private ServerSocket    server   = null; 
    private DataInputStream in       =  null;
    private DataOutputStream out = null;
  
    // constructor with port 
    public BasicServer(int port) 
    { 

	Map< Integer,Integer> hm = new HashMap< Integer,Integer>();
        // starts server and waits for a connection 
        try
        { 
            server = new ServerSocket(port); 
  
            System.out.println("Listening on 5000");
  
            while(true){
		
		socket = server.accept();
  
            // takes input from the client socket 
            in = new DataInputStream(socket.getInputStream()); 

	    out = new DataOutputStream(socket.getOutputStream()); 
  
            String line = "";
	    
  
            // reads message from client until "disconnect" is sent 
            while (!line.equals("disconnect")) 
            { 
                try
                { 
                    line = in.readUTF();
	   	    if( line.substring(0,3).equals("add") )
			{
				Integer num = Integer.parseInt( line.substring(4) );
				if( !hm.containsKey(num) )
				{
					hm.put(num,1);
				}
				else
				{
					hm.put(num, hm.get(num) + 1);
				}
				System.out.println("ADD "+num);
				out.writeUTF(String.valueOf( hm.get(num) ) );
			}
		    else if( line.substring(0,4).equals("read") )
			{
				Integer num = Integer.parseInt( line.substring(5) );
				if( hm.containsKey(num) )
				{System.out.println("READ "+num+" "+hm.get(num)); 
				out.writeUTF(String.valueOf( hm.get(num) ) );}
				else{ System.out.println("READ "+num+" 0"); out.writeUTF("0"); }
			} }
                catch(IOException i) 
                { 
                    System.out.println(i); 
                } 
            } 
            System.out.println("DIS"); 
  
            // close connection 
	    socket.close(); 
            in.close(); }
        } 
        catch(IOException i) 
        {
        } 
    } 
  
    public static void main(String args[]) 
    { 
        BasicServer server = new BasicServer(5000); 
    } 
} 
