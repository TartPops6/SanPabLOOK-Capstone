package sanpablook.study.sanpablook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.study.sanpablook.R;

public class ProductMenu extends AppCompatActivity {
    ImageView Coconut, Pinayte, Ube, Cacao, Kape, Lettuce, Handicrafts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_menu);

        Coconut = findViewById(R.id.Coconut);
        Pinayte = findViewById(R.id.PinayteProduct);
        Ube = findViewById(R.id.Ube);
        Cacao = findViewById(R.id.Cacao);
        Kape = findViewById(R.id.Kape);
        Lettuce = findViewById(R.id.Lettuce);
        Handicrafts = findViewById(R.id.handicrafts);

        Coconut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductMenu.this,ColettesBukoPie.class);
                startActivity(intent);
            }
        });

        Pinayte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductMenu.this,Pinayte.class);
                startActivity(intent);
            }
        });

        Ube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductMenu.this,UbeMariaAna.class);
                startActivity(intent);
            }
        });

        Cacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductMenu.this,TanimAgriCacao.class);
                startActivity(intent);
            }
        });

        Kape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductMenu.this,MamengKape.class);
                startActivity(intent);
            }
        });

        Lettuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductMenu.this,LauraFarm.class);
                startActivity(intent);
            }
        });

        Handicrafts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductMenu.this,HandicraftsAbrego.class);
                startActivity(intent);
            }
        });

    }
}