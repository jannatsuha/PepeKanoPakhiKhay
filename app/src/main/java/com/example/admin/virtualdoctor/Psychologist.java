package com.example.admin.virtualdoctor;

import android.app.SearchManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Psychologist extends BaseActivity {

    DatabaseReference databaseReference,databaseReference2;
    FirebaseDatabase firebaseDatabase;
    RecyclerView recyclerView;
    private ArrayList<String> list2;
    private ArrayList<DoctorModel> list1;
    private DoctorShowAdapter doctorShowAdapter,doctorShowAdapter2;
    android.support.v7.widget.SearchView searchViews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psychologist);
        this.setTitle("Psychologists");

        recyclerView=findViewById(R.id.psychoDocRecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list2=new ArrayList<String>();
        list1 = new ArrayList<DoctorModel>();
        list2=Singleton.getInstance().getFrameList();

        list1.clear();
        for(int i=0;i<=list2.size()-1;i++) {
            String s= list2.get(i);
            databaseReference2 = FirebaseDatabase.getInstance().getReference().child("DoctorList").child(s).child("psychiatry");
            databaseReference2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
                        DoctorModel doctorModel = dataSnapshot2.getValue(DoctorModel.class);
                        list1.add(doctorModel);
                    }

                    doctorShowAdapter = new DoctorShowAdapter(Psychologist.this, list1);
                    recyclerView.setAdapter(doctorShowAdapter);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        doctorShowAdapter2= new DoctorShowAdapter(Psychologist.this,list1);
        recyclerView.setAdapter(doctorShowAdapter2);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        SearchManager searchManager= (SearchManager) getSystemService(SEARCH_SERVICE);
        searchViews= (android.support.v7.widget.SearchView) menu.findItem(R.id.searchBox).getActionView();
        searchViews.setQueryHint("Search By Doctor Name");
        searchViews.setSubmitButtonEnabled(true);
        searchViews.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Toast.makeText(Anesthesiology.this, " "+query, Toast.LENGTH_SHORT).show();
                doctorShowAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Toast.makeText(Anesthesiology.this, " "+newText, Toast.LENGTH_SHORT).show();
                doctorShowAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
