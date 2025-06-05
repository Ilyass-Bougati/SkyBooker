package skybooker.client.DTO.register;

public class RegisterRequestDTO {
    public String email;
    public String password;
    public String telephone;
    public String adresse;
    public String nom;
    public String prenom;
    public String cin;
    public int age;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
