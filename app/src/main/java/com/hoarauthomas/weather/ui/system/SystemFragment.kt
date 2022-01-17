package com.hoarauthomas.weather.ui.system

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.hoarauthomas.weather.databinding.FragmentSystemBinding
import java.io.BufferedReader
import java.io.FileReader

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SystemFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SystemFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    private lateinit var binding: FragmentSystemBinding


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
    ): View {
        binding = FragmentSystemBinding.inflate(inflater, container, false)
        val view = binding.root

        val fields = Build.VERSION_CODES::class.java.fields
        var codeName = "UNKNOWN"
        fields.filter { it.getInt(Build.VERSION_CODES::class) == Build.VERSION.SDK_INT }
            .forEach { codeName = it.name }


        binding.systemSdk.text = "SDK version : " + Build.VERSION.RELEASE

        binding.systemCpu.text =
            "CPU : " + android.os.HardwarePropertiesManager.DEVICE_TEMPERATURE_BATTERY
        binding.systemManufacturer.text = "Fabricant : " + Build.MODEL




        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                    isGranted: Boolean ->

                if (isGranted) {
                    binding.systemCameraPermission.text = "Camera permission : Granted"
                } else {
                    binding.systemCameraPermission.text = "Camera permission : not authorized"
                }
            }


        if (requireContext().packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            binding.systemFrontCameraFeature.text = "Front camera : enabled"


        } else {
            binding.systemFrontCameraFeature.text = "Front camera : disabled"
        }

        checkMyPermission()

        return view
    }


    private fun checkMyPermission() {

        when {
            ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                binding.systemCameraPermission.text = "Granted"
                //continue worklof here...
            }

            //already granted
            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.CAMERA
            ) -> {
                binding.systemCameraPermission.text = "Camera permission : Granted"
            }
            else -> {
                binding.systemCameraPermission.text = "Camera permission : Not authorized"
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }

        }
    }



    fun getCPUInfo(): Map<String, String>? {
        val br = BufferedReader(FileReader("/proc/cpuinfo"))
        var str: String
        val output: MutableMap<String, String> = HashMap()
        while (br.readLine().also { str = it } != null) {
            val data = str.split(":").toTypedArray()
            if (data.size > 1) {
                var key = data[0].trim { it <= ' ' }.replace(" ", "_")
                if (key == "model_name") key = "cpu_model"
                output[key] = data[1].trim { it <= ' ' }
            }
        }
        br.close()
        return output
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SystemFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}