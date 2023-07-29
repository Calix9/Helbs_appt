package com.example.helbs_apptz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                        .setTitle("please Verify")
                        .setDescription("Taarifa ya mnufaika inaitajika")
                        .setNegativeButtonText("rudi")
                        .build();
                getPrompt().authenticate(promptInfo);
            }
        });


    }
   private BiometricPrompt getPrompt(){
       Executor executor = ContextCompat.getMainExecutor(this);
       BiometricPrompt.AuthenticationCallback callback = new BiometricPrompt.AuthenticationCallback() {
           @Override
           public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
               super.onAuthenticationError(errorCode, errString);
               notifyUser(errString.toString());
           }

           @Override
           public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
               super.onAuthenticationSucceeded(result);
               notifyUser("Authentication Successfully!!!");
               Intent intent = new Intent(MainActivity.this,SecretActivity.class);
               startActivity(intent);
           }

           @Override
           public void onAuthenticationFailed() {
               super.onAuthenticationFailed();
               notifyUser("Authentification failed!!!");

           }
       };
       BiometricPrompt BiometricPrompt = new BiometricPrompt(this, executor,callback);
       return BiometricPrompt;
   }

   private void  notifyUser(String message){
       Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
   }
}