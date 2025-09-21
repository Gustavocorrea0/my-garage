package com.example.gustavo.mygaragev2.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gustavo.mygaragev2.model.Car
import com.example.gustavo.mygaragev2.databinding.AdpterCarsBinding

class CarAdapter(var cars: MutableList<Car>): RecyclerView.Adapter<CarAdapter.CarHolder> () {

    // holder
    inner class CarHolder(var binding: AdpterCarsBinding):
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarHolder {
        AdpterCarsBinding.inflate(LayoutInflater.from(parent.context),
            parent,false).apply {
            return CarHolder(this)
        }
    }

    override fun getItemCount(): Int {
        return cars.size
    }

    override fun onBindViewHolder(holder: CarHolder, position: Int) {
        cars[position].also { car ->
            holder.binding.txtCarName.text = car.carName
            holder.binding.txtCarPlate.text = car.carPlate
        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

}
