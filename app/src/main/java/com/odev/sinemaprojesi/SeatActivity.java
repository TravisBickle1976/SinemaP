package com.odev.sinemaprojesi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.odev.sinemaprojesi.Models.Film;
import com.odev.sinemaprojesi.Models.Ticket;

import java.util.List;

import static com.odev.sinemaprojesi.Adapters.FilmsAdapter.selected_film_id;
import static com.odev.sinemaprojesi.ChooseSaloonFragment.saloon_no;
import static com.odev.sinemaprojesi.ChooseSessionFragment.session_no;
import static com.odev.sinemaprojesi.GetDataActivities.GetTicketInfoActivity.seats;

public class SeatActivity extends AppCompatActivity {

    View view;
    LinearLayout seat_container_layout;
    LinearLayout horizontal_layouts[];
    int koltukSayisi;
    int row, column;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat);

        row = 10;   column = 10;
        koltukSayisi = row * column;

        seat_container_layout = findViewById(R.id.seat_container_layout);
        horizontal_layouts = new LinearLayout[10];
        Bundle bundle = getIntent().getExtras();
        final String film_name = bundle.getString("film");
        final int price = bundle.getInt("price");

        for(int i = 0; i < row; i++){
            horizontal_layouts[i] = new LinearLayout(getApplicationContext());

            for(int j = 0; j < column; j++){
                ImageView imageView = new ImageView(getApplicationContext());

                if(seats[i*column + j] == 0){
                    imageView.setBackgroundResource(R.drawable.seat_empty2);
                    final int finalI = i;
                    final int finalJ = j;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            new AlertDialog.Builder(getApplicationContext())
                                    .setTitle("Bilet alımı")
                                    .setMessage(film_name + " filmine " + "Salon: "+saloon_no+" Sıra: "+(finalI +1)+" Sütun: "+(finalJ +1)+" biletini almak üzeresiniz.\n"+
                                            "Onaylıyor musunuz?")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            //Bilet alımı database'e kaydediliyor.

                                            //Ticket ticket = new Ticket(film_selected, session_no, saloon_no, finalI+1, finalJ+1, 50, -1);

                                            String ticket_id = "";
                                            for(int i = 0; i < 8; i++){
                                                ticket_id += String.valueOf((int)(Math.random()*10));
                                            }

                                            String date = "19 Mayıs 2020";

                                            reference = database.getReference("Users/"+auth.getUid()+"/Tickets/"+ticket_id);
                                            reference.setValue(date+"/"+session_no+"/"+saloon_no+"/"+(finalI +1)+"/"+(finalJ +1));
                                            Ticket ticket = new Ticket(date, film_name, session_no, saloon_no, (finalI+1), (finalJ+1), price,-1);
                                            reference = database.getReference("Tickets/"+session_no+"/"+saloon_no);
                                            reference.setValue(ticket);

                                            Intent intent = new Intent(SeatActivity.this, OperationSuccessActivity.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .setNegativeButton(android.R.string.no, null)
                                    .show();



                        }
                    });
                } else{
                    imageView.setBackgroundResource(R.drawable.seat_reserved2);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getApplicationContext(), "Başka bir koltuk seçiniz!", Toast.LENGTH_LONG).show();
                        }
                    });
                }

                horizontal_layouts[i].addView(imageView);

            }

            seat_container_layout.addView(horizontal_layouts[i]);
        }


    }
}
