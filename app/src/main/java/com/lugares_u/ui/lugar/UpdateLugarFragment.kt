package com.lugares_u.ui.lugar

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.lugares_u.R
import com.lugares_u.databinding.FragmentUpdateLugarBinding
import com.lugares_u.model.Lugar
import com.lugares_u.viewmodel.LugarViewModel



class UpdateLugarFragment : Fragment() {
    private var _binding: FragmentUpdateLugarBinding? = null
    private val binding get() = _binding!!
    private lateinit var lugarViewModel: LugarViewModel

    //Defino un argumento para obtener los argumentos pasados las fragmento
    private val args by navArgs<UpdateLugarFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        lugarViewModel = ViewModelProvider(this).get(LugarViewModel::class.java)
        _binding = FragmentUpdateLugarBinding.inflate(inflater, container, false)

        binding.etNombre.setText(args.lugar.nombre)
        binding.etCorreoLugar.setText(args.lugar.correo)
        binding.etTelefono.setText(args.lugar.telefono)
        binding.etWeb.setText(args.lugar.web)
        binding.tvLongitud.text = args.lugar.longitud.toString()
        binding.tvLatitud.text = args.lugar.latitud.toString()
        binding.tvAltura.text = args.lugar.altura.toString()
        binding.btEmail.setOnClickListener{
            escribirCorreo()
        }
        binding.btUpdateLugar.setOnClickListener{
            updateLugar()
        }
        binding.btDeleteLugar.setOnClickListener{
            deleteLugar()
        }
        binding.btPhone.setOnClickListener{
            llamarLugar()
        }
        binding.btWhatsapp.setOnClickListener{
            mensajeWhastApps()
        }
        binding.btWeb.setOnClickListener{
            verWeb()
        }

        return binding.root

    }
    private fun verWeb()
    {
        val para = binding.etWeb.text.toString()
        if(para.isNotEmpty()){ //puedo enviar Mensaje WhatsApp
            val uri = Uri.parse("http://$para")
            val intent = Intent(Intent.ACTION_VIEW,uri)
            startActivity(intent)
        }
        else{
            Toast.makeText(requireContext(),getString(R.string.msg_data),Toast.LENGTH_LONG).show()
        }
    }

    private fun mensajeWhastApps()
    {
        val para = binding.etTelefono.text.toString()
        if(para.isNotEmpty()){ //puedo enviar Mensaje WhatsApp
            val intent = Intent(Intent.ACTION_VIEW)
            val uri = "whatsapp://send?phone506$para&text=" + getString(R.string.msg_saludos)
            intent.setPackage("com.whatsapp")
            intent.data = Uri.parse(uri)
            startActivity(intent)

        }
        else{
            Toast.makeText(requireContext(),getString(R.string.msg_data),Toast.LENGTH_LONG).show()
        }
    }

    private fun llamarLugar()
    {
        val para = binding.etTelefono.text.toString()
        if(para.isNotEmpty()){ //puedo enviar el correo
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:$para")
            //se solicita los permisos para la llamada
            if(requireActivity().checkSelfPermission(Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED) //se debe perdir los permisos
            {
                //pedir permisos
                requireActivity().requestPermissions(arrayOf(Manifest.permission.CALL_PHONE),105)
            }
            else{
                requireActivity().startActivity(intent)
            }

        }
        else{
            Toast.makeText(requireContext(),getString(R.string.msg_data),Toast.LENGTH_LONG).show()
        }
    }

    private fun escribirCorreo()
    {
        val para = binding.etCorreoLugar.text.toString()
        if(para.isNotEmpty()){ //puedo enviar el correo
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "message/rfc822"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(para))
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.msg_saludos)+ binding.etNombre.text.toString())
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.msg_mensaje_correo))
            startActivity(intent)

        }
        else{
            Toast.makeText(requireContext(),getString(R.string.msg_data),Toast.LENGTH_LONG).show()
        }
    }

    private fun deleteLugar() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.msg_delete_lugar))
        builder.setMessage(getString(R.string.msg_seguro_borrado) +" ${args.lugar.nombre}?")
        builder.setNegativeButton(getString(R.string.msg_no)){_,_ ->}
        builder.setPositiveButton(getString(R.string.msg_si)){_,_ ->
            lugarViewModel.deleteLugar(args.lugar)
            Toast.makeText(requireContext(),getString(R.string.msg_lugar_deleted),Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateLugarFragment_to_nav_lugar)
        }
        builder.show()
    }

    private fun updateLugar() {
        val nombre = binding.etNombre.text.toString()
        if(nombre.isNotEmpty()){//se puede agregar un lugar
            val correo = binding.etCorreoLugar.text.toString()
            val telefono = binding.etTelefono.text.toString()
            val web = binding.etWeb.text.toString()
            val lugar = Lugar(args.lugar.id,
                nombre,correo,web,telefono,
                args.lugar.latitud,args.lugar.longitud,args.lugar.altura,
                args.lugar.ruta_audio,args.lugar.ruta_imagen)
            lugarViewModel.saveLugar(lugar)
            Toast.makeText(requireContext(),getText(R.string.msg_lugar_updated),Toast.LENGTH_SHORT)
            findNavController().navigate(R.id.action_updateLugarFragment_to_nav_lugar)
        }
        else{//sino no se puede modificar el lugar


            Toast.makeText(requireContext(),getText(R.string.msg_data),Toast.LENGTH_LONG)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}