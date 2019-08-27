import java.net.*;
import java.io.*;
import java.util.Scanner;

public class SocketUdp {
    public static void main(String[] Str) throws IOException, InterruptedException {
        int client=0,server=0,end = 0;
        if(Str[2].equals("Client")){
            client = 1;
        }
        else if(Str[2].equals("Server")){
            server=1;
        }
        while(true){
            if(client == 1){
                if(end  == 1){
                    break;
                }
                DatagramSocket ds = new DatagramSocket();
                InetAddress ip = InetAddress.getByName(Str[0]);
                byte buf[] = null;
                System.out.print("Me : ");
                Scanner input = new Scanner(System.in);
                StringBuilder name = new StringBuilder();;
                name.append(Str[3]+" : ");
                String mssg = input.nextLine();
                if(mssg.equals("Exit")){
                    end = 1;
                }
                mssg = name + mssg;
                buf = mssg.getBytes();
                DatagramPacket DpSend = new DatagramPacket(buf, buf.length, ip, Integer.parseInt(Str[1]));
                ds.send(DpSend);
                server = 1;
                client = 0;
                if(end == 1){
                    break;
                }
                Thread.sleep(1000);
            }
            else if(server == 1){
                DatagramSocket ds = new DatagramSocket(Integer.parseInt(Str[1]));
                byte[] receive = new byte[65535];
                DatagramPacket DpReceive = null;
                DpReceive = new DatagramPacket(receive, receive.length);
                ds.receive(DpReceive);
                System.out.println(data(receive));
                server = 0;
                client = 1;
                if(end  == 1){
                    break;
                }
                ds.close();
                ds.disconnect();
            }

        }

    }
    public static StringBuilder data(byte[] a)
    {
        if (a == null)
            return null;
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while (a[i] != 0)
        {
            ret.append((char) a[i]);
            i++;
        }
        return ret;
    }
}
