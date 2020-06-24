package com.main.voiceoperationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.main.voiceoperationapp.api.AssistantClient;
import com.main.voiceoperationapp.authentication.AuthenticationHelper;
import com.main.voiceoperationapp.client.audio.AudioPlayer;
import com.main.voiceoperationapp.client.audio.AudioRecorder;
import com.main.voiceoperationapp.config.AssistantConf;
import com.main.voiceoperationapp.config.AudioConf;
import com.main.voiceoperationapp.config.AuthenticationConf;
import com.main.voiceoperationapp.config.DeviceRegisterConf;
import com.main.voiceoperationapp.config.IoConf;
import com.main.voiceoperationapp.device.DeviceRegister;
import com.main.voiceoperationapp.exception.AuthenticationException;
import com.main.voiceoperationapp.exception.AudioException;
import com.main.voiceoperationapp.exception.ConverseException;
import com.main.voiceoperationapp.exception.DeviceRegisterException;
import com.main.voiceoperationapp.logger.Logger;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements AuthenticationHelper.Listener, DeviceRegister.Listener{

    private static String TAG = "VoiceOperationApp:MainActivity";

    public static final String EXTRA_MESSAGE = "";
    static final int RESULT_SUB_ACTIVITY = 1000;
    private static final int REQUEST_CODE = 2000;
    private int lang ;

    // テキスト入力後1秒間カウント
    long countNumber = 1000;
    long interval = 10;
    CountDown countDown = new CountDown(countNumber, interval);

    private EditText editText;
    private TextView textView;

    private static AuthenticationConf authenticationConf = null;
    private static AuthenticationHelper authenticationHelper = null;
    private static DeviceRegisterConf deviceRegisterConf = null;
    private static DeviceRegister deviceRegister = null;
    private static AssistantConf assistantConf = null;
    private static AssistantClient assistantClient = null;
    private static AudioConf audioConf = null;
    private static AudioRecorder audioRecorder = null;
    private static AudioPlayer audioPlayer = null;
    private static IoConf ioConf = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread Logger = new Logger(getApplicationContext());
        Logger.start();

        // 言語選択 0:日本語、1:英語、2:オフライン、その他:General
        lang = 0;

        // 認識結果を表示させる
        editText = findViewById(R.id.transcription_TextView);
        textView = findViewById(R.id.textView);

        Button buttonStart = findViewById(R.id.startRecording_Button);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("");
                editText.getEditableText().clear();
                // 音声認識を開始
                speech();
            }
        });

        // Initiating Scanner to take user input
        readText();

        try {
            main();
        } catch (AuthenticationException ex) {
            ex.printStackTrace();
            Log.e(TAG, ex.toString());
        } catch (AudioException ex) {
            ex.printStackTrace();
            Log.e(TAG, ex.toString());
        } catch (ConverseException ex) {
            ex.printStackTrace();
            Log.e(TAG, ex.toString());
        } catch (DeviceRegisterException ex) {
            ex.printStackTrace();
            Log.e(TAG, ex.toString());
        }
    }

    public void main() throws AuthenticationException, AudioException, ConverseException, DeviceRegisterException {
        Config root = ConfigFactory.load();
        Config conf = root.getConfig("authentication");
        authenticationConf = new AuthenticationConf(conf);
        conf = root.getConfig("deviceRegister");
        deviceRegisterConf = new DeviceRegisterConf(conf);
        conf = root.getConfig("assistant");
        assistantConf = new AssistantConf(conf);
        conf = root.getConfig("audio");
        audioConf = new AudioConf(conf);
        conf = root.getConfig("io");
        ioConf = new IoConf(conf);

        // Authentication
        authenticationHelper = new AuthenticationHelper(authenticationConf);
        authenticationHelper.setListener(this);
        if (authenticationHelper.checkFileExists()) {
            try {
                authenticationHelper
                        .authenticate(true)
                        .orElseThrow(() -> new AuthenticationException("Error during authentication"));
            } catch (AuthenticationException e) {
                e.printStackTrace();
            }
            checkRefreshNeed();
            registerDevice();
            buildClient();
            speech();
        }
        else{
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(getApplication(), SubActivity.class);
            intent.putExtra(EXTRA_MESSAGE, "");
            startActivityForResult( intent, RESULT_SUB_ACTIVITY );
        }
    }

    // Check if we need to refresh the access token to request the api
    private void checkRefreshNeed(){
        if (authenticationHelper.expired()) {
            try {
                authenticationHelper
                        .refreshAccessToken()
                        .orElseThrow(() -> new AuthenticationException("Error refreshing access token"));
            } catch (AuthenticationException e) {
                e.printStackTrace();
            }
        }
    }

    // Register Device model and device
    private void registerDevice(){
        deviceRegister = new DeviceRegister(deviceRegisterConf, authenticationHelper.getOAuthCredentials().getAccessToken());
        deviceRegister.setListener(this);
        try {
            deviceRegister.register();
        } catch (DeviceRegisterException e) {
            e.printStackTrace();
        }
    }

    // Build the client (stub)
    private void buildClient(){
        assistantClient = new AssistantClient(authenticationHelper.getOAuthCredentials(), assistantConf,
                deviceRegister.getDeviceModel(), deviceRegister.getDevice(), ioConf);
    }

    // editTexの入力確認
    public void readText(){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //テキスト変更前
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //テキスト変更中
            }

            @Override
            public void afterTextChanged(Editable s) {
                //テキスト変更後
                String inputStr= s.toString();
                if(inputStr.length() > 0){
                    countDown.cancel();
                    countDown.start();
                }
            }
        });
    }

    // editTextの入力終了検知後の処理
    private void endOfInput(){
        // アクセストークン更新
        if (authenticationHelper.expired()) {
            try {
                authenticationHelper
                        .refreshAccessToken()
                        .orElseThrow(() -> new AuthenticationException("Error refreshing access token"));
            } catch (AuthenticationException e) {
                e.printStackTrace();
            }

            // Update the token for the assistant client
            assistantClient.updateCredentials(authenticationHelper.getOAuthCredentials());
        }

        audioPlayer = new AudioPlayer(audioConf);

        // Get input (text or voice)
        byte[] request = editText.getText().toString().getBytes();

        // requesting assistant with text query
        try {
            Log.i(TAG, request.toString());
            assistantClient.requestAssistant(request);
        } catch (ConverseException e) {
            e.printStackTrace();
        }

//        try {
//            Thread.sleep(300);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        if( assistantClient.getTextResponse() != null){
            Log.i(TAG,assistantClient.getTextResponse());
            if(editText.length() != 0){
                textView.setText(assistantClient.getTextResponse());
                assistantClient.clearTextResponse();
            }
        }

        if (Boolean.TRUE.equals(ioConf.getOutputAudio())) {
            byte[] audioResponse = assistantClient.getAudioResponse();
            if (audioResponse != null && audioResponse.length > 0) {
                try {
                    audioPlayer.play(audioResponse);
                } catch (AudioException e) {
                    e.printStackTrace();
                }
            } else {
                Log.i(TAG,"No response from the API");
            }
        }
    }

    // テキスト入力後1秒間のカウントダウン
    class CountDown extends CountDownTimer {

        CountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            // 完了
            if(editText.getText().toString().length() > 0){
                endOfInput();
            }
        }

        // インターバルで呼ばれる
        @Override
        public void onTick(long millisUntilFinished) {
        }
    }

    // 音声入力処理
    private void speech() {
        // 音声認識の　Intent インスタンス
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        if(lang == 0){
            // 日本語
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.JAPAN.toString() );
        }
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 100);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "どうぞお話しください");
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        try {
            // インテント発行
            startActivityForResult(intent, REQUEST_CODE);
        }
        catch (ActivityNotFoundException e) {
            e.printStackTrace();
           textView.setText("音声を認識できませんでした");
        }
    }

    // SubActivity/音声入力処理 からの返しの結果を受け取る
    protected void onActivityResult( int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 認証コード入力後処理
        if(resultCode == RESULT_OK && requestCode == RESULT_SUB_ACTIVITY &&
                null != data) {
            AuthenticationHelper.AUTHENTICATION_CODE
                    = data.getStringExtra(MainActivity.EXTRA_MESSAGE);

            boolean authFlag = true;

            AuthenticationHelper authenticationHelper = new AuthenticationHelper(authenticationConf);
            authenticationHelper.setListener(this);
            try {
                authenticationHelper
                        .authenticate(false)
                        .orElseThrow(() -> new AuthenticationException("Error during authentication"));
            } catch (AuthenticationException e) {
                e.printStackTrace();
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("エラー")
                        .setMessage("認証コードが正しくありません。\nアプリケーションを終了します。")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        }).show();
                authFlag = false;
            }

            if(authFlag){
                checkRefreshNeed();
                registerDevice();
                buildClient();
                speech();
            }
        }

        // 音声解析処理
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE &&
                null != data){
            // 認識結果を ArrayList で取得
            ArrayList<String> candidates =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            if(candidates.size() > 0) {
                // 認識結果候補で一番有力なものを表示
                editText.setText( candidates.get(0));
            }
        }
    }

    // implements method
    @Override
    public void startBrowser(Intent intent){
        startActivity(intent);
    }

    @Override
    public String createFile(String fileName){
        String filePath = this.getFilesDir().getPath().toString() + fileName;
        File file = new File(filePath);
        return filePath;
    }

    @Override
    public boolean isAppInstalled(String appPackage) {
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(appPackage, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            Uri uri = Uri.parse("market://details?id="+appPackage);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            app_installed = false;
        }
        return app_installed ;
    }
}

