package com.example.appgreenlightdemo.ui.fragments.country

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appgreenlightdemo.R
import com.example.appgreenlightdemo.databinding.FragmentCountryBinding
import com.example.appgreenlightdemo.network.NetworkCountry
import com.example.appgreenlightdemo.network.NetworkResponse
import com.example.appgreenlightdemo.ui.MainActivity
import com.example.appgreenlightdemo.ui.fragments.CountryViewModel
import com.example.appgreenlightdemo.util.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/***
 * createdBy : Amit
 * description :
 *  this is a country fragment, responsible for handling the country data and interacting with user.
 */
@AndroidEntryPoint
class CountryFragment : Fragment() {

    private var bind : FragmentCountryBinding? = null
    private val binder get() = bind!!
    private lateinit var navController: NavController
    private lateinit var adapter: CountryAdapter
    private val viewModel : CountryViewModel by viewModels()
    private lateinit var mainActivity: MainActivity
    private var list : List<NetworkCountry> = ArrayList()
    private var orderBy : Boolean = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentCountryBinding.inflate(inflater, container, false)
        viewModel.setStateEvent(Utils.MainStateEvent.GetPostEvent)
        return binder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        lifecycleScope.launch {
            binder.imgBack.setOnClickListener { mainActivity.onBackPressed() }
            binder.rcCountry.layoutManager = LinearLayoutManager(mainActivity)
            viewModel.run {
                dataState.observe(viewLifecycleOwner, Observer { dataState ->

                    when(dataState) {
                        is Utils.DataState.Success<NetworkResponse> -> {
                            list = dataState.data.responseData.country.sortedBy { it.country }
                            adapter = CountryAdapter(mainActivity, ArrayList(list), navController)
                            binder.rcCountry.adapter = adapter
                            binder.lytCountry.isVisible = true
                            binder.progress.isVisible = false
                        }

                        is Utils.DataState.Error -> {
                            Log.e("TAG","error as ${dataState.exception.message}")
                            Toast.makeText(mainActivity, "Error as : ${dataState.exception.message}", Toast.LENGTH_SHORT).show()
                            binder.lytCountry.isVisible = false
                            binder.progress.isVisible = false
                        }

                        is Utils.DataState.Loading -> {
                            binder.lytCountry.isVisible = false
                            binder.progress.isVisible = true
                        }
                    }
                })
            }
        }
    }

    override fun onDestroyView() {
        bind = null
        super.onDestroyView()
    }
}