package group.chatting.application;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;

public class UserOne implements ActionListener , Runnable {
    
    JTextField text;
    JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    //java da pencere oluşturmak için kullanılan bir sınıf
    static DataOutputStream dout;
    
    BufferedReader reader;
    BufferedWriter writer;
    String name = "Melike Bayam";
    
    UserOne() {
        
        f.setLayout(null);
        //manuel olarak büyüklük vs bilgilerini gireceğimizi bildirir
        
        JPanel p1 = new JPanel();
        //panel oluşturduk
        p1.setBackground(new Color(0, 0, 128));
        p1.setBounds(0, 0, 450, 70);
        //üst sınır boyutunu ve rengini belirledik 
        p1.setLayout(null);
        f.add(p1);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5, 20, 25, 25);
        p1.add(back);
        //ekranda görülecek ok işaretini oluşturduk
        
        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
                //mouseta oka basarak çıkmak için yazdık
            }
        });
        
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/pp.png"));
        Image i5 = i4.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40, 5, 60, 60);
        p1.add(profile);
        //bu kod satırlarında profil resmi ekleme, boyut belirleme yaptık
        
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300, 20, 30, 30);
        p1.add(video);
        //bu kod satırlarında video kamera simgesi ekledik, boyutu biçimlendirdik

        
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(360, 20, 35, 30);
        p1.add(phone);
        //bu kod satırlarında telefon simgesi ve özellikleri belirlendi

        
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel morevert = new JLabel(i15);
        morevert.setBounds(420, 20, 10, 25);
        p1.add(morevert);
        //more kısmının özelliklerini belirttik
        
        JLabel name = new JLabel("Group Chat");
        name.setBounds(110, 15, 100, 18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 14));
        p1.add(name);
        //kullanıcı-server a isim verdik ve punto, renk özellikleri seçtik

        
        JLabel status = new JLabel("Siz, Esra Ballı, Ebrar Erin");
        status.setBounds(110, 35, 160, 18);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF", Font.BOLD, 14));
        p1.add(status);
        //grup bilgisiyle ilgili düzenlemeler yaptık
        
        a1 = new JPanel();
        a1.setBounds(5, 75, 440, 570);
        f.add(a1);
        //mesajlaşma panelinin ölçüleri oluşturuldu
        
        text = new JTextField();
        text.setBounds(5, 655, 310, 40);
        text.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f.add(text);
        //burada mesaj yazılacak yazı ekran panelini oluşturduk
        
        JButton send = new JButton("Gonder");
        send.setBounds(320, 655, 123, 40);
        send.setBackground(new Color(0, 0, 128));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f.add(send);
        //send butonu oluşturduk ve boyut belirledik
        
        f.setSize(450, 700);
        //dış çerçeve ölçülerini belirledik 
        f.setLocation(20, 50);
        //çerçevenin ekranın neresinde açılacağını belirledik
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE);
        //arkaplan rengini seçtik
        
        f.setVisible(true);
        
        try {
            Socket socket = new Socket("localhost", 2003);
            //sunucuyla bağlantı için port verdik
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public void actionPerformed(ActionEvent ae) {
        try {
            String out = "<html><p>" + name + "</p><p>" + text.getText() + "</p></html>" ;
            //mesaj formatını ayarladık
            JPanel p2 = formatLabel(out);

            a1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));
            //gönderilen mesajın sağda olmasını ayarladık
            a1.add(vertical, BorderLayout.PAGE_START);

            try {
                writer.write(out);
                writer.write("\r\n");
                writer.flush();
                //bufferedwriter içindeki verilerin direkt yazılması için kullandık
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            text.setText("");

            f.repaint();
            //görüntüdeki değişimlerin güncellenmesi için yazdık
            f.invalidate();
            f.validate();  
            //genel olarak bu 3 satır arayüzde değişiklik yapıldığında bunları güncellemek için kullanılır
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static JPanel formatLabel(String out) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + out + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(173, 216, 230));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(0, 15, 15, 50));
        
        panel.add(output);
        //gönderilen mesajın ekrana basılması,renk boyut bilgisi hakkında
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        
        panel.add(time);
        //gönderilen mesaja saat bilgisi eklendi
        return panel;
    }
    
    public void run(){
        try {
            String msg = "";
            while(true) {
                //mesaj okundu
                msg = reader.readLine();
                if (msg.contains(name)) {
                    continue;
                }
                
                JPanel panel = formatLabel(msg);
                
                JPanel left = new JPanel(new BorderLayout());
                left.setBackground(Color.WHITE);
                left.add(panel, BorderLayout.LINE_START);
                vertical.add(left);
                
                a1.add(vertical, BorderLayout.PAGE_START);
                
                f.repaint();
                f.invalidate();
                f.validate();   
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public static void main(String[] args) {
        UserOne one = new UserOne();
        Thread t1 = new Thread(one);
        t1.start();
    }
}




