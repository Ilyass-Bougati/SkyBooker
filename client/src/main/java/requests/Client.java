package requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;

public class Client {
    private static final String url = "http://localhost:8080/api/v1";
    private static final OkHttpClient client = new OkHttpClient();
    private static String token = "";
    private static boolean isLoggedIn = false;

    public static void login(String username, String password) throws IOException {
        if (isLoggedIn) {
            System.out.println("Already logged in");
            return;
        }

        RequestBody body = RequestBody.create("", MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(url + "/auth/")
                .post(body)
                .header("Authorization", Credentials.basic(username, password))
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            ResponseBody res = response.body();
            if (res != null) {
                token = res.string();
                isLoggedIn = true;
            } else {
                throw new IOException("Unexpected code " + response);
            }
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    public static void post(String route, Object object) throws IOException {
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
        Response response = call.execute();
        if (response.isSuccessful()) {
            System.out.println(request.toString());
        } else {
            System.out.println("Unexpected code " + response.body().string());
        }
    }

    public static String getToken() {
        return token;
    }
}
