package com.odev.sinemaprojesi.GetDataActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.odev.sinemaprojesi.EmployeesActivity;
import com.odev.sinemaprojesi.Models.Employee;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.odev.sinemaprojesi.R;

import java.util.ArrayList;
import java.util.List;

public class GetEmployeeInfoFromDatabase extends AppCompatActivity {

    DatabaseReference reference;
    FirebaseDatabase database;
    public static List<Employee> employees_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_employee_info_from_database);

        Toast.makeText(getApplicationContext(),"Çalışan bilgileri yükleniyor... Lütfen bekleyiniz.", Toast.LENGTH_LONG).show();


        database = FirebaseDatabase.getInstance();
        employees_list = new ArrayList<>();

        reference = database.getReference("Employees/");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    if(childDataSnapshot.child("full_name").getValue() != null &&
                            childDataSnapshot.child("position").getValue() != null){

                        Employee employee = childDataSnapshot.getValue(Employee.class);

                        int search = 0;

                            for(Employee employee1 : employees_list){
                                if(employee != null && employee.getFull_name() != null && employee1.getFull_name().equals(employee.getFull_name())){
                                    search = 1;
                                    break;
                                }
                            }

                        if(search == 0) employees_list.add(employee);

                    }

                }


                Intent intent = new Intent(GetEmployeeInfoFromDatabase.this, EmployeesActivity.class);

                startActivity(intent);

                finish();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });



    }



}
