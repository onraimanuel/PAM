package com.example.proyekpamkelompok06.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.proyekpamkelompok06.MainActivity
import com.example.proyekpamkelompok06.R
import com.example.proyekpamkelompok06.app.ApiConfig
import com.example.proyekpamkelompok06.helper.SharedPref
import com.example.proyekpamkelompok06.model.ResponseModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.btn_google
import kotlinx.android.synthetic.main.activity_register.edtEmail
import kotlinx.android.synthetic.main.activity_register.edtPass
import kotlinx.android.synthetic.main.activity_register.loading
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterActivity : AppCompatActivity() {
    lateinit var  s:SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        s = SharedPref(this)

        btn_register.setOnClickListener {
            register()
        }

        btn_google.setOnClickListener {
            dataDummy()
        }
    }

    fun dataDummy(){
        edtNama.setText("Onra")
        edtKTP.setText("1234523421541132")
        edtEmail.setText("onraimanuel11@gmail.com")
        edtHP.setText("082215836272")
        edtPass.setText("oke12345")

    }

    fun register() {
        if (edtNama.text.isEmpty()) {
            edtNama.error = "Nama tidak boleh kosong"
            edtNama.requestFocus()
            return
        } else if (edtKTP.text.isEmpty()) {
            edtKTP.error = "KTP tidak boleh kosong"
            edtKTP.requestFocus()
            return
        } else if (edtHP.text.isEmpty()) {
            edtHP.error = "Nomor HP tidak boleh kosong"
            edtHP.requestFocus()
            return
        } else if (edtEmail.text.isEmpty()) {
            edtEmail.error = "Email tidak boleh kosong"
            edtEmail.requestFocus()
            return
        } else if (edtPass.text.isEmpty()) {
            edtPass.error = "Password tidak boleh kosong"
            edtPass.requestFocus()
            return
        }

        loading.visibility = View.VISIBLE
        ApiConfig.instanceRetrofit.register(edtNama.text.toString(), edtKTP.text.toString(), edtHP.text.toString(), edtEmail.text.toString(), edtPass.text.toString()).enqueue(object : Callback<ResponseModel>{

            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                loading.visibility = View.GONE
                val resp = response.body()!!

                if (resp.success == 1){
                    s.setStatusLogin(true)
                    val intents = Intent(this@RegisterActivity, MainActivity::class.java)
                    intents.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intents)
                    finish()
                    Toast.makeText(this@RegisterActivity, "Selamat Datang" + resp.user.namaLengkap, Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@RegisterActivity, "Error: "+resp.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                loading.visibility = View.GONE
                    Toast.makeText(this@RegisterActivity, "Error: " + t.message, Toast.LENGTH_SHORT).show()

            }

        })

    }
}