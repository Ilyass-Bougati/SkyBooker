package DTO;

public class RegisterRequestDTO {
    private static String email;
    private static String password;
    private static String telephone;
    private static String adresse;
    private static String nom;
    private static String prenom;
    private static String CIN;
    private static int age;

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        RegisterRequestDTO.email = email;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        RegisterRequestDTO.password = password;
    }

    public static String getTelephone() {
        return telephone;
    }

    public static void setTelephone(String telephone) {
        RegisterRequestDTO.telephone = telephone;
    }

    public static String getAdresse() {
        return adresse;
    }

    public static void setAdresse(String adresse) {
        RegisterRequestDTO.adresse = adresse;
    }

    public static String getNom() {
        return nom;
    }

    public static void setNom(String nom) {
        RegisterRequestDTO.nom = nom;
    }

    public static String getPrenom() {
        return prenom;
    }

    public static void setPrenom(String prenom) {
        RegisterRequestDTO.prenom = prenom;
    }

    public static String getCIN() {
        return CIN;
    }

    public static void setCIN(String CIN) {
        RegisterRequestDTO.CIN = CIN;
    }

    public static int getAge() {
        return age;
    }

    public static void setAge(int age) {
        RegisterRequestDTO.age = age;
    }
}
