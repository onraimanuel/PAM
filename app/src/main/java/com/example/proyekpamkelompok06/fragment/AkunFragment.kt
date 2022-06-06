package com.example.proyekpamkelompok06.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.proyekpamkelompok06.MainActivity
import com.example.proyekpamkelompok06.R
import com.example.proyekpamkelompok06.activity.LoginActivity
import com.example.proyekpamkelompok06.helper.SharedPref


class AkunFragment : Fragment() {

    lateinit var s:SharedPref
    lateinit var btnLogout:TextView
    lateinit var textViewNamaAkun: TextView
    lateinit var textViewNoKTPAkun: TextView
    lateinit var textViewEmailAkun: TextView
    lateinit var textViewNoHPAkun: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_akun, container, false)
        btnLogout = view.findViewById(R.id.btn_logout)
        
        init(view)
        s = SharedPref(requireActivity())

        btnLogout.setOnClickListener{
            s.setStatusLogin(false)
        }

        setData()

        return view
    }

    fun setData(){
        if(s.getUser() == null){
            val intents = Intent(activity, LoginActivity::class.java)
            intents.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intents)
            return
        }

        val user = s.getUser()!!

        textViewNamaAkun.text = user.namaLengkap
        textViewNoKTPAkun.text = user.noKTP
        textViewEmailAkun.text = user.email
        textViewNoHPAkun.text = user.noHP
    }
    private fun init(view: View) {
        btnLogout = view.findViewById(R.id.btn_logout)
        textViewNamaAkun = view.findViewById(R.id.textViewNamaAkun)
        textViewNoKTPAkun = view.findViewById(R.id.textViewNoKTPAkun)
        textViewEmailAkun = view.findViewById(R.id.textViewEmailAkun)
        textViewNoHPAkun = view.findViewById(R.id.textViewNoHPAkun)
    }

}
