package sanpablook.study.sanpablook;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.study.sanpablook.R;

import sanpablook.study.sanpablook.HomeFragment;

public class testMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testmainactivity);

        String fragmentName = getIntent().getStringExtra("BottomNavBar");
        if ("BottomNavBar".equals(fragmentName)) {
            Intent intent = new Intent(this, BottomNavBar.class);
            startActivity(intent);
            finish();
        }
    }
}