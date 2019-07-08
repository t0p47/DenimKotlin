package com.t0p47.capitals.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.t0p47.capitals.R
import com.t0p47.capitals.adapter.CardViewAdapter
import com.t0p47.capitals.databinding.ActivityMainBinding
import com.t0p47.capitals.model.Capital

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MyViewModel
    lateinit var binding: ActivityMainBinding
    private var capitalsList: ArrayList<Capital>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MyViewModel::class.java)
        binding.vm = viewModel


        if(viewModel.repositories.value == null){
            viewModel.loadRepositories()
        }

        binding.rvView.layoutManager = LinearLayoutManager(this)
        binding.fab.setOnClickListener{
            val intent = Intent(this, NewCapitalActivity::class.java)
            startActivityForResult(intent, 1)
        }

        viewModel.repositories.observe(this,
            Observer<ArrayList<Capital>>{
                capitalsList = it
                binding.rvView.adapter = CardViewAdapter(capitalsList!!)
            })
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){

        if(resultCode == RESULT_OK){
            if(capitalsList != null){
                val capital = viewModel.addNewCapital(data)

                capitalsList!!.add(capital)
                binding.rvView.adapter?.notifyDataSetChanged()
            }
        }

    }
}
