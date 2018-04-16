package com.mukmenev.android.findjob.classes;
import com.mukmenev.android.findjob.classes.square_otto.BusStation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
public class TakeDataFromFirebase{
    private String tag;
    private ArrayList<Ad>list;
    private ArrayList<Company>listCompany;
    public void getFromServer(String t) {
        tag=t;
        list = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("ads")
                .addValueEventListener(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot data : dataSnapshot.getChildren()){
                                    if (data.getValue(Ad.class).toString().contains(tag)){
                                        list.add(data.getValue(Ad.class));
                                    }
                                }
                                recieveData(list);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        }
                );
    }
    public void getFromServerCompanise(String filter){
        listCompany = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("companies")
                .addValueEventListener(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot data : dataSnapshot.getChildren()){
                                    listCompany.add(data.getValue(Company.class));
                                    sortList(listCompany,filter);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        }
                );
    }
    private void sortList(ArrayList<Company> L, String filter) {
        if (filter.equals("")){
            recieveDataWithCompanies(L);
        }else{
            ArrayList<Company>listSort = new ArrayList<>();
            for (int i = 0;i<L.size();i++){
                if (L.get(i).toString().contains(filter)){
                    listSort.add(L.get(i));
                }
            }
            recieveDataWithCompanies(listSort);
        }
    }
    @Subscribe
    private void recieveDataWithCompanies(ArrayList<Company> LC) {
        BusStation.getBus().post(LC);
    }
    @Subscribe
    private void recieveData(ArrayList<Ad> list) {
        if (list!=null &&list.size()>0){
            BusStation.getBus().post(list);
        }
    }
}
