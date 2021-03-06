package com.example.admin.virtualdoctor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Asthma extends BaseActivity {

    RecyclerView recyclerView;
    DoctorShowAdapter doctorShowAdapter;
    public ArrayList<String> list2;
    ArrayList<DoctorModel> list1;
    DatabaseReference databaseReference2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asthma);

        this.setTitle("Asthma Specialists");

        recyclerView=findViewById(R.id.asthmaDocRecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list1 = new ArrayList<DoctorModel>();
        list2=new ArrayList<String>();
        list2=Singleton.getInstance().getFrameList();

        list1.clear();
        for(int i=0;i<=list2.size()-1;i++) {
            String s= list2.get(i);
            databaseReference2 = FirebaseDatabase.getInstance().getReference().child("DoctorList").child(s).child("asthma");
            databaseReference2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
                        DoctorModel doctorModel = dataSnapshot2.getValue(DoctorModel.class);
                        list1.add(doctorModel);
                    }

                    doctorShowAdapter = new DoctorShowAdapter(Asthma.this, list1);
                    recyclerView.setAdapter(doctorShowAdapter);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
