
package BackEnd;


public class User {
    private String username;
    private String password;
    private boolean role;
    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
    }
    
    public boolean isAdmin()
    {
        return role;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setRole(boolean role)
    {
        this.role = role;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}
