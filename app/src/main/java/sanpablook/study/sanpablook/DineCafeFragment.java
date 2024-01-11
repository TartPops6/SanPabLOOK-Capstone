package sanpablook.study.sanpablook;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.sanpablook.R;

public class DineCafeFragment extends Fragment {

    CardView cafePalmeras, cafeSulyap, cafeCasa;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dine_cafe, container, false);

        /*CardViews*/
        cafePalmeras = view.findViewById(R.id.cafePalmeras);
        cafeSulyap = view.findViewById(R.id.cafeSulyap);
        cafeCasa = view.findViewById(R.id.cafeCasa);

        cafePalmeras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDinePalmerasActivity (view);
            }
        });

        cafeSulyap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDineSulyapActivity (view);
            }
        });

        cafeCasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDineCasaActivity (view);
            }
        });
        return view;
    }

    private void goToDinePalmerasActivity(View view) {
        Intent intent = new Intent(getActivity(), DinePalmerasActivity .class);
        startActivity(intent);
    }
    private void goToDineSulyapActivity(View view) {
        Intent intent = new Intent(getActivity(), DineSulyapActivity .class);
        startActivity(intent);
    }
    private void goToDineCasaActivity(View view) {
        Intent intent = new Intent(getActivity(), DineCasaActivity .class);
        startActivity(intent);
    }
}



