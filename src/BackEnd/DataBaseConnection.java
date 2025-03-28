package BackEnd;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.hsqldb.jdbc.JDBCDataSource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.sql.*;

public class DataBaseConnection {
   private String url = "jdbc:hsqldb:hsql://localhost:9001/coop";
   private String user = "sa";
   private String password = "clave123";
   private Connection connection;
    public DataBaseConnection()
    {
        try
        {
             Class.forName("org.hsqldb.jdbc.JDBCDriver");
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Data base connection succesful!");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void letRole(User user)
    {    
        try {
            String query = "SELECT uc.rol FROM COOPERATIVA.usuario_cliente uc WHERE uc.id_usuario = ? AND uc.contrasena = ?";
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, user.getUsername());  
            stm.setString(2, user.getPassword());
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {                
                user.setRole(rs.getBoolean("rol"));
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean SearchUser(User user)
    {
        try {
            String query = "SELECT uc.id_usuario, uc.contrasena FROM COOPERATIVA.usuario_cliente uc WHERE uc.id_usuario = ? AND uc.contrasena = ?";
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, user.getUsername());  
            stm.setString(2, user.getPassword());
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                letRole(user);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
    
   public boolean addUser(LinkedHashMap<String, Object> data, boolean role) {
        String sql = "CALL COOPERATIVA.crear_usuario(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setString(1, (String) data.get("codigo_empleado"));
            stmt.setString(2, (String) data.get("id_usuario"));
            stmt.setString(3, (String) data.get("contrasena"));
            stmt.setBoolean(4, role);
            stmt.setDate(5, (data.get("fecha_de_nacimiento") != null) 
                                ? new java.sql.Date(((java.util.Date) data.get("fecha_de_nacimiento")).getTime()): null);
            stmt.setString(6, (String) data.get("primer_nombre"));
            stmt.setString(7, (String) data.get("segundo_nombre"));
            stmt.setString(8, (String) data.get("primer_apellido"));
            stmt.setString(9, (String) data.get("segundo_apellido"));
            stmt.setString(10, (String) data.get("referencia"));
            stmt.setString(11, (String) data.get("ciudad"));
            stmt.setString(12, (String) data.get("avenida"));
            stmt.setString(13, (String) data.get("casa"));
            stmt.setString(14, (String) data.get("departamento"));
            stmt.setString(15, (String) data.get("calle"));
            stmt.setString(16, (String) data.get("correo_primario"));
            stmt.setString(17, (String) data.get("correo_secundario"));
            stmt.setString(18, (String) data.get("usuario_creador"));
            
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
   
    public LinkedHashMap<String, Object> getUserById(String idUsuario) {
        String query = "CALL COOPERATIVA.obtener_usuario_por_id(?)";
        LinkedHashMap<String, Object> userData = new LinkedHashMap<>();
        try
        {
            PreparedStatement stm = connection.prepareStatement(query); 

            stm.setString(1, idUsuario);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                int columnCount = rs.getMetaData().getColumnCount();
                for(int i = 1; i <= columnCount; i++) 
                {
                    String columnName = rs.getMetaData().getColumnName(i);
                    Object value = rs.getObject(i);
                    userData.put(columnName, value);
                }
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return userData;
    }
    public boolean modifiedUser(LinkedHashMap<String, Object> userData,boolean role)
    {
        try 
        {
            String sql = "CALL COOPERATIVA.actualizar_usuario(?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,getCodeEmp(getStringOrNull(userData.get("id_usuario"))));
            ps.setString(2, getStringOrNull(userData.get("id_usuario")));
            ps.setString(3, getStringOrNull(userData.get("contrasena")));
            ps.setBoolean(4, role);
            ps.setDate(5,  (userData.get("fecha_de_nacimiento") != null) 
                                ? new java.sql.Date(((java.util.Date) userData.get("fecha_de_nacimiento")).getTime())  : null);
            ps.setString(6, getStringOrNull(userData.get("primer_nombre")));
            ps.setString(7, userData.get("segundo_nombre").toString());
            ps.setString(8, getStringOrNull(userData.get("primer_apellido")));
            ps.setString(9, getStringOrNull(userData.get("segundo_apellido")));
            ps.setString(10, getStringOrNull(userData.get("referencia")));
            ps.setString(11, getStringOrNull(userData.get("ciudad")));
            ps.setString(12, getStringOrNull(userData.get("avenida")));
            ps.setString(13, getStringOrNull(userData.get("casa")));
            ps.setString(14, getStringOrNull(userData.get("departamento")));
            ps.setString(15, getStringOrNull(userData.get("calle")));
            ps.setString(16, getStringOrNull(userData.get("correo_primario")));
            ps.setString(17, getStringOrNull(userData.get("correo_secundario")));
            ps.setString(18, getCodeEmp(Main.logged.getUsername()));
            return ps.execute();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    
    public String getCodeEmp(String id_user)
    {
        try
        {
            String query = "SELECT u.codigo_empleado FROM COOPERATIVA.usuario_cliente u WHERE u.id_usuario = ?";
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, id_user);
            ResultSet rs = stm.executeQuery();
            String name= "";
            if(rs.next())
            {
                name = rs.getString("codigo_empleado");
                
            }
            return name;
        }catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public void addTelephones(String codeEmp, ArrayList<String> phones) {
        if (phones.isEmpty()) return;
        
        try
        {
            String query = "CALL COOPERATIVA.crear_usuario_telefono(?,?)";
            for (int i = 0; i < phones.size();i++) {
                PreparedStatement stm = connection.prepareStatement(query);
                int j =1;
                stm.setString(j++, codeEmp);
                stm.setString(j, phones.get(i));
                stm.executeUpdate();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public ArrayList<String> getTelephones(String codeEmp)
    {
        ArrayList<String> phones = new ArrayList<String>();
        try
        {
            String query = "CALL COOPERATIVA.obtener_usuario_telefono(?)";
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, codeEmp);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                phones.add(rs.getString("telefonos"));
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return phones;
    }
    
    public Account getAccount(String code)
    {
        Account account = null;
        try
        {
            String query = "CALL COOPERATIVA.obtener_cuenta(?)";
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1,code);
            ResultSet rs = stm.executeQuery();
            
            String t1 = null, t2 = null;
            double s1 = 0, s2 = 0;
            if (rs.next()) 
            {
                t1 = rs.getString(1);
                s1 = rs.getDouble(2);
            }
            if (rs.next()) 
            {
                t2 = rs.getString(1);
                s2 = rs.getDouble(2);
            }
            if(t1.contains("CAP"))
            {
                account = new Account(t1,t2,s1,s2);
            }else
            {
                account = new Account(t2,t1,s2,s1);
            }
            
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return account;
    }
    
    public boolean setPayment(String num_cuenta, String desc, double saldo)
    {
        try
        {
            String query = "CALL COOPERATIVA.crear_transaccion(?,?,?)";
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1,num_cuenta);
            stm.setDouble(2,saldo);
            stm.setString(3,desc);
            stm.executeUpdate();
            return true;
        }catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    
    public void modPayment(double saldo, String cuenta, String codeEmp)
    {
        try
        {
            String query = "CALL COOPERATIVA.modificar_cuenta(?,?,?)";
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1,cuenta);
            stm.setDouble(2,saldo);
            stm.setString(3,codeEmp);
            stm.executeUpdate();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void setLiquidation(String codeEmp, double saldo)
    {
        try
        {
            String query = "CALL COOPERATIVA.crear_liquidacion_parcial(?,?)";
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1,codeEmp);
            stm.setDouble(2,saldo);
            stm.executeUpdate();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private String getStringOrNull(Object value) {
        if (value == null || value.toString().trim().isEmpty()) {
            return null;
        }
        return value.toString();
    }
    
    public int countLoans(String user)
    {
        String query = "CALL COOPERATIVA.contar_prestamos(?)";
        try
        {
          PreparedStatement stmt = connection.prepareStatement(query);
          stmt.setString(1, getCodeEmp(user));
          ResultSet rs = stmt.executeQuery();
          if(rs.next()){
            return rs.getInt(1);
          }
          else return -1;
        }catch(Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }

    public double getMoneyApo(String user) {
        Account account = getAccount(getCodeEmp(user));
        return account.saldo_Apo();
    }

    public void setLoan(double d, int i, String tipo, String user) {
        String query = "CALL COOPERATIVA.crear_prestamo(?,?,?,?)";
        try
        {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setDouble(1, d);
            stmt.setInt(2, i);
            stmt.setString(3, tipo);
            stmt.setString(4, getCodeEmp(user));
            stmt.execute();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public Loan getLoan(String user)
    {
        Loan l;
        String query = "CALL COOPERATIVA.conseguir_prestamo(?)";
        try
        {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, getCodeEmp(user));
            ResultSet rs = stmt.executeQuery();
             if(rs.next())
             {
                 l = new Loan(rs.getDouble(1), rs.getDouble(2),rs.getDate(3).toString(), rs.getString(4),rs.getInt(5),rs.getString(6));
                 return l;
             }
             return null;
        }catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    public void setPay(Loan l)
    {
        String query = "CALL COOPERATIVA.crear_pago(?,?,?,?,?,?)";
        try
        {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1,l.getNumber());
            stmt.setDouble(2, l.getMonto());
            stmt.setDouble(3, l.getSaldo());
            stmt.setString(4, l.getTipo());
            stmt.setInt(5, l.getPeriods());
            stmt.setString(6, getCodeEmp(user));
            stmt.executeUpdate();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void setClosure(String user)
    {
        String query = "CALL COOPERATIVA.hacer_cierre(?)";
        
        try
        {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1,getCodeEmp(user));
            stmt.executeUpdate();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public double getTotalLiquidation(String user, String modified)
    {
        String query = "CALL COOPERATIVA.crear_liquidacion_total(?,?,?)";
        double total = 0;
        try
        {
            CallableStatement stmt = connection.prepareCall(query);
            stmt.setString(1,getCodeEmp(user));
            stmt.setString(2,getCodeEmp(modified));
            stmt.registerOutParameter(3, Types.DOUBLE);
            stmt.executeUpdate();
            total = stmt.getDouble(3);
            System.out.println(total);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return total;
        
    }
    
    public Object[][] getDivs(int year) {
        String query = "CALL COOPERATIVA.dividendos_anio(?)";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setInt(1, year);
            ResultSet rs = stm.executeQuery();
            ArrayList<Object[]> rows = new ArrayList<>();
            double tot_por = 0;
            double total = 0;
            double total_g = 0;
            while (rs.next()) {
                Object[] row = new Object[6];
                row[0] = rs.getString(1);
                row[1] = rs.getDate(2);
                row[2] = rs.getString(3);
                double saldo = rs.getBigDecimal(4).doubleValue();
                total += saldo;
                row[3] = saldo;
                double porcentaje = rs.getBigDecimal(5).doubleValue();
                tot_por+= porcentaje;
                row[4] = porcentaje;
                double ganancia = rs.getBigDecimal(6).doubleValue();
                total_g += ganancia;
                row[5] = ganancia;
                rows.add(row);
            }
            Object[][] data = new Object[rows.size() + 1][6];
            for (int i = 0; i < rows.size(); i++) {
                data[i] = rows.get(i);
            }
            data[rows.size()][0] = "Totales";
            data[rows.size()][1] = rows.size();
            data[rows.size()][2] = "";
            data[rows.size()][3] = total;
            data[rows.size()][4] = tot_por;
            data[rows.size()][5] = total_g;
            rs.close();
            stm.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Object[][] getNuevasAfiliaciones(int year) {
        String query = "CALL COOPERATIVA.nuevas_afiliaciones(?)";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setInt(1, year);
            ResultSet rs = stm.executeQuery();
            ArrayList<Object[]> rows = new ArrayList<>();
            double totalInversion = 0;
            double totalAhorro = 0;
            double total = 0;
            while (rs.next()) {
                Object[] row = new Object[6];
                row[0] = rs.getString(1);  // codigo_empleado
                row[1] = rs.getString(2);  // nombre
                row[2] = rs.getDate(3);    // fecha_afiliacion

                double inversion = rs.getBigDecimal(4).doubleValue();
                totalInversion += inversion;
                row[3] = inversion;

                double ahorro = rs.getBigDecimal(5).doubleValue();
                totalAhorro += ahorro;
                row[4] = ahorro;

                double totalSaldo = rs.getBigDecimal(6).doubleValue();
                total += totalSaldo;
                row[5] = totalSaldo;

                rows.add(row);
            }

            Object[][] data = new Object[rows.size() + 1][6];
            for (int i = 0; i < rows.size(); i++) {
                data[i] = rows.get(i);
            }

            data[rows.size()][0] = "Totales";
            data[rows.size()][1] = rows.size();
            data[rows.size()][2] = "";
            data[rows.size()][3] = totalInversion;
            data[rows.size()][4] = totalAhorro;
            data[rows.size()][5] = total;

            rs.close();
            stm.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object[][] getEstadoCuenta(int year, String cuenta) {
    String query = "CALL COOPERATIVA.estado_cuenta(?,?)";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setInt(1, year);
            stm.setString(2, cuenta);
            ResultSet rs = stm.executeQuery();
            ArrayList<Object[]> rows = new ArrayList<>();
            double total_monto = 0;
            int total = 0;
            while (rs.next()) {
                Object[] row = new Object[5];
                row[0] = rs.getString(1);
                double monto = rs.getDouble(2);
                row[1] = monto;
                total_monto += monto; 
                row[2] =  rs.getDate(3).toString();
                total++;
                row[3] = rs.getString(4);
                rows.add(row);
            }

            Object[][] data = new Object[rows.size() + 1][5];
            for (int i = 0; i < rows.size(); i++) {
                data[i] = rows.get(i);
            }

            data[rows.size()][0] = "Totales";
            data[rows.size()][1] = total_monto;
            data[rows.size()][2] = total;
            data[rows.size()][3] = "";
            data[rows.size()][4] = "";

            rs.close();
            stm.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
