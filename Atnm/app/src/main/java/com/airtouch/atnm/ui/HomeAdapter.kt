package com.airtouch.atnm.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airtouch.atnm.R
import com.airtouch.atnm.models.Pairs
import com.airtouch.atnm.models.PairsWitheRate

class HomeAdapter :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private var pairsList: List<PairsWitheRate> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pairs, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.onBind(pairsList[position])
    }

    override fun getItemCount(): Int = pairsList.size

    fun submitList(pairsList: List<PairsWitheRate>) {
        this.pairsList = pairsList
        notifyDataSetChanged()
    }

    inner class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val from: TextView = itemView.findViewById(R.id.from)
        private val to: TextView = itemView.findViewById(R.id.to)
        private val rate: TextView = itemView.findViewById(R.id.rate)

        fun onBind(paris: PairsWitheRate) {
            from.text = paris.from
            to.text = paris.to
            rate.text = paris.rate.toString()
        }
    }
}