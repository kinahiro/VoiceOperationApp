package com.main.voiceoperationapp.authentication;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.main.voiceoperationapp.config.AuthenticationConf;
import com.main.voiceoperationapp.exception.AuthenticationException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import static android.os.Looper.getMainLooper;

public class AuthenticationHelper {

    private static String TAG = "VoiceOperationApp";
    public static String AUTHENTICATION_CODE = "";

    final Handler handler = new Handler(getMainLooper());
    Listener mListener;

    public void setListener(Listener listener) {
        mListener = listener;
    }

    // The current credentials for the app
    private static OAuthCredentials oAuthCredentials;

    // The client to perform HTTP request for oAuth2 authentication
    private OAuthClient oAuthClient;

    // The Gson object to store the credentials in a file
    private Gson gson;

    // The configuration for the authentication module (see reference.conf in resources)
    private AuthenticationConf authenticationConf;

    public static Response<OAuthCredentials> response = null;

    private String credentialsFile = "";

    public AuthenticationHelper(AuthenticationConf authenticationConf) {
        this.authenticationConf = authenticationConf;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(authenticationConf.getGoogleOAuthEndpoint())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        oAuthClient = retrofit.create(OAuthClient.class);
        gson = new Gson();
    }

    public OAuthCredentials getOAuthCredentials() {
        return oAuthCredentials;
    }

    public boolean checkFileExists(){
        String path = mListener.createFile(authenticationConf.getCredentialsFilePath());
        File file = new File(path);
        credentialsFile = path;
        if (file.exists()) {
            Log.i(TAG, "Loading oAuth credentials from file");
            return true;
        }
        else {
            return false;
        }
    }

    public Optional<OAuthCredentials> authenticate(boolean fileExistFlag) throws AuthenticationException {
        try{
            if(fileExistFlag){
                Log.i(TAG,"Loading oAuth credentials from file");
                oAuthCredentials = gson.fromJson(new JsonReader(new FileReader(credentialsFile)), OAuthCredentials.class);
                Log.i(TAG,"Access Token: " + oAuthCredentials.getAccessToken());
            }
            else {
                Optional<OAuthCredentials> optCredentials = requestAccessToken();
                if (optCredentials.isPresent()) {
                    oAuthCredentials = optCredentials.get();
                    Log.i(TAG,"Access Token: " + oAuthCredentials.getAccessToken());
                    saveCredentials();
                }
            }
            return Optional.of(oAuthCredentials);
        }
        catch (Exception e){
            throw new AuthenticationException("Error during authentication", e);
        }
    }

    /**
     * Check if the token is expired
     *
     * @return true if the access token need to be refreshed false otherwise
     */
    public boolean expired() {
        // Add a delay to be sure to not make a request with an expired token
        return oAuthCredentials.getExpirationTime() - System.currentTimeMillis() < authenticationConf.getMaxDelayBeforeRefresh();
    }

    /**
     * Refresh the access token for oAuth authentication
     *
     * @return the new (refreshed) credentials
     */
    public Optional<OAuthCredentials> refreshAccessToken() throws AuthenticationException {
        Log.i(TAG,"Refreshing access token");
        try {
            // Network処理
            RefreshAccessTokenTask refAccessTokenTask = new RefreshAccessTokenTask(authenticationConf, oAuthClient, oAuthCredentials);
            refAccessTokenTask.execute();

            if (response.isSuccessful()) {
                Log.i(TAG,"New Access Token: " + response.body().getAccessToken());
                oAuthCredentials.setAccessToken(response.body().getAccessToken());
                oAuthCredentials.setExpiresIn(response.body().getExpiresIn());
                oAuthCredentials.setTokenType(response.body().getTokenType());
                saveCredentials();
                return Optional.of(oAuthCredentials);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new AuthenticationException("Error during authentication", e);
        }
    }

    private Optional<OAuthCredentials> requestAccessToken() throws URISyntaxException, IOException {
        String code = AUTHENTICATION_CODE;
        Log.d(TAG,"Authentication code : " + code);


        // Network処理
        GetAccessTokenTask getAccessTokenTask = new GetAccessTokenTask(authenticationConf, oAuthClient);
        getAccessTokenTask.execute(code);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (response.isSuccessful()) {
            oAuthCredentials = response.body();
            return Optional.of(oAuthCredentials);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Request an access token by asking the user to authorize the application
     *
     * @return credentials if the request succeeds
     * @throws URISyntaxException if the request fails
     * @throws IOException        if the request fails
     */
    public void showAuthenticateBrowser() throws URISyntaxException, IOException {
        String url = "https://accounts.google.com/o/oauth2/v2/auth?" +
                "scope=" + authenticationConf.getScope() + "&" +
                "response_type=code&" +
                "redirect_uri=" + authenticationConf.getCodeRedirectUri() + "&" +
                "client_id=" + authenticationConf.getClientId();

        String packageName = "com.android.chrome";
        String className = "com.google.android.apps.chrome.Main";
        // Chromeが存在するか確認する
        if(mListener.isAppInstalled(packageName)){
            // Open a browser to authenticate using oAuth2
            // ブラウザを開き、oAuth2を使用して認証する
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.setClassName(packageName,className);
            mListener.startBrowser(intent);
        }

        Log.i(TAG,"Allow the application in your browser and copy the authorization code in the console");
    }

    /**
     * Save the credentials in a file
     *
     * @throws IOException if the file cannot be created
     */
    private void saveCredentials() throws IOException {
        String filePath = authenticationConf.getCredentialsFilePath();
        filePath = mListener.createFile(filePath);

        try (FileWriter writer = new FileWriter(filePath)) {
            // Set the expiration Date
            oAuthCredentials.setExpirationTime(System.currentTimeMillis() + oAuthCredentials.getExpiresIn() * 1000);
            gson.toJson(oAuthCredentials, writer);
        }
    }

    public interface Listener {
        // 他にもMainActivityに実装するメソッドを羅列していく
        void startBrowser(Intent intent);
        String createFile(String fileName);
        boolean isAppInstalled(String appPackage);
    }
}

class GetAccessTokenTask extends AsyncTask<String, Void, Response<OAuthCredentials>> {

    static String TAG = "VoiceOperationApp";

    private OAuthClient oAuthClient;

    AuthenticationConf authenticationConf;

    public GetAccessTokenTask(AuthenticationConf config, OAuthClient Client){
        authenticationConf = config;
        oAuthClient = Client;
    }

    protected Response<OAuthCredentials> doInBackground(String... codes) {
        try {
            String code = new String(codes[0]);
            AuthenticationHelper.response = oAuthClient.getAccessToken(
                    code,
                    authenticationConf.getClientId(),
                    authenticationConf.getClientSecret(),
                    authenticationConf.getCodeRedirectUri(),
                    "authorization_code")
                    .execute();
            return AuthenticationHelper.response;
        } catch (Exception e)
        {
            Log.e(TAG,"Could not get access token : " + e.toString());
            return null;
        }
    }

    protected void onPostExecute(Response<OAuthCredentials> result) {
    }
}

class RefreshAccessTokenTask extends AsyncTask<String, Void, Response<OAuthCredentials>> {

    static String TAG = "VoiceOperationApp";

    private OAuthCredentials oAuthCredentials;

    private OAuthClient oAuthClient;

    private AuthenticationConf authenticationConf;

    public RefreshAccessTokenTask(AuthenticationConf config, OAuthClient Client, OAuthCredentials Credentials){
        authenticationConf = config;
        oAuthClient = Client;
        oAuthCredentials = Credentials;
    }

    protected Response<OAuthCredentials> doInBackground(String... codes) {
        try {
            AuthenticationHelper.response = oAuthClient.refreshAccessToken(
                    oAuthCredentials.getRefreshToken(),
                    authenticationConf.getClientId(),
                    authenticationConf.getClientSecret(),
                    "refresh_token")
                    .execute();
            return AuthenticationHelper.response;
        } catch (Exception e)
        {
            Log.e(TAG,"Could not refresh access token : " + e.toString());
            return null;
        }
    }

    protected void onPostExecute(Response<OAuthCredentials> result) {
    }
}

