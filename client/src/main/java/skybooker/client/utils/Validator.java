package skybooker.client.utils;

public class Validator {
    private static final String emailRegex = "^[a-zA-Z0-9\\._-]*@[a-zA-Z0-9_-]*\\.[a-z]{2,7}$";
    private static final String nameRegex = "^[A-Za-z]+$";
    // TODO : Change this later
    private static final String passwordRegex = "^[a-zA-Z0-9]{6,20}$";
    private static final String phoneNumberRegex = "^[0-9]{10}$";

    public static boolean checkEmailValidity(String email) {
        return email.matches(emailRegex) && email.length() <= 320;
    }

    public static boolean checkNameValidity(String name) {
        return name.matches(nameRegex);
    }

    public static boolean checkPasswordValidity(String password) {
        return password.matches(passwordRegex);
    }

    public static boolean checkPhoneNumberValidity(String phoneNumber) {
        return phoneNumber.matches(phoneNumberRegex);
    }
}
