package okhttp;

import com.google.gson.Gson;
import dto.AuthResponseDTO;
import dto.AutoRequestDTO;
import dto.ErrorDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class RegistrationTest {
    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();
    String baseURL = "https://contactapp-telran-backend.herokuapp.com";

    //eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibG92ZUBnbWFpbC5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTY5OTA5Njk2NywiaWF0IjoxNjk4NDk2OTY3fQ._llAmj4iW2J7ACwJBNs5vUnn3t3SkDQonpAq0iEbfyw
    @Test
    public void RegistrationPositive() throws IOException {
        int i = (int)(System.currentTimeMillis()/1000)%3600;
        AutoRequestDTO requestDTO = AutoRequestDTO.builder()
                .username( "love"+ i +"@gmail.com")
                .password("abC250712#")
                .build();
        RequestBody requestBody = RequestBody.create(gson.toJson(requestDTO), JSON);

        Request request = new Request.Builder()
                .url(baseURL + "/v1/user/registration/usernamepassword")
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String responseJson = response.body().string();
            AuthResponseDTO responseDTO = gson.fromJson(responseJson, AuthResponseDTO.class);

            System.out.println("Response code is -----> " + response.code());
            System.out.println(responseDTO.getToken());
            Assert.assertTrue(response.isSuccessful());

        } else {
            ErrorDTO errorDTO = gson.fromJson(response.body().string(), ErrorDTO.class);
            System.out.println("Response code is -----> " + response.code());
            System.out.println(errorDTO.getStatus() + "========" + errorDTO.getMessage() + "========" + errorDTO.getError());
            Assert.assertFalse(response.isSuccessful());

        }
    }
}
