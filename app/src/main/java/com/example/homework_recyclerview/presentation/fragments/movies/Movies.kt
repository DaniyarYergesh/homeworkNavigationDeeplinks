package com.example.homework_recyclerview.presentation.fragments.movies

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.convertor.R
import com.google.android.material.textfield.TextInputEditText

class Movies : Fragment(R.layout.layout_fragment_movies) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val izdeu = view.findViewById<TextInputEditText>(R.id.izdey)
        izdeu.setHint("\uD83D\uDD0D Izdey")

        val forsajMovie = view.findViewById<ImageView>(R.id.imageView)
        forsajMovie.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_moviesFragment_to_fragmentMovieDetails)
        }
    }


}