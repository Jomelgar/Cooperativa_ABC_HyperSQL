package BackEnd;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import org.hsqldb.types.Types;

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
    
   public boolean addUser(LinkedHashMap<String, Object> userData, boolean role) {
        String query = "INSERT INTO COOPERATIVA.usuario_cliente("
                + "id_usuario, contrasena, primer_nombre, segundo_nombre, primer_apellido, "
                + "segundo_apellido, referencia, ciudad, avenida, casa, departamento, calle, "
                + "correo_primario, correo_secundario, usuario_creador, rol ) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stm = connection.prepareStatement(query)) {
            int index = 1;
            for (Map.Entry<String, Object> entry : userData.entrySet()) {
                if (entry.getValue() == null) {
                    stm.setNull(index, Types.VARCHAR);
                } else {
                    stm.setObject(index, entry.getValue());
                }
                index++;
            }
            stm.setBoolean(index, role);
            
            stm.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
   
    public LinkedHashMap<String, Object> getUserById(String idUsuario) {
        String query = "SELECT * FROM COOPERATIVA.usuario_cliente WHERE id_usuario = ?";
        LinkedHashMap<String, Object> userData = new LinkedHashMap<>();

        try {
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

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userData;
    }
    public boolean modifiedUser(LinkedHashMap<String, Object> userData,boolean role)
    {
        try 
        {
            String query = ("UPDATE COOPERATIVA.usuario_cliente SET ");
            for (String column : userData.keySet()) {
                if (!column.equals("id_usuario")) {
                    query+= column + (" = ?, ");
                }
            }
            query += "rol = ?, ";
            query += "fecha_ultima_actualizacion = ?";
            query += (" WHERE id_usuario = ?");
             int index = 1;
            Object idUsuario = null;
            PreparedStatement stm = connection.prepareStatement(query);
            for (Map.Entry<String, Object> entry : userData.entrySet()) {
                if (entry.getKey().equals("id_usuario")) {
                    idUsuario = entry.getValue();
                    continue;
                }
                if (entry.getValue() == null) {
                    stm.setNull(index, Types.VARCHAR);
                } else {
                    stm.setObject(index, entry.getValue());
                }
                index++;
            }
            stm.setBoolean(index, role);
            index++;
            LocalDate fechaActual = LocalDate.now();
            stm.setDate(index, java.sql.Date.valueOf(fechaActual));
            index++;
            stm.setObject(index, idUsuario);
            stm.executeUpdate();
            letRole(BackEnd.Main.logged);
            return true;
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
            String query = "INSERT INTO COOPERATIVA.usuario_telefono(codigo_empleado,telefonos) VALUES";
            for (int i = 0; i < phones.size();i++) {
                query += (i < phones.size()-1)? "(?,?), " : "(?,?)";
            }
            
            int i = 1;
            PreparedStatement stm = connection.prepareStatement(query);
            for (String p : phones) 
            {
                stm.setString(i++, codeEmp);
                stm.setString(i++, p);
            }
            
            stm.executeUpdate();
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
            String query = "SELECT telefonos FROM COOPERATIVA.usuario_telefono WHERE codigo_empleado = ?";
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
}
