package com.example.surykatka.jsonprzyklad;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.surykatka.jsonprzyklad.Modele.ModelSprzety;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {


private TextView text_wyswietl;
private EditText wpisz_adres;
private ListView lista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create default options which will be used for every
        //  displayImage(...) call if no options will be passed to this method
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
        .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
        .defaultDisplayImageOptions(defaultOptions)
        .build();

        ImageLoader.getInstance().init(config); // Do it on Application start

      //  Button przycisk_wyswietl = (Button) findViewById(R.id.przycisk_wyswietl);
        lista =(ListView) findViewById(R.id.lista_sprzetow);


        //wpisz_adres.setText("192.168.2.103:80/mojFolder/mojPlik.txt");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_odswierz){
            new JSONTask().execute("http://192.168.2.103:80/mojFolder/mojPlik.txt");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //public void Przycisk_Wyswietl(View view) {
    //
    //    wpisz_adres = (EditText) findViewById(R.id.ADRES);
    //
    //    String adres = wpisz_adres.getText().toString();
    //
    //   new JSONTask().execute("http://"+adres);
    //
    //
    //}


    public class JSONTask extends AsyncTask<String, String, List<ModelSprzety>> {

        @Override
        protected List<ModelSprzety> doInBackground(String... params) {

            HttpURLConnection polaczenie = null;
            BufferedReader czytnik_strumienia = null;

            try {
                URL adres_url = new URL(params[0]);
                polaczenie = (HttpURLConnection) adres_url.openConnection();
                polaczenie.connect();

                InputStream strumien = polaczenie.getInputStream();

                czytnik_strumienia = new BufferedReader(new InputStreamReader(strumien));

                StringBuffer bufor = new StringBuffer();

                String linijka = "";
                while ((linijka = czytnik_strumienia.readLine()) != null) {
                    bufor.append(linijka);
                }


                String finalJson = bufor.toString();



                JSONObject jsonObject = new JSONObject(finalJson);
                JSONArray jsonArray = jsonObject.getJSONArray("Sprzety");
                //StringBuffer dane = new StringBuffer();

                List<ModelSprzety> modelSprzetyList = new ArrayList<ModelSprzety>();

                for (int i=0; i<jsonArray.length(); i++) {


                    JSONObject jsonFinalObject = jsonArray.getJSONObject(i);
                    ModelSprzety modelSprzety = new ModelSprzety();

                    modelSprzety.setNazwa(jsonFinalObject.getString("Nazwa"));
                    modelSprzety.setModel(jsonFinalObject.getString("Model"));
                    modelSprzety.setProducent(jsonFinalObject.getString("Producent"));
                    modelSprzety.setRok_prod(jsonFinalObject.getInt("Rok_prod"));
                    modelSprzety.setFotka(jsonFinalObject.getString("foto"));
                    modelSprzety.setSerial_number(jsonFinalObject.getString("nr_fabr"));
                    modelSprzetyList.add(modelSprzety);


                    //String nazwa_sprzetu = jsonFinalObject.getString("Nazwa");
                    //int rok_prod = jsonFinalObject.getInt("Rok_prod");

                    //dane.append(nazwa_sprzetu+" - "+rok_prod+"\n");

                }

                return modelSprzetyList;

                //return dane.toString();
               // return bufor.toString();




            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();

            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (polaczenie != null) {
                    polaczenie.disconnect();
                }
                if (czytnik_strumienia != null) {
                    try {
                        czytnik_strumienia.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<ModelSprzety> rezultat) {
            super.onPostExecute(rezultat);
            //TextView text_wyswietl = (TextView) findViewById(R.id.text_wyswietl);
            //text_wyswietl.setText(rezultat);
            SprzetyAdapter adapter = new SprzetyAdapter(getApplicationContext(), R.layout.element_listy, rezultat);
            lista.setAdapter(adapter);
        }
    }

    public class SprzetyAdapter extends ArrayAdapter{

        private List<ModelSprzety> modelSprzetyList;
        private int resource;
        private LayoutInflater inflater;

        public SprzetyAdapter(Context context, int resource, List<ModelSprzety> objects) {
            super(context, resource,  objects);
            modelSprzetyList = objects;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

             if (convertView == null) {
            convertView = inflater.inflate(resource, null);
             }


            TextView text_nazwa, text_model, text_producent, text_rok_prod, text_sn;
            ImageView zdjecie;

            zdjecie = (ImageView)convertView.findViewById(R.id.imageView);

            text_nazwa = (TextView) convertView.findViewById(R.id.text_nazwa);
            text_model = (TextView) convertView.findViewById(R.id.text_model);
            text_producent = (TextView) convertView.findViewById(R.id.text_producent);
            text_rok_prod = (TextView) convertView.findViewById(R.id.text_rok_prod);
            text_sn = (TextView) convertView.findViewById(R.id.text_sn);


            ImageLoader.getInstance().displayImage(modelSprzetyList.get(position).getFotka(), zdjecie);

            text_nazwa.setText(modelSprzetyList.get(position).getNazwa());
            text_model.setText(modelSprzetyList.get(position).getModel());
            text_producent.setText(modelSprzetyList.get(position).getProducent());
            text_rok_prod.setText(""+modelSprzetyList.get(position).getRok_prod());
            text_sn.setText(modelSprzetyList.get(position).getSerial_number());







            return convertView;
        }
    }

}



