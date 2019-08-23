package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    EditText username;
    EditText fullname;
    EditText email;
    EditText password;
    Button register;
    TextView txt_login;
    FirebaseAuth auth;
    DatabaseReference reference;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username=(EditText)findViewById(R.id.username);
        fullname=(EditText)findViewById(R.id.fullname);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        register=(Button)findViewById(R.id.register);
        txt_login=(TextView)findViewById(R.id.txt_login);
        auth=FirebaseAuth.getInstance();
        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd=new ProgressDialog(RegisterActivity.this);
                pd.setMessage("please wait...");
                pd.show();
                String str_username=username.getText().toString();
                String str_fullname=fullname.getText().toString();
                String str_email=email.getText().toString();
                String str_password=password.getText().toString();
                register(str_username,str_fullname,str_email,str_password);
            }
        });
    }
    private void register(final String username,final String fullname,String email,String password){
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser=auth.getCurrentUser();
                            String userid=firebaseUser.getUid();
                            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
                            HashMap<String,Object> hashMap=new HashMap<>();
                            hashMap.put("id",userid);
                            hashMap.put("username",username.toLowerCase());
                            hashMap.put("fullname",fullname);
                            hashMap.put("bio","");
                            hashMap.put("imageurl","https://firebasestorage.googleapis.com/v0/b/insagram-f2227.appspot.com/o/rename.png?alt=media&token=3948fc00-aca3-45e1-8839-27251d7c56eb");
                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        pd.dismiss();
                                        Intent intent=new Intent(RegisterActivity.this,Homepage.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }
                            });
                        }else
                        {
                            pd.dismiss();
                            Toast.makeText(RegisterActivity.this,"You cant register",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

}
}
