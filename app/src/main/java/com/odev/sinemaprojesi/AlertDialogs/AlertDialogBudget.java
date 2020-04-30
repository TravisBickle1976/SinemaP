package com.odev.sinemaprojesi.AlertDialogs;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.odev.sinemaprojesi.BudgetActivity;
import com.odev.sinemaprojesi.Models.BudgetItem;
import com.odev.sinemaprojesi.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.odev.sinemaprojesi.GetDataActivities.GetBudgetInfoFromDatabase.incomes_list;
import static com.odev.sinemaprojesi.GetDataActivities.GetBudgetInfoFromDatabase.outcomes_list;

public class AlertDialogBudget {
    Activity activity;
    FirebaseDatabase database;
    DatabaseReference reference;

    public AlertDialogBudget(final Activity activity, final int income_or_outcome){
        this.activity = activity;
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.alertlayout_budget, null, false);
        TextView alert_info_tv = view.findViewById(R.id.alert_info_tv);
        final EditText budget_operation_et = view.findViewById(R.id.budget_operation_et);
        final EditText budget_amount_et = view.findViewById(R.id.budget_amount_et);
        Button alert_cancel_button = view.findViewById(R.id.alert_cancel_button);
        Button alert_add_button = view.findViewById(R.id.alert_add_button);
        database = FirebaseDatabase.getInstance();

        if(income_or_outcome == 1) {
            alert_info_tv.setText("Gelir Ekle");
        }
        else{
            alert_info_tv.setText("Gider Ekle");
        }

        budget_operation_et.setHint("İşlemi giriniz");
        budget_amount_et.setHint("Miktarı giriniz");

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
                String islem = budget_operation_et.getText().toString();
                Integer miktar = Integer.valueOf(budget_amount_et.getText().toString());

                if(islem.equals("") || miktar == null || miktar.equals("")){
                    Toast.makeText(activity, "Lütfen alanları doldurunuz!", Toast.LENGTH_LONG).show();
                }
                else{
                    BudgetItem budgetItem = new BudgetItem(islem, miktar);
                    String budget_operation_id = "";

                    for(int i = 0; i < 8; i++){
                        budget_operation_id += String.valueOf((int)(Math.random()*10));
                    }

                    if(income_or_outcome == 1){
                        incomes_list.clear();
                        incomes_list.add(budgetItem);
                        reference = database.getReference("Budget/Incomes/"+budget_operation_id+"/operation");
                        reference.setValue(islem);
                        reference = database.getReference("Budget/Incomes/"+budget_operation_id+"/amount");
                        reference.setValue(miktar);
                    }
                    else{
                        outcomes_list.clear();
                        outcomes_list.add(budgetItem);  //DB'ye eklenmesi beklenmez. Biz ekleyip gösteririz, database2e sonra eklenir.
                        reference = database.getReference("Budget/Outcomes/"+budget_operation_id+"/operation");
                        reference.setValue(islem);
                        reference = database.getReference("Budget/Outcomes/"+budget_operation_id+"/amount");
                        reference.setValue(miktar);
                    }

                    Intent intent = new Intent(activity, BudgetActivity.class);
                    activity.startActivity(intent);

                }

            }
        });

    }

}
