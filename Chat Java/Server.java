import java.net.*;
import java.io.*;


class Server
{

    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;
    
    //Constructor


    public Server()
    {
   
        try{
            server = new ServerSocket(7777);
            System.out.println("Server is ready to accept connection");
            System.out.println("Waiting....");
            socket = server.accept();

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();
        }

        catch(Exception e){
            e.printStackTrace();
        }


    }

    public void startReading()
    {
        //thread-read krega

        Runnable r1 = () -> {

            System.out.println("Reader start");

            try{

            

            while(true && !socket.isClosed()){
                
                String msg = br.readLine();
                if(msg.equals("exit"))
                {
                    System.out.println("Client termininated the chat");
                    socket.close();
                    break;
                }

                System.out.println("Clent : "+msg);
             
            }
        }catch(Exception e){
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

                if(content.equals("exit")) {
                    socket.close();
                    break;
                }
 }
 } catch(Exception e){
    
    System.out.println("connection  is closed");
 }
 System.out.println("connection  is closed");

            
        



        };

        new Thread(r2).start();

    }


    public static void main(String[] args) {
        System.out.println("Server");

        new Server();
    }
}