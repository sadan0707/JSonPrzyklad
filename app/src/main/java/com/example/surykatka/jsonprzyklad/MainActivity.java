package com.example.surykatka.jsonprzyklad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Button przycisk_wyswietl = (Button) findViewById(R.id.przycisk_wyswietl);

        }


    public void Przycisk_Wyswietl(View view) {

        TextView text_wyswietl = (TextView) findViewById(R.id.text_wyswietl);
        //text_wyswietl.setText("Wyświtlana zawartość to: ");

        HttpURLConnection polaczenie = null;
        BufferedReader czytnik_strumienia = null;

        try {
            URL adres_url = new URL("tu wpisz adres URL");
            polaczenie = (HttpURLConnection) adres_url.openConnection();
            polaczenie.connect();

            InputStream strumien = polaczenie.getInputStream();

            czytnik_strumienia = new BufferedReader(new InputStreamReader(strumien));

            StringBuffer bufor = new StringBuffer();

            String linijka = "";
            while ((linijka = czytnik_strumienia.readLine()) != null) {
                bufor.append(linijka);
            }

            text_wyswietl.setText("Wyświtlana zawartość to: "+bufor.toString());


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if(polaczenie!=null) {
                polaczenie.disconnect();
            }
            if(czytnik_strumienia!=null) {
                try {
                    czytnik_strumienia.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    }

