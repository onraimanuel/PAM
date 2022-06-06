package com.example.proyekpamkelompok06.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proyekpamkelompok06.R
import com.example.proyekpamkelompok06.helper.Helper
import com.example.proyekpamkelompok06.model.Produk
import com.example.proyekpamkelompok06.model.Pulsa
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail_pulsa.*
import kotlinx.android.synthetic.main.toolbar.*

class DetailPulsaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_pulsa)
        getInfoPls()
    }

    fun getInfoPls(){

        val data = intent.getStringExtra("extra")
        val pulsa = Gson().fromJson<Pulsa>(data, Pulsa::class.java)
        textViewNamaDetailPulsa.text = pulsa.nama
        textViewHargaDetailPulsa.text = Helper().gantiRupiah(pulsa.harga)
        textViewDeskripsiDetailPulsa.text = pulsa.deskripsi

//    companion object{
//        const val NAMA = "nama"
//        const val HARGA = "harga"
//        const val DESKRIPSI = "deskripsi"
//    }

        setSupportActionBar(toolbar)
        supportActionBar?.title = pulsa.nama
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowCustomEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}