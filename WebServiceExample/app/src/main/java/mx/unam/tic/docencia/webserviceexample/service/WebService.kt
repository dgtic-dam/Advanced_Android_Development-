package mx.unam.tic.docencia.webserviceexample.service

import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class WebService {

    fun getService(url:String):String{
        var urlConnection:HttpURLConnection?=null
        var result:String=""

        try {
            val url=URL(url) //validar que url sea valida
            urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.requestMethod="GET"
            urlConnection.connect()

            val inputStream=urlConnection.inputStream //bytes que se trajeron de la conexion
            return inputStream.bufferedReader().readText() //regresa la cadena
        }catch (e:IOException){
            return result
        }finally {
            if (urlConnection!=null)
                urlConnection.disconnect()
        }
    }
}