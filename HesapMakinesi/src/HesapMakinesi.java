import java.awt.BorderLayout;

import java.awt.Button;

import java.awt.Color;

import java.awt.Font;

import java.awt.Frame;

import java.awt.GridLayout;

import java.awt.Label;

import java.awt.Panel;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.awt.event.WindowAdapter;

import java.awt.event.WindowEvent;

public class HesapMakinesi extends Frame implements ActionListener {

    Label     display;

    Button    onOff;

    Button[]  tuslar;

    Frame     parent;

    long oncekiSayi = 0;

    char operatie = '=';

    boolean yeniGirdi=true;

    Font bigFont = new Font("Arial",Font.PLAIN,24); //Tuslar ve ekranin g�r�n�m� i�in bir �font� tanimliyoruz


    public static void main(String[] arg) {

        new HesapMakinesi().setVisible(true);

    }

   

    public HesapMakinesi() {

        super("Hesap makinesi"); //Window  (pencere) basligi

        olusturGUI();

        startFlashing();

    }

   

    public void olusturGUI() {

        parent=this;

        display = new Label(" ",Label.RIGHT);

        display.setBackground(Color.yellow);

        display.setFont(bigFont);

       

        onOff = new Button("On");

        onOff.addActionListener(this);

        onOff.setFont(bigFont);

       

        Panel tusPaneli = new Panel(); //Bu paneli tuslarimisi yerlestirmek i�in kullanacagiz.

        tusPaneli.setLayout(new GridLayout(4,4));

        String[] isaretler = {"9","8","7", "/",

                           "6","5","4", "*",

                           "3","2","1", "-",

                           "0","C","=", "+" };

        tuslar = new Button[16];

        //�nce �isaretler� adinda bize lazim olan isaretleri iceren bir String dizi olusturduk.

      

        for (int b=0; b<16; b++) {

            tuslar[b]=new Button(isaretler[b]);

            tuslar[b].setFont(bigFont);

            tuslar[b].addActionListener(this);

            tusPaneli.add(tuslar[b]);

            }

        /*Tuslarimizi (on/of hari�) daha �nce hazirladigimiz �tusPaneli�ne for d�ng�s� yardimi ile tek tek          yerlestiriyor

          ve hepsini ActionListener�e bildiriyoruz. ActionListener aray�z� sayesinde tuslarimizin ne  yapmasi herektigini

          belirleyebilecegiz*/

              

        this.add(display,BorderLayout.NORTH);

        this.add(tusPaneli,BorderLayout.CENTER);

        this.add(onOff,BorderLayout.SOUTH);

        /*Ekranimizi (display), k���k tuslarimizi yapistirdigimiz tusPanel�imizi ve on/off tusumuzu container�e (this)

          ekliyoruz */

        this.setSize(250,250);

       

        this.addWindowListener(new WindowAdapter() {

            @Override

                    public void windowClosing(WindowEvent we){

                        System.exit(0);

                        }

                    }

                );

         // Bu kod par�asi ise penceremizi X butonu ile kapatabilmemizi saglar.

         

    }

   

    public void actionPerformed(ActionEvent evt) {

        /* ActionListener aray�z�nden �implemente� ettigimiz ve tuslarimizin hareketlerini denetleyip

         * verdigimiz kpmotlara g�re ne yapilmasi gerektiginin anlatildigi sinifimiz. */

        

        if ( onOff==evt.getSource() ) { //�rnegin burada anlatilmak istenen onOff tusuna basildiginda�

            doOnOff();

            return;

            }

        if (onOff.getLabel().equals("On")) /*onOff butonunun Label�i "On" ise..

                                                      *Dikkat! Java�da String ifadenin karsilastirilmasi == ile degil

                                                     * equals(); yordami ile yapilir. */

            return;

       

        char input = evt.getActionCommand().charAt(0); 

        System.out.println("input:"+input);

       

        if (input>='0' & input<='9' ) {

             if (display.equals("0") || yeniGirdi)

                display.setText(input+"");

             else

                display.setText(display.getText()+input);

             return;

            }

 

        if (input=='C') {

            oncekiSayi=0;

            operatie='=';

            yeniGirdi=true;

            display.setText("0");

            return;

            }  

       

        String tekst="0"+display.getText().trim();

        long sayi = Long.parseLong(tekst);

 

        hesapla(input,sayi);

        display.setText(oncekiSayi+"");

        }

     

    public void hesapla(char input, long sayi) {

        System.out.println("hesapla:"+input+"|"+sayi);

        switch (operatie) {

            case '=' : oncekiSayi= sayi; break;

            case '+' : oncekiSayi+=sayi; break;

            case '-' : oncekiSayi-=sayi; break;

            case '*' : oncekiSayi*=sayi; break;

            case '/' : oncekiSayi/=sayi; break;

            }          

        operatie=input;

        yeniGirdi=true;

        }

   

      

    public void doOnOff() {

        yeniGirdi=true;

        if ( onOff.getLabel().equals("On") ) {

            onOff.setLabel("Off");

            display.setBackground(Color.yellow);

            display.setText("0");

            return;

            }

        onOff.setLabel("On");

        display.setText(" ");

        startFlashing();

        }

 

public void startFlashing() {

    Runnable flash = new Runnable() {

        public void run() {

            boolean yellow=true;

            while(onOff.getLabel().equals("On")) {

                if (yellow) display.setBackground(Color.green);

                       else display.setBackground(Color.yellow);

                yellow=!yellow;

                try { Thread.sleep(600); } catch (Exception ex) { }  

                } // end while                   

            } // end run

        }; // end Runnable

      new Thread(flash).start();

     }

   

    }