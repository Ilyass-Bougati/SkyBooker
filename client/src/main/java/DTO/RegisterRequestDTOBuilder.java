package DTO;

public class RegisterRequestDTOBuilder {
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
        RegisterRequestDTOBuilder.email = email;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        RegisterRequestDTOBuilder.password = password;
    }

    public static String getTelephone() {
        return telephone;
    }

    public static void setTelephone(String telephone) {
        RegisterRequestDTOBuilder.telephone = telephone;
    }

    public static String getAdresse() {
        return adresse;
    }

    public static void setAdresse(String adresse) {
        RegisterRequestDTOBuilder.adresse = adresse;
    }

    public static String getNom() {
        return nom;
    }

    public static void setNom(String nom) {
        RegisterRequestDTOBuilder.nom = nom;
    }

    public static String getPrenom() {
        return prenom;
    }

    public static void setPrenom(String prenom) {
        RegisterRequestDTOBuilder.prenom = prenom;
    }

    public static String getCIN() {
        return CIN;
    }

    public static void setCIN(String CIN) {
        RegisterRequestDTOBuilder.CIN = CIN;
    }

    public static int getAge() {
        return age;
    }

    public static void setAge(int age) {
        RegisterRequestDTOBuilder.age = age;
    }

    /**
     * This function creates a RegisterRequestDTO from the static data that was fed into the RegisterRequestDTOBuilder
     * This is a very weird way of doing stuff, and I don't like it, but it's working
     *
     * @return a RegisterRequestDTO
     */
    public static RegisterRequestDTO build() {
        RegisterRequestDTO dto = new RegisterRequestDTO();
        dto.cin = getCIN();
        dto.email = getEmail();
        dto.password = getPassword();
        dto.telephone = getTelephone();
        dto.adresse = getAdresse();
        dto.nom = getNom();
        dto.prenom = getPrenom();
        dto.age = getAge();

        // removing old data
        setAdresse("");
        setCIN("");
        setEmail("");
        setTelephone("");
        setNom("");
        setPrenom("");
        setAge(0);
        setPassword("");
        return dto;
    }
}
