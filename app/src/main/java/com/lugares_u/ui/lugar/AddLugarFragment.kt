package com.lugares_u.ui.lugar

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.lugares_u.R
import com.lugares_u.databinding.FragmentAddLugarBinding
import com.lugares_u.model.Lugar
import com.lugares_u.viewmodel.LugarViewModel


class AddLugarFragment : Fragment() {
    private var _binding: FragmentAddLugarBinding? = null
    private val binding get() = _binding!!
    private lateinit var lugarViewModel: LugarViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        lugarViewModel = ViewModelProvider(this).get(LugarViewModel::class.java)
        _binding = FragmentAddLugarBinding.inflate(inflater, container, false)
        binding.btAddLugar.setOnClickListener{
            addLugar()
        }
        activaGPS()
        return binding.root
    }

    private fun activaGPS()
    {
        //se solicita los permisos para la llamada
        if(requireActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED &&
            requireActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED) //se debe perdir los permisos
        {//no tiene permisos otorgados se deben pedir
            //pedir permisos
            requireActivity().requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),105)
        }
        else{
           //se tienen los permisos oara activar el GPS
            val ubicacion : FusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(requireContext())
            ubicacion.lastLocation.addOnSuccessListener {
                if(it != null)
                {
                    binding.tvLatitud.text ="${it.latitude}"
                    binding.tvLongitud.text ="${it.longitude}"
                    binding.tvAltura.text ="${it.altitude}"
                }
                else
                {
                    binding.tvLatitud.text ="0.0"
                    binding.tvLongitud.text ="0.0"
                    binding.tvAltura.text ="0.0"
                }
            }
        }
    }

    private fun addLugar() {
        val nombre = binding.etNombre.text.toString()
        if(nombre.isNotEmpty()){//se puede agregar un lugar
            val correo = binding.etCorreoLugar.text.toString()
            val telefono = binding.etTelefono.text.toString()
            val web = binding.etWeb.text.toString()
            val latitud = binding.tvLatitud.text.toString().toDouble()
            val longitud = binding.tvLongitud.text.toString().toDouble()
            val altura = binding.tvAltura.text.toString().toDouble()
            val lugar = Lugar(0,nombre,correo,web,telefono,latitud,longitud,altura,"","")
            lugarViewModel.saveLugar(lugar)
            Toast.makeText(requireContext(),getText(R.string.msg_lugar_added),Toast.LENGTH_SHORT)
            findNavController().navigate(R.id.action_addLugarFragment_to_nav_lugar)
        }
        else{//sino no se puede agregar el lugar


            Toast.makeText(requireContext(),getText(R.string.msg_data),Toast.LENGTH_LONG)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}