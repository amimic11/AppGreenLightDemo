package com.example.appgreenlightdemo.ui.fragments.region

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.appgreenlightdemo.databinding.CardLayoutBinding
import com.example.appgreenlightdemo.network.NetworkCountryRegion
import com.example.appgreenlightdemo.ui.MainActivity

class RegionAdapter(val mainActivity: MainActivity, val list : ArrayList<NetworkCountryRegion>, val navController: NavController) : RecyclerView.Adapter<RegionAdapter.ViewHold>() {
    class ViewHold(val binder :  CardLayoutBinding) : RecyclerView.ViewHolder(binder.root) {
        fun bindInfo(networkCountryRegion: NetworkCountryRegion, navController: NavController) {
            binder.txtCard.text = networkCountryRegion.region
            binder.txtCard.setOnClickListener {
                navController.navigate(RegionFragmentDirections.actionRegionFragmentToAreaFragment(networkCountryRegion.territory))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHold {
        val binder = CardLayoutBinding.inflate(LayoutInflater.from(mainActivity), parent, false)
        return ViewHold(binder)
    }

    override fun onBindViewHolder(holder: ViewHold, position: Int) {
        holder.bindInfo(list[position], navController)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}