package com.main.voiceoperationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.main.voiceoperationapp.authentication.AuthenticationHelper;
import com.main.voiceoperationapp.config.AuthenticationConf;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;


public class SubActivity extends AppCompatActivity implements AuthenticationHelper.Listener{

    private EditText editText;
    private String message;
    private DialogFragment dialog;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        // to get message from MainActivity
        Intent intent = getIntent();
        message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        editText = findViewById(R.id.edit_text);

        Config root = ConfigFactory.load();
        Config conf = root.getConfig("authentication");
        AuthenticationConf authenticationConf = new AuthenticationConf(conf);
        AuthenticationHelper authenticationHelper = new AuthenticationHelper(authenticationConf);
        authenticationHelper.setListener(this);
        try {
            authenticationHelper.showAuthenticateBrowser();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // back to MainActivity
        Button button = findViewById(R.id.ok_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                if (editText.getText().length() != 0) {
                    String str = message + editText.getText().toString();
                    intent.putExtra(MainActivity.EXTRA_MESSAGE, str);
                    editText.setText("");
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else{
                    fragmentManager = getSupportFragmentManager();
                    dialog = new AlertDialogFragment();
                    dialog.show(fragmentManager, null);
                }
            }
        });
    }

    public static class AlertDialogFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity())
                    .setTitle("エラー")
                    .setMessage("認証コードが入力されていません。")
                    .setPositiveButton("OK", null)
                    .create();
        }

        @Override
        public void onPause() {
            super.onPause();

            // onPause でダイアログを閉じる場合
            dismiss();
        }
    }

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
