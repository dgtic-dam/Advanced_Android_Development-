package mx.unam.tic.docencia.volleyserviceexample.services

import org.json.JSONObject

interface MoviesListServiceInterface {

    fun get(path:String,params: JSONObject?,completionHandler:(response:JSONObject?)->Unit)

    fun post(path: String, params: JSONObject?,completionHandler: (response: JSONObject?) -> Unit)
}