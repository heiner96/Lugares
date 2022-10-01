package com.lugares_u.ui.lugar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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

        return binding.root
    }

    private fun addLugar() {
        val nombre = binding.etNombre.text.toString()
        if(nombre.isNotEmpty()){//se puede agregar un lugar
            val correo = binding.etCorreoLugar.text.toString()
            val telefono = binding.etTelefono.text.toString()
            val web = binding.etWeb.text.toString()
            val lugar = Lugar(0,nombre,correo,web,telefono,0.0,0.0,0.0,"","")
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