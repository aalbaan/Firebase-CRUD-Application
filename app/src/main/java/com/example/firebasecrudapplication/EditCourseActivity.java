package com.example.firebasecrudapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditCourseActivity extends AppCompatActivity {

    private TextInputEditText courseNameEdt,coursePriceEdt,courseSuitedForEdt,courseImgEdt,courseLinkEdt,courseDescEdt;
    private Button updateCourseBtn,deleteCourseBtn;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private  String courseID;
    private  CourseRVModel courseRVModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        courseNameEdt = findViewById(R.id.idEtdCourseName);
        coursePriceEdt = findViewById(R.id.idEtdCoursePrice);
        courseSuitedForEdt = findViewById(R.id.idEtdCourseSuitedFor);
        courseImgEdt = findViewById(R.id.idEtdCourseImageLink);
        courseLinkEdt = findViewById(R.id.idEtdCourseLink);
        courseDescEdt = findViewById(R.id.idEtdCourseDesc);
        updateCourseBtn = findViewById(R.id.idBtnUpdateCourse);
        deleteCourseBtn = findViewById(R.id.idBtnDeleteCourse);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        courseRVModel = getIntent().getParcelableExtra("course");

        if (courseRVModel != null) {
            // on below line we are setting data to our edit text from our modal class.
            courseNameEdt.setText(courseRVModel.getCourseName());
            coursePriceEdt.setText(courseRVModel.getCoursePrice());
            courseSuitedForEdt.setText(courseRVModel.getBestSuitedFor());
            courseImgEdt.setText(courseRVModel.getCourseImg());
            courseLinkEdt.setText(courseRVModel.getCourseLink());
            courseDescEdt.setText(courseRVModel.getCourseDescription());
            courseID = courseRVModel.getCourseID();

        }

        // on below line we are initializing our database reference and we are adding a child as our course id.
        databaseReference = firebaseDatabase.getReference("Courses").child(courseID);

        updateCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
                // on below line we are getting data from our edit text.
                String courseName=courseNameEdt.getText().toString();
                String coursePrice=coursePriceEdt.getText().toString();
                String suitedFor=courseSuitedForEdt.getText().toString();
                String courseImg=courseImgEdt.getText().toString();
                String courseLink=courseLinkEdt.getText().toString();
                String courseDesc=courseDescEdt.getText().toString();

                // on below line we are creating a map for
                // passing a data using key and value pair.
                Map<String,Object> map = new HashMap<>();
                map.put("courseName",courseName);
                map.put("courseDescription",courseDesc);
                map.put("coursePrice",coursePrice);
                map.put("bestSuitedFor",suitedFor);
                map.put("courseImg",courseImg);
                map.put("courseLink",courseLink);
                map.put("courseID",courseID);

                // on below line we are calling a database reference on
                // add value event listener and on data change method
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingPB.setVisibility(View.GONE);
                        // adding a map to our database.
                        databaseReference.updateChildren(map);
                        Toast.makeText(EditCourseActivity.this,"Course Updated..",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditCourseActivity.this,MainActivity.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditCourseActivity.this,"Failed to update cours..",Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });

        deleteCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling a method to delete a course.
                deleteCourse();
            }
        });
    }
    private  void deleteCourse (){
        // calling a method to delete the course.
        databaseReference.removeValue();
        Toast.makeText(this,"Course Deleted..", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditCourseActivity.this,MainActivity.class));
    }
}