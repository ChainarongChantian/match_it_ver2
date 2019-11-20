/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation.game_done

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.android.navigation.R
import com.example.android.navigation.database.UserDatabase
import com.example.android.navigation.databinding.FragmentGameDoneBinding
import com.example.android.navigation.game.GameFragmentArgs
import com.example.android.navigation.game.GameViewModel
import com.example.android.navigation.game.GameViewModelFactory
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

class GameDoneFragment : Fragment() {

    private lateinit var viewModel: GameDoneViewModel
    private lateinit var viewModelFactory: GameDoneViewModelFactory

            override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val args = GameDoneFragmentArgs.fromBundle(arguments!!)
        val application = requireNotNull(this.activity).application
        val dataSource = UserDatabase.getInstance(application).userDatabaseDao
        viewModelFactory = GameDoneViewModelFactory(args.username, args.gameScore, args.timeSpent, dataSource, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GameDoneViewModel::class.java)

        val binding = DataBindingUtil.inflate<FragmentGameDoneBinding>(
                inflater, R.layout.fragment_game_done, container, false)

        binding.playAgainButton.setOnClickListener { view ->
            val actionGameDoneToGame = GameDoneFragmentDirections
                    .actionGameDoneFragmentToGameFragment(args.username, 0, 0)
            view.findNavController()
                    .navigate(actionGameDoneToGame)
        }

        binding.quitToMenuButton.setOnClickListener { view ->
            val actionGameDoneToMenu = GameDoneFragmentDirections
                    .actionGameDoneFragmentToMenuFragment()
            view.findNavController()
                    .navigate(actionGameDoneToMenu)
        }

//        Toast.makeText(context, "onGameDoneFragment -> username: ${args.username}| gameScore: ${args.gameScore} | timeSpent: ${args.timeSpent}", Toast.LENGTH_LONG).show()

        Snackbar.make(activity!!.findViewById(android.R.id.content),
                        getString(R.string.score_saved),
                        Snackbar.LENGTH_LONG).show()

        binding.gameDoneViewModel = viewModel
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.winner_menu, menu)
        if(null == getShareIntent().resolveActivity(activity!!.packageManager)) {
            menu.findItem(R.id.share)?.setVisible(false)
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.share -> shareGameScore()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getShareIntent() : Intent {
        val args = GameDoneFragmentArgs.fromBundle(arguments!!)
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
                .putExtra(Intent.EXTRA_TEXT, getString(R.string.share_game_score, args.gameScore))
        return shareIntent
    }
    private fun shareGameScore() {
        startActivity(getShareIntent())
    }
}
