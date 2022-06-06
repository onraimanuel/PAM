package com.example.proyekpamkelompok06.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyekpamkelompok06.R
import com.example.proyekpamkelompok06.activity.DetailProdukActivity
import com.example.proyekpamkelompok06.helper.Helper
import com.example.proyekpamkelompok06.model.Produk
import com.example.proyekpamkelompok06.util.Config
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList

class AdapterProduk(var activity: Activity, var data:ArrayList<Produk>):RecyclerView.Adapter<AdapterProduk.Holder>() {
    class Holder(view: View):RecyclerView.ViewHolder(view){
        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
        val tvHarga = view.findViewById<TextView>(R.id.tv_harga)
        val tvDeskipsi = view.findViewById<TextView>(R.id.tv_deskripsi)
        val imgProduk = view.findViewById<ImageView>(R.id.img_produk)
        val layout = view.findViewById<CardView>(R.id.layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_produk, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tvNama.text = data[position].nama
        holder.tvHarga.text = Helper().gantiRupiah(data[position].harga)
        holder.tvDeskipsi.text = data[position].deskripsi

        val url = Config.baseURL +data[position].gambar

        Picasso.get()
            .load(url)
            .placeholder(R.drawable.ic_baseline_home_24_orange)
            .error(R.drawable.ic_baseline_edit_24_orange)
            .into(holder.imgProduk)


        holder.layout.setOnClickListener {
            val act = Intent(activity, DetailProdukActivity::class.java)

            val str = Gson().toJson(data[position], Produk::class.java)

            act.putExtra("extra", str)
            activity.startActivity(act)
        }
    }

}