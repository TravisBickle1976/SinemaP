package com.odev.sinemaprojesi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.odev.sinemaprojesi.Adapters.EmployeeAdapter;
import com.odev.sinemaprojesi.AlertDialogs.AlertDialogEmployee;

import static com.odev.sinemaprojesi.GetDataActivities.GetEmployeeInfoFromDatabase.employees_list;

public class EmployeesActivity extends AppCompatActivity {

    RecyclerView employees_recyclerview;
    Button add_employee_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees);

        employees_recyclerview = findViewById(R.id.employees_listview);
        add_employee_button = findViewById(R.id.add_employee_button);

        add_employee_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogEmployee alertDialogEmployee = new AlertDialogEmployee(EmployeesActivity.this, null, 0);
            }
        });

        Log.i("deneyen",employees_list.toString());
        EmployeeAdapter employeeAdapter = new EmployeeAdapter(getApplicationContext(), EmployeesActivity.this, employees_list);
        employees_recyclerview.setAdapter(employeeAdapter);
        employees_recyclerview.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(EmployeesActivity.this, MainActivity.class);

        startActivity(intent);
    }


}
