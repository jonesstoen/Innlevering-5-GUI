import java. util.Scanner;
class GameOfLife{

    public static void hovedoppgave(){
        // opretter verden med 8 rader og 12 kolonner
        Verden verden = new Verden( 8,  12);
        
        // lager en for løkke som skriver ut og oppdater verden fra oprettelsen og igjennom 3 generasjoenr
        for(int i = 0; i <= 3;i ++){

            System.out.println("Generasjon " + i + ": ");
            verden.tegn();
            verden.oppdatering();}
    }

    public static void sporrOmInput(){
        Scanner s= new Scanner(System.in);
        
        System.out.println("Hvor mange Rader? ");
        int rader= Integer.parseInt(s.nextLine());

        System.out.println("Hvor mange kolonner? ");
        int kolonner = Integer.parseInt(s.nextLine());

        Verden verden = new Verden(rader,kolonner);

        for (int i = 0; i <= 3; i ++){

            System.out.println("Generasjon" + i + ": ");
            verden.tegn();
            verden.oppdatering();

        } s.close();
    }
    public static void inputFortsett(){
        String fortsett= "f";
        Scanner s = new Scanner(System.in);
        

        Verden verden = new Verden(8,12);
        while ( fortsett.equals("f") ){
            
            verden.tegn();
            System.out.println("Trykk f for å fortsette");
            fortsett = s.nextLine();
            
            verden.oppdatering();

        }s.close();
    }
    public static void main(String[]args){
        hovedoppgave();
        // Fjern kommentarene for å kjøre de frivillige utvidelsene
        //sporrOmInput();
        //inputFortsett();
        

        
    }
}