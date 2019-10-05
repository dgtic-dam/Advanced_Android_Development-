package mx.unam.tic.docencia.volleyserviceexample.services

import android.app.Application
import android.text.TextUtils
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class VolleyServicesExampleApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        instance=this
    }

    val requestQueue:RequestQueue?=null
        get(){ //accediendo a la propiedad de get de la variable
            if (field==null){
                return Volley.newRequestQueue(applicationContext)
            }
            return field
        }

    fun <T> addToRequestQueue(request:Request<T>,tag:String){ //<T> de cualquier tipo
        request.tag=if (TextUtils.isEmpty(tag))TAG else tag //Se obtiene el tag
        requestQueue?.add(request) //Se agrega a la pila
    }

    fun <T>  addToRequestQueue(request: Request<T>){
        request.tag= TAG
        requestQueue?.add(request)
    }

    fun cancelPendingRequest(tag:Any){
        if(requestQueue!=null)
            requestQueue!!.cancelAll(tag)
    }

    companion object{
        private val TAG = VolleyServicesExampleApplication::class.java.simpleName //Accedemos al objeto de Volley y accedemos a su nombre simple y se asigna al TAG

        @get:Synchronized
        var instance:VolleyServicesExampleApplication?=null
            private set
    }

}