package com.example.appgreenlightdemo.ui.fragments.employee

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appgreenlightdemo.R
import com.example.appgreenlightdemo.databinding.FragmentEmployeeBinding
import com.example.appgreenlightdemo.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * createdBy : Amit
 * description :
 * this is employee fragment class, which is responsible for showing data to the user.
 */
@AndroidEntryPoint
class EmployeeFragment : Fragment() {

    private var bind : FragmentEmployeeBinding? = null
    private val binder get() = bind!!
    private lateinit var mainActivity: MainActivity
    private val args : EmployeeFragmentArgs by navArgs()
    private val viewModel : EmployeeViewModel by viewModels()
    private lateinit var adapter: EmployeeAdapter
    private var sortBy : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentEmployeeBinding.inflate(inflater, container, false)
        viewModel.setEmployeeData(args.Area)
        binder.txtTitle.text = args.Area
        return binder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            binder.rcEmployee.layoutManager = LinearLayoutManager(mainActivity)
            binder.imgBack.setOnClickListener { mainActivity.onBackPressed() }
            binder.btnEmployee.setOnClickListener {
                when{
                    sortBy -> viewModel.setListbyASC()
                    else -> viewModel.setListbyDSC()
                }
                sortBy = !sortBy
            }
            viewModel.run {
                obsEmployee.observe(viewLifecycleOwner, Observer {list ->
                    adapter = EmployeeAdapter(mainActivity, list)
                    binder.rcEmployee.adapter = adapter
                })
            }
        }
    }
}