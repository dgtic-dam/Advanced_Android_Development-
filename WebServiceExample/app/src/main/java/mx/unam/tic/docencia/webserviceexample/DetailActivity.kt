package mx.unam.tic.docencia.webserviceexample

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.OrientationEventListener
import android.view.View
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_main.*
import mx.unam.tic.docencia.webserviceexample.listener.OnSetMovieListListener
import mx.unam.tic.docencia.webserviceexample.listener.OnSetMovieListener
import mx.unam.tic.docencia.webserviceexample.models.Movie
import mx.unam.tic.docencia.webserviceexample.models.MoviesList
import mx.unam.tic.docencia.webserviceexample.service.WebService
import java.lang.ref.WeakReference

class DetailActivity : AppCompatActivity(), OnSetMovieListener {

    override fun onSetMovie(movie: Movie) {
        titleTextView.text=movie.title
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val imdbID=intent.getStringExtra("idMovie")
        val detailTask=DetailTask(this)
        detailTask.setOnSetMovieListener(this)
        detailTask.execute("http://www.omdbapi.com/?apikey=3e1ff16&i="+imdbID)
    }

    companion object{

        class DetailTask internal constructor(context: DetailActivity):AsyncTask<String,Void,Movie>() {

            private val activityReference:WeakReference<DetailActivity> = WeakReference(context)

            private lateinit var onSetMovieListener: OnSetMovieListener

            fun setOnSetMovieListener(onSetMovieListener: OnSetMovieListener){
                this.onSetMovieListener=onSetMovieListener
            }

            override fun onPreExecute() {
                super.onPreExecute()
                val activity = activityReference.get()
                if(activity==null || activity.isFinishing)
                    return
                activity.detailProgressBar.visibility= View.VISIBLE
            }

            override fun doInBackground(vararg p0: String?): Movie? {
                val response = p0[0]?.let { WebService().getService(it) }
                if (response.isNullOrEmpty())
                    return null
                else{
                val movie=Gson().fromJson(response,Movie::class.java)
                return movie
                }
            }

            override fun onPostExecute(result: Movie?) {
                super.onPostExecute(result)
                val activity = activityReference.get()
                if (activity==null || activity.isFinishing)
                    return
                activity.detailProgressBar.visibility=View.GONE

                if (::onSetMovieListener.isInitialized && result!=null){
                    onSetMovieListener.onSetMovie(result)
                }else{
                    Log.d("error", "Error")
                }
            }
        }
    }
}
