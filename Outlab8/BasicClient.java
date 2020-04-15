import java.net.*; 
import java.io.*;
import java.util.*; 
  
public class BasicClient 
{  
    private Socket socket            = null;
    private DataInputStream  in   = null; 
    private DataOutputStream out     = null; 

    public BasicClient(String filename) 
    { 
	Map< Integer,Integer> m = new HashMap< Integer,Integer>();
	String line = "";
	int sum = 0;

	File file = new File(filename);
	Scanner sc = null;
	try
	{ 
    	sc = new Scanner(file);
	}
	catch(FileNotFoundException e){}

	if(sc.hasNextLine())
	{
		line = sc.nextLine();
	}

	String[] arrOfStr = line.split(" ");

	String address = arrOfStr[1]; //problem here
	int port = Integer.parseInt( arrOfStr[2] );
	int connected = 0;

	try{
	    socket = new Socket(address, port);
            System.out.println("OK");
		connected = 1;

            out    = new DataOutputStream(socket.getOutputStream());
	    in    = new DataInputStream(socket.getInputStream());
	     }

	catch(IOException e){System.out.println("No Server");}

	while(sc.hasNextLine() && connected==1){

	if(sc.hasNextLine())
	{
		line = sc.nextLine();

	}
	else
	{
		break;
	}
	
        while (!line.equals("disconnect")) 
        {
	try
	{
		if(line.substring(0,3).equals("add"))
		{
			out.writeUTF(line);
			int num = Integer.parseInt( line.substring(4) );
			m.put( num , Integer.parseInt( in.readUTF() ) );
		}
		else if(line.substring(0,4).equals("read"))
		{
			out.writeUTF(line);
			int num = Integer.parseInt( line.substring(5) );
			int weight = Integer.parseInt( in.readUTF() );
			int delta=0;
			if(m.containsKey(num)){
			delta = weight - m.get(num) ;}
			else{ delta = weight; }
			sum += delta;
			System.out.println(weight+" "+delta);	
		}
		else if(line.substring(0,5).equals("sleep"))
		{
			try
			{
				int num = Integer.parseInt( line.substring(6) );
				Thread.sleep(num);
			}
			catch(InterruptedException e){} }

		if(sc.hasNextLine())
		{
		line = sc.nextLine();  
		}
		else
		{
			break;
		} 
	} // try closes    
	
	catch(IOException i){}

        } //while closes

	try
	{
	out.writeUTF(line);
	System.out.println("OK");
	}
	catch(IOException i){}

	if(sc.hasNextLine())
	{
		line=sc.nextLine();
	}
	else
	{
		System.out.println(sum);
		break;
	}

	int sleep_and_connect = 0;

	if(line.length() > 5)
	{
		if(line.substring(0,5).equals("sleep"))
		{
			try
			{
				int num = Integer.parseInt( line.substring(6) );
				Thread.sleep(num);
			}
			catch(InterruptedException e){}

			if(sc.hasNextLine())
			{
			line = sc.nextLine();
			}
			else
			{
				System.out.println(sum);
				break;
			}
			
			String[] newarr = line.split(" ");

			if(newarr[0].equals("connect") && newarr[1].equals(address) && Integer.parseInt( newarr[2] )==port)
			{
				sleep_and_connect = 1;
				try{
				socket = new Socket(address,port);
				out    = new DataOutputStream(socket.getOutputStream());
	    			in    = new DataInputStream(socket.getInputStream());}
				catch(IOException i){}
				System.out.println("OK");
			}
			else
			{
				System.out.println("No Server");
				break;
			}
		}
	}
		
	if( sleep_and_connect==0 && line.length() > 7)
	{

		String[] newarr = line.split(" ");

		if(newarr[0].equals("connect") && newarr[1].equals(address) && Integer.parseInt( newarr[2] )==port)
		{
			try{
			socket = new Socket(address,port);
			out    = new DataOutputStream(socket.getOutputStream());
	    		in    = new DataInputStream(socket.getInputStream());
			System.out.println("OK");}
			catch(UnknownHostException u){}
			catch(IOException e){}
		}
		else
		{
			System.out.println("No Server");
			break;
		}	
	}
  } //while closes
 	/*try{
            input.close(); 
            out.close(); 
            socket.close(); 
        } 
        catch(IOException i) 
        { 
        } */
        
    } 
  
    public static void main(String args[]) 
    { 
        BasicClient client = new BasicClient(args[0]); 
    } 
} 
