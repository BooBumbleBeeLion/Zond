package searchzond.fayz.zond.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import searchzond.fayz.zond.Activity.MainActivity;
import searchzond.fayz.zond.Class.Adapter;
import searchzond.fayz.zond.Class.Adapter2;
import searchzond.fayz.zond.Class.MainItems;
import searchzond.fayz.zond.Class.User;
import searchzond.fayz.zond.R;

public class ListFragment extends Fragment {

    public View myView;
    private String thing, year, month, day, color, marka, place, name, name2, image, content;
    private DatabaseReference myRef, myRef2;
    private FirebaseDatabase database;
    private FirebaseAuth firebaseAuth;
    private ArrayList<MainItems> Items = new ArrayList<>();
    private ArrayList<MainItems> Items2 = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private Adapter adapter;
    private Adapter2 adapter2;
    private Context context;

    private String category, yearSpinner, monthSpinner, daySpinner;
    private String[]    categoryes = { "Кошелёк", "Ключи", "Документы", "Сумка", "Электроника", "Животные", "Другое"},
                        years = { "2020", "2021" },
                        months = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"},
                        days = new String[31];


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.activity_list, container, false);
        // Текущий контекст
        context = inflater.getContext();

        ImageView goHome = myView.findViewById(R.id.button_close);

        // Слушатель кнопки назад
        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_conteiner, new MainFragment()).addToBackStack(null).commit();
            }
        });
        // Заполняем массив days :D
        for (int j = 1; j <= 31; j++){
            days[j-1] = String.valueOf(j);
        }

        final TextView categoryView = myView.findViewById(R.id.categoryView);
        final TextView dateView = myView.findViewById(R.id.dateView);
        final Button buttonSearch = myView.findViewById(R.id.buttonSearch);

        final Spinner spinnerCategory = myView.findViewById(R.id.spinnerCategory);
        final Spinner spinnerYears = myView.findViewById(R.id.spinnerYears);
        final Spinner spinnerMonths = myView.findViewById(R.id.spinnerMonths);
        final Spinner spinnerDays = myView.findViewById(R.id.spinnerDays);

        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, categoryes);
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, years);
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, months);
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, days);

        // Определяем разметку для использования при выборе элемента
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Применяем адаптер к элементу spinner
        spinnerCategory.setAdapter(categoryAdapter);
        spinnerYears.setAdapter(yearAdapter);
        spinnerMonths.setAdapter(monthAdapter);
        spinnerDays.setAdapter(dayAdapter);

        // Работа с получением выбранной категории
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Получаем выбранный объект
                String item = (String)parent.getItemAtPosition(position);
                categoryView.setText(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinnerCategory.setOnItemSelectedListener(itemSelectedListener);
        // Выбранные день/месяц/год.
        spinnerYears.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                yearSpinner = years[i];
                dateView.setText("Потерян " + daySpinner + "." + monthSpinner + "." + yearSpinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerMonths.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                monthSpinner = months[i];
                dateView.setText("Потерян " + daySpinner + "." + monthSpinner + "." + yearSpinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerDays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                daySpinner = days[i];
                dateView.setText("Потерян " + daySpinner + "." + monthSpinner + "." + yearSpinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Получаем текущее состояние аутентификации
        firebaseAuth = FirebaseAuth.getInstance();

        // Идентифицируем ресайклеры
        recyclerView = myView.findViewById(R.id.recyclerView1);
        recyclerView2 = myView.findViewById(R.id.recyclerView2);

        // Оптимизация путем задания фиксированных размеров ресайклеров
        recyclerView.setHasFixedSize(true);
        recyclerView2.setHasFixedSize(true);

        // Задает нашему RecyclerView реализацию по объекту создания LinearLayoutManager по контексту MainActivity в Горизонтальном виде
        recyclerView.setLayoutManager(new LinearLayoutManager(new MainActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView2.setLayoutManager(new LinearLayoutManager(new MainActivity(), LinearLayoutManager.VERTICAL, false));

        // Создаем объект класса Adapter(2) в который подаем Listы Items по текущему контексту
        adapter = new Adapter(Items, myView.getContext());
        adapter2 = new Adapter2(Items2, myView.getContext());

        // Текущий экземпляр FirebaseDatabase
        database = FirebaseDatabase.getInstance();

        // Находим в иерархии пути add и search
        myRef = database.getReference("add");
        myRef2 = database.getReference("search");

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Считываем поле
                category = getEn(categoryView.getText().toString());
                // Выводим поле
                Toast.makeText(context, category, Toast.LENGTH_SHORT).show();
                // Очищаем лист
                Items.clear();
                Items2.clear();
                // Спускаемся по ветке до этой категории в add
                Query query_search = myRef.child(category);//.startAt("3").endAt("3"+"\uf8ff");
                // в search
                Query query_search2 = myRef2.child(category);
                // Считываем все значения в этой ветке
                query_search.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // snapshot это все данные ветки category; ds-все дети snapshot; ds1-все дети ds(дошли до thing а в нем уже структура User)
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            for (DataSnapshot ds1 : ds.getChildren()) {
                                // Пихаем конкретный thing в соответствующий класс User
                                User user = ds1.getValue(User.class);
                                try {
                                    if (    Integer.parseInt(user.getYear()) >= Integer.parseInt(yearSpinner) &&
                                            Integer.parseInt(user.getMonth()) >= Integer.parseInt(monthSpinner) &&
                                            Integer.parseInt(user.getDay()) >= Integer.parseInt(daySpinner)){
                                        // Считываем значения
                                        image = user.getImage();
                                        thing = user.getThing();
                                        place = user.getPlace();
                                        year = user.getYear();
                                        month = user.getMonth();
                                        day = user.getDay();
                                        color = user.getColor();
                                        marka = user.getMarka();
                                        content = user.getContent();
                                        // Добавляем в лист карточек отображения
                                        Items.add(new MainItems(image, getRus(Objects.requireNonNull(snapshot.getKey())) + " - \"" + marka + "\"", thing, place, year, month, day, color, content));
                                    }
                                }catch (Exception e){

                                }
                            }
                        }
                        // Обновляем ресайклер
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(context, "Ууупс...", Toast.LENGTH_SHORT).show();
                    }
                });
                query_search2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            for (DataSnapshot ds1 : ds.getChildren()) {
                                User user = ds1.getValue(User.class);
                                try {
                                    if (    Integer.parseInt(user.getYear()) >= Integer.parseInt(yearSpinner) &&
                                            Integer.parseInt(user.getMonth()) >= Integer.parseInt(monthSpinner) &&
                                            Integer.parseInt(user.getDay()) >= Integer.parseInt(daySpinner)){
                                        // Считываем значения
                                        image = user.getImage();
                                        thing = user.getThing();
                                        place = user.getPlace();
                                        year = user.getYear();
                                        month = user.getMonth();
                                        day = user.getDay();
                                        color = user.getColor();
                                        marka = user.getMarka();
                                        content = user.getContent();
                                        // Добавляем в лист карточек отображения
                                        Items2.add(new MainItems(image, getRus(Objects.requireNonNull(snapshot.getKey())) + " - \"" + marka + "\"", thing, place, year, month, day, color, content));
                                    }
                                }catch (Exception e){

                                }
                            }
                        }
                        // Обновляем ресайклер
                        recyclerView2.setAdapter(adapter2);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(context, "Ууупс...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return myView;
    }

    public String getRus(String s) {
        switch (s) {
            case "wallet":
                return "Кошелёк";
            case "keys":
                return "Ключи";
            case "docs":
                return "Документы";
            case "bag":
                return "Сумка";
            case "electron":
                return "Электроника";
            case "pet":
                return "Животные";
            case "other":
                return "Другое";
            default: return "Ошибка";
        }
    }
    public String getEn(String s) {
        switch (s) {
            case "Кошелёк":
                return "wallet";
            case "Ключи":
                return "keys";
            case "Документы":
                return "docs";
            case "Сумка":
                return "bag";
            case "Электроника":
                return "electron";
            case "Животные":
                return "pet";
            case "Другое":
                return "other";
            default: return "Ошибка";
        }
    }
}
