package mx.unam.tic.docencia.volleyserviceexample.services

import org.json.JSONObject


class APIController constructor(serviceInjection: MoviesListServiceInterface): MoviesListServiceInterface {

    private val service:MoviesListServiceInterface=serviceInjection

    override fun get(
        path: String,
        params: JSONObject?,
        completionHandler: (response: JSONObject?) -> Unit
    ) {
        service.get(path, params,completionHandler)
    }




}