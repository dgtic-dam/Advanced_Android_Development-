package mx.unam.tic.docencia.volleyserviceexample.services

import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject


class ServiceVolley:MoviesListServiceInterface {

    val TAG=ServiceVolley::class.java.simpleName

    override fun get(
        path: String,
        params: JSONObject?,
        completionHandler: (response: JSONObject?) -> Unit
    ) {
        val jsonObject=object : JsonObjectRequest(Method.GET,path,null,
            Response.Listener { response ->
                completionHandler(response)
            },Response.ErrorListener { error ->
                completionHandler(null)
            }){
            @Throws(AuthFailureError::class)
            override fun getHeaders(): MutableMap<String, String> {
                val headers=HashMap<String, String>()
                headers.put("Content-Type","application/json")
                return headers
            }
        }
        VolleyServicesExampleApplication.instance?.addToRequestQueue(jsonObject)
    }

    override fun post(
        path: String,
        params: JSONObject?,
        completionHandler: (response: JSONObject?) -> Unit
    ) {
        val jsonObject=object : JsonObjectRequest(Method.GET,path,params,
            Response.Listener { response ->
                completionHandler(response)
            },Response.ErrorListener { error ->
                completionHandler(null)
            }){
            @Throws(AuthFailureError::class)
            override fun getHeaders(): MutableMap<String, String> {
                val headers=HashMap<String, String>()
                headers.put("Content-Type","application/json")
                return headers
            }
        }
        VolleyServicesExampleApplication.instance?.addToRequestQueue(jsonObject)
    }

}