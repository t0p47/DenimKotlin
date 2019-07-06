package com.t0p47.capitals.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.t0p47.capitals.R
import com.t0p47.capitals.adapter.CardViewAdapter
import com.t0p47.capitals.databinding.ActivityMainBinding
import com.t0p47.capitals.model.Capital
import com.t0p47.capitals.model.CapitalResponse
import com.t0p47.capitals.rest.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    val TAG = "LOG_TAG"
    lateinit var binding: ActivityMainBinding
    private var disposable: Disposable? = null
    private var capitalsList: ArrayList<Capital>? = null

    private val capitalsService by lazy{
        ApiInterface.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val viewModel = ViewModelProviders.of(this).get(MyViewModel::class.java)

        viewModel.loadRepositories()
        binding.rvView.layoutManager = LinearLayoutManager(this)
        binding.fab.setOnClickListener{
            Log.d("LOG_TAG", "Fab clicked")
            /*if(capitalsList != null){
                val capital = Capital()
                capital.capital = "Bangkok"
                capital.country = "Thailand"
                capitalsList!!.add(capital)
                binding.rvView.adapter?.notifyDataSetChanged()
            }*/
            intent = Intent(this, NewCapitalActivity::class.java)
            startActivityForResult(intent, 1)
        }

        viewModel.repositories.observe(this,
            Observer<ArrayList<Capital>>{ it?.let{
                Log.d("LOG_TAG", "Get data from internet")
                binding.rvView.adapter = capitalsList?.let { CardViewAdapter(it) }
            }})
        //getCapitalSecond()
        //getCapitals()

        /*
        capitalsList = capitalResponse.capitals
        binding.rvView.adapter = capitalsList?.let { CardViewAdapter(it) }
        */
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){

        if(resultCode == RESULT_OK){
            if(capitalsList != null){
                val capital = Capital()
                val cap = data?.getStringExtra("capital")
                Log.d("LOG_TAG","Capital: $cap")
                capital.capital = data?.getStringExtra("capital")
                capital.country = data?.getStringExtra("country")
                capital.description = data?.getStringExtra("description")
                val imgList = (data?.getStringExtra("images")?.split(",")) as ArrayList
                capital.images = imgList
                capitalsList!!.add(capital)
                binding.rvView.adapter?.notifyDataSetChanged()
            }
        }

    }

    private fun getCapitalSecond(){
        disposable = capitalsService.getCapitals()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> showResult(result) },
                { error -> Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show() }
            )
    }

    private fun showResult(capitalResponse: CapitalResponse){
        Log.d("LOG_TAG","Response complete")
        capitalsList = capitalResponse.capitals
        binding.rvView.adapter = capitalsList?.let { CardViewAdapter(it) }
    }
}
