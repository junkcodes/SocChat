import java.net.*;
import java.io.*;
import java.util.Scanner;

public class SocketUdp {
    public static void main(String[] Str) throws IOException, InterruptedException {
        int client=0,server=0,end = 0;
        StringBuilder name = new StringBuilder();
        if(Str[2].equals("Client")){
            client = 1;
        }
        else if(Str[2].equals("Server")){
            server=1;
        }
        while(true){
            if(client == 1){
                DatagramSocket ds = new DatagramSocket();
                InetAddress ip = InetAddress.getByName(Str[0]);
                byte buf[] = null;
                buf = Str[3].getBytes();
                DatagramPacket DpSend = new DatagramPacket(buf, buf.length, ip, Integer.parseInt(Str[1]));
                ds.send(DpSend);
                System.out.print("Me : ");
                Scanner input = new Scanner(System.in);
                String mssg = input.nextLine();
                buf = mssg.getBytes();
                DpSend = new DatagramPacket(buf, buf.length, ip, Integer.parseInt(Str[1]));
                ds.send(DpSend);
                server = 1;
                client = 0;
                if(mssg.equals("Exit")){
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
                name = data(receive);
                DpReceive = new DatagramPacket(receive, receive.length);
                ds.receive(DpReceive);
                System.out.println(name+" : "+data(receive));
                server = 0;
                client = 1;
                if(data(receive).toString().equals("Exit")){
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
