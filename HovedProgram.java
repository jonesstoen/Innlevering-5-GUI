import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HovedProgram{
    private static JFrame vindu;
    private static Timer timer;
    
     
    public static void main(String[]args){
        try {
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    
    }catch (Exception e ){System.exit(1);}

    vindu = new JFrame(" GAME OF LIFE GUI ");//oppretter et vindu
    vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    vindu.setBackground(Color.BLUE);
    JPanel velkommen = velkomstpanel();//oppretter et panel
    vindu.setLocationRelativeTo(null);
    vindu.add(velkommen);
    vindu.pack();
    vindu.setVisible(true);
    }
    
    private static JPanel velkomstpanel(){//oppretter velkomstpanel som skal vises før spillet starter
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));//formaterer panelet

        JLabel velkommen = new JLabel("Velkommen til GameOfLife"); 
        panel.add(velkommen);
        velkommen.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Legger til tekstfelter for å angi antall rader og kolonner
        JLabel raderLabel = new JLabel("Antall rader:");
        JTextField raderFelt = new JTextField(5);
        JLabel kolonnerLabel = new JLabel("Antall kolonner:");
        JTextField kolonnerFelt = new JTextField(5);

        JPanel inputPanel = new JPanel();
        inputPanel.add(raderLabel);
        inputPanel.add(raderFelt);
        inputPanel.add(kolonnerLabel);
        inputPanel.add(kolonnerFelt);
        panel.add(inputPanel);//legger til inputpanel
        JPanel knappPanel = new JPanel();
        JButton avslutttKnapp = new JButton("Avslutt");
        JButton startKnapp = new JButton("Hent Spillbrettet");
        startKnapp.setBackground(Color.GREEN);
        avslutttKnapp.setBackground(Color.RED);
        knappPanel.add(startKnapp);
        knappPanel.add(avslutttKnapp);
        //oppretter actionlisteners for knappene
        class AvsluttknappBehandler implements ActionListener{ 
            @Override
            public void actionPerformed(ActionEvent e ){
                System.exit(0);


            }
        }
        class StartKnappbehandler implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    // henter antall rader og kolonner fra tekstfeltene
                    int antallRader = Integer.parseInt(raderFelt.getText());//
                    int antallKolonner = Integer.parseInt(kolonnerFelt.getText());
                    startSpill(panel, antallRader, antallKolonner);
                } catch (NumberFormatException error) {
                    // viser feilmelding hvis brukeren skriver inn noe annet enn heltall
                    JOptionPane.showMessageDialog(vindu, "Kun Heltall Tilatt", "Feil", JOptionPane.ERROR_MESSAGE);
                }

            }
        }

        // legg til actionlistener
        startKnapp.addActionListener(new StartKnappbehandler());
        avslutttKnapp.addActionListener(new AvsluttknappBehandler());

        //legg knapper på panel
        panel.add(knappPanel);

        return panel;

    }
    //metode for å starte spillet og simuleringen
    private static void startSpill(JPanel panel, int antallRader, int antallKolonner){
        Verden verden = new Verden (antallRader, antallKolonner);
        verden.tegn();

        JPanel hovedPanel = new JPanel(new BorderLayout());
        
        JPanel spillPanel = new JPanel();
        JPanel toppMeny = new JPanel();
        toppMeny.setLayout(new FlowLayout(FlowLayout.CENTER));
        //vi må ha en teller for å vise antall levende celler
        JLabel levendeTeller = new JLabel("Antall Levende: "+ verden.nyttrutenett.antallLevende());
        toppMeny.add(levendeTeller);
        //oppretter et spillpanel som skal inneholde cellene
        spillPanel.setLayout(new GridLayout(verden.nyttrutenett.hentAntrader(), verden.nyttrutenett.hentAntkolonner()));
        //så må vi legge til JButtons fordi vi skal kunne interagere med spillbrettet. 
        for ( int rad = 0; rad< verden.nyttrutenett.hentAntrader(); rad++){

            for (int kol = 0; kol<verden.nyttrutenett.hentAntkolonner(); kol++){

                Celle celle = verden.nyttrutenett.hentRutene(rad,kol);
                JButton celleKnapp = new JButton(celle.hentStatusTegn()+"");
                if( celle.erLevende()==true){
                    celleKnapp.setBackground(Color.GREEN);
                }else{ celleKnapp.setBackground(Color.BLACK);}
                
                //må lage en action listener for celleKnapp
                class celleKnappBehandler implements ActionListener {
                    private JLabel levendeTeller;
                    //har levendetelleren som parameter slik at vi kan oppdatere telleren når vi endrer status på cellene.
                    public celleKnappBehandler(JLabel levendeTeller){
                        this.levendeTeller= levendeTeller;
                    }
                    @Override
                    public void actionPerformed(ActionEvent e){

                        JButton celleKnapp =(JButton) e.getSource();//henter knappen som ble trykket på
                        if( celle.erLevende()==true){
                            celle.settDoed();
                            celleKnapp.setText(celle.hentStatusTegn()+"");
                            celleKnapp.setBackground(Color.BLACK);
                            

                        }
                        else if (celle.erLevende()==false){
                            celle.settLevende();
                            celleKnapp.setText(celle.hentStatusTegn()+"");
                            celleKnapp.setBackground(Color.GREEN);
                            
                        }
                        levendeTeller.setText("Antall Levende: " + verden.nyttrutenett.antallLevende());//oppdaterer telleren
                    }

                }

                celleKnapp.addActionListener(new celleKnappBehandler(levendeTeller));//legger til actionlistener
                spillPanel.add(celleKnapp);

            }

        }
        //legger til knapper for å starte og avslutte simuleringen
        JButton startSimulering = new JButton("START");
        JButton avsluttSimulering = new JButton("AVSLUTT");
        class AvsluttSimuleringBehandler implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        }
        avsluttSimulering.addActionListener(new AvsluttSimuleringBehandler());
        class StartSimuleringBehandler implements ActionListener{
            
            //metode for å oppdatere spillet
            public void oppdaterspill(){
                //fjerner alle knappene fra spillpanelet og oppdaterer før vi legger inn nye celler
                 spillPanel.removeAll();


                verden.oppdatering();
                for (int rad = 0; rad< verden.nyttrutenett.hentAntrader(); rad++){

                    for (int kol = 0; kol<verden.nyttrutenett.hentAntkolonner(); kol++){
        
                        Celle celle = verden.nyttrutenett.hentRutene(rad,kol);
                        JButton celleKnapp = new JButton(celle.hentStatusTegn()+"");
                        if( celle.erLevende()==true){
                            celleKnapp.setBackground(Color.GREEN);
                        }else{ celleKnapp.setBackground(Color.BLACK);}
                        spillPanel.add(celleKnapp);
        
                    }
        
                }
                levendeTeller.setText("Antall Levende: " + verden.nyttrutenett.antallLevende());
                spillPanel.validate();
                spillPanel.repaint();
                
            }
            @Override
            public void actionPerformed(ActionEvent e){
                //fjerner start knappen når den er trykket på.
                toppMeny.remove(startSimulering);
                toppMeny.validate();
                
                
                //oppretter en timer som skal oppdatere spillet hvert 2 sekund
                timer = new Timer(2000, new ActionListener(){
                    //oppdatering skal kjøres hvert 2 sekund
                    @Override
                    public void actionPerformed(ActionEvent e){
                        oppdaterspill();
                        
                    }
                });
                
                timer.start();


            }
        }
        //legger til actionlistener for startSimulering
        startSimulering.addActionListener(new StartSimuleringBehandler());
        
        toppMeny.add(startSimulering);
        toppMeny.add(avsluttSimulering);
        
        
        hovedPanel.add(toppMeny,BorderLayout.NORTH);//legger til toppmenyen
        vindu.remove(panel);//fjerner velkomstpanelet
        hovedPanel.add(spillPanel, BorderLayout.CENTER);//legger til spillpanelet

        vindu.add(hovedPanel);//legger til hovedpanelet
       
        vindu.validate();//oppdaterer vinduet
        vindu.pack();//pakker vinduet
        
        

    }
    
    

    
  

   

}