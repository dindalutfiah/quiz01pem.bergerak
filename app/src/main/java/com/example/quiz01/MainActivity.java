package com.example.quiz01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText namaEditText, jumlahEditText, kodeBarangEditText;
    private RadioButton goldRadioButton, silverRadioButton, biasaRadioButton;
    private Button prosesButton;
    private Map<String, Integer> hargaBarangMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        namaEditText = findViewById(R.id.namaEditText);
        jumlahEditText = findViewById(R.id.jumlahEditText);
        kodeBarangEditText = findViewById(R.id.kodeBarangEditText);
        goldRadioButton = findViewById(R.id.goldRadioButton);
        silverRadioButton = findViewById(R.id.silverRadioButton);
        biasaRadioButton = findViewById(R.id.biasaRadioButton);
        prosesButton = findViewById(R.id.prosesButton);

        hargaBarangMap.put("PCO", 2730551);
        hargaBarangMap.put("AA5", 9999999);
        hargaBarangMap.put("SGS", 12999999);

        goldRadioButton.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View v) {
            if (goldRadioButton.isChecked()) {
                silverRadioButton.setChecked(false);
                biasaRadioButton.setChecked(false);
            }
        }
        });

        silverRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (silverRadioButton.isChecked()) {
                    goldRadioButton.setChecked(false);
                    biasaRadioButton.setChecked(false);
                }
            }
        });biasaRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (biasaRadioButton.isChecked()) {
                    goldRadioButton.setChecked(false);
                    silverRadioButton.setChecked(false);
                }
            }
        });

        prosesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prosesTransaksi();
            }
        });
    } private void prosesTransaksi() {
        String nama = namaEditText.getText().toString();
        int jumlahBarang;
        try {
            jumlahBarang = Integer.parseInt(jumlahEditText.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Masukkan jumlah barang yang valid", Toast.LENGTH_SHORT).show();
            return;
        }

        String jenisMembership = "";
        if (goldRadioButton.isChecked()) {
            jenisMembership = "Gold";
        } else if (silverRadioButton.isChecked()) {
            jenisMembership = "Silver";
        } else if (biasaRadioButton.isChecked()) {
            jenisMembership = "Biasa";
        }String kodeBarang = kodeBarangEditText.getText().toString().toUpperCase(); // Mengubah menjadi huruf kapital
        int hargaBarang = 0;

        // Validasi kode barang dan ambil harga sesuai kode barang
        if (hargaBarangMap.containsKey(kodeBarang)) {
            hargaBarang = hargaBarangMap.get(kodeBarang);
        } else {
            Toast.makeText(this, "Kode barang tidak valid", Toast.LENGTH_SHORT).show();
            return;
        }

        int totalHarga = jumlahBarang * hargaBarang;

        // Berikan diskon harga sebesar 100 ribu jika total harga melebihi 10 juta
        if (totalHarga > 10000000) {
            totalHarga -= 100000;
        } // Format harga ke format mata uang Rupiah
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        String hargaFormatted = formatRupiah.format(hargaBarang);

        Intent intent = new Intent(this, TransaksiActivity.class);
        intent.putExtra("nama", nama);
        intent.putExtra("jumlahBarang", jumlahBarang);
        intent.putExtra("jenisMembership", jenisMembership);
        intent.putExtra("kodeBarang", kodeBarang);
        intent.putExtra("hargaBarang", hargaFormatted);
        intent.putExtra("totalHarga", totalHarga);
        startActivity(intent);
    }
}






