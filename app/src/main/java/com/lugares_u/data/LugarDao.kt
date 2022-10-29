package com.lugares_u.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.ktx.Firebase
import com.lugares_u.model.Lugar

class LugarDao
{
    //definiendo la jerarquia donde se gestionan los lugares
    private val coleccion1 = "lugaresApp"
    private val usuario = Firebase.auth.currentUser?.email.toString()
    private val coleccion2 = "misLugares"

    //"Conexion" a la base de datos en la nube
    private var firestore : FirebaseFirestore =
        FirebaseFirestore.getInstance()


    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings
            .Builder().build()
    }

    //CRUD
    fun saveLugar(lugar: Lugar){
        //Un documentReference es un enlace a un documento(json) en la nube
        val documento : DocumentReference
        if(lugar.id.isEmpty())//será un lugar nuevo ..... pues no tiene un id
        {
            documento = firestore
                .collection(coleccion1)
                .document(usuario)
                .collection(coleccion2)
                .document()
            lugar.id = documento.id
        }
        else{//el lugar ya tiene id... eciste en la nube
            documento = firestore
                .collection(coleccion1)
                .document(usuario)
                .collection(coleccion2)
                .document(lugar.id)
        }
        //Ahora se inserta o actualiza el lugar...
        documento.set(lugar)
            .addOnSuccessListener {
                Log.d("SaveLugar","Lugar agregado o modifico")
            }
            .addOnCanceledListener {
                Log.e("SaveLugar","Lugar NO agregado o modificado")
            }
    }

    fun deleteLugar(lugar: Lugar){
        //se valida si el id del lugar  tiene no está vacio... si es asi se puede borrar
        if(lugar.id.isNotEmpty()){
            firestore
                .collection(coleccion1)
                .document(usuario)
                .collection(coleccion2)
                .document(lugar.id)
                .delete()

                .addOnSuccessListener {
                    Log.d("DeleteLugar","Lugar eliminado")
                }
                .addOnCanceledListener {
                    Log.e("DeleteLugar","Lugar NO eliminado")
                }
        }
    }

    fun getLugares() : MutableLiveData<List<Lugar>>{
        val listaLugares = MutableLiveData<List<Lugar>>()

        firestore
            .collection(coleccion1)
            .document(usuario)
            .collection(coleccion2)
            .addSnapshotListener{ instantenea, error->

                if(error!=null){//hubo un error en la recureparion de los datos
                    return@addSnapshotListener
                }
                if(instantenea != null){
                    //Se logró recuperar la info y hay informacion
                    val lista = ArrayList<Lugar>()
                    instantenea.documents.forEach{
                        var lugar = it.toObject(Lugar::class.java)
                        if(lugar!=null)//Si se pudo convertir en lugar
                        {
                            lista.add(lugar)
                        }
                    }
                    listaLugares.value = lista
                }

            }

        return listaLugares
    }

}