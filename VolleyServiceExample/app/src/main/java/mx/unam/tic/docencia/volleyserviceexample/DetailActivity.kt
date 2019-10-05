package mx.unam.tic.docencia.volleyserviceexample


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import mx.unam.tic.docencia.volleyserviceexample.services.APIController
import mx.unam.tic.docencia.volleyserviceexample.services.ServiceVolley
import mx.unam.tic.docencia.webserviceexample.listener.OnSetMovieListener
import mx.unam.tic.docencia.webserviceexample.models.Movie

class DetailActivity : AppCompatActivity(), OnSetMovieListener {

    override fun onSetMovie(movie: Movie) {
        titleTextView.text=movie.title
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val imdbID=intent.getStringExtra("idMovie")
        val service = ServiceVolley()
        val apiController= APIController(service)
        apiController.get("http://www.omdbapi.com/?apikey=3e1ff16&i="+imdbID,null,{ response ->
            val movie=Gson().fromJson<Movie>(response.toString(),Movie::class.java)
            titleTextView.text=movie.title
        })
    }

}
