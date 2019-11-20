package com.example.android.navigation.menu

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.android.navigation.R
import com.example.android.navigation.databinding.FragmentMenuBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_menu.*
import timber.log.Timber

class MenuFragment : Fragment() {
    private lateinit var viewModel: MenuViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Timber.i("MenuFragment onCreateView")

        viewModel = ViewModelProviders.of(this).get(MenuViewModel::class.java)
        val binding = DataBindingUtil.inflate<FragmentMenuBinding>(inflater,
                R.layout.fragment_menu, container, false)

        binding.playGameButton.setOnClickListener {

            viewModel.setUsername(binding.usernamePlay.text.toString())
//            Timber.i("username: ${viewModel.username.value} | gameScore: ${viewModel.gameScore} | timesPENT: ${viewModel.timeSpent}")
            var actionMenuToGame = MenuFragmentDirections
                            .actionMenuFragmentToGameFragment(viewModel.username.value!!, viewModel.gameScore, viewModel.timeSpent)
            NavHostFragment.findNavController(this).navigate(actionMenuToGame)
        }

        Snackbar.make(activity!!.findViewById(android.R.id.content),
                getString(R.string.hello_user),
                Snackbar.LENGTH_LONG).show()

        binding.rankingButton.setOnClickListener { view ->
           view.findNavController()
                   .navigate(MenuFragmentDirections.actionMenuFragmentToRankingFragment())
        }

        binding.usernamePlay.addTextChangedListener(object: TextWatcher {

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.playGameButton.isEnabled = binding.playGameButton.text.toString() != ""
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return NavigationUI.onNavDestinationSelected(item, view!!.findNavController()) || super.onOptionsItemSelected(item)
    }

    override fun onStart() {

        super.onStart()
        Timber.i("MenuFragment onStart")
    }

    override fun onStop() {

        super.onStop()
        Timber.i("MenuFragment onStop")
    }

    override fun onResume() {

        super.onResume()
        Timber.i("MenuFragment onResume")
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        Timber.i("MenuFragment onCreate")
    }

    override fun onAttach(context: Context) {

        super.onAttach(context)
        Timber.i("MenuFragment onAttach")
    }

    override fun onDetach() {

        super.onDetach()
        Timber.i("MenuFragment onDetach")
    }

    override fun onPause() {

        super.onPause()
        Timber.i("MenuFragment onPause")
    }

    override fun onDestroy() {

        super.onDestroy()
        Timber.i("MenuFragment onDestroy")
    }
}