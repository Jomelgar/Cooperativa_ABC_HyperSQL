package BackEnd;

public class Loan {
    private String date, number;
    private double monto,saldo;
    private String tipo;

    public String getTipo() {
        return tipo;
    }
    private int periods;
    
    public Loan(double aDouble, double aDouble1, String toString, String string, int aInt,String t) {
        date= toString;
        number = string;
        monto = aDouble;
        saldo = aDouble1;
        periods = aInt;
        tipo = t;
    }
    
    public String getDate() {
        return date;
    }

    public String getNumber() {
        return number;
    }

    public double getMonto() {
        return monto;
    }

    public double getSaldo() {
        return saldo;
    }

    public int getPeriods() {
        return periods;
    }
    
}
