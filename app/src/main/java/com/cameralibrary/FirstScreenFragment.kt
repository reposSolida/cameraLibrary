package com.cameralibrary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.camera.data.models.UserProfile
import com.camera.utils.Utils.toJson
import com.cameralibrary.databinding.FirstScreenFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstScreenFragment : Fragment() {

    private val userProfile = UserProfile(
        "OPRUMIN",
        "sd.t0t4l!",
        "https://preproduccion.solidatec.com/sd_16/servlet/",
        "INTEGRACION",
        "24",
        "OPRUMIN",
        "2024-07-23 09:51:35",
        "Semana 2",
        true,
        "Sincronización completada correctament",
        true,
        "OPRUMIN",
        "V",
        false,
        false,
        false
    )
    private lateinit var binding: FirstScreenFragmentBinding
    private val viewModel: FirstScreenFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FirstScreenFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // El fragment anterior va a tener que posicionarse como backFragment
        //setActiviyBackFragment(R.id.amd_fragment_client_visitation)  TODO

        binding.btnCamera.setOnClickListener {
            val action = FirstScreenFragmentDirections.actionFirstScreenFragmentToCameraFragment(
                userProfile.toJson(),
                "A_SUC_GEN"
                )
            findNavController().navigate(action)
        }


        binding.btnSync.setOnClickListener{
            viewModel.syncData(userProfile, requireContext())
        }
    }

}