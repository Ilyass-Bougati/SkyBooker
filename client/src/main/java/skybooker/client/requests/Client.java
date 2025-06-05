package skybooker.client.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import skybooker.client.exceptions.ExceptionHandler;
import okhttp3.*;

import java.io.IOException;

public class Client {
    private static final String url = "http://localhost:8080/api/v1";
    private static final OkHttpClient client = new OkHttpClient();
    private static String token = "";
    private static boolean isLoggedIn = false;

    public static void login(String username, String password) throws Exception, IOException {
        if (isLoggedIn) {
            return;
        }

        RequestBody body = RequestBody.create("", MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(url + "/auth/login")
                .post(body)
                .header("Authorization", Credentials.basic(username, password))
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                ResponseBody res = response.body();
                if (res != null) {
                    token = res.string();
                    isLoggedIn = true;
                } else {
                    throw ExceptionHandler.getException(response);
                }
            } else {
                throw new IOException("Unexpected code " + response);
            }
        }
    }

    public static String post(String route, Object object) throws Exception, IOException {
        // turning to json
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(object);

        RequestBody formBody = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(url + route)
                .post(formBody)
                .header("Authorization", "Bearer " + token)
                .build();

        Call call = client.newCall(request);
        try (Response res = call.execute()) {
            if (res.isSuccessful() && res.body() != null) {
                return res.body().string();
            } else {
                throw ExceptionHandler.getException(res);
            }
        }
    }

    public static String get(String route) throws Exception {
        Request request = new Request.Builder()
                .url(url + route)
                .header("Authorization", "Bearer " + token)
                .build();

        Call call = client.newCall(request);
        try (Response res = call.execute()) {
            if (res.isSuccessful() && res.body() != null) {
                return res.body().string();
            } else {
                throw ExceptionHandler.getException(res);
            }
        }
    }

    public static String unAuthorizedPost(String route, Object object) throws Exception {
        // turning to json
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(object);

        RequestBody formBody = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(url + route)
                .post(formBody)
                .build();

        Call call = client.newCall(request);
        try (Response res = call.execute()) {
            if (res.isSuccessful() && res.body() != null) {
                return res.body().string();
            } else {
                throw ExceptionHandler.getException(res);
            }
        }
    }

    public static String getToken() {
        return token;
    }

    public static void logout() {
        token = "";
        isLoggedIn = false;
    }
}
