package com.example.appgreenlightdemo.ui.fragments.zone

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.appgreenlightdemo.databinding.CardLayoutBinding
import com.example.appgreenlightdemo.network.NetworkZone
import com.example.appgreenlightdemo.ui.MainActivity
import com.example.appgreenlightdemo.ui.fragments.country.CountryFragmentDirections

/**
 * createdBy : Amit
 * description : it is adapter class, responsible for handling zone list for recyclerview in zone fragment.
 */
class ZoneAdapter(val mainActivity: MainActivity, val list : ArrayList<NetworkZone>, val navController: NavController) : RecyclerView.Adapter<ZoneAdapter.ViewHold>() {
    class ViewHold(val binder : CardLayoutBinding) : RecyclerView.ViewHolder(binder.root) {
        fun bindInfo(networkZone: NetworkZone, navController: NavController) {
            binder.txtCard.text = networkZone.zone
            binder.txtCard.setOnClickListener {
                navController.navigate(ZoneFragmentDirections.actionZoneFragmentToRegionFragment(networkZone.territory))
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