package com.odev.sinemaprojesi.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.odev.sinemaprojesi.AlertDialogs.AlertDialogEmployee;
import com.odev.sinemaprojesi.Models.BudgetItem;
import com.odev.sinemaprojesi.Models.Employee;
import com.odev.sinemaprojesi.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.define>{
    List<Employee> list;
    Context context;
    Activity activity;
    DatabaseReference reference;
    FirebaseDatabase database;

    public EmployeeAdapter(Context context, Activity activity, List<Employee> list) {
        this.context = context;
        this.list = list;
        this.activity = activity;
        database = FirebaseDatabase.getInstance();
    }

    @NonNull
    @Override
    public EmployeeAdapter.define onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.employeeadapter_layout,parent,false);

        return new EmployeeAdapter.define(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeAdapter.define holder, final int position) {
        if(position == 0){
            holder.employee_name_tv.setText("İsim Soyisim\n\n" +  list.get(position).getFull_name());
            holder.employee_age_tv.setText("Yaş\n\n" + list.get(position).getAge());
            holder.employee_position_tv.setText("Pozisyon\n\n" + list.get(position).getPosition());
            holder.employee_salary_tv.setText("Maaş\n\n" + list.get(position).getSalary());
        } else{
            holder.employee_name_tv.setText( list.get(position).getFull_name());
            holder.employee_age_tv.setText("" + list.get(position).getAge());
            holder.employee_position_tv.setText( list.get(position).getPosition());
            holder.employee_salary_tv.setText(""  + list.get(position).getSalary());
        }



        holder.edit_employee_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogEmployee alertDialogEmployee = new AlertDialogEmployee(activity, list.get(position), 1);
            }
        });
        holder.delete_employee_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
                alertDialog.setTitle("Uyarı");
                alertDialog.setMessage("Bu çalışanı silmek istediğinizden emin misiniz?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Evet",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                reference = database.getReference("Employees/"+list.get(position).getEmployee_id());
                                reference.removeValue();

                                list.remove(list.get(position));

                                //Realtime olduğu için intent ile aynı yere geçme işine gerek yok.

                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Hayır",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class define extends RecyclerView.ViewHolder
    {
        TextView employee_name_tv, employee_age_tv, employee_position_tv, employee_salary_tv;
        Button edit_employee_button, delete_employee_button;

        public define(View itemView){
            super(itemView);
            employee_name_tv = itemView.findViewById(R.id.employee_name_tv);
            employee_age_tv = itemView.findViewById(R.id.employee_age_tv);
            employee_position_tv = itemView.findViewById(R.id.employee_position_tv);
            employee_salary_tv = itemView.findViewById(R.id.employee_salary_tv);
            edit_employee_button = itemView.findViewById(R.id.edit_employee_button);
            delete_employee_button = itemView.findViewById(R.id.delete_employee_button);

        }
    }




}
