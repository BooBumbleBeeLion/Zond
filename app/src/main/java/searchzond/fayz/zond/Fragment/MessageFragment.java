package searchzond.fayz.zond.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import searchzond.fayz.zond.R;

public class MessageFragment extends Fragment {

    public View myView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_message_main, container, false);

        return myView;
    }
}
