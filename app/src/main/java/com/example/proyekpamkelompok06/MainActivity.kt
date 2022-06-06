package com.example.proyekpamkelompok06

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.proyekpamkelompok06.activity.LoginActivity
import com.example.proyekpamkelompok06.activity.MasukActivity
import com.example.proyekpamkelompok06.fragment.AkunFragment
import com.example.proyekpamkelompok06.fragment.HomeFragment
import com.example.proyekpamkelompok06.fragment.KeranjangFragment
import com.example.proyekpamkelompok06.helper.SharedPref
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    val fragmentHome: Fragment = HomeFragment()
    val fragmentAkun: Fragment = AkunFragment()
    val fragmentKeranjang: Fragment = KeranjangFragment()
    val fm: FragmentManager = supportFragmentManager
    var active: Fragment = fragmentHome

    private lateinit var menu: Menu
    private lateinit var menuItem: MenuItem
    private lateinit var bottomNavigationView: BottomNavigationView

    private var statusLogin = false
    private lateinit var s: SharedPref

    private var dariDetail : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        s = SharedPref(this)

        setUpBottomNav()

        LocalBroadcastManager.getInstance(this).registerReceiver(pesan, IntentFilter("event:keranjang"))
    }

    val pesan : BroadcastReceiver = object :BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {
            dariDetail = true
        }

    }

    fun setUpBottomNav(){

        fm.beginTransaction().add(R.id.container, fragmentHome).show(fragmentHome).commit()
        fm.beginTransaction().add(R.id.container, fragmentAkun).hide(fragmentAkun).commit()
        fm.beginTransaction().add(R.id.container, fragmentKeranjang).hide(fragmentKeranjang).commit()

        bottomNavigationView = findViewById(R.id.nav_view)
        menu = bottomNavigationView.menu
        menuItem = menu.getItem(0)
        menuItem.isChecked = true

        bottomNavigationView.setOnNavigationItemSelectedListener{ item ->
            when(item.itemId){
                R.id.navigation_home ->{
                    callFragment(0,fragmentHome)

                }
                R.id.navigation_keranjang ->{
                    callFragment(1,fragmentKeranjang)

                }
                R.id.navigation_akun ->{
                    if (s.getStatusLogin()){
                        callFragment(2,fragmentAkun)
                    }else {
                        startActivity(Intent(this,MasukActivity::class.java))
                    }
                }
            }
            false
        }
    }


    fun callFragment(int: Int, fragment: Fragment){
        menuItem = menu.getItem(int)
        menuItem.isChecked = true
        fm.beginTransaction().hide(active).show(fragment).commit()
        active = fragment
    }

    override fun onResume() {
        if (dariDetail){
            dariDetail = false
            callFragment(1,fragmentKeranjang)
        }
        super.onResume()
    }
}