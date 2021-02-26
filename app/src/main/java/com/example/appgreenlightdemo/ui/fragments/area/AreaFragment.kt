package com.example.appgreenlightdemo.ui.fragments.area

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appgreenlightdemo.R
import com.example.appgreenlightdemo.databinding.FragmentAreaBinding
import com.example.appgreenlightdemo.databinding.FragmentZoneBinding
import com.example.appgreenlightdemo.ui.MainActivity
import com.example.appgreenlightdemo.ui.fragments.zone.ZoneAdapter
import com.example.appgreenlightdemo.ui.fragments.zone.ZoneFragmentArgs
import com.example.appgreenlightdemo.ui.fragments.zone.ZoneViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AreaFragment : Fragment() {

    private var bind : FragmentAreaBinding? = null
    private val binder get() = bind!!
    private lateinit var mainActivity: MainActivity
    private lateinit var navController: NavController
    private val viewModel : AreaViewModel by viewModels()
    private val args : AreaFragmentArgs by navArgs()
    private lateinit var adapter: AreaAdapter
    private var sortBy : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentAreaBinding.inflate(inflater, container, false)
        binder.txtTitle.text = args.Region
        viewModel.setAreaData(args.Region)
        return binder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        lifecycleScope.launch {
            binder.rcArea.layoutManager = LinearLayoutManager(mainActivity)
            binder.imgBack.setOnClickListener { mainActivity.onBackPressed() }
            binder.btnArea.setOnClickListener {
                when{
                    sortBy -> viewModel.setListbyASC()
                    else -> viewModel.setListbyDSC()
                }
                sortBy = !sortBy
            }
            viewModel.run {
                obsAreas.observe(viewLifecycleOwner, Observer { list ->
                    when{
                        list.size > 0 -> {
                            adapter = AreaAdapter(mainActivity, list = ArrayList(list), navController)
                            binder.rcArea.adapter = adapter
                        }
                    }
                })
            }
        }
    }
}