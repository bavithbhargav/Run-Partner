package com.example.runpartner.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.runpartner.databinding.ItemRunBinding
import com.example.runpartner.db.Run
import com.example.runpartner.other.TrackingUtility
import java.text.SimpleDateFormat
import java.util.*

class RunAdapter : RecyclerView.Adapter<RunAdapter.RunViewHolder>() {

    inner class RunViewHolder(val binding: ItemRunBinding): RecyclerView.ViewHolder(binding.root)

    val diffCallback = object : DiffUtil.ItemCallback<Run>() {
        override fun areItemsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Run>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
        return RunViewHolder(
            return RunViewHolder(ItemRunBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ))
        )
    }

    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
        val run = differ.currentList[position]
        Glide.with(holder.itemView).load(run.img).into(holder.binding.ivRunImage)

        val calender = Calendar.getInstance().apply {
            timeInMillis = run.timestamp
        }
        val dateFormat = SimpleDateFormat("dd.MM.yy",Locale.getDefault())
        holder.binding.tvDate.text = dateFormat.format(calender.time)

        val avgSpeed = "${run.avgSpeedInKMH}km/h"
        holder.binding.tvAvgSpeed.text = avgSpeed

        val distanceInKms = "${run.distanceInMeters/1000f}km"
        holder.binding.tvDistance.text = distanceInKms

        holder.binding.tvTime.text = TrackingUtility.getFormattedStopWatchTime(run.timeInMillis)

        val caloriesBurned = "${run.caloriesBurned}kcal"
        holder.binding.tvCalories.text = caloriesBurned
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}