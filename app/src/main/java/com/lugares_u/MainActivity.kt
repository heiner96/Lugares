package com.lugares_u

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lugares_u.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //Objeto para generar acceso a los controles creados en la vista ...
    private lateinit var  binding: ActivityMainBinding


    //objeto para realizar la comunicacion
    private  lateinit var  auth: FirebaseAuth

    //Para hacer la autenticacion con Google
    private lateinit var googleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //inicializo el objeto de autenticacion, realmente firebase
        FirebaseApp.initializeApp(this)
        auth = Firebase.auth

        binding.btLogin.setOnClickListener { hacelogin() }
        binding.btRegister.setOnClickListener { haceRegitro() }

        //Se establecen los parametros par hacer la autenticacion en Google
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id_r))
            .requestEmail()
            .build()

        //Set fija la solicitud del "cliente" de google
        googleSignInClient = GoogleSignIn.getClient(this,gso)
        binding.btGoogle.setOnClickListener{ googleSignIn() }

    }

    private fun googleSignIn() {
    val signInClient = googleSignInClient.signInIntent
        startActivityForResult(signInClient, 5000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==5000)
        {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val cuenta = task.getResult( ApiException::class.java )!!
                //hasta aca todo fue en google...
                //ahora se hace la autenticacion en firebase (registro)
                firebaseAuthWithGoogle(cuenta.idToken!!)
            }catch (e: ApiException){
                Log.e("",e.stackTraceToString())
            }
        }

    }
    private fun firebaseAuthWithGoogle(idToken: String){
        //se fijan las credenciales recuperadas de la autenticacion en Google
        val credenciales = GoogleAuthProvider.getCredential(idToken,null)

        //se usan las credenciales para autenticarse en Firebase
        auth.signInWithCredential(credenciales)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    val user = auth.currentUser
                    refresca(user)
                }
                else{
                    refresca(null)
                }
            }
    }

    private fun haceRegitro() {
        val  correo = binding.etCorreo.text.toString()
        val  clave = binding.etClave.text.toString()

        auth.createUserWithEmailAndPassword(correo,clave).addOnCompleteListener(this){ task ->
            if(task.isSuccessful){
                val user = auth.currentUser
            refresca(user)
        }
            else{ // si no hizo el registro....
            Toast.makeText(baseContext,getString(R.string.tv_fallo), Toast.LENGTH_LONG).show()
                refresca(null)

            }
        }
    }

    private fun refresca(user: FirebaseUser?) {
   if(user != null){
       // me paso a la pantalla principal .....
       val intent = Intent(this,Principal::class.java)

       startActivity(intent)
   }
    }

    private fun hacelogin() {
        val  correo = binding.etCorreo.text.toString()
        val  clave = binding.etClave.text.toString()

        auth.signInWithEmailAndPassword(correo,clave).addOnCompleteListener(this){ task ->
            if(task.isSuccessful){
                val user = auth.currentUser
                refresca(user)
            }
            else{ // si no hizo el registro....
                Toast.makeText(baseContext,getString(R.string.tv_fallo), Toast.LENGTH_LONG).show()
                refresca(null)

            }
        }
    }

    override fun onStart() //se ejecuta cuando la app esta en la pantalla
    {//login automantico
        super.onStart()
        val usuario = auth.currentUser
        refresca(usuario)
    }

}