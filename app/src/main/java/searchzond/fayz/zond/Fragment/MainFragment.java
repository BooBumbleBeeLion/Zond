package searchzond.fayz.zond.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import searchzond.fayz.zond.Activity.InputActivity;
import searchzond.fayz.zond.Activity.MainActivity;
import searchzond.fayz.zond.Class.Adapter;
import searchzond.fayz.zond.Class.Adapter2;
import searchzond.fayz.zond.Class.MainItems;
import searchzond.fayz.zond.Class.User;
import searchzond.fayz.zond.R;


public class MainFragment extends Fragment {

    public View myView;
    private String thing, year, month, day, color, marka, place, name, name2, image, content;
    private DatabaseReference myRef, myRef2;
    private FirebaseDatabase database;
    private int vid = 0;
    private int vid2 = 0;
    private ArrayList<MainItems> Items = new ArrayList<>();
    private ArrayList<MainItems> Items2 = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private Adapter adapter;
    private Adapter2 adapter2;
    private Context context;
    private FirebaseAuth firebaseAuth;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Выставляем вью фрагмента по fragment_main
        myView = inflater.inflate(R.layout.fragment_main, container, false);
        // Получаем текущее состояние аутентификации
        firebaseAuth = FirebaseAuth.getInstance();
        ImageView likeBtn = myView.findViewById(R.id.likeBtn);

        // Слушатель избранного
        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    requireActivity().getSupportFragmentManager().beginTransaction().
                            replace(R.id.fragment_conteiner, new LikeFragment()).addToBackStack(null).commit();
                } catch (Exception e) { } //конец конструкции
            }
        });
        // Текущий контекст
        context = inflater.getContext();
        // Идентифицируем ресайклеры
        recyclerView = myView.findViewById(R.id.recyclerView);
        recyclerView2 = myView.findViewById(R.id.recyclerView2);

        // Оптимизация путем задания фиксированных размеров ресайклеров
        recyclerView.setHasFixedSize(true);
        recyclerView2.setHasFixedSize(true);

        // Задает нашему RecyclerView реализацию по объекту создания LinearLayoutManager по контексту MainActivity в Горизонтальном виде
        recyclerView.setLayoutManager(new LinearLayoutManager(new MainActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setLayoutManager(new LinearLayoutManager(new MainActivity(), LinearLayoutManager.HORIZONTAL, false));

        // Создаем объект класса Adapter(2) в который подаем Listы Items по текущему контексту
        adapter = new Adapter(Items, myView.getContext());
        adapter2 = new Adapter2(Items2, myView.getContext());

        // Текущий экземпляр FirebaseDatabase
        database = FirebaseDatabase.getInstance();

        myRef = database.getReference("add");
        myRef2 = database.getReference("search");

        // Перебирая все виды найденных вещей добавляем их в List Items с их данными
        while (vid < 7) {
            switch (vid) {
                case 0:
                    name = "wallet";
                    break;
                case 1:
                    name = "keys";
                    break;
                case 2:
                    name = "docs";
                    break;
                case 3:
                    name = "bag";
                    break;
                case 4:
                    name = "electron";
                    break;
                case 5:
                    name = "pet";
                    break;
                case 6:
                    name = "other";
                    break;
            }

            myRef.child(name).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds : dataSnapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot1 : ds.getChildren()) {
                            User user = dataSnapshot1.getValue(User.class);
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
                            Items.add(new MainItems(image, getRus(Objects.requireNonNull(dataSnapshot.getKey())) + " - \"" + marka + "\"", thing, place, year, month, day, color, content));
                        }
                    }
                    recyclerView.setAdapter(adapter);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(new MainActivity(), "Ууупс...", Toast.LENGTH_SHORT).show();
                }
            });
            vid++;
        }

        while (vid2 < 7) {
            switch (vid2) {
                case 0:
                    name2 = "wallet";
                    break;
                case 1:
                    name2 = "keys";
                    break;
                case 2:
                    name2 = "docs";
                    break;
                case 3:
                    name2 = "bag";
                    break;
                case 4:
                    name2 = "electron";
                    break;
                case 5:
                    name2 = "pet";
                    break;
                case 6:
                    name2 = "other";
                    break;
            }

            myRef2.child(name2).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds : dataSnapshot.getChildren()) {
                        for (DataSnapshot ds1 : ds.getChildren()) {
                            User user = ds1.getValue(User.class);
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
                            Items2.add(new MainItems(image, getRus(Objects.requireNonNull(dataSnapshot.getKey())) + " - \"" + marka + "\"", thing, place, year, month, day, color, content));
                        }
                    }
                    recyclerView2.setAdapter(adapter2);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(new MainActivity(), "Ууупс...", Toast.LENGTH_SHORT).show();
                }
            });

            vid2++;
        }

        return myView;
    }

    private void checkUserStatus() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){}
        else{
            startActivity(new Intent(context, InputActivity.class));
            getActivity().finish();
        }
    }
    // Метод перевода категории для русскоязычного отображения
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
}
