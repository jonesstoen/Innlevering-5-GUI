import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
public class HovedProgram{
    private static JFrame vindu;
     
    public static void main(String[]args){
        try {
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    
    }catch (Exception e ){System.exit(1);}

    vindu = new JFrame(" GAME OF LIFE GUI ");
    vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel velkommen = velkomstpanel();
    vindu.setLocationRelativeTo(null);
    vindu.add(velkommen);
    
    

    vindu.pack();
    vindu.setVisible(true);

   



    }
    private static JPanel velkomstpanel(){
        JPanel panel = new JPanel();

        JLabel velkommen = new JLabel("Velkommen til GameOfLife");
        panel.add(velkommen);

        JButton avslutttKnapp = new JButton("Avslutt");
        JButton startKnapp = new JButton("Start Simuleringen");
        startKnapp.setBackground(Color.GREEN);
        avslutttKnapp.setBackground(Color.RED);

        class AvsluttknappBehandler implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e ){
                System.exit(0);


            }
        }
        class StartKnappbehandler implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e){
                startSpill(panel);

            }
        }

        // legg til actionlistener
        startKnapp.addActionListener(new StartKnappbehandler());
        avslutttKnapp.addActionListener(new AvsluttknappBehandler());

        //legg knapper på panel
        panel.add(startKnapp);
        panel.add(avslutttKnapp);

        return panel;

    }
    
    private static void startSpill(JPanel panel){
        //TODO: starte spillet

       

        //TODO: lage spill verden
        Verden verden = new Verden (10,10);
        verden.tegn();

        JPanel hovedPanel = new JPanel(new BorderLayout());

        JPanel spillPanel = new JPanel();
        JPanel toppMeny = new JPanel();
        toppMeny.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel levendeTeller = new JLabel("Antall Levende: "+ verden.nyttrutenett.antallLevende());
        toppMeny.add(levendeTeller);

        spillPanel.setLayout(new GridLayout(verden.nyttrutenett.hentAntrader(), verden.nyttrutenett.hentAntkolonner()));
        //så må vi legge til JButtons fordi vi skal kunne interagere med spillbrettet. 
        for ( int rad = 0; rad< verden.nyttrutenett.hentAntrader(); rad++){

            for (int kol = 0; kol<verden.nyttrutenett.hentAntkolonner(); kol++){

                Celle celle = verden.nyttrutenett.hentRutene(rad,kol);
                JButton celleKnapp = new JButton(celle.hentStatusTegn()+"");
                if( celle.erLevende()==true){
                    celleKnapp.setBackground(Color.GREEN);
                }else{ celleKnapp.setBackground(Color.RED);}
                
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
                            celleKnapp.setBackground(Color.RED);
                            

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