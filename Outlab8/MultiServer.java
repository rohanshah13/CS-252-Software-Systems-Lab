import java.util.*;
import java.net.*;
import java.io.*;
import java.util.concurrent.ExecutorService; 
import java.util.concurrent.Executors; 

public class MultiServer{

	public static Map< Integer,Integer> hm = new HashMap< Integer,Integer>();

	static class RunnableImpl implements Runnable { 

	Socket socket = null;

	RunnableImpl(Socket socket){
		this.socket = socket;
	}
  
        public void run() 
        { 

	synchronized(this)
	{
		try{
		DataInputStream in = new DataInputStream(this.socket.getInputStream());
		DataOutputStream out = new DataOutputStream(this.socket.getOutputStream());

		String line = "";

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
                    break;
                } 
            } 
            System.out.println("DIS"); 

            this.socket.close();
            in.close();
        }
        catch(Exception e) {}
    }} 
}
    public static void main(String[] args)
    {
    	try{
    	ServerSocket server = new ServerSocket(5000);

    	System.out.println("Listening on 5000");

    	ExecutorService pool = Executors.newFixedThreadPool(100);

    	while(true)
    	{
    		Socket socket=server.accept();
    		
    		pool.execute(new RunnableImpl(socket));
    	}
    	}
    	catch(Exception e){}
    }
 }
