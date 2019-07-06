package com.t0p47.capitals.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CapitalActivity : AppCompatActivity() {

	//lateinit var binding: ActivityCapitalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = DataBindingUtil.setContentView(this, R.layout.activity_capital)

        //binding.tvCountry.text = intent.getStringExtra("country")
        //binding.tvCapital.text = intent.getStringExtra("capital")
        //binding.tvDescription.text = intent.getStringExtra("description")
    }
}
