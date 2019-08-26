import java.net.*;
import java.io.*;
import java.util.Scanner;

public class SocketChat {
    static String mssg;
    public static void setMssg(String data){
        mssg = data;
    }
    public static void main(String[] Str) throws IOException {
        if(Str.length == 2){
            ServerSocket serversoc = new ServerSocket(Integer.parseInt(Str[1]));
            serversoc.setSoTimeout(10000);
            Socket server = serversoc.accept();
            DataInputStream mssgin = new DataInputStream(server.getInputStream());
            System.out.println(mssgin.readUTF()+" Just Connected");
            DataOutputStream mssgout = new DataOutputStream(server.getOutputStream());
            mssgout.writeUTF(Str[0]);
            Thread input = new Thread(new Runnable() {
                Scanner input = new Scanner(System.in);
                public void run() {
                    while (true) {
                        setMssg(input.nextLine());
                    }
                }
            });
            Thread inputmssg = new Thread(new Runnable() {
                DataInputStream mssgin = new DataInputStream(server.getInputStream());
                public void run() {
                    while (true) {
                        try {
                            String out = mssgin.readUTF();
                            System.out.println(out);
                            if(out.equals("Exit")){
                                server.close();
                                System.exit(0);
                            }
                        } catch (IOException e) {
                            System.exit(0);
                        }
                    }
                }
            });
            input.start();
            inputmssg.start();
            while(true) {
                mssgout = new DataOutputStream(server.getOutputStream());
                if(mssg != null){
                    mssgout.writeUTF(Str[0]+" : "+mssg);
                    if(mssg.equals("Exit")){
                        server.close();
                        System.exit(0);
                    }
                    setMssg(null);
                }
            }
        }
        else if(Str.length == 3){
            Socket client = new Socket(Str[1],Integer.parseInt(Str[2]));
            DataOutputStream mssgout = new DataOutputStream(client.getOutputStream());
            mssgout.writeUTF(Str[0]);
            DataInputStream mssgin = new DataInputStream(client.getInputStream());
            System.out.println("Just Connected with "+mssgin.readUTF());
            Thread input = new Thread(new Runnable() {
                Scanner input = new Scanner(System.in);
                public void run() {
                    while (true) {
                        setMssg(input.nextLine());
                    }
                }
            });
            Thread inputmssg = new Thread(new Runnable() {
                DataInputStream mssgin = new DataInputStream(client.getInputStream());
                public void run() {
                    while (true) {
                        try {
                            String out = mssgin.readUTF();
                            System.out.println(out);
                            if(out.equals("Exit")){
                                client.close();
                                System.exit(0);
                            }
                        } catch (IOException e) {
                            System.exit(0);
                        }
                    }
                }
            });
            input.start();
            inputmssg.start();
            while(true) {
                mssgout = new DataOutputStream(client.getOutputStream());
                if(mssg != null){
                    mssgout.writeUTF(Str[0]+" : "+mssg);
                    if(mssg.equals("Exit")){
                        client.close();
                        System.exit(0);
                    }
                    mssg = null;
                }
            }
        }
        else{
            System.out.println("Usage :\nTo Connect :\nSocketChat yourname ip port\nTo Host :\nSocketChat yourname");
        }
    }
}

