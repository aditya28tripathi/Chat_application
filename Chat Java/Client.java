import java.net.*;
import java.io.*; 

public class Client {

    Socket socket;

    BufferedReader br;
    PrintWriter out;
    

    public Client() {
        try 
        {
            System.out.println("Sending request to server");
socket = new Socket("127.0.0.1" , 7777);
System.out.println("Connection done");

 br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();
        } catch(Exception e)
        {

        }
    }

    public void startReading()
    {
        //thread-read krega

        Runnable r1 = () -> {

            System.out.println("Reader start");


            try{

            while(!socket.isClosed()){
               
                String msg = br.readLine();
                if(msg.equals("exit"))
                {
                    System.out.println("Server termininated the chat");
                   socket.close();
                    break;
                }

                System.out.println("Server : "+msg);
            
            }
        } catch(Exception e){
            System.out.println("connection  is closed");
        }



        };
        new Thread(r1).start();
    }

    public void startWriting()
    {

        //thread-data usser se lega nd send to client

        Runnable r2 = () -> {
            System.out.println("Writer started");

            try{
 while(true){ 
            

                BufferedReader br1  = new BufferedReader(new InputStreamReader(System.in));
                String content = br1.readLine();

                out.println(content);
                out.flush();


                if(content.equals("exit"))
                {
                    socket.close();
                    break;
                }

            
        }

        System.out.println("connection  is closed");
        
    } catch(Exception e){
        e.printStackTrace();
    }



        };

        new Thread(r2).start();

    }


    public static void main(String[] args) {
        System.out.println("client");
        new Client(); 
    }
    
}
