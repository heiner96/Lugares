package com.lugares_u.ui.lugar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lugares_u.R
import com.lugares_u.adapter.LugarAdatper
import com.lugares_u.databinding.FragmentLugarBinding
import com.lugares_u.viewmodel.LugarViewModel

class LugarFragment : Fragment() {

    private var _binding: FragmentLugarBinding? = null
    private val binding get() = _binding!!
    private lateinit var lugarViewModel: LugarViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        lugarViewModel = ViewModelProvider(this).get(LugarViewModel::class.java)

        _binding = FragmentLugarBinding.inflate(inflater, container, false)
        binding.btAddLugarFab.setOnClickListener{
            findNavController().navigate(R.id.action_nav_lugar_to_addLugarFragment)
        }
        //Activacion del RecyclerView
        val lugarAdatper = LugarAdatper()
        val reciclador = binding.reciclador
        reciclador.adapter = lugarAdatper
        reciclador.layoutManager = LinearLayoutManager(requireContext())
        lugarViewModel.getLugares.observe(viewLifecycleOwner) { lugares->
            lugarAdatper.setData(lugares)
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}