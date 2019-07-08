package com.t0p47.capitals.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.t0p47.capitals.R
import com.t0p47.capitals.adapter.ImageCardViewAdapter
import com.t0p47.capitals.databinding.ActivityCapitalBinding

class CapitalActivity : AppCompatActivity() {

	lateinit var binding: ActivityCapitalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_capital)

        binding.tvCountry.text = intent.getStringExtra("country")
        binding.tvCapital.text = intent.getStringExtra("capital")
        binding.tvDescription.text = intent.getStringExtra("description")

        val imgList = intent.getStringExtra("images")
        if(imgList.isNotEmpty()){
            val imgArray: ArrayList<String> = if(imgList.contains(",")){
                (imgList?.split(",")) as ArrayList
            }else{
                arrayListOf(imgList!!)
            }

            binding.rvImages.layoutManager = GridLayoutManager(this, 2)
            binding.rvImages.adapter = ImageCardViewAdapter(imgArray)
        }
    }
}
