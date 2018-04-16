package com.mukmenev.android.findjob.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mukmenev.android.findjob.R;
import com.mukmenev.android.findjob.classes.Company;

import java.util.ArrayList;

public class CompaniesAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Company> companies;
    TextView text_company_name,about_company,year_start,type_of_work,location_company,category_company,contacts_company;
    RatingBar rating_company;

    public CompaniesAdapter(Context context, ArrayList<Company> products) {
        ctx = context;
        companies = products;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return companies.size();
    }

    // элемент по позиции
    @Override
    public Company getItem(int position) {
        return companies.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override

    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_company, parent, false);
        }
        Company company = getCompany(position);
        init(view);
        text_company_name.setText(company.getName());
        about_company.setText(company.getAbout());
        year_start.setText(company.getYear_start());
        type_of_work.setText(company.getType_of_work());
        location_company.setText(company.getAdress());
        category_company.setText(company.getCategory());
        contacts_company.setText(company.getContakts());
        return view;
    }

    private void init(View view) {
        text_company_name = (TextView)view.findViewById(R.id.text_company_name);
        about_company = (TextView)view.findViewById(R.id.about_company);
        year_start = (TextView)view.findViewById(R.id.year_start);
        type_of_work = (TextView)view.findViewById(R.id.type_of_work);
        location_company = (TextView)view.findViewById(R.id.location_company);
        category_company = (TextView)view.findViewById(R.id.category_company);
        contacts_company = (TextView)view.findViewById(R.id.contacts_company);

        rating_company = (RatingBar) view.findViewById(R.id.rating_company);
    }

    // товар по позиции
    Company getCompany(int position) {
        return ((Company) getItem(position));
    }

}