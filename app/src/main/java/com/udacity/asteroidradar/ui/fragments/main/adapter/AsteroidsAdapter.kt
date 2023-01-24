package com.udacity.asteroidradar.ui.fragments.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.pojo.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.OneAsteroidItemBinding

class AsteroidsAdapter(
    private var data: List<Asteroid>,
    private val asteroidListener: AsteroidListener
) :
    RecyclerView.Adapter<AsteroidsAdapter.AsteroidViewHolder>() {

    fun setData(data: List<Asteroid>) {
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        holder.bind(data[position], asteroidListener)
    }

    override fun getItemCount() = data.size


    class AsteroidViewHolder private constructor(private val binding: OneAsteroidItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Asteroid, asteroidListener: AsteroidListener) {
            binding.asteroid = item
            binding.clickListener = asteroidListener
            binding.statusIcon.contentDescription = if (item.isPotentiallyHazardous)
                "Potentially Hazardous"
            else
                "Not Hazardous"
        }

        companion object {
            fun from(parent: ViewGroup): AsteroidViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.one_asteroid_item, parent, false)
                return AsteroidViewHolder(OneAsteroidItemBinding.bind(view))
            }
        }
    }

    class AsteroidListener(val clickListener: (asteroid: Asteroid) -> Unit) {
        fun onClick(asteroid: Asteroid) = clickListener(asteroid)
    }

}
