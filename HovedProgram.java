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
                    @Override
                    public void actionPerformed(ActionEvent e){
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
                    }

                }

                celleKnapp.addActionListener(new celleKnappBehandler());
                spillPanel.add(celleKnapp);

            }

        }
        JButton startSimulering = new JButton("START");
        JButton avsluttSimulering = new JButton("AVSLUTT");


        toppMeny.add(startSimulering);
        toppMeny.add(avsluttSimulering);
        

        hovedPanel.add(toppMeny,BorderLayout.NORTH);
        vindu.remove(panel);
        hovedPanel.add(spillPanel, BorderLayout.CENTER);

        vindu.add(hovedPanel);
       
        vindu.validate();
        vindu.pack();
        
        

    }

    
  

   

}