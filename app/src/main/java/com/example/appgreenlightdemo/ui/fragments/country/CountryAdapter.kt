package com.example.appgreenlightdemo.ui.fragments.country

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.appgreenlightdemo.R
import com.example.appgreenlightdemo.databinding.CardLayoutBinding
import com.example.appgreenlightdemo.network.NetworkCountry
import com.example.appgreenlightdemo.ui.MainActivity

class CountryAdapter(
    val mainActivity: MainActivity,
    val list: ArrayList<NetworkCountry>,
    val navController: NavController
) : RecyclerView.Adapter<CountryAdapter.ViewHold>() {
    class ViewHold(val binder : CardLayoutBinding) : RecyclerView.ViewHolder(binder.root) {
        fun bindInfo(country: NetworkCountry, navController: NavController) {
            binder.txtCard.text = country.country
            binder.txtCard.setOnClickListener {
                Log.e("card", "found ${country.territory}")
                navController.navigate(CountryFragmentDirections.actionCountryFragmentToZoneFragment(country.territory))
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