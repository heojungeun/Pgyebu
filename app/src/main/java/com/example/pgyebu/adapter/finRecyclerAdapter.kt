package com.example.pgyebu.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.pgyebu.R
import com.example.pgyebu.Utils.IconDict
import com.example.pgyebu.entity.EcoEvent
import com.example.pgyebu.databinding.ItemEcoeventBinding
import java.text.NumberFormat
import java.util.*

class finRecyclerAdapter(val context: Context, val itemClick: (EcoEvent) -> Unit): RecyclerView.Adapter<finRecyclerAdapter.innerHolder>() {

    private var ecoEvents: MutableList<EcoEvent> = mutableListOf()
//        mutableListOf(
//        FinData(content = "잠 와서 햄버거", cost = "5,600원", category = "fastfood"),
//        FinData(content = "커피 수혈", cost = "2,600원", category = "coffee"),
//        FinData(content = "약 처방", cost = "7,600원", category = "etc"),
//    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        innerHolder(ItemEcoeventBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: innerHolder, position: Int) {
        holder.bind(ecoEvents[position])
    }

    override fun getItemCount(): Int {
        return ecoEvents.size
    }

    inner class innerHolder(private val binding: ItemEcoeventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(finData: EcoEvent) {
            binding.contentTextView.text = finData.content

            if(finData.eventType == "INCOME"){
                binding.costTextView.text = NumberFormat.getInstance().format(finData.amount) + "원"
                binding.costTextView.setTextColor(ContextCompat.getColor(context, R.color.item_income))
            } else {
                binding.costTextView.text = "-" + NumberFormat.getInstance().format(finData.amount) + "원"
            }
            IconDict.icon_dict[finData.category]?.let { binding.iconImageView.setImageResource(it) } // finData.category
            binding.root.setOnClickListener {
                itemClick(finData)
            }
        }
    }

    fun updateItem(item: EcoEvent) {
        ecoEvents.add(item)
    }

    fun updateList(items: MutableList<EcoEvent>){
        ecoEvents.clear()
        ecoEvents.addAll(items)
        notifyDataSetChanged()
    }

}