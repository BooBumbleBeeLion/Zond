package searchzond.fayz.zond.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Pattern;

import searchzond.fayz.zond.R;


public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Инициализация элементов активити.
        final EditText emailField = findViewById(R.id.emailField);
        final EditText passField = findViewById(R.id.passField);
        final EditText passConfirmField = findViewById(R.id.passConfirmField);
        final EditText nameField = findViewById(R.id.nameField);
        final EditText phoneField = findViewById(R.id.phoneField);
        final Button btnRegister = findViewById(R.id.btnRegister);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Регистрация...");

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Запись всех полей EditText в отдельные переменные
                final String pass = passField.getText().toString().trim();
                final String passConfirm = passConfirmField.getText().toString().trim();
                final String email = emailField.getText().toString().trim();
                final String name = nameField.getText().toString().trim();
                final String phone = phoneField.getText().toString().trim();
                // Запись даты регистрации
                Date currentDate = new Date();
                DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
                final String dateOfRegistration = dateFormat.format(currentDate);
                // Создание паттерна номера телефона
                Pattern pattern = Pattern.compile("(8|\\+7)\\d{10}");
                // Проверка всех полей на корректность вводимых данных
                if(!pattern.matcher(phone).matches()){
                    phoneField.setError("Неверно указан номер телефона");
                    phoneField.setFocusable(true);
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    emailField.setError("Неверный email");
                    emailField.setFocusable(true);
                }
                else if (pass.length()<6)
                {
                    passField.setError("Слишком короткий пароль, минимум 6 символов ");
                    passField.setFocusable(true);
                }
                else if (!passConfirm.equals(pass)) {
                    passConfirmField.setError("Пароли не совпадают!");
                    passConfirmField.setFocusable(true);
                }
                else if (name.length()<3) {
                    nameField.setError("Слишком корткое имя, минимум 3 символа");
                    nameField.setFocusable(true);
                }
                else{
                    // Показываем прогресс
                    progressDialog.show();
                    // Создаем юзера по email и pass =>
                    // => Проверка успешности регистрации с помощью слушателя addOnCompleteListener на активити регистрации по классу FireBase-AuthResult
                    mAuth.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Если регистрация успешная, переводим пользователя в рабочую область.
                                        progressDialog.dismiss();

                                        FirebaseUser user = mAuth.getCurrentUser();
                                        String Email = user.getEmail();
                                        String uid = user.getUid();

                                        // Создаем ассоциативный массив ключ-значение для занесения пунктов регистрации
                                        HashMap<Object, String> hashMap = new HashMap<>();
                                        hashMap.put("email", Email);
                                        hashMap.put("uid", uid);
                                        hashMap.put("phone", phone);
                                        hashMap.put("name", name);
                                        hashMap.put("image", "");
                                        hashMap.put("dateOfRegistration",dateOfRegistration );
                                        hashMap.put("address", "");
                                        hashMap.put("nickname", "");

                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        // Получаем ссылку на базу данных Users
                                        DatabaseReference databaseReference = database.getReference("Users");
                                        databaseReference.child(uid).setValue(hashMap);


                                        Toast.makeText(RegisterActivity.this, "Регистрация прошла успешно.",
                                                Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                        finish();
                                    } else {
                                        // Если регистрация неудачная, вывести это
                                        progressDialog.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Ошибка авторизации.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(RegisterActivity.this, "" + e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                    });
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // updateUI(currentUser);
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(RegisterActivity.this, InputActivity.class));
        finish();
    }
}


