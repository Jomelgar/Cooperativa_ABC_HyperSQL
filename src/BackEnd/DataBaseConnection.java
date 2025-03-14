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
            String sql = "CALL COOPERATIVA.actualizar_usuario(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            CallableStatement ps = connection.prepareCall(sql);
            ps.setString(1, getStringOrNull(userData.get("id_usuario")));
            ps.setString(2, getStringOrNull(userData.get("contrasena")));
            ps.setBoolean(3, role);
            ps.setDate(4,  (userData.get("fecha_de_nacimiento") != null) 
                                ? new java.sql.Date(((java.util.Date) userData.get("fecha_de_nacimiento")).getTime())  : null);
            ps.setString(5, getStringOrNull(userData.get("primer_nombre")));
            ps.setString(6, getStringOrNull(userData.get("segundo_nombre")));
            ps.setString(7, getStringOrNull(userData.get("primer_apellido")));
            ps.setString(8, getStringOrNull(userData.get("segundo_apellido")));
            ps.setString(9, getStringOrNull(userData.get("referencia")));
            ps.setString(10, getStringOrNull(userData.get("ciudad")));
            ps.setString(11, getStringOrNull(userData.get("avenida")));
            ps.setString(12, getStringOrNull(userData.get("casa")));
            ps.setString(13, getStringOrNull(userData.get("departamento")));
            ps.setString(14, getStringOrNull(userData.get("calle")));
            ps.setString(15, getStringOrNull(userData.get("correo_primario")));
            ps.setString(16, getStringOrNull(userData.get("correo_secundario")));
            ps.setString(17, getCodeEmp(Main.logged.getUsername()));
            
            // Ejecutar la consulta
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
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
    
}
