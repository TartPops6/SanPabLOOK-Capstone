package sanpablook.study.sanpablook;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.study.sanpablook.R;

public class ProductsFragment extends Fragment {

    ImageView Coconut, Pinayte, Ube, Cacao, Kape, Lettuce, Handicrafts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_products, container, false);

        Coconut = view.findViewById(R.id.Coconut);
        Pinayte = view.findViewById(R.id.PinayteProduct);
        Ube = view.findViewById(R.id.Ube);
        Cacao = view.findViewById(R.id.Cacao);
        Kape = view.findViewById(R.id.Kape);
        Lettuce = view.findViewById(R.id.Lettuce);
        Handicrafts = view.findViewById(R.id.handicrafts);

        Coconut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ColettesBukoPie.class);
                startActivity(intent);
            }
        });

        Pinayte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Pinayte.class);
                startActivity(intent);
            }
        });

        Ube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UbeMariaAna.class);
                startActivity(intent);
            }
        });

        Cacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TanimAgriCacao.class);
                startActivity(intent);
            }
        });

        Kape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MamengKape.class);
                startActivity(intent);
            }
        });

        Lettuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LauraFarm.class);
                startActivity(intent);
            }
        });

        Handicrafts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HandicraftsAbrego.class);
                startActivity(intent);
            }
        });

        return view;
    }
}