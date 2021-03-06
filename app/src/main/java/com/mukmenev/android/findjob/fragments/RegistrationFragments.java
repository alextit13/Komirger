package com.mukmenev.android.findjob.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mukmenev.android.findjob.R;
import com.mukmenev.android.findjob.classes.Company;
import com.mukmenev.android.findjob.enums.EnumForCategories;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class RegistrationFragments extends Fragment {
    private EditText name_company,about_company,date_of_start_company,type_of_work_company,contacts_company,address_company;
    private Spinner spinner_kategory_company;
    private Button button_save_company;
    private View view;
    private Context context;

    public void mGetData(Context c){
        context = c;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.registration_company_fragment,container,false);
        init(view);
        completeSpinner();
        clicker(view);
        return view;
    }

    private void completeSpinner() {
        List<EnumForCategories> somethingList = Arrays.asList(EnumForCategories.values());

        ArrayList<String>list = new ArrayList<>();
        for (int i = 0; i<somethingList.size();i++){
            list.add(somethingList.get(i).name());
        }
        Collections.sort(list.subList(1, list.size()));
        ArrayAdapter arrayAdapter = new ArrayAdapter(context,android.R.layout.simple_spinner_item, list);
        spinner_kategory_company.setAdapter(arrayAdapter);
    }

    private void clicker(View view) {
        button_save_company.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveDataCompany();
                    }
                }
        );
    }

    private void saveDataCompany() {
        if (checkCompleteAllFields()){
            Company company = new Company(name_company.getText().toString(),
                    about_company.getText().toString()
                    ,new Date().getTime()+"",date_of_start_company.getText().toString()
                    ,type_of_work_company.getText().toString()
                    ,3,false,spinner_kategory_company.getSelectedItem().toString(),
                    contacts_company.getText().toString()
                    ,address_company.getText().toString());
            FirebaseDatabase.getInstance().getReference().child("companies").child(company.getName())
                    .setValue(company);
            Toast.makeText(context, "Ваша компания успешно добавлена в каталог! \n" +
                    " Благодарим Вас!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkCompleteAllFields() {
        boolean check = false;
        if (name_company.getText().toString().length()>0
                &&about_company.getText().toString().length()>0
                &&date_of_start_company.getText().toString().length()>0
                &&type_of_work_company.getText().toString().length()>0
                &&contacts_company.getText().toString().length()>0
                &&address_company.getText().toString().length()>0){
            check = true;
        }else{
            Toast.makeText(context, "Все поля обязательны для заполнения", Toast.LENGTH_SHORT).show();
            check = false;
        }
        return check;
    }

    private void init(View view) {
        name_company = (EditText)view.findViewById(R.id.name_company);
        about_company = (EditText)view.findViewById(R.id.about_company);
        date_of_start_company = (EditText)view.findViewById(R.id.date_of_start_company);
        type_of_work_company = (EditText)view.findViewById(R.id.type_of_work_company);
        contacts_company = (EditText)view.findViewById(R.id.contacts_company);
        address_company = (EditText)view.findViewById(R.id.address_company);

        spinner_kategory_company = (Spinner)view.findViewById(R.id.spinner_kategory_company);

        button_save_company = (Button) view.findViewById(R.id.button_save_company);
    }
}
