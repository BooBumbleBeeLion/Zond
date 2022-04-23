package searchzond.fayz.zond.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.IntRange;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.auth.FirebaseAuth;

import searchzond.fayz.zond.R;

public class NoInternetActivity extends AppCompatActivity {
    //  Проверка наличия авторизации
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);

        //  Инициализируем наш SwipeRefreshLayout на активити
        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //  getConnectionType возвращает состояние подключения к интернету, где 0-отсутствует, 1-мобильная связь, 2-вайфай
                final int check = getConnectionType(getApplicationContext());
                // Если подключение ЕСТЬ, то перекидываем в рабочую область.
                if (check == 1 || check == 2) {
                    if(mAuth.getCurrentUser() != null) {
                        startActivity(new Intent(NoInternetActivity.this, MainActivity.class));
                    }
                    else{
                        startActivity(new Intent(NoInternetActivity.this, InputActivity.class));
                    }
                    finish();
                }
                // ИНАЧЕ просто ничего не делаем, не размножаем активити.
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_green_light, android.R.color.holo_red_dark);
    }


//  Проверка подключения к интернету
    @IntRange(from = 0, to = 2)
    public static int getConnectionType(Context context) {
        int result = 0; // Returns connection type. 0: none; 1: mobile data; 2: wifi
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (cm != null) {
                NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        result = 2;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        result = 1;
                    }
                }
            }
        } else {
            if (cm != null) {
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if (activeNetwork != null) {
                    // connected to the internet
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                        result = 2;
                    } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                        result = 1;
                    }
                }
            }
        }
        return result;
    }


    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        // Если стек фрагментов равен нулю, то обрабатываем выход из приложения, иначе просто переходим к прошлому фрагменту.
        if (count == 0) {
            android.app.AlertDialog.Builder quitDialog = new AlertDialog.Builder(this);
            quitDialog.setTitle("Вы уверены, что хотите выйти?");

            quitDialog.setPositiveButton("Уверен", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            quitDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                }
            });

            quitDialog.show();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }
}


