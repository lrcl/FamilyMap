package cs240.fms.ServerFacade.DataHandling;


public class Main {
    public static void main(String[] args){
        Generator g = new Generator();
        g.loadData();
        if(g.femaleNames != null) {
            System.out.println("check out fnames");
        }
        if(g.maleNames != null) {
            System.out.println("check out mnames");
        }
        if(g.locations != null) {
            System.out.println("check out locations");
        }
        if(g.lastNames != null) {
            System.out.println("check out last names");
        }
    }
}
