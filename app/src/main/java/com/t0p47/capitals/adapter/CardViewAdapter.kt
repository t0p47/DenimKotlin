package com.t0p47.capitals.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.t0p47.capitals.activity.CapitalActivity
import com.t0p47.capitals.databinding.CapitalCardBinding
import com.t0p47.capitals.model.Capital


class CardViewAdapter(val capitalList: ArrayList<Capital>): RecyclerView.Adapter<CardViewAdapter.CardViewHolder>() {

	class CardViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
		var binding: CapitalCardBinding? = DataBindingUtil.bind(itemView)
		init{
			Log.d("LOG_TAG","CardViewHolder create")
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder{
		Log.d("LOG_TAG", "CardViewAdapter: onCreateViewHolder")
		val inflater = LayoutInflater.from(parent.context)
		val binding = CapitalCardBinding.inflate(inflater, parent, false)
		return CardViewHolder(binding.root)
	}

	override fun onBindViewHolder(holder: CardViewHolder, position: Int){
		val capital = capitalList[position]
		holder.binding?.capital = capital
		holder.binding?.click = object: CapitalClickHandler{

			override fun onDotsClick(v: View){
				Log.d("LOG_TAG","CardViewAdapter: onDotsClick")
				val intent = Intent(v.context, CapitalActivity::class.java)
				intent.putExtra("capital",capital.capital)
				intent.putExtra("country",capital.country)
				intent.putExtra("description",capital.description)
				intent.putExtra("images", capital.images)
				startActivity(v.context, intent, null)
			}

		}

		//val dr = holder.binding.imgView.getDrawable()
		//val bmp: Bitmap = (dr.getCurrent() as GlideBitmapDrawable).getBitmap()

		//val intent = Intent(context, CapitalActivity::class.java)
		//intent.putExtra("capital",capital.capital)
		//intent.putExtra("country",capital.country)
		//intent.putExtra("description",capital.description)
		//startActivity(this, intent)

	}

	override fun getItemCount(): Int{
		return capitalList.size
	}

}

interface CapitalClickHandler{
	fun onDotsClick(v: View)
}