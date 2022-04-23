package searchzond.fayz.zond.Activity;


import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import searchzond.fayz.zond.R;


public class DopActivity extends AppCompatActivity {

    TextView dopText, dopText1, dopText2, dopText3, dopText4, dopText5;
    ImageView add_foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dop);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        add_foto = findViewById(R.id.add_foto);
        dopText = findViewById(R.id.dopText);
        dopText1 = findViewById(R.id.dopText1);
        dopText2 = findViewById(R.id.dopText2);
        dopText3 = findViewById(R.id.dopText3);
        dopText4 = findViewById(R.id.dopText4);
        dopText5 = findViewById(R.id.dopText5);

        Picasso.get().load(getIntent().getStringExtra("image")).into(add_foto);
        dopText.setText(getIntent().getStringExtra("dopText1"));
        dopText1.setText(getIntent().getStringExtra("dopText2"));
        dopText2.setText(getIntent().getStringExtra("dopText3"));
        dopText3.setText(getIntent().getStringExtra("dopText4"));
        dopText4.setText(getIntent().getStringExtra("dopText"));
        dopText5.setText(getIntent().getStringExtra("dopText5"));

        ImageView buttonHome = findViewById(R.id.button_back);

        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
