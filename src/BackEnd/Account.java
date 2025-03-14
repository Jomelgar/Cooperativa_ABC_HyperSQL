package BackEnd;

/**
 *
 * @author jomel
 */
public class Account {
    private String[] numero = new String[2];
    private double[] saldo = new double[2];
        
    public Account(String numP, String numF, double sP, double sF) {
        numero[0] = numP;
        numero[1] = numF;
        saldo[0] = sP;
        saldo[1] = sF;
    }
    
    public String numero_Apo()
    {
        return numero[0];
    }
    
    public String numero_Afi()
    {
        return numero[1];
    }
    
    public double saldo_Apo()
    {
        return saldo[0];
    }
    
    public double saldo_Afi()
    {
        return saldo[1];
    }
}
