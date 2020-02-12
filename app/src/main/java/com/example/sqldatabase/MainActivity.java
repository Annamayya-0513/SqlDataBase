package com.example.sqldatabase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MySqlite mySqlite;

    EditText EmailEditTxt,PhoneNumberEditTxt,NameEditText,SnoEditTxt;
    Button InsertBtn ,ReadBtn,UpdateBtn,ViewAllBtn,DeleteBtn,DeleteAllRowsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mySqlite = new MySqlite(this);

        initialize();
        onClick();
    }

    private void initialize() {

        EmailEditTxt = (EditText)findViewById(R.id.EmailEditTxt);
        PhoneNumberEditTxt = (EditText)findViewById(R.id.PhoneNumberEditTxt);
        NameEditText = (EditText)findViewById(R.id.NameEditText);
        SnoEditTxt = (EditText)findViewById(R.id.SnoEditTxt);
        InsertBtn = (Button)findViewById(R.id.InsertBtn);
        ReadBtn = (Button)findViewById(R.id.ReadBtn);
        UpdateBtn =(Button)findViewById(R.id.UpdateBtn);
        ViewAllBtn =(Button)findViewById(R.id.ViewAllBtn);
        DeleteBtn =(Button)findViewById(R.id.DeleteBtn);
        DeleteAllRowsBtn =(Button)findViewById(R.id.DeleteAllRowsBtn);
    }
    private void onClick() {

        InsertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strname,stremail,strmobile;

                strname = NameEditText.getText().toString();
                stremail = EmailEditTxt.getText().toString();
                strmobile = PhoneNumberEditTxt.getText().toString();

                mySqlite.insertData(strname,stremail,strmobile);
                Toast.makeText(MainActivity.this, "Inserted Successful!", Toast.LENGTH_SHORT).show();
            }
        });

        ReadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mySqlite.readData();

                Toast.makeText(MainActivity.this,"Read Data" , Toast.LENGTH_SHORT).show();
            }
        });

        UpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdate = mySqlite.updateData(NameEditText.getText().toString(),
                        EmailEditTxt.getText().toString(),PhoneNumberEditTxt.getText().toString(),SnoEditTxt.getText().toString());
                
                if(isUpdate == true){

                    Toast.makeText(MainActivity.this, "Data Upate", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Data Not Upate", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ViewAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor cursor = mySqlite.getAllData();

                if(cursor.getCount() == 0){
///Show Message
                    showMessage("Error","Nothing  Found");
                    return;
                }

                StringBuffer stringBuffer = new StringBuffer();
                while (cursor.moveToNext()){

                    stringBuffer.append("Id :"+cursor.getString(0)+"\n");
                    stringBuffer.append("Name :"+cursor.getString(1)+"\n");
                    stringBuffer.append("Email :"+cursor.getString(2)+"\n");
                    stringBuffer.append("Mobile :"+cursor.getString(3)+"\n\n");

                }
                /// Show All Data
                showMessage("Data",stringBuffer.toString());
            }
        });

        DeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer deleteRows = mySqlite.deleteData(SnoEditTxt.getText().toString());

                if (deleteRows >0){
                    Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Data not Deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        DeleteAllRowsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase sqLiteDatabase = mySqlite.getWritableDatabase();


            }
        });



    }
///Show message in Click View All Button
    private void showMessage(String title, String Message) {

        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

//public void clearTable(){
//
//        mySqlite.deleteAllData();
//}

}
