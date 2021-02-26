package com.example.appgreenlightdemo.ui.fragments.area

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.appgreenlightdemo.databinding.CardLayoutBinding
import com.example.appgreenlightdemo.network.NetworkArea
import com.example.appgreenlightdemo.ui.MainActivity

/***
 * createdBy : Amit
 * description :
 *  This is a area Adapter class for handling the recyclerview for area list...
 */
class AreaAdapter(val mainActivity: MainActivity, val list : ArrayList<NetworkArea>, val navController: NavController) : RecyclerView.Adapter<AreaAdapter.ViewHold>() {
    class ViewHold(val binder: CardLayoutBinding) : RecyclerView.ViewHolder(binder.root) {
        fun bindInfo(networkArea: NetworkArea, navController: NavController) {
            binder.txtCard.text = networkArea.area
            binder.txtCard.setOnClickListener {
                navController.navigate(AreaFragmentDirections.actionAreaFragmentToEmployeeFragment(networkArea.territory))
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