package com.example.quiz01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.NumberFormat;
import java.util.Locale;

public class TransaksiActivity extends AppCompatActivity {

    private TextView welcomeTextView;
    private TextView transaksiTextView;
    private TextView thanksTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);

        welcomeTextView = findViewById(R.id.welcomeTextView);
        transaksiTextView = findViewById(R.id.transaksiTextView);
        thanksTextView = findViewById(R.id.thanksTextView);
        Button shareButton = findViewById(R.id.shareButton);

        Intent intent = getIntent();
        String nama = intent.getStringExtra("nama");
        int jumlahBarang = intent.getIntExtra("jumlahBarang", 0);
        String jenisMembership = intent.getStringExtra("jenisMembership");
        String kodeBarang = intent.getStringExtra("kodeBarang");
        String hargaBarang = intent.getStringExtra("hargaBarang");
        int totalHarga = intent.getIntExtra("totalHarga", 0);
        double diskonMember = intent.getDoubleExtra("diskonMember", 0); // Mengambil diskon member dari intent

        welcomeTextView.setText("Selamat datang, " + nama + "!\nTipe Member: " + jenisMembership);

        String transaksiDetail = "Transaksi hari ini:\n" +
                "Kode Barang: " + kodeBarang + "\n" +
                "Nama Barang: " + getNamaBarang(kodeBarang) + "\n" +
                "Jumlah Barang: " + jumlahBarang + "\n" +
                "Harga: " + hargaBarang + "\n" +
                "Total Harga: " + formatRupiah(totalHarga) + "\n";

        double diskonPersen = 0;
        switch (jenisMembership) {
            case "Gold":
                diskonPersen = 0.1; // Diskon 10%
                break;
            case "Silver":
                diskonPersen = 0.05; // Diskon 5%
                break;
            case "Biasa":
                diskonPersen = 0.02; // Diskon 2%
                break;
            default:
                break;
        }
        double diskonMemberValue = totalHarga * diskonPersen;
        transaksiDetail += "Diskon Member: " + formatRupiah((int) diskonMemberValue) + "\n";

        // Jika total harga melebihi 10 juta, tambahkan diskon harga
        if (totalHarga > 10000000) {
            int discountHarga = 100000;
            transaksiDetail += "Diskon Harga: " + formatRupiah(discountHarga) + "\n";
            totalHarga -= discountHarga;
        }

        int totalBayar = (int) (totalHarga - diskonMemberValue);
        transaksiDetail += "Jumlah Bayar: " + formatRupiah(totalBayar) + "\n";

        transaksiTextView.setText(transaksiDetail);

        thanksTextView.setText("Terima kasih telah berbelanja disini!");

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String transaksi = welcomeTextView.getText().toString() + "\n" +
                        transaksiTextView.getText().toString() + "\n" +
                        thanksTextView.getText().toString();
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, transaksi);
                startActivity(Intent.createChooser(shareIntent, "Bagikan Transaksi"));
            }
        });
    }


    private String getNamaBarang(String kodeBarang) {
        switch (kodeBarang) {
            case "PCO":
                return "Poco M3";
            case "AA5":
                return "Acer Aspire 5";
            case "SGS":
                return "Samsung Galaxy S";
            default:
                return "Unknown";
        }
    }

    private String formatRupiah(int harga) {
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        return formatRupiah.format(harga);
    }
}
