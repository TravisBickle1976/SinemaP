package com.odev.sinemaprojesi.GetDataActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.odev.sinemaprojesi.EmployeesActivity;
import com.odev.sinemaprojesi.FirstLoginActivity;
import com.odev.sinemaprojesi.Models.Employee;
import com.odev.sinemaprojesi.Models.Ticket;
import com.odev.sinemaprojesi.R;
import com.odev.sinemaprojesi.SeatActivity;
import com.odev.sinemaprojesi.TicketActivity;

import java.util.ArrayList;
import java.util.List;
import static com.odev.sinemaprojesi.Adapters.FilmsAdapter.selected_film_id;
import static com.odev.sinemaprojesi.ChooseSaloonFragment.saloon_no;
import static com.odev.sinemaprojesi.ChooseSessionFragment.session_no;


public class GetTicketInfoActivity extends AppCompatActivity {

    DatabaseReference reference;
    FirebaseDatabase database;
    public static List<Ticket> ticketList;
    public static int seats[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_ticket_info);

        Toast.makeText(getApplicationContext(),"Dolu Yer bilgileri yükleniyor... Lütfen bekleyiniz.", Toast.LENGTH_LONG).show();

        database = FirebaseDatabase.getInstance();
        ticketList = new ArrayList<>();
        seats = new int[100];

        String date = "19 Mayıs 2020";
        reference = database.getReference("Tickets/"+date+"/"+session_no+"/"+saloon_no);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {


                        Ticket ticket = childDataSnapshot.getValue(Ticket.class);

                        int search = 0;

                        for(Ticket ticket1 : ticketList){
                            if(ticket1 != null && ticket1.getSaloon() != 0 && ticket1.getSession() == ticket.getSession()
                                    && ticket1.getSaloon() == ticket.getSaloon() && ticket1.getRow() == ticket.getRow()
                            && ticket1.getColumn() == ticket.getColumn()){
                                search = 1;
                                break;
                            }
                        }

                        if(search == 0){
                            Log.i("tryandcatch",ticket.getRow() + "");
                            ticketList.add(ticket);
                            seats[ticket.getRow() * 10 + ticket.getColumn()] = 1;
                        }

                }


                Intent intent = new Intent(GetTicketInfoActivity.this, SeatActivity.class);
                intent.putExtra("film", ticketList.get(0).getFilm());
                intent.putExtra("price", ticketList.get(0).getPrice());

                startActivity(intent);

                finish();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


    }
}
