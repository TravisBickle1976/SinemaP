package com.odev.sinemaprojesi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class TicketActivity extends AppCompatActivity {

    TextView buy_ticket_tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

           getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentLayoutTicket,new TicketMenuFragment())
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();


    }



}
