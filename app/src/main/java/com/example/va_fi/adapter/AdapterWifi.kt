package com.example.va_fi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.va_fi.databinding.WifiItemBinding
import com.example.va_fi.model.WifiUrl

class AdapterWifi(private val list: List<WifiUrl>, private val onClickWifiItem: OnClickWifiItem) :
    RecyclerView.Adapter<AdapterWifi.WifiViewHolder>() {
    inner class WifiViewHolder(val wifiItemBinding: WifiItemBinding) :
        RecyclerView.ViewHolder(wifiItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WifiViewHolder {
        return WifiViewHolder(
            WifiItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: WifiViewHolder, position: Int) {
        holder.wifiItemBinding.apply {
            tvWifi.text = list[position].url.split("?")[1].substring(8)
            root.setOnClickListener {
                onClickWifiItem.onClick(list[position])
            }
            btnDlt.setOnClickListener {
                onClickWifiItem.onClickDlt(list[position])
            }


        }
    }

    interface OnClickWifiItem {
        fun onClick(wifiUrl: WifiUrl)
        fun onClickDlt(wifiUrl: WifiUrl)
    }
}