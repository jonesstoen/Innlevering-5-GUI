public class Celle{
    public boolean levende;
    private Celle[] naboer;
    public int antNaboer;
    public int antLevendeNaboer;

    //konstruktør
    public Celle(){
        this.levende = false;
        this.naboer = new Celle[8];
        this.antNaboer = 0;
        this.antLevendeNaboer = 0;

    }

    public void  settDoed(){
        levende = false;

    }

    public void settLevende(){
       levende = true;

    }

    public boolean erLevende(){
        return levende;
    }

    public char hentStatusTegn(){

        if(levende == true){
            return  'O';
        } else{
            return '.';
        }
    }

    public void leggTilNabo(Celle nabo){
        if(antNaboer < naboer.length){

            naboer[antNaboer]= nabo;
            antNaboer++;
        }

    }

    public void tellLevendeNaboer(){

        antLevendeNaboer=0;
        for ( int i = 0; i < naboer.length; i++ ){

            if(naboer[i]!= null && naboer[i].erLevende() == true){
                antLevendeNaboer ++;
            }                         
        }
    }
    public void oppdaterStatus(){

        if ( antLevendeNaboer < 2|| antLevendeNaboer > 3){
            this.levende=false;

        } else if (antLevendeNaboer == 3){
            levende = true;
        }
    }

}