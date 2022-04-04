package com.example.sendcreatecsv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private MaterialButton sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendBtn = findViewById(R.id.send_button);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // first create file object for file placed at location
                // specified by filepath
                String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/TEST_CSV.csv";
                File file = new File(filePath);
                try {
                    // create FileWriter object with file as parameter
                    FileWriter outputfile = new FileWriter(file);

                    // create CSVWriter object filewriter object as parameter
                    CSVWriter writer = new CSVWriter(outputfile);

                    // adding header to csv
                    String[] header = { "Name", "Class", "Marks" };
                    writer.writeNext(header);

                    // add data to csv
                    String[] data1 = { "Aman", "10", "620" };
                    writer.writeNext(data1);
                    String[] data2 = { "Suraj", "10", "630" };
                    writer.writeNext(data2);
                    String[] data3 = { "Edo", "09", "690" };
                    writer.writeNext(data3);

                    // closing writer connection
                    writer.close();

                    // creation of the file's uri to send
                    Uri fileURI = FileProvider.getUriForFile(MainActivity.this, getApplicationContext().getPackageName() + ".provider", file);

                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "THiS iS mY TeSt tO sEnD.");
                    sendIntent.setDataAndType(fileURI, "text/csv");
                    sendIntent.putExtra(Intent.EXTRA_STREAM, fileURI);

                    Intent shareIntent = Intent.createChooser(sendIntent, "tEsT!");
                    startActivity(shareIntent);

                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}