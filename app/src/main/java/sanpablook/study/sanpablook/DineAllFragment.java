package sanpablook.study.sanpablook;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.sanpablook.R;

public class DineAllFragment extends Fragment {

    CardView hotPalmeras, popularPalmeras, preferPalmeras, hotSulyap, popularSulyap, preferSulyap, hotCasa, popularCasa, preferCasa;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dine_all, container, false);

        /*CardViews*/
        hotPalmeras = view.findViewById(R.id.hotPalmeras);
        hotSulyap = view.findViewById(R.id.hotSulyap);
        hotCasa = view.findViewById(R.id.hotCasa);
        popularPalmeras = view.findViewById(R.id.popularPalmeras);
        popularSulyap = view.findViewById(R.id.popularSulyap);
        popularCasa = view.findViewById(R.id.popularCasa);
        preferPalmeras = view.findViewById(R.id.preferPalmeras);
        preferSulyap = view.findViewById(R.id.preferSulyap);
        preferCasa = view.findViewById(R.id.preferCasa);


        //PALMERAS CARDVIEWS
        hotPalmeras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDinePalmerasActivity (view);
            }
        });
        popularPalmeras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDinePalmerasActivity (view);
            }
        });
        preferPalmeras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDinePalmerasActivity (view);
            }
        });

        //SULYAP CARDVIEWS
        hotSulyap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDineSulyapActivity(view);
            }
        });
        popularSulyap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDineSulyapActivity(view);
            }
        });
        preferSulyap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDineSulyapActivity(view);
            }
        });

        hotCasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDineCasaActivity(view);
            }
        });
        popularCasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDineCasaActivity(view);
            }
        });
        preferCasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDineCasaActivity(view);
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