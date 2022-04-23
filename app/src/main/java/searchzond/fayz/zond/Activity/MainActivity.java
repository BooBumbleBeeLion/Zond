package searchzond.fayz.zond.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import searchzond.fayz.zond.Fragment.AddFragment;
import searchzond.fayz.zond.Fragment.ListFragment;
import searchzond.fayz.zond.Fragment.MainFragment;
import searchzond.fayz.zond.Fragment.MessageFragment;
import searchzond.fayz.zond.Fragment.ProfileFragment;
import searchzond.fayz.zond.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        ImageView homeBtn = findViewById(R.id.homeBtn);
        ImageView buttonSearch = findViewById(R.id.searchBtn);
        ImageView buttonAdd = findViewById(R.id.addBtn);
        ImageView buttonMessage = findViewById(R.id.messageBtn);
        ImageView buttonProfile = findViewById(R.id.profileBtn);

        // Отрывает первым окном заданный фрагмент
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragment_conteiner, new MainFragment()).commit();
        }

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.fragment_conteiner, new MainFragment()).commit();
                } catch (Exception e) { } //конец конструкции
            }
        });

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.fragment_conteiner, new ListFragment()).addToBackStack(null).commit();
                } catch (Exception e) { } //конец конструкции
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.fragment_conteiner, new AddFragment()).addToBackStack(null).commit();

                } catch (Exception e) { } //конец конструкции
            }
        });

        buttonMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.fragment_conteiner, new MessageFragment()).addToBackStack(null).commit();
                } catch (Exception e) { } //конец конструкции
            }
        });

        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.fragment_conteiner, new ProfileFragment()).addToBackStack(null).commit();
                } catch (Exception e) { } //конец конструкции
            }
        });

    }


    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        // Если стек фрагментов равен нулю, то обрабатываем выход из приложения, иначе просто переходим к прошлому фрагменту.
        if (count == 0) {
            AlertDialog.Builder quitDialog = new AlertDialog.Builder(this);
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
