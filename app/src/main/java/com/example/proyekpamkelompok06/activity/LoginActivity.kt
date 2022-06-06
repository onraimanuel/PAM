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
import kotlinx.android.synthetic.main.activity_login.edtEmail
import kotlinx.android.synthetic.main.activity_login.edtPass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
lateinit var  s:SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        s = SharedPref(this)

        btn_login.setOnClickListener {
            login()
        }
    }

    fun login() {
         if (edtEmail.text.isEmpty()) {
            edtEmail.error = "Email tidak boleh kosong"
            edtEmail.requestFocus()
            return
        } else if (edtPass.text.isEmpty()) {
            edtPass.error = "Password tidak boleh kosong"
            edtPass.requestFocus()
            return
        }

        loading.visibility = View.VISIBLE
        ApiConfig.instanceRetrofit.login(edtEmail.text.toString(), edtPass.text.toString()).enqueue(object :
            Callback<ResponseModel> {

            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                loading.visibility = View.GONE
                val resp = response.body()!!

                if (resp.success == 1){
                    s.setStatusLogin(true)
                    s.setUser(resp.user)

                    val intents = Intent(this@LoginActivity, MainActivity::class.java)
                    intents.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intents)
                    finish()
                    Toast.makeText(this@LoginActivity, "Selamat Datang " + resp.user.namaLengkap, Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@LoginActivity, "Error: "+resp.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                loading.visibility = View.GONE
                Toast.makeText(this@LoginActivity, "Error: " + t.message, Toast.LENGTH_SHORT).show()

            }

        })

    }
}