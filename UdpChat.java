import java.net.*;
import java.io.*;
import java.util.Scanner;

public class UdpChat {
    public static void main(final String[] Str) throws IOException, InterruptedException {
        DatagramSocket ds = new DatagramSocket();
        InetAddress ip = InetAddress.getByName(Str[0]);
        Thread output = new Thread(new Runnable() {
            public void run() {
                DatagramSocket ds = null;
                try {
                    ds = new DatagramSocket(1234);
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                byte[] receive = new byte[65535];
                DatagramPacket DpReceive = null;
                while (true) {
                    DpReceive = new DatagramPacket(receive, receive.length);
                    try {
                        ds.receive(DpReceive);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String mssg = data(receive).toString();
                    String[] part = mssg.split(":");
                    if(!part[0].equals(Str[2])){
                        System.out.println(data(receive));
                        if (part.length == 2 && part[1].equals(" Exit")) {
                            ds.close();
                            ds.disconnect();
                            break;
                        }
                    }
                }
            }
        });
        output.start();
        byte buf[] = null;
        StringBuilder name = new StringBuilder();;
        name.append(Str[2]+": ");
        Scanner input = new Scanner(System.in);
        while(true) {
            System.out.print(Str[2] +": ");
            String mssg = input.nextLine();
            mssg = name + mssg;
            buf = mssg.getBytes();
            DatagramPacket DpSend = new DatagramPacket(buf, buf.length, ip, Integer.parseInt(Str[1]));
            ds.send(DpSend);
            if (mssg.equals("Exit!")) {
                break;
            }
        }

    }

    public static StringBuilder data(byte[] a) {
        if (a == null)
            return null;
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while (a[i] != 0) {
            ret.append((char) a[i]);
            i++;
        }
        return ret;
    }
}