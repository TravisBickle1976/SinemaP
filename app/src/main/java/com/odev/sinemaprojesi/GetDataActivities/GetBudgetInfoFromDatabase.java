package com.odev.sinemaprojesi.GetDataActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.odev.sinemaprojesi.BudgetActivity;
import com.odev.sinemaprojesi.Models.BudgetItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.odev.sinemaprojesi.R;

import java.util.ArrayList;
import java.util.List;

public class GetBudgetInfoFromDatabase extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;
    public static List<BudgetItem> outcomes_list;
    public static List<BudgetItem> incomes_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_budget_info_from_database);

        Toast.makeText(getApplicationContext(),"Bütçe bilgileri yükleniyor... Lütfen bekleyiniz.", Toast.LENGTH_LONG).show();


        incomes_list = new ArrayList<>();
        outcomes_list = new ArrayList<>();
        database = FirebaseDatabase.getInstance();

        reference = database.getReference("Budget/Incomes");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    if(childDataSnapshot.child("operation").getValue() != null &&
                            childDataSnapshot.child("amount").getValue() != null){
                        String operation = childDataSnapshot.child("operation").getValue().toString();
                        int amount = Integer.valueOf(childDataSnapshot.child("amount").getValue().toString());

                        BudgetItem income = new BudgetItem(operation, amount);

                        int search = 0;
                        for(BudgetItem budgetItem : incomes_list){
                            if(budgetItem.getOperation().equals(income.getOperation()) && budgetItem.getAmount() == income.getAmount()){
                                search = 1;
                                break;
                            }
                        }

                        if(search == 0) incomes_list.add(income);

                    }

                }

                Intent intent = new Intent(GetBudgetInfoFromDatabase.this, BudgetActivity.class);

                startActivity(intent);

                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


        reference = database.getReference("Budget/Outcomes");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    if(childDataSnapshot.child("operation").getValue() != null &&
                            childDataSnapshot.child("amount").getValue() != null){

                        String operation = childDataSnapshot.child("operation").getValue().toString();
                        int amount = Integer.valueOf(childDataSnapshot.child("amount").getValue().toString());

                        BudgetItem outcome = new BudgetItem(operation, amount);

                        int search = 0;
                        for(BudgetItem budgetItem : outcomes_list){
                            if(budgetItem.getOperation().equals(outcome.getOperation()) && budgetItem.getAmount() == outcome.getAmount()){
                                search = 1;
                                break;
                            }
                        }

                        if(search == 0) outcomes_list.add(outcome);

                    }

                }


                Intent intent = new Intent(GetBudgetInfoFromDatabase.this, BudgetActivity.class);

                startActivity(intent);

                finish();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


    }
}
