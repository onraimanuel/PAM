package com.example.proyekpamkelompok06.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.proyekpamkelompok06.R
import com.example.proyekpamkelompok06.helper.Helper
import com.example.proyekpamkelompok06.model.Produk
import com.example.proyekpamkelompok06.room.MyDatabase
import com.example.proyekpamkelompok06.util.Config
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail_produk.*
import kotlinx.android.synthetic.main.toolbar.toolbar
import kotlinx.android.synthetic.main.toolbar_custom.*

class DetailProdukActivity : AppCompatActivity() {
    lateinit var produk: Produk
    lateinit var myDb: MyDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_produk)
        myDb = MyDatabase.getInstance(this)!!

        getInfo()
        mainButton()
        checkKeranjang()
    }

    private fun mainButton(){
        btn_keranjang.setOnClickListener {
            val data = myDb.daoKeranjang().getProduk(produk.id)
            if (data == null){
                insert()
            }else{
                data.jumlah += 1
                update(data)
            }
        }

        btn_favorit.setOnClickListener{
            get()
        }

        btn_toKeranjang.setOnClickListener{
            val intents = Intent("event:keranjang")
            LocalBroadcastManager.getInstance(this).sendBroadcast(intents)
            onBackPressed()
        }
    }

    private fun insert(){
        CompositeDisposable().add(Observable.fromCallable { myDb.daoKeranjang().insert(produk) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                checkKeranjang()
                Log.d("respons", "data inserted")
                Toast.makeText(this, "Berhasil menambah kekeranjang", Toast.LENGTH_SHORT).show()
            })
    }

    private fun update(data: Produk){
        CompositeDisposable().add(Observable.fromCallable { myDb.daoKeranjang().update(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                checkKeranjang()
                Log.d("respons", "data inserted")
                Toast.makeText(this,"Data berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
            })
    }

    private fun checkKeranjang(){
        val dataKeranjangs = myDb.daoKeranjang().getAll()

        if(dataKeranjangs.isNotEmpty()){
            div_angka.visibility = View.VISIBLE
            tv_angka.text = dataKeranjangs.size.toString()
        }else{
            div_angka.visibility = View.GONE
        }
    }

    fun get(){
        val listNote = myDb.daoKeranjang().getAll() // get All data
        for(note :Produk in listNote){
            println("-----------------------")
            println(note.nama)
            println(note.harga)
        }
    }


    fun getInfo(){

        val data = intent.getStringExtra("extra")
        produk = Gson().fromJson<Produk>(data, Produk::class.java)
        textViewNamaDetailKantin.text = produk.nama
        textViewStokDetailKantin.text = produk.stok.toString()
        textViewHargaDetailKantin.text = Helper().gantiRupiah(produk.harga)
        textViewDeskripsiDetailKantin.text = produk.deskripsi

        val url = Config.baseURL + produk.gambar

        Picasso.get()
            .load(url)
            .placeholder(R.drawable.ic_baseline_home_24_orange)
            .error(R.drawable.ic_baseline_edit_24_orange)
            .resize(400,400)
            .into(imageViewDetailKantin)

//    companion object{
//        const val NAMA = "nama"
//        const val HARGA = "harga"
//        const val DESKRIPSI = "deskripsi"
//    }

        setSupportActionBar(toolbar)
        supportActionBar?.title = produk.nama
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowCustomEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}