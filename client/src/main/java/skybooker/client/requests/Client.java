package skybooker.client.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import skybooker.client.DTO.ClientDTO;
import skybooker.client.DTO.PassagerDTO;
import skybooker.client.exceptions.ExceptionHandler;
import okhttp3.*;

public class Client {
    private static final String url = "http://localhost:8080/api/v1";
    private static final OkHttpClient client = new OkHttpClient();
    private static String token = "";
    private static boolean loggedIn = false;
    private static ClientDTO clientDetails = null;

    public static void login(String username, String password) throws Exception {
        if (loggedIn) {
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
                    loggedIn = true;
                }
            } else {
                throw ExceptionHandler.getException(response);
            }
        }
    }

    public static String post(String route, Object object) throws Exception {
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

    public static String put(String route, Object object) throws Exception {
        // turning to json
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(object);

        RequestBody formBody = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(url + route)
                .put(formBody)
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

    public static void delete(String route) throws Exception {
        Request request = new Request.Builder()
                .url(url + route)
                .delete()
                .header("Authorization", "Bearer " + token)
                .build();

        Call call = client.newCall(request);
        try (Response res = call.execute()) {
            if (res.isSuccessful() && res.body() != null) {
                return;
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

    public static void fetchClient() throws Exception {
        String res = get("/client/");
        ObjectMapper mapper = new ObjectMapper();
        clientDetails = mapper.readValue(res, ClientDTO.class);
    }

    public static ClientDTO getClientDetails() {
        return clientDetails;
    }

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static String getToken() {
        return token;
    }

    public static void logout() {
        token = "";
        loggedIn = false;
    }


}
