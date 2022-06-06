package com.example.proyekpamkelompok06.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyekpamkelompok06.R
import com.example.proyekpamkelompok06.activity.DetailProdukActivity
import com.example.proyekpamkelompok06.activity.DetailPulsaActivity
import com.example.proyekpamkelompok06.helper.Helper
import com.example.proyekpamkelompok06.model.Produk
import com.example.proyekpamkelompok06.model.Pulsa
import com.google.gson.Gson
import kotlin.collections.ArrayList

class AdapterPulsa(var activity: Activity, var data:ArrayList<Pulsa>):RecyclerView.Adapter<AdapterPulsa.Holder>() {
    class Holder(view: View):RecyclerView.ViewHolder(view){
        val tvNamaPulsa = view.findViewById<TextView>(R.id.tv_namaPulsa)
        val tvHargaPulsa = view.findViewById<TextView>(R.id.tv_hargaPulsa)
        val tvDeskripsiPulsa = view.findViewById<TextView>(R.id.tv_deskripsiPulsa)
        val layout = view.findViewById<CardView>(R.id.layoutPulsa)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_pulsa, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tvNamaPulsa.text = data[position].nama
        holder.tvHargaPulsa.text = Helper().gantiRupiah(data[position].harga)
        holder.tvDeskripsiPulsa.text = data[position].deskripsi

        holder.layout.setOnClickListener {
            val act = Intent(activity, DetailPulsaActivity::class.java)

            val str = Gson().toJson(data[position], Pulsa::class.java)

            act.putExtra("extra", str)
            activity.startActivity(act)
        }
    }

}