package searchzond.fayz.zond.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import searchzond.fayz.zond.Class.User;
import searchzond.fayz.zond.R;

import static android.app.Activity.RESULT_OK;

public class SearchLevelFragment extends Fragment {

    View myView;
    String name, myString, thing, place, color, day, marka, content, image;
    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    DatabaseReference myRef;
    Bitmap bitmap;
    Bundle bundle;
    ImageView add_foto;
    ImageView add_foto2;
    ProgressDialog pd;
    Intent intent;
    int PICK_IMAGE_MULTIPLE = 1;
    int count;
    Context context;
    private String yearSpinner, monthSpinner, daySpinner;
    private String[]
            years = { "2020", "2021" },
            months = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"},
            days = new String[31];

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_search_level, container, false);
        context = inflater.getContext();

        bundle = getArguments();
        myString = bundle.getString("key");

        // Текущее состояние аутентификации.
        firebaseAuth = FirebaseAuth.getInstance();
        // Диалог прогресса на нашей активити.
        pd = new ProgressDialog(getActivity());
        // Текущее состояние User
        user = firebaseAuth.getCurrentUser();

        // Инициализация полей ввода
        final EditText messageName = myView.findViewById(R.id.message_name_thing);
        final EditText messagePlace = myView.findViewById(R.id.message_place);
        final EditText messageColor = myView.findViewById(R.id.message_color);
        final EditText messageModel = myView.findViewById(R.id.message_model);
        final EditText messageContent = myView.findViewById(R.id.message_content);
// <Блок спиннеров даты>
        for (int j = 1; j <= 31; j++){
            days[j-1] = String.valueOf(j);
        }
        final Spinner spinnerYears = myView.findViewById(R.id.spinnerYears);
        final Spinner spinnerMonths = myView.findViewById(R.id.spinnerMonths);
        final Spinner spinnerDays = myView.findViewById(R.id.spinnerDays);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, years);
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, months);
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, days);
        // Определяем разметку для использования при выборе элемента
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinnerYears.setAdapter(yearAdapter);
        spinnerMonths.setAdapter(monthAdapter);
        spinnerDays.setAdapter(dayAdapter);
        // Получаем выбранные в спиннере значения.
        spinnerYears.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                yearSpinner = years[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerMonths.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                monthSpinner = months[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerDays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                daySpinner = days[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
// <Блок спиннеров даты/>
        add_foto = myView.findViewById(R.id.add_foto);
        add_foto2 = myView.findViewById(R.id.add_foto2);
        ImageView goHome = myView.findViewById(R.id.button_close);
        ImageView goBack = myView.findViewById(R.id.button_back);

        //viewFoto = myView.findViewById(R.id.viewPhoto);
        add_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Выберите картинку"), PICK_IMAGE_MULTIPLE);
            }
        });


        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_conteiner, new MainFragment()).addToBackStack(null).commit();
            }
        });


        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_conteiner, new AddCategoryLevelFragment()).addToBackStack(null).commit();
            }
        });

        TextView add_text = myView.findViewById(R.id.add_text);
        ImageView add_image = myView.findViewById(R.id.add_img);

        switch (myString) {

            case "wallet":
                name = "Кошелёк";
                add_text.setText("Кошелёк");
                add_image.setImageResource(R.drawable.search_wallet);
                break;

            case "keys":
                name = "Ключи";
                add_text.setText("Ключи");
                add_image.setImageResource(R.drawable.search_keys);
                break;

            case "docs":
                name = "Документы";
                add_text.setText("Документы");
                add_image.setImageResource(R.drawable.search_documents);
                break;

            case "bag":
                name = "Сумка";
                add_text.setText("Сумка");
                add_image.setImageResource(R.drawable.search_bag);
                break;

            case "electron":
                name = "Электроника";
                add_text.setText("Электроника");
                add_image.setImageResource(R.drawable.search_electronics);
                break;

            case "pet":
                name = "Животные";
                add_text.setText("Животные");
                add_image.setImageResource(R.drawable.search_pets);
                break;

            case "other":
                name = "Другое";
                add_text.setText("Другое");
                add_image.setImageResource(R.drawable.search_other);
                break;
        }

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("search");


        myRef.child(myString).child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                count = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Button buttonAdd = myView.findViewById(R.id.buttonAdd);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (messageName.getText().length() >= 2 && messagePlace.getText().length() >= 2 && image != null &&
                        messageColor.getText().length() >= 0 && messageModel.getText().length() >= 0 && messageContent.getText().length() >= 2) {

                    count++;

                    thing = messageName.getText().toString();
                    place = messagePlace.getText().toString();
                    color = messageColor.getText().toString();
                    marka = messageModel.getText().toString();
                    content = messageContent.getText().toString();

                    User user = new User(image, thing, place, yearSpinner, monthSpinner, daySpinner, color, marka, content);

                    myRef.child(myString).child(firebaseAuth.getUid()).child("thing" + count).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), name + "  отправлен на поиск", Toast.LENGTH_SHORT).show();
                                messageName.setText("");
                                messagePlace.setText("");
                                messageColor.setText("");
                                messageModel.setText("");
                                messageContent.setText("");
                            } else {
                                Toast.makeText(getContext(), "Что-то пошло не так", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    add_foto.setVisibility(View.VISIBLE);
                    add_foto2.setVisibility(View.GONE);
                    add_foto2.setImageBitmap(null);
                    bitmap = null;
                    image = null;
                } else {
                    Toast.makeText(getContext(), "Есть незаполненные поля", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return myView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            // Когда изображение выбрано
            pd.setMessage("Загрузка изображения...");
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK
                    && data.getData() != null) {

                Uri mImageUri = data.getData();
                pd.show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                    bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getActivity().getContentResolver(), mImageUri));
                else
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), mImageUri);

                add_foto.setVisibility(View.GONE);
                add_foto2.setVisibility(View.VISIBLE);
                add_foto2.setImageBitmap(bitmap);

                final StorageReference profileImageRef = FirebaseStorage.getInstance().getReference("search/" + user.getUid() + "_" + name + "_" + "thing" + count+1 + ".jpg");

                if (mImageUri != null) {
                    profileImageRef.putFile(mImageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    profileImageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            image = task.getResult().toString();
                                            pd.dismiss();
                                        }
                                    });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                }
                            });
                }
            } else {
                Toast.makeText(getContext(), "Вы не выбрали изображение!",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Что-то пошло не так " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
}