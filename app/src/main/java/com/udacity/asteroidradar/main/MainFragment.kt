package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
enum class filter { today_Aster, week_Aster, all_Aster }
class MainFragment : Fragment() {

    private val Adapter=RecyclerAdapter(OnClickListener { asteroid ->
        viewModel.onClicked(asteroid)



    })

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.asteroidRecycler.adapter=Adapter
        viewModel.returnedAsteroids.observe(viewLifecycleOwner, Observer { asteroid ->
            asteroid.apply {
                Adapter.submitList(this )
            } })

        viewModel.ToDetails.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.onNavigated()
            }
        })




        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.filterChanged(
            when (item.itemId) {
                R.id.today_Aster -> {
                    filter.today_Aster
                }
                R.id.week_Aster -> {
                    filter.week_Aster
                }
                else -> {
                    filter.all_Aster
                }
            }
        )
        return true
    }
}
