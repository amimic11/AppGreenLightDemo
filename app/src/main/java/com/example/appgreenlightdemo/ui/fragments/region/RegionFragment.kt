package com.example.appgreenlightdemo.ui.fragments.region

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
import com.example.appgreenlightdemo.databinding.FragmentRegionBinding
import com.example.appgreenlightdemo.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * createdBy : Amit
 * description : this is a region fragment, responsible for handling the interaction from user.
 */
@AndroidEntryPoint
class RegionFragment : Fragment() {

    private var bind : FragmentRegionBinding? = null
    private val binder get() = bind!!
    private lateinit var mainActivity: MainActivity
    private lateinit var navController: NavController
    private val viewModel : RegionViewModel by viewModels()
    private val args : RegionFragmentArgs by navArgs()
    private lateinit var adapter: RegionAdapter
    private var sortBy : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentRegionBinding.inflate(inflater, container, false)
        viewModel.getRegionData(args.Zone)
        binder.txtTitle.text = args.Zone
        return binder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        lifecycleScope.launch {
            binder.rcRegion.layoutManager = LinearLayoutManager(mainActivity)
            binder.imgBack.setOnClickListener { mainActivity.onBackPressed() }
            binder.btnRegion.setOnClickListener {
                when{
                    sortBy -> viewModel.setListbyASC()
                    else -> viewModel.setListbyDSC()
                }
                sortBy = !sortBy
            }
            viewModel.run {
                obsRegion.observe(viewLifecycleOwner, Observer { list ->
                    when {
                        list.isNotEmpty() -> {
                            adapter = RegionAdapter(mainActivity, list, navController)
                            binder.rcRegion.adapter = adapter
                        }
                    }
                })
            }
        }
    }
}