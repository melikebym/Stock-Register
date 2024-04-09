package group.chatting.application;

import java.net.*;
import java.io.*;
import java.util.*;

public class Server implements Runnable {
    
    Socket socket;
    
    public static Vector client = new Vector();
    //vektor bağlı olan clientleri tutmak için
    public Server (Socket socket) {
        try {
            this.socket = socket;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //istisnai durumlarda bu istisnayı ekrana yazdırmayı sağlar
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //gelen verileri okumak için
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            //socket üzerinden veri göndermek içn
            
            client.add(writer);
            //bufferedwriter nesnesini vektöre (client) eklemek için
            
            while(true) { //sürekli olarak clientleri dinler
                String data = reader.readLine().trim();
                System.out.println("Received " + data);
                //clientten gelen mesajı okumak için
                
                for (int i = 0; i < client.size(); i++) {
                    try {
                        BufferedWriter bw = (BufferedWriter) client.get(i);
                        bw.write(data);
                        bw.write("\r\n");
                        //bu kod topluluğu bufferedwriter nesnesine kodu yazmak için
                        bw.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // main metod sunucuyu başlatır ve gelen bağlantıları dinler
    public static void main(String[] args) throws Exception {
        ServerSocket s = new ServerSocket(2003);
        //ServerSocket oluşturulur
        while(true) {
            Socket socket = s.accept();
            //yeni bağlantı kabul edilir
            Server server = new Server(socket);
            //her bağlantı için yeni server nesnesi oluşur
            Thread thread = new Thread(server);
            thread.start();
            //her bağlantı için thread oluşur ve başlatılır
        }
    }
}