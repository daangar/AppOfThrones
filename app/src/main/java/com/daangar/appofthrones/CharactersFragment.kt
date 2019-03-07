package com.daangar.appofthrones

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_characters.*

class CharactersFragment : Fragment() {


    val list: RecyclerView by lazy {
        val list: RecyclerView = view!!.findViewById(R.id.charactersList) as RecyclerView
        list.layoutManager = LinearLayoutManager(context)
        list
    }

    val adapter: CharactersAdapter by lazy {
        val adapter = CharactersAdapter() { item, position ->
            clickListener.onItemClicked(item)
        }
        adapter
    }

    lateinit var clickListener: OnItemClickListener

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnItemClickListener)
            clickListener = context
        else
            throw IllegalArgumentException("Attached activity doesn't implement CharacterFragment.OnItemClickListener")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_characters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val characters: MutableList<Character> = CharactersRepo.characters
//        adapter.setCharacters(characters)

        list.adapter = adapter
        //Log.i("CharactersActivity", "${characters.size}")

        btnRetry.setOnClickListener {
            retry()
        }
    }

    override fun onResume() {
        super.onResume()
        requestCharacters()
    }

    private fun retry() {
        layoutError.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE

        requestCharacters()
    }

    private fun requestCharacters() {
        CharactersRepo.requestCharacters(context!!,
                { characters ->
                    view?.let {
                        progressBar.visibility = View.INVISIBLE
                        charactersList.visibility = View.VISIBLE
                        adapter.setCharacters(characters)
                    }
                },
                {
                    view?.let {
                        progressBar.visibility = View.INVISIBLE
                        layoutError.visibility = View.VISIBLE
                    }
                })
    }



    interface OnItemClickListener {
        fun onItemClicked(character: Character)
    }

}