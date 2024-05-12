class Verden{
    public Rutenett nyttrutenett;
    public int genNr = 0;


    public Verden(int antRader,int antKolonner){

        //initialiserer generasjonsnummer
        this.genNr = 0;

        // opretter instans av rutenett
        nyttrutenett= new Rutenett(antRader, antKolonner); 
        
        //fyller rutenettet og kobler cellene sammen med tidligere metoder
        nyttrutenett.fyllMedTilfeldigeCeller();

        nyttrutenett.kobleAlleCeller();
    }
    public void tegn(){

        System.out.println("Generasjon: " + genNr + ", Antall levende celler: " + nyttrutenett.antallLevende());
         nyttrutenett.tegnRutenett();
    }
    public void oppdatering(){

        // går gjennom alle celler i rutenettet og teller og oppdaterer antall levende naboer for hver celle
        // laget getmetoder i Rutenett klassen for å kunne hente antrader antkolonner og rutene
        for(int rad = 0; rad < nyttrutenett.hentAntrader(); rad ++){
            for(int kol = 0; kol < nyttrutenett.hentAntkolonner(); kol ++){

                nyttrutenett.hentRutene(rad,kol).tellLevendeNaboer();
            }
        }
    
        // går gjennom alle celler i rutenettet igjen og oppdaterer status  på hver celle
        for(int rad = 0; rad < nyttrutenett.hentAntrader(); rad ++ ){

            for(int kol = 0; kol < nyttrutenett.hentAntkolonner(); kol ++ ){

                nyttrutenett.hentRutene(rad,kol).oppdaterStatus();
            }
        }
        genNr ++ ;
    }
    
    
    
    

 

}