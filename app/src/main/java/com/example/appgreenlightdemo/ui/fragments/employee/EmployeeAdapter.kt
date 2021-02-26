package com.example.appgreenlightdemo.ui.fragments.employee

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.appgreenlightdemo.databinding.CardLayoutBinding
import com.example.appgreenlightdemo.network.NetworkEmployee
import com.example.appgreenlightdemo.ui.MainActivity

/**
 * createdBy : Amit
 * description : this class will handle the employee list for recyclerview in employee fragment.
 */
class EmployeeAdapter(val mainActivity: MainActivity, val list : ArrayList<NetworkEmployee>) : RecyclerView.Adapter<EmployeeAdapter.ViewHold>() {
    class ViewHold(val binder : CardLayoutBinding) : RecyclerView.ViewHolder(binder.root) {
        fun bindInfo(employee : NetworkEmployee) {
            binder.txtCard.text = employee.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHold {
        val binder = CardLayoutBinding.inflate(LayoutInflater.from(mainActivity), parent, false)
        return ViewHold(binder)
    }

    override fun onBindViewHolder(holder: ViewHold, position: Int) {
        holder.bindInfo(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}