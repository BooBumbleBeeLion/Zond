package searchzond.fayz.zond.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import searchzond.fayz.zond.R;

public class InputActivity extends AppCompatActivity {
    // getInstance возвращает текущий экземпляр Firebase
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Инициализация элементов активити.
        final EditText btn_username = findViewById(R.id.btn_username);
        final EditText btn_password = findViewById(R.id.btn_password);

        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        Button btnSignIn = findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Получаем текст, переводим в string, убираем лишние пробелы вначале и в конце.
                String username = btn_username.getText().toString().trim();
                String pass = btn_password.getText().toString().trim();
                progressDialog = new ProgressDialog(InputActivity.this);
                progressDialog.setMessage("Вход...");
                progressDialog.show();
                mAuth.signInWithEmailAndPassword(username, pass)
                        .addOnCompleteListener(InputActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Если вход успешный, то переводим пользователя в рабочую область.
                                    progressDialog.dismiss();
                                    startActivity(new Intent(InputActivity.this, MainActivity.class));
                                    finish();
                                } else {
                                    // Если вход неудачный, то выводим на экран сообщение.
                                    progressDialog.dismiss();
                                    Toast.makeText(InputActivity.this, "Ошибка Авторизации.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
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