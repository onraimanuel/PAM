package com.example.proyekpamkelompok06.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyekpamkelompok06.R
import com.example.proyekpamkelompok06.adapter.AdapterKeranjang
import com.example.proyekpamkelompok06.helper.Helper
import com.example.proyekpamkelompok06.room.MyDatabase


class KeranjangFragment : Fragment() {

    lateinit var myDb : MyDatabase

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_keranjang, container, false)
        init(view)
        myDb =  MyDatabase.getInstance(requireActivity())!!
        mainButton()
        return view
    }

    private fun dispProduk(){

        val listProduk = myDb!!.daoKeranjang().getAll() as ArrayList
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        lateinit var adapter: AdapterKeranjang

        adapter = AdapterKeranjang(requireActivity(), listProduk, object : AdapterKeranjang.Listeners{
            override fun onUpdate() {
                hitungTotal()
            }

            override fun onDelete(position: Int) {
                listProduk.removeAt(position)
                adapter.notifyDataSetChanged()
                hitungTotal()
            }

        })

        rvProduk.adapter = adapter
        rvProduk.layoutManager = layoutManager
    }

    fun hitungTotal(){
        val listProduk = myDb!!.daoKeranjang().getAll() as ArrayList

        var totalHarga = 0
        for (produk in listProduk){
            val harga = Integer.valueOf(produk.harga)
            totalHarga += (harga * produk.jumlah)
        }

        tvTotal.text = Helper().gantiRupiahInt(totalHarga)
    }

    private fun mainButton(){
        btnDelete.setOnClickListener {

        }

        btnBayar.setOnClickListener {

        }
    }

    lateinit var btnDelete: ImageView
    lateinit var rvProduk: RecyclerView
    lateinit var tvTotal:TextView
    lateinit var btnBayar:TextView

    private fun init(view: View) {
        btnDelete = view.findViewById(R.id.btn_delete)
        rvProduk = view.findViewById(R.id.rv_produk)
        tvTotal = view.findViewById(R.id.tv_total)
        btnBayar = view.findViewById(R.id.btn_bayar)
    }

    override fun onResume() {
        dispProduk()
        hitungTotal()
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }
}
