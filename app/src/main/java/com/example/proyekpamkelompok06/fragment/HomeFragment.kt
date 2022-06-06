package com.example.proyekpamkelompok06.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.proyekpamkelompok06.R
import com.example.proyekpamkelompok06.adapter.AdapterProduk
import com.example.proyekpamkelompok06.adapter.AdapterPulsa
import com.example.proyekpamkelompok06.adapter.AdapterSlider
import com.example.proyekpamkelompok06.app.ApiConfig
import com.example.proyekpamkelompok06.model.Produk
import com.example.proyekpamkelompok06.model.Pulsa
import com.example.proyekpamkelompok06.model.ResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    lateinit var vpSlider: ViewPager
    lateinit var rvProduk: RecyclerView
    lateinit var rvPulsa: RecyclerView
    lateinit var listProduk: ArrayList<Produk>
    lateinit var listPulsa: ArrayList<Pulsa>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        init(view)
        getProduk()
        getPulsa()
        listProduk =  ArrayList()
        listPulsa =  ArrayList()

        return view
    }

    fun displayProduk() {
        val arrSlider = ArrayList<Int>()
        arrSlider.add(R.drawable.plaza)
        arrSlider.add(R.drawable.logodel)

        val adapterSlider = AdapterSlider(arrSlider, activity)
        vpSlider.adapter = adapterSlider

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL

        val layoutManager2 = GridLayoutManager(context, 2)
        layoutManager.orientation = GridLayoutManager.HORIZONTAL
//        println("cel ${listProduk.size}")

        rvProduk.adapter = AdapterProduk(requireActivity(), listProduk)
        rvProduk.layoutManager = layoutManager

        rvPulsa.adapter = AdapterPulsa(requireActivity(),listPulsa)
        rvPulsa.layoutManager = layoutManager2

//        rvProduk.adapter = AdapterProduk(arrProduk)
//        rvProduk.layoutManager = layoutManager
//
//        rvPulsa.adapter = AdapterPulsa(arrPulsa)
//        rvPulsa.layoutManager = layoutManager2
    }

    fun getProduk() {
        ApiConfig.instanceRetrofit.getProduk().enqueue(object :
            Callback<ResponseModel> {

            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                if (response.isSuccessful) {
                    response.body()?.itemMenuKantin?.forEach {
                        listProduk.add(it)
                    }
                    displayProduk()
                } else {
                    Log.d("Error", response.message())
                }
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Log.d("Error ", t.message.toString())
            }

        })
    }

    fun getPulsa() {
        ApiConfig.instanceRetrofit.getPulsa().enqueue(object :
            Callback<ResponseModel> {

            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                if (response.isSuccessful) {
                    response.body()?.itemMenuPulsa?.forEach {
                        listPulsa.add(it)
                    }
                    displayProduk()
                } else {
                    Log.d("Error", response.message())
                }
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Log.d("Error ", t.message.toString())
            }

        })
    }

    fun init(view: View) {
        vpSlider = view.findViewById(R.id.vp_slider)
        rvProduk = view.findViewById(R.id.rv_produk)
        rvPulsa = view.findViewById(R.id.rv_pulsa)
    }

//    val arrProduk: ArrayList<Produk>get(){
//        val arr = ArrayList<Produk>()
//        val p1 = Produk()
//        p1.nama ="Goreng"
//        p1.harga = "Rp.50.000"
//        p1.gambar = R.drawable.plaza
//
//        val p2 = Produk()
//        p2.nama ="Logo Del"
//        p2.harga = "Rp.51.000"
//        p2.gambar = R.drawable.logodel
//
//        arr.add(p1)
//        arr.add(p2)
//        return arr
//    }

//    val arrPulsa: ArrayList<Pulsa> get() {
//            val arr = ArrayList<Pulsa>()
//            val pls1 = Pulsa()
//            pls1.nama = "Goreng"
//            pls1.harga = "Rp.50.000"
//
//            val pls2 = Pulsa()
//            pls2.nama = "Logo Del"
//            pls2.harga = "Rp.51.000"
//
//            val pls3 = Pulsa()
//            pls3.nama = "Goreng"
//            pls3.harga = "Rp.50.000"
//
//            val pls4 = Pulsa()
//            pls4.nama = "Logo Del"
//            pls4.harga = "Rp.51.000"
//
//            arr.add(pls1)
//            arr.add(pls2)
//            arr.add(pls3)
//            arr.add(pls4)
//            return arr
//        }
}
