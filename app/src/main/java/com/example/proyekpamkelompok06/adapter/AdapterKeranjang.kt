package com.example.proyekpamkelompok06.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyekpamkelompok06.R
import com.example.proyekpamkelompok06.helper.Helper
import com.example.proyekpamkelompok06.model.Produk
import com.example.proyekpamkelompok06.room.MyDatabase
import com.example.proyekpamkelompok06.util.Config
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlin.collections.ArrayList

class AdapterKeranjang(var activity: Activity, var data:ArrayList<Produk>, var listener :Listeners):RecyclerView.Adapter<AdapterKeranjang.Holder>() {

    class Holder(view: View):RecyclerView.ViewHolder(view){
        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
        val tvHarga = view.findViewById<TextView>(R.id.tv_harga)
        val imgProduk = view.findViewById<ImageView>(R.id.img_produk)
        val layout = view.findViewById<CardView>(R.id.layout)

        val btnTambah = view.findViewById<ImageView>(R.id.btn_tambah)
        val btnKurang = view.findViewById<ImageView>(R.id.btn_kurang)
        val btnHapus = view.findViewById<ImageView>(R.id.btn_deletes)
        val checkBox = view.findViewById<CheckBox>(R.id.checkBox)
        val tvJumlah = view.findViewById<TextView>(R.id.tv_jumlahs)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_keranjang, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val produk = data[position]
        val hargaProduk = Integer.valueOf(produk.harga)

        holder.tvNama.text = produk.nama
        holder.tvHarga.text = Helper().gantiRupiah(hargaProduk * produk.jumlah)

        var jumlah = produk.jumlah

        val url = Config.baseURL + produk.gambar

        Picasso.get()
            .load(url)
            .placeholder(R.drawable.ic_baseline_home_24_orange)
            .error(R.drawable.ic_baseline_edit_24_orange)
            .into(holder.imgProduk)


//        holder.layout.setOnClickListener {
//            val act = Intent(activity, DetailProdukActivity::class.java)
//
//            val str = Gson().toJson(data[position], Produk::class.java)
//
//            act.putExtra("extra", str)
//            activity.startActivity(act)
//        }

        holder.btnTambah.setOnClickListener {
            jumlah++
            produk.jumlah = jumlah
            update(produk)
            holder.tvJumlah.text = jumlah.toString()
            holder.tvHarga.text =Helper().gantiRupiah(hargaProduk * jumlah)
        }

        holder.btnKurang.setOnClickListener {
            if (jumlah <= 1) return@setOnClickListener
            jumlah--
            produk.jumlah = jumlah
            update(produk)
            holder.tvJumlah.text = jumlah.toString()
            holder.tvHarga.text = Helper().gantiRupiah(hargaProduk * jumlah)

        }

        holder.btnHapus.setOnClickListener {
            delete(produk)
            listener.onDelete(position)

        }
    }

    interface Listeners{
        fun onUpdate()

        fun onDelete(position: Int)
    }

    private fun update(data: Produk){
        val myDb = MyDatabase.getInstance(activity)
        CompositeDisposable().add(Observable.fromCallable { myDb!!.daoKeranjang().update(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                listener.onUpdate()
            })
    }

    private fun delete(data: Produk){
        val myDb = MyDatabase.getInstance(activity)
        CompositeDisposable().add(Observable.fromCallable { myDb!!.daoKeranjang().delete(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {

            })
//        data.remove(produk)
//        this.notifyDataSetChanged()
    }

}