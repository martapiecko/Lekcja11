package com.example.x.lekcja11;

import android.database.sqlite.SQLiteCursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Telefon> telefonList;
    private EditText nazwaIn;
    private EditText modelIn;
    private EditText opisIn;

    private TextView nazwaOut;
    private TextView modelOut;
    private TextView opisOut;

    private Button dodaj;
    private Button usun;
    private Button nastepny;
    private Button poprzedni;
    private Button wyswietl;

    DatabaseHelper databaseHelper;
    private int aktualnaWartosc;
    private int maksymalnaWartosc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicjalizacja();
        OnClickListeners();
    }

    protected void inicjalizacja() {
        nazwaIn = (EditText) findViewById(R.id.nazwaIn);
        modelIn = (EditText) findViewById(R.id.modelIn);
        opisIn = (EditText) findViewById(R.id.opisIn);

        nazwaOut = (TextView) findViewById(R.id.nazwaOut);
        modelOut = (TextView) findViewById(R.id.modelOut);
        opisOut = (TextView) findViewById(R.id.opisOut);

        dodaj = (Button) findViewById(R.id.dodaj);
        usun = (Button) findViewById(R.id.usun);
        nastepny = (Button) findViewById(R.id.nastepny);
        poprzedni = (Button) findViewById(R.id.poprzedni);
        wyswietl = (Button) findViewById(R.id.wyswietl);
        databaseHelper = new DatabaseHelper(this);
        telefonList = new ArrayList<>();
    }

    public void addItemToDatabase() {
        Telefon telefon = new Telefon(0, nazwaIn.getText().toString(), modelIn.getText().toString(), opisIn.getText().toString());
        databaseHelper.insertTelefon(telefon);
        getItemsFromDatabase();
    }

    public void getItemsFromDatabase() {
        telefonList.clear();
        SQLiteCursor cursor = databaseHelper.getData();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                telefonList.add(new Telefon(Long.parseLong(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            }
        }

        maksymalnaWartosc = telefonList.size();
        aktualnaWartosc = 0;
        if (maksymalnaWartosc != 0)
            wyswietl(telefonList.get(0));
               else{
            wyswietl(null);
        }
    }

    public void wyswietl(Telefon telefon) {
        if (telefon != null) {
            nazwaOut.setText(telefon.getNazwa());
            modelOut.setText(telefon.getModel());
            opisOut.setText(telefon.getOpis());
        } else {
            nazwaOut.setText("Brak danych");
            modelOut.setText("Brak danych");
            opisOut.setText("Brak danych");
        }
    }

    public void usun(String id) {
        databaseHelper.deleteData(id);
    }

    protected void OnClickListeners() {
        final View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.dodaj:
                        addItemToDatabase();
                        Toast.makeText(MainActivity.this, "Dodano telefon do bazy danych.", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.poprzedni:
                        aktualnaWartosc--;
                        if (aktualnaWartosc < 0) aktualnaWartosc = maksymalnaWartosc - 1;
                        if (maksymalnaWartosc != 0)
                            wyswietl(telefonList.get(aktualnaWartosc));
                        else
                            wyswietl(null);
                        break;
                    case R.id.nastepny:
                        aktualnaWartosc++;
                        if (aktualnaWartosc >= maksymalnaWartosc) aktualnaWartosc = 0;
                        if (maksymalnaWartosc != 0)
                            wyswietl(telefonList.get(aktualnaWartosc));
                        else
                            wyswietl(null);
                        break;
                    case R.id.usun:
                        if (maksymalnaWartosc != 0) {
                            usun(Long.toString(telefonList.get(aktualnaWartosc).getId()));
                            getItemsFromDatabase();
                            Toast.makeText(MainActivity.this, "Usunięto telefon z bazy danych.", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case R.id.wyswietl:
                        getItemsFromDatabase();
                        Toast.makeText(MainActivity.this, "Pobrano dane z bazy danych.", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }
            }
        };
        dodaj.setOnClickListener(listener);
        nastepny.setOnClickListener(listener);
        poprzedni.setOnClickListener(listener);
        usun.setOnClickListener(listener);
        wyswietl.setOnClickListener(listener);
    }
}
