package sanpablook.study.sanpablook;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import sanpablook.study.sanpablook.Adapter.SectionPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.study.sanpablook.R;

import java.util.Arrays;
import java.util.List;

public class DineFragment extends Fragment {
    View myFragment;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_dine, container, false);
        viewPager = myFragment.findViewById(R.id.viewPager);
        tabLayout = myFragment.findViewById(R.id.tabLayout);

        AutoCompleteTextView autoCompleteTextView = myFragment.findViewById(R.id.searchBar);

        List<String> items = Arrays.asList("Tahanan ni Aling Meding", "Casa San Pablo (Hotel)", "Casa Palmera", "Sulyap Gallery Cafe", "Casa San Pablo (Dine)", "Palmeras Garden Restaurant");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, items);

        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = (String) parent.getItemAtPosition(position);
            if (selectedItem.isEmpty()) {
                Toast.makeText(getContext(), "No results", Toast.LENGTH_SHORT).show();
            } else {
                switch (selectedItem) {
                    case "Casa San Pablo (Hotel)":
                        startActivity(new Intent(getContext(), HotelCasa.class));
                        break;
                    case "Tahanan ni Aling Meding":
                        startActivity(new Intent(getContext(), HotelMeding.class));
                        break;
                    case "Casa Palmera":
                        startActivity(new Intent(getContext(), HotelPalmeras.class));
                        break;
                    case "Sulyap Gallery Cafe":
                        startActivity(new Intent(getContext(), DineSulyapActivity.class));
                        break;
                    case "Casa San Pablo (Dine)":
                        startActivity(new Intent(getContext(), DineCasaActivity.class));
                        break;
                    case "Palmeras Garden Restaurant":
                        startActivity(new Intent(getContext(), DinePalmerasActivity.class));
                        break;
                    default:
                        Toast.makeText(getContext(), "No results found", Toast.LENGTH_SHORT).show();
                        break;
                    // Add more cases as needed
                }
            }
        });

        return myFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUpViewPager(ViewPager viewPager) {
        SectionPagerAdapter adapter = new SectionPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new DineAllFragment(), "All");
        adapter.addFragment(new DineCafeFragment(), "Café");
        adapter.addFragment(new DineAlfrescoFragment(), "Al Fresco");

        viewPager.setAdapter(adapter);
    }
}