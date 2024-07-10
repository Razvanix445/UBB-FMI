package transport.network.dto;

import java.io.Serializable;

public class ReservationManagerDTO implements Serializable {
    private String name;
    private String password;

    public ReservationManagerDTO(String name) {
        this(name,"");
    }

    public ReservationManagerDTO(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString(){
        return "ReservationManagerDTO["+name+' '+password+"]";
    }
}
