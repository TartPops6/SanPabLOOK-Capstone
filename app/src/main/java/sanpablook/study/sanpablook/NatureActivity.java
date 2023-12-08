package sanpablook.study.sanpablook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NatureActivity extends AppCompatActivity {

    private Button imagebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nature);

        View button = findViewById(R.id.sampaloc_lake_pic);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(NatureActivity.this, NatureSampalocLakeActivity.class);
                startActivity(intent);

            }
        });

        return;
    }
}