package transport.model;

public class ReservationManager extends Entity<Long> {

    private String name;
    private String password;

    public ReservationManager(String name, String password) {
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

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "RM: " +
                "Name: " + name +
                "; Password: " + password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservationManager)) return false;
        ReservationManager that = (ReservationManager) o;
        return name.equals(that.name) &&
                password.equals(that.password);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }
}
