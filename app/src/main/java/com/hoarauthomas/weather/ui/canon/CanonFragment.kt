package com.hoarauthomas.weather.ui.canon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hoarauthomas.weather.databinding.FragmentCanonBinding
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CanonFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentCanonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCanonBinding.inflate(inflater, container, false)
        val view = binding.root

        val url = "https://firebase.google.com/"
        var document: Document? = null


        //getWebsite()

        return view
    }

//    private fun getWebsite() {
//
//        Thread {
//
//            val builder = StringBuilder()
//
//            try {
//                val doc = Jsoup.connect("http://www.ssaurel.com/blog").get()
//                val title = doc.title()
//                val links: Elements = doc.select("a[href]")
//                builder.append(title).append("\n")
//                for (link in links) {
//                    builder.append("\n").append("Link : ").append(link.attr("href"))
//                        .append("\n").append("Text : ").append(link.text())
//                }
//            } catch (e: IOException) {
//             //   builder.append("Error : ").append(e.getMessage()).append("\n")
//            }
//            runOnUiThread(Runnable { result.setText(builder.toString()) })
//        }.start()
//    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CanonFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}