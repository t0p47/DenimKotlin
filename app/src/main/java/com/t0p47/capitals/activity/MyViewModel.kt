package com.t0p47.capitals.activity

import android.app.Application
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.t0p47.capitals.model.Capital
import com.t0p47.capitals.model.CapitalRepository
import com.t0p47.capitals.model.CapitalResponse
import com.t0p47.capitals.rest.ApiInterface
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup

class MyViewModel: AndroidViewModel {

	private val capitalsService by lazy{
		ApiInterface.create(getApplication())
	}
    lateinit var disposable: Disposable
    var repositories = MutableLiveData<ArrayList<Capital>>()

    constructor(application: Application) : super(application)

    fun loadRepositories(){
		Log.d("LOG_TAG","loadRepositories")
    	/*disposable = capitalRepository.getRepositories().subscribeWith(object: DisposableObserver<CapitalResponse>(){

    		override fun onError(e: Throwable){
				Log.d("LOG_TAG","Error: ${e.stackTrace}")
			}

    		override fun onNext(data: CapitalResponse){
				Log.d("LOG_TAG","onNext")
    			repositories.value = data
    		}

    		override fun onComplete(){
				Log.d("LOG_TAG","OnComplete")
			}

		})*/

		disposable = capitalsService.getCapitals()
            /*.map(Function1<CapitalResponse, ArrayList<Capital>{
                override fun call(capitalResponse: CapitalResponse): ArrayList<Capital>(){
                    return capitalResponse.capitals
                }
            })*/
            .map {capResponse -> capResponse.capitals}
            /*.flatMap(Func1<ArrayList<Capital>>, Observable<Capital>{

                override fun call(capitals: ArrayList<Capital>): Observable<Capital>(){
                    return Observable.from(capitals)
                }

            })*/
            .flatMap {capitals -> Observable.fromIterable(capitals)}
            /*.map(Func1<Capital, Capital>(){

                override fun call(capital: Capital): Capital{
                    capital.images.forEach{
                        val imgUri = Uri.parse(it)
                        if(imgUri.host == "en.wikipedia"){
                            val doc = Jsoup.connect(it).get
                            val fileName = it.split("File:")[1]
                            val imgs = doc.select("img[src$=${fileName}]").first()
                            it = imgs.attr("src")
                        }
                    }
                    return capital
                }

            })*/
            .map {capital ->
                fun changeImgLink(capital: Capital): Capital{
                    capital.images?.forEachIndexed { id, value ->
                        val imgUri = Uri.parse(value)
                        Log.d("LOG_TAG","Check image host: ${imgUri.host}")
                        if(imgUri.host == "en.wikipedia.org"){
                            val doc = Jsoup.connect(value).get()
                            val fileName = value.split("File:")[1]
                            val imgs = doc.select("img[src$=${fileName}]").first()
                            capital.images?.set(id, "http:${imgs.attr("src")}")
                        }
                    }
                    return capital
                }
                changeImgLink(capital)
            }
            /*.collect(ArrayList(), (container, value) -> {
                Log.d("LOG_TAG", "Adding $value to container")
                container.add(value)    
            })*/
            .collect({ArrayList<Capital>()}, {container, value-> container.add(value) })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                //{ result -> repositories.value = result },
                { result -> repositories.value = result },
                { error -> Log.d("LOG_TAG","Error: ${error.message}") }
            )
    }

    override fun onCleared(){
    	super.onCleared()
    	if(!disposable.isDisposed){
    		disposable.dispose()
    	}
    }

}