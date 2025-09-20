package com.example.gustavo.mygaragev2.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gustavo.mygaragev2.model.City
import com.example.gustavo.mygaragev2.databinding.AdpterCitiesBinding
import kotlin.toString

class CityAdapter(var cities: MutableList<City>): RecyclerView.Adapter<CityAdapter.CityHolder> () {

    // holder
    inner class CityHolder(var binding: AdpterCitiesBinding):
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        AdpterCitiesBinding.inflate(LayoutInflater.from(parent.context),
            parent,false).apply {
            return CityHolder(this)
        }
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        cities[position].also { city ->
            holder.binding.txtName.text = city.name
            holder.binding.txtPeople.text = city.people.toString()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

}
