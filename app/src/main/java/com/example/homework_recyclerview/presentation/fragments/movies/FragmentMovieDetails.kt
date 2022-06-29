package com.example.homework_recyclerview.presentation.fragments.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.convertor.R


class FragmentMovieDetails : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.layout_fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val close = view.findViewById<View>(R.id.iconClose)
        close.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.nav_host_fragment_activity_main,
                    Movies(), "Fragment"
                ).addToBackStack(null).commit()
        }

    }

}