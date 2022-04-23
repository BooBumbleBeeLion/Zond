package searchzond.fayz.zond.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import searchzond.fayz.zond.R;

import java.util.Objects;

public class AddCategoryLevelFragment extends Fragment {

    public View myView;
    String value;
    String myString;
    Fragment fragment;
    Bundle bundle;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_add_category_level, container, false);

        LinearLayout wallet = myView.findViewById(R.id.wallet_layout);
        LinearLayout keys = myView.findViewById(R.id.keys_layout);
        LinearLayout docs = myView.findViewById(R.id.docs_layout);
        LinearLayout bag = myView.findViewById(R.id.bag_layout);
        LinearLayout electron = myView.findViewById(R.id.electron_layout);
        LinearLayout pet = myView.findViewById(R.id.pet_layout);
        LinearLayout other = myView.findViewById(R.id.other_layout);

        fragment = new AddLevelFragment();
        bundle = new Bundle();

        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    value = "wallet";
                    bundle.putString("key", value);
                    fragment.setArguments(bundle);

                    requireActivity().getSupportFragmentManager().beginTransaction().
                            replace(R.id.fragment_conteiner, fragment).addToBackStack(null).commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        keys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    value = "keys";
                    bundle.putString("key", value);
                    fragment.setArguments(bundle);

                    requireActivity().getSupportFragmentManager().beginTransaction().
                            replace(R.id.fragment_conteiner, fragment).addToBackStack(null).commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        docs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    value = "docs";
                    bundle.putString("key", value);
                    fragment.setArguments(bundle);

                    requireActivity().getSupportFragmentManager().beginTransaction().
                            replace(R.id.fragment_conteiner, fragment).addToBackStack(null).commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        bag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    value = "bag";
                    bundle.putString("key", value);
                    fragment.setArguments(bundle);

                    requireActivity().getSupportFragmentManager().beginTransaction().
                            replace(R.id.fragment_conteiner, fragment).addToBackStack(null).commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        electron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    value = "electron";
                    bundle.putString("key", value);
                    fragment.setArguments(bundle);

                    requireActivity().getSupportFragmentManager().beginTransaction().
                            replace(R.id.fragment_conteiner, fragment).addToBackStack(null).commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    value = "pet";
                    bundle.putString("key", value);
                    fragment.setArguments(bundle);

                    requireActivity().getSupportFragmentManager().beginTransaction().
                            replace(R.id.fragment_conteiner, fragment).addToBackStack(null).commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    value = "other";
                    bundle.putString("key", value);
                    fragment.setArguments(bundle);

                    requireActivity().getSupportFragmentManager().beginTransaction().
                            replace(R.id.fragment_conteiner, fragment).addToBackStack(null).commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        return myView;
    }
}