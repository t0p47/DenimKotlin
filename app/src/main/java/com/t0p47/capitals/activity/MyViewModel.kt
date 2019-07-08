package com.t0p47.capitals.activity

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.t0p47.capitals.model.Capital
import com.t0p47.capitals.model.CapitalRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

class MyViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val ioScope = CoroutineScope(Dispatchers.IO + viewModelJob)
    val isLoading = ObservableField<Boolean>()

    private lateinit var disposable: Disposable
    private val remoteData = CapitalRepository(getApplication())


    var repositories = MutableLiveData<ArrayList<Capital>>()

    fun loadRepositories(){

        disposable = remoteData.getRepositories()
            .map {capResponse -> capResponse.capitals}
            .flatMap {capitals -> Observable.fromIterable(capitals)}
            .map {capital ->
                fun changeImgLink(capital: Capital): Capital{
                    capital.images?.forEachIndexed { id, value ->
                        val imgUri = Uri.parse(value)
                        if(imgUri.host == "en.wikipedia.org"){
                            val doc = Jsoup.connect(value).get()
                            val fileName = value.split("File:")[1]
                            val imgs = doc.select("img[src$=$fileName]").first()
                            capital.images?.set(id, "https:${imgs.attr("src")}")
                        }
                    }
                    return capital
                }
                changeImgLink(capital)
            }
            .collect({ArrayList<Capital>()}, {container, value-> container.add(value) })
            .doOnSubscribe { isLoading.set(true)}
            .doAfterTerminate { isLoading.set(false)}
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> repositories.value = result },
                { error -> Log.d("LOG_TAG","Error: ${error.message}") }
            )
    }

    private fun changeImgLink(capital: Capital): Capital {
        capital.images?.forEachIndexed { id, value ->
            val imgUri = Uri.parse(value)
            if(imgUri.host == "en.wikipedia.org"){
                val doc = Jsoup.connect(value.trim()).get()
                val fileName = value.split("File:")[1]
                val imgs = doc.select("img[src$=$fileName]").first()
                capital.images?.set(id, "https:${imgs.attr("src")}")
            }
        }
        return capital
    }

    fun addNewCapital(data: Intent?): Capital {

        var capital = Capital()
        capital.capital = data?.getStringExtra("capital")
        capital.country = data?.getStringExtra("country")
        capital.description = data?.getStringExtra("description")
        val imgList = data?.getStringExtra("images")

        if(imgList!!.contains(",")){
            capital.images = (imgList.split(",")) as ArrayList
        }else{
            capital.images = arrayListOf(imgList)
        }

        ioScope.launch(Dispatchers.IO){
            capital = changeImgLink(capital)
        }
        //continuation.resume(capital)

        return capital

    }

    override fun onCleared(){
    	super.onCleared()
    	if(!disposable.isDisposed){
    		disposable.dispose()
    	}
    }

}