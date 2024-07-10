package bugtracking.model;

public class UserWithTypeDTO {
    private String name;
    private String username;
    private String password;
    private String email;
    private String type;

    public UserWithTypeDTO(String name, String username, String password, String email, String type) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "UserWithTypeDTO{" + "name=" + name + ", username=" + username + ", password=" + password + ", email=" + email + ", type=" + type + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        UserWithTypeDTO that = (UserWithTypeDTO) obj;
        return name.equals(that.name) && username.equals(that.username) && password.equals(that.password) && email.equals(that.email) && type.equals(that.type);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}
