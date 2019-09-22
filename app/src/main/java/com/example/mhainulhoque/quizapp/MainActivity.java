package com.example.mhainulhoque.quizapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mhainulhoque.quizapp.Common.Common;
import com.example.mhainulhoque.quizapp.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText editNewUser, editNewEmail, editNewPassword, editNewConPass; //for signup
    EditText editUser, editPass;
    Button signIn, signUp;

    FirebaseDatabase database;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        editUser = (EditText) findViewById(R.id.edtUser);
        editPass = (EditText) findViewById(R.id.edtPassword);

        signUp = (Button) findViewById(R.id.sign_up);
        signIn = (Button) findViewById(R.id.sing_in);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSingupDilog();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin(editUser.getText().toString(),editPass.getText().toString());

            }
        });


    }

    private void signin(final String user, final String pwd){
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(user).exists()){

                    if (!user.isEmpty()){
                        User login=dataSnapshot.child(user).getValue(User.class);
                        //Log.d("userid", dataSnapshot.getValue(User.class).toString());
                        if(login.getPassword().equals(pwd))
                        {
                           Intent intent = new Intent(MainActivity.this, Home.class);
                            Common.currentUser =login;
                            startActivity(intent);
                           finish();
                           Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
//
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "wrong password", Toast.LENGTH_SHORT).show();

                        }

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Please enter user name", Toast.LENGTH_SHORT).show();

                    }


                }
                else {
                    Toast.makeText(getApplicationContext(), "user doesn't exist", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void showSingupDilog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Sign Up");
        alertDialog.setMessage("Plase fill full information");

        LayoutInflater inflater = this.getLayoutInflater();
        final View sign_up_layout = inflater.inflate(R.layout.sing_up_layout, null);

        editNewUser = (EditText) sign_up_layout.findViewById(R.id.edtNewUserName);
        editNewEmail = (EditText) sign_up_layout.findViewById(R.id.edtNewEmail);
        editNewPassword = (EditText) sign_up_layout.findViewById(R.id.edtNewPassword);

        alertDialog.setView(sign_up_layout);
        alertDialog.setIcon(R.drawable.ic_account_circle_black_24dp);

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

              final User user=new User(editNewUser.getText().toString(),
                editNewPassword.getText().toString(),
                      editNewEmail.getText().toString());


                    users.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child(user.getUserName()).exists())
                                Toast.makeText(getApplicationContext(), "User already exits !", Toast.LENGTH_SHORT).show();
                            else
                            {
                                users.child(user.getUserName())
                                        .setValue(user);
                                Toast.makeText(getApplicationContext(), "User registation sucessful", Toast.LENGTH_SHORT).show();


                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    dialog.dismiss();

                }


        });
        alertDialog.show();
    }
}
