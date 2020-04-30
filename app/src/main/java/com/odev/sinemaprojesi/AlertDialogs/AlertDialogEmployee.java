package com.odev.sinemaprojesi.AlertDialogs;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.odev.sinemaprojesi.Models.Employee;
import com.odev.sinemaprojesi.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AlertDialogEmployee {
    Activity activity;
    FirebaseDatabase database;
    DatabaseReference reference;

    public AlertDialogEmployee(final Activity activity, final Employee employee, final int edit_or_add){
        this.activity = activity;
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.alertlayout_employee, null, false);

        final EditText employee_fullname_et = view.findViewById(R.id.employee_fullname_et);
        final EditText employee_age_et = view.findViewById(R.id.employee_age_et);
        final EditText employee_salary_et = view.findViewById(R.id.employee_salary_et);
        final EditText employee_position_et = view.findViewById(R.id.employee_position_et);
        Button alert_cancel_button = view.findViewById(R.id.alert_cancel_button2);
        Button alert_add_button = view.findViewById(R.id.alert_add_button2);
        database = FirebaseDatabase.getInstance();

        if(edit_or_add == 1){
            alert_add_button.setText("Değiştir");
            employee_fullname_et.setText(employee.getFull_name());
            employee_age_et.setText(employee.getAge() + "");
            employee_position_et.setText(employee.getPosition());
            employee_salary_et.setText(employee.getSalary() + "");
        }
        else{
            alert_add_button.setText("Ekle");
        }

        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(activity);
        alert.setView(view);
        alert.setCancelable(false);
        final android.app.AlertDialog dialog = alert.create();
        dialog.show();
        dialog.setCancelable(true);

        alert_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        alert_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String employee_fullname  = employee_fullname_et.getText().toString();
                    int employee_age         = Integer.valueOf(employee_age_et.getText().toString());
                    String employee_position = employee_position_et.getText().toString();
                    int employee_salary      = Integer.valueOf(employee_salary_et.getText().toString());

                    if(employee_fullname.equals("") || employee_age_et.getText().toString().equals("") || employee_position.equals("")
                            || employee_salary_et.getText().toString().equals("")){
                        Toast.makeText(activity, "Lütfen alanları doldurunuz!", Toast.LENGTH_LONG).show();
                    } else{
                        if(edit_or_add == 1){
                            reference = database.getReference("Employees/"+employee.getEmployee_id());
                            Employee employee2 = new Employee(employee.getEmployee_id(), employee_fullname, employee_position, employee_age, employee_salary);
                            reference.setValue(employee2);

                        } else{

                            String employee_id = "";
                            for(int i = 0; i < 8; i++){
                                employee_id += String.valueOf((int)(Math.random()*10));
                            }
                            Employee employee = new Employee(employee_id, employee_fullname, employee_position, employee_age, employee_salary);


                            reference = database.getReference("Employees/"+employee_id);
                            reference.setValue(employee);

                        }

  //Real time olduğu için bilgileri almak için tekrar GetEmployeeInfo'ya gitmeme gerek yok.
                    }

                } catch (Exception e){
                    Toast.makeText(activity, "Lütfen bilgileri doğru giriniz!", Toast.LENGTH_LONG).show();
                }





            }
        });

    }


}
