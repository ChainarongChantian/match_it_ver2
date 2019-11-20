package com.example.android.navigation.ranking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.android.navigation.R
import com.example.android.navigation.database.UserDatabase
import com.example.android.navigation.databinding.FragmentRankingBinding
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

class RankingFragment : Fragment() {

    private lateinit var viewModelFactory: RankingViewModelFactory
    private lateinit var viewModel: RankingViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        Timber.i("RankingFragment onCreateView")

        Snackbar.make(activity!!.findViewById(android.R.id.content),
                getString(R.string.you_are_there),
                Snackbar.LENGTH_LONG).show()

        val application = requireNotNull(this.activity).application
        val dataSource = UserDatabase.getInstance(application).userDatabaseDao
        viewModelFactory = RankingViewModelFactory(dataSource,application)
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(RankingViewModel::class.java)

        val binding = DataBindingUtil.inflate<FragmentRankingBinding>(
                inflater, R.layout.fragment_ranking, container, false)

        var adapter = RankingAdapter()
        binding.listScore.adapter = adapter

        viewModel.scoreList?.observe(this, Observer {
            adapter.submitList(it)
        })

        binding.backToMenu.setOnClickListener { view ->
            val actionRankingToMenu = RankingFragmentDirections
                    .actionRankingFragmentToMenuFragment()
            view.findNavController()
                    .navigate(actionRankingToMenu)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RankingViewModel::class.java)
    }

}
