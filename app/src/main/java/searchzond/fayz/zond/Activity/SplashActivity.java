package searchzond.fayz.zond.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;

import searchzond.fayz.zond.R;

/**
 * SplashActivity нужен для базовой проверки аутентификации и подключения к интернету
 * Во время проверок показывается Splash-логотип приложения длительностью SPLASH_DISPLAY_LENGHT
 * */

public class SplashActivity extends Activity {
//      Проверка наличия авторизации
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
//      Время задержки SplashActivity
    final int SPLASH_DISPLAY_LENGHT = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash );

//      getConnectionType возвращает состояние подключения к интернету, где 0-отсутствует, 1-мобильная связь, 2-вайфай
        final int check = NoInternetActivity.getConnectionType(getApplicationContext());

        Window w = getWindow();
        w.setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );

        View decorView = getWindow().getDecorView();

        int uiOptions =   View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                       | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility( uiOptions );

//      Сначала задержка -> Проверка check подключения к инету -> Проверка mAuth авторизации
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (check != 0) {
                    if (mAuth.getCurrentUser() != null) {
                        SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    } else {
                        SplashActivity.this.startActivity(new Intent(SplashActivity.this, InputActivity.class));
                    }
                }
                else{
                    SplashActivity.this.startActivity(new Intent(SplashActivity.this, NoInternetActivity.class));
                }
                finish();
            }
        }, SPLASH_DISPLAY_LENGHT );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
