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

package com.example.android.navigation.game

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.media.Image
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.android.navigation.R
import com.example.android.navigation.databinding.FragmentGameBinding
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber
import java.util.*
import kotlin.concurrent.schedule

//        Timber.i("get src name ${R.drawable.ic_box}") //2131165288
//        Timber.i("get src name ${R.drawable.ic_coconut}") //2131165289
//        Timber.i("get src name ${R.drawable.ic_passion_fruit}") //2131165300
//        Timber.i("get src name ${R.drawable.ic_pomegranate}") //2131165301
//        Timber.i("get src name ${R.drawable.ic_orange}") //2131165299
//        Timber.i("get src name ${R.drawable.ic_lime}") //2131165295
//        Timber.i("get src name ${R.drawable.ic_kiwi}") //2131165292
//        Timber.i("get src name ${R.drawable.ic_guava}") //2131165291
//        Timber.i("get src name ${R.drawable.ic_grapefruit}") //2131165290

class GameFragment: Fragment() {

    private lateinit var binding: FragmentGameBinding
    private lateinit var viewModel: GameViewModel
    private lateinit var viewModelFactory: GameViewModelFactory
    private var firstFruit = -1
    private lateinit var firstFruitImg: View
    private var secondFruit = -1
    private lateinit var secondFruitImg: View
    private var periodChoose = 0
    private lateinit var img: MutableList<ImageButton>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val args = GameFragmentArgs.fromBundle(arguments!!)
        viewModelFactory = GameViewModelFactory(args.username, args.gameScore, args.timeSpent)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GameViewModel::class.java)

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game, container, false)

        Timber.i("ViewModelProviders.of Called")

        Snackbar.make(activity!!.findViewById(android.R.id.content),
                getString(R.string.game_beginning),
                Snackbar.LENGTH_LONG).show()

        binding.gameViewModel = viewModel

        putDataToArray()
        viewModel.resetList()

        binding.box1.setOnClickListener {
            unpackBox(it)
        }
        binding.box2.setOnClickListener {
            unpackBox(it)
        }
        binding.box3.setOnClickListener {
            unpackBox(it)
        }
        binding.box4.setOnClickListener {
            unpackBox(it)
        }
        binding.box5.setOnClickListener {
            unpackBox(it)
        }
        binding.box6.setOnClickListener {
            unpackBox(it)
        }
        binding.box7.setOnClickListener {
            unpackBox(it)
        }
        binding.box8.setOnClickListener {
            unpackBox(it)
        }
        binding.box9.setOnClickListener {
            unpackBox(it)
        }
        binding.box10.setOnClickListener {
            unpackBox(it)
        }
        binding.box11.setOnClickListener {
            unpackBox(it)
        }
        binding.box12.setOnClickListener {
            unpackBox(it)
        }
        binding.box13.setOnClickListener {
            unpackBox(it)
        }
        binding.box14.setOnClickListener {
            unpackBox(it)
        }
        binding.box15.setOnClickListener {
            unpackBox(it)
        }
        binding.box16.setOnClickListener {
            unpackBox(it)
        }

        binding.imSureButton.setOnClickListener {

            viewModel.timeSpent = viewModel.gameTimer.secondsCount
            val actionGameToGameDone = GameFragmentDirections
                    .actionGameFragmentToGameDoneFragment(viewModel.gameScore, viewModel.timeSpent, viewModel.username)
            NavHostFragment.findNavController(this).navigate(actionGameToGameDone)
        }

        return binding.root
    }

    override fun onStart() {

        viewModel.onStart()
        super.onStart()
    }

    override fun onStop() {

        Timber.i("onGameFragment -> username: ${viewModel.username} | gameScore: ${viewModel.gameScore} | timeSpent: ${viewModel.timeSpent}")
        viewModel.onStop()
        super.onStop()
    }

    @SuppressLint("ResourceType")
    fun unpackBox(box: View) {

        val boxId: String = box.resources.getResourceName(box.id).split("/")[1]
        val boxIdToListIndex = viewModel.fruitList[boxId.substring(3, boxId.length).toInt()-1]
        box.tag = boxIdToListIndex
        (box as ImageButton).setImageResource( boxIdToListIndex)


        if(firstFruit < 0) {

            periodChoose = viewModel.gameTimer.secondsCount
            Timber.i("choose first: ${periodChoose}")

            firstFruit = boxIdToListIndex
            firstFruitImg = box
        }
        else if(secondFruit < 0) {

            periodChoose = viewModel.gameTimer.secondsCount - periodChoose
            Timber.i("choose second: ${periodChoose}")

            if(!(firstFruitImg == box)) {
                secondFruit = boxIdToListIndex
                secondFruitImg = box

                Timer("DelayMatch", false).schedule(600) {

                    matchFruits(firstFruit, secondFruit)
                    firstFruit = -1
                    secondFruit = -1


                    for(bindingData in img) {
                        bindingData.isClickable = true
                    }
                    binding.imSureButton.isClickable = true
                }

                for(bindingData in img) {
                    bindingData.isClickable = false
                }
                binding.imSureButton.isClickable = false

            }
            else {
                Timber.i("you click fruit again")
            }
        }
    }

    private fun matchFruits(first: Int, second: Int) {

        if(first == second) {
            firstFruitImg.visibility = View.INVISIBLE
            secondFruitImg.visibility = View.INVISIBLE

            viewModel.calculateScore(periodChoose)
        }
        else {
            packBox(firstFruitImg)
            packBox(secondFruitImg)
        }

        periodChoose = 0
    }

    @SuppressLint("ResourceType")
    private fun packBox(box: View) {

        Timber.i("pack box: ${box.tag}")
        box.tag = R.drawable.ic_box

        (box as ImageButton).setImageResource(R.drawable.ic_box)
    }

    private fun putDataToArray() {

        img = mutableListOf(
                binding.box1,
                binding.box2,
                binding.box3,
                binding.box4,
                binding.box5,
                binding.box6,
                binding.box7,
                binding.box8,

                binding.box9,
                binding.box10,
                binding.box11,
                binding.box12,
                binding.box13,
                binding.box14,
                binding.box15,
                binding.box16
        )
    }
}
