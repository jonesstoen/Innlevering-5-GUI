class Rutenett{
    public int antRader;
    public int antKolonner;
    public Celle[][] rutene;
    // konstruktør
    public  Rutenett(int antRader,int antKolonner){

       this.antRader = antRader;
       this.antKolonner = antKolonner;
       rutene = new Celle[antRader][antKolonner];

    } 

    public void lagCelle(int rad, int kolonne){

        Celle nycelle = new Celle();
        if (Math.random() <= 0.3333){

            nycelle.settLevende();
            
        } rutene[rad][kolonne] = nycelle;
    }
    
    
    public void fyllMedTilfeldigeCeller(){

        // går gjennom hver rad 
        for ( int rad = 0; rad < antRader; rad ++){

            //får gjennom hver kolonne i hver rad 
            for (int kolonne = 0; kolonne<antKolonner; kolonne++){

                lagCelle(rad,kolonne);
            }
        }
    }


    public Celle hentCelle(int rad, int kolonne){

        //sjekker om indeksene som er gitt er innefor rutenettet
        if(rad >= 0 && rad < antRader && kolonne >= 0 && kolonne < antKolonner){
            
            return rutene[rad][kolonne];

        } else {

            return null; // returnerer null hvis utenfor
        }
    }
    public void tegnRutenett(){

        // går gjennom radene
        for(int rad = 0; rad < antRader;rad ++){

            //går gjennom kolonnene for radene
            for (int kolonne = 0; kolonne < antKolonner ; kolonne++){

                Celle celle = hentCelle(rad,kolonne);
                System.out.print(celle.hentStatusTegn());              
            }
            System.out.println("");

        }
    }
    public void settNaboer(int rad, int kolonne){

        // henter den aktuelle cellen
        Celle celle = hentCelle(rad,kolonne);

        // så må vi gå igennom alle naboene 
        //iterer gjennom radene
        for (int r = -1; r <= 1; r++){

            //iterere gjennom kolonnene
            for(int k = -1; k <= 1; k++){

                //kan ikke sette seg selv som nabo så må hoppe over [0][0] med continue
                if(r==0 && k==0){
                    continue;
                }else{
                    //opretter instans av celle for nabo
                    Celle nabo = hentCelle(rad + r, kolonne + k);

                    //sjekker at nabo ikke er null
                    if(nabo != null){
                    celle.leggTilNabo(nabo);
                }
                }


            }
        }

    }
    public void kobleAlleCeller() {

        // iterere for  hver rad
        for (int rad = 0; rad < antRader; rad ++) {

            // iterere for  hver kolonne i  gjeldende raden
            for (int kol = 0; kol < antKolonner; kol++) {

                // bruker settNaboer for hver celle
                settNaboer(rad, kol);
            }
        }
    }
    public int antallLevende(){

        //teller for å telle levende
        int teller = 0;

        for(int rad = 0; rad < antRader;rad ++){

            //går gjennom kolonnene for radene
            for (int kolonne = 0; kolonne < antKolonner ; kolonne ++){

                //sjekker om cellene er levende og plusser på teller
                if (rutene[rad][kolonne].erLevende()) {
                    teller ++;
                }
            }
    } return teller;

    }
    // hent metoder for å kunne bruke de private variablene, i Verden klassen
    public int hentAntrader(){
        return this.antRader;
    }
    public int hentAntkolonner(){
        return this.antRader;
    }
    public Celle hentRutene(int rad, int kol) {
        return rutene[rad][kol];
    }


        

        
    
    
        
    
}