package searchzond.fayz.zond.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import searchzond.fayz.zond.R;

public class AddFragment extends Fragment {

    public View myView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_add, container, false);

        ImageView home = myView.findViewById(R.id.add_button_close);
        RelativeLayout found = myView.findViewById(R.id.but);
        RelativeLayout lost = myView.findViewById(R.id.but2);

        // Слушатель кнопки крестика => Перемещение на MainFragment.
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    requireActivity().getSupportFragmentManager().beginTransaction().
                            replace(R.id.fragment_conteiner, new MainFragment()).addToBackStack(null).commit();
                } catch (Exception e) { e.printStackTrace(); }
            }
        });
        // Слушатель кнопки НАШЁЛ
        found.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    requireActivity().getSupportFragmentManager().beginTransaction().
                            replace(R.id.fragment_conteiner, new AddCategoryLevelFragment()).addToBackStack(null).commit();
                } catch (Exception e) { e.printStackTrace(); }
            }
        });
        // Слушатель кнопки ПОТЕРЯЛ
        lost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    requireActivity().getSupportFragmentManager().beginTransaction().
                            replace(R.id.fragment_conteiner, new SearchCategoryLevelFragment()).addToBackStack(null).commit();
                } catch (Exception e) { e.printStackTrace(); }
            }
        });


        return myView;
    }
}
