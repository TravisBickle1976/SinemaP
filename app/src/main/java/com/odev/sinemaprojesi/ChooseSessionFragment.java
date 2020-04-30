package com.odev.sinemaprojesi;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class ChooseSessionFragment extends Fragment {

    public static int session_no;
    View view;
    CardView session1, session2, session3, session4, session5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_choose_session, container, false);

        session1 = view.findViewById(R.id.session1);
        session2 = view.findViewById(R.id.session2);
        session3 = view.findViewById(R.id.session3);
        session4 = view.findViewById(R.id.session4);
        session5 = view.findViewById(R.id.session5);

        session1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session_no = 1;

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentLayoutTicket,new ChooseSaloonFragment())
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        session2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session_no = 2;

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentLayoutTicket,new ChooseSaloonFragment())
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        session3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session_no = 3;

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentLayoutTicket,new ChooseSaloonFragment())
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        session4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session_no = 4;

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentLayoutTicket,new ChooseSaloonFragment())
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        session5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session_no = 5;

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentLayoutTicket,new ChooseSaloonFragment())
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });




        return view;
    }








}
