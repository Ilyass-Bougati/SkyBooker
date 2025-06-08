package skybooker.client.utils;

public class Validator {
    private static final String emailRegex = "^[a-zA-Z0-9\\._-]*@[a-zA-Z0-9_-]*\\.[a-z]{2,7}$";
    private static final String nameRegex = "^[A-Za-z]+$";
    private static final String passwordSpecialCharactersRegex = ".*[^A-Za-z0-9].*";
    private static final String passwordCapitalCharactersAndNumbersRegex = ".*[A-Z0-9].*";
    private static final String phoneNumberRegex = "^[0-9]{10}$";

    public static boolean checkEmailValidity(String email) {
        return email.matches(emailRegex) && email.length() <= 320;
    }

    public static boolean checkNameValidity(String name) {
        return name.matches(nameRegex);
    }

    public static boolean checkPasswordValidity(String password) {
        return password.length() >= 8 && password.length() <= 30
                && password.matches(passwordSpecialCharactersRegex)
                && password.matches(passwordCapitalCharactersAndNumbersRegex);
    }

    public static boolean checkPhoneNumberValidity(String phoneNumber) {
        return phoneNumber.matches(phoneNumberRegex);
    }
}
