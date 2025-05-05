package requests;

import com.squareup.okhttp.*;

import java.io.IOException;

public class Client {
    private static final String url = "http://localhost:8080/api/v1";
    private static final OkHttpClient client = new OkHttpClient();
    private static String token = "";

    public static void login(String username, String password) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), username + ":" + password);
        Request request = new Request.Builder()
                .url(url + "/auth/")
                .post(body)
                .header("Authorization", Credentials.basic(username, password))
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            token = response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    public static String getToken() {
        return token;
    }
}
