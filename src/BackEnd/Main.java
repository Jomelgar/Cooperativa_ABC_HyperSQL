
package BackEnd;

import FrontEnd.MainWindow;

public class Main {
    
    public static DataBaseConnection db = new DataBaseConnection();
    public static User logged = null;
    public static void main(String[] args)
    {
        MainWindow main = new MainWindow(null);
        main.show();
    }
    
}
