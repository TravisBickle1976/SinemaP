package com.odev.sinemaprojesi;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.odev.sinemaprojesi.GetDataActivities.GetTicketInfoActivity;


public class ChooseSaloonFragment extends Fragment {
    View view;
    CardView saloon1, saloon2, saloon3;
    public static int saloon_no = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_choose_saloon, container, false);

         saloon1 = view.findViewById(R.id.saloon1);
         saloon2 = view.findViewById(R.id.saloon2);
         saloon3 = view.findViewById(R.id.saloon3);

         saloon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saloon_no = 1;

                Intent intent = new Intent(getActivity(), GetTicketInfoActivity.class);
                startActivity(intent);
            }
        });

        saloon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saloon_no = 2;

                Intent intent = new Intent(getActivity(), GetTicketInfoActivity.class);
                startActivity(intent);
            }
        });

        saloon3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saloon_no = 3;

                Intent intent = new Intent(getActivity(), GetTicketInfoActivity.class);
                startActivity(intent);
            }
        });



         return view;
    }
}
