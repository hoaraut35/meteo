package com.hoarauthomas.weather.ui.Player

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.google.android.exoplayer2.ExoPlayer
import com.hoarauthomas.weather.R
import com.hoarauthomas.weather.databinding.FragmentPlayerBinding


class PlayerFragment : Fragment() {

    private lateinit var binding: FragmentPlayerBinding
    private lateinit var simplePlayer: ExoPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPlayerBinding.inflate(inflater, container, false)
        val view = binding.root

        val recyclerView = view.findViewById<RecyclerView>(R.id.player_recyclerview)


        recyclerView.addOnScrollListener(object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                //Toast.makeText(requireContext(),"position" + newState,Toast.LENGTH_SHORT).show()


//                simplePlayer.hasPreviousMediaItem()
                //              simplePlayer.play()

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {


                    //val position = recyclerView.layou
                    //         Toast.makeText(requireContext(),"position : "+ recyclerView.layoutManager!!.focusedChild.get.toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //Toast.makeText(requireContext(),"position" + dx.toString() + " position : " + dy.toString(),Toast.LENGTH_SHORT).show()


            }
        })


        val myListURI =
            mutableListOf("https://i.imgur.com/7bMqysJ.mp4", "https://i.imgur.com/jKjet6a.mp4","rtsp://rtsp.stream/pattern")

        //build player


        //init uri
        //  val mediaItem = MediaItem.fromUri("https://i.imgur.com/7bMqysJ.mp4")
        //val mediaItem2 = MediaItem.fromUri("https://i.imgur.com/jKjet6a.mp4")

        //    simplePlayer.addMediaItem(mediaItem)
        //  simplePlayer.addMediaItem(mediaItem2)

        setupRecyclerView(recyclerView, myListURI, requireContext())

        return view
    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView?,
        //  simplePlayer: ExoPlayer,
        myListURI: MutableList<String>,
        requireContext: Context
    ) {
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        recyclerView?.adapter = AdapterPlayer(myListURI, requireContext)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PlayerFragment().apply {
                arguments = Bundle().apply {
                    //   putString(ARG_PARAM1, param1)
                    //  putString(ARG_PARAM2, param2)
                }
            }
    }


}