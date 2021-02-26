package com.example.appgreenlightdemo.ui.fragments.zone

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
import com.example.appgreenlightdemo.databinding.FragmentZoneBinding
import com.example.appgreenlightdemo.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * createdBy : Amit
 * description :
 * this is a zone fragment, responsible for showing data and interact with user
 */
@AndroidEntryPoint
class ZoneFragment : Fragment() {

    private var bind : FragmentZoneBinding? = null
    private val binder get() = bind!!
    private lateinit var mainActivity: MainActivity
    private lateinit var navController: NavController
    private val viewModel : ZoneViewModel by viewModels()
    private val args : ZoneFragmentArgs by navArgs()
    private lateinit var adapter: ZoneAdapter
    private var sortBy : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /**
         * binding the view with zone fragment.
         */
        bind = FragmentZoneBinding.inflate(inflater, container, false)
        viewModel.setZoneData(args.Country)
        binder.txtTitle.text = args.Country
        return binder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        lifecycleScope.launch {
            binder.rcZone.layoutManager = LinearLayoutManager(mainActivity)
            binder.imgBack.setOnClickListener { mainActivity.onBackPressed() }
            binder.btnZone.setOnClickListener {
                when{
                    sortBy -> viewModel.setListbyASC()
                    else -> viewModel.setListbyDSC()
                }
                sortBy = !sortBy
            }
            viewModel.run {
                obsZoneList.observe(viewLifecycleOwner, Observer { list ->
                    when{
                        list.isNotEmpty() -> {
                            adapter = ZoneAdapter(mainActivity, list, navController)
                            binder.rcZone.adapter = adapter
                        }
                    }
                })
            }
        }
    }
}