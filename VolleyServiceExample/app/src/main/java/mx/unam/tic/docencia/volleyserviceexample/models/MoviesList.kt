package mx.unam.tic.docencia.webserviceexample.models


import com.google.gson.annotations.SerializedName

data class MoviesList(
    @SerializedName("Response")
    val response: String,
    @SerializedName("Search")
    val search: ArrayList<Search>,
    @SerializedName("totalResults")
    val totalResults: String
)