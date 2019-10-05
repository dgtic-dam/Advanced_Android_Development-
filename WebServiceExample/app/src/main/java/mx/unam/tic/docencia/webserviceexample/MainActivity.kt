package mx.unam.tic.docencia.webserviceexample

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import mx.unam.tic.docencia.webserviceexample.adapters.MovieAdapter
import mx.unam.tic.docencia.webserviceexample.listener.OnSetMovieListListener
import mx.unam.tic.docencia.webserviceexample.models.Movie
import mx.unam.tic.docencia.webserviceexample.models.MoviesList
import mx.unam.tic.docencia.webserviceexample.service.WebService
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() , OnSetMovieListListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initView()
        val movieServiceTask=MovieServiceTask(this)
        movieServiceTask.setOnSetMovieListListener(this)
        movieServiceTask.execute("http://www.omdbapi.com/?apikey=3e1ff16&s=avengers")

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSetMovieList(moviesList: MoviesList) {
        setMovieList(moviesList)
    }

    private fun initView() {
        val layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )
        movieRecyclerView.layoutManager = layoutManager
        movieRecyclerView.setHasFixedSize(true)
    }

    fun setMovieList(moviesList: MoviesList){
        val movieAdapter=MovieAdapter(this,moviesList.search)
        movieAdapter.setOnMovieClickListener { imdbID ->
            val detailIntent= Intent(this,DetailActivity::class.java)
            detailIntent.putExtra("idMovie",imdbID)
            startActivity(detailIntent)
        }
        movieRecyclerView.adapter=movieAdapter
    }

    companion object {
        class MovieServiceTask internal constructor(context: MainActivity) :
            AsyncTask<String, Void, MoviesList>() {

            private val activityReference: WeakReference<MainActivity> =
                WeakReference(context)

            private lateinit var onSetMovieListListener: OnSetMovieListListener

            fun setOnSetMovieListListener(onSetMovieListListener: OnSetMovieListListener){
                this.onSetMovieListListener=onSetMovieListListener
            }

            override fun onPreExecute() {
                super.onPreExecute()
                val activity = activityReference.get()
                if(activity==null || activity.isFinishing)
                    return
                activity.movieProgressBar.visibility= View.VISIBLE
            }

            override fun doInBackground(vararg p0: String?): MoviesList? {
                val response=p0[0]?.let { WebService().getService(it) }
                if(!response.isNullOrEmpty()){
                    val movieList= Gson().fromJson(response,MoviesList::class.java)
                    return movieList
                }else{
                    return null
                }
            }

            override fun onPostExecute(result: MoviesList?) {
                super.onPostExecute(result)
                val activity = activityReference.get()
                if (activity==null || activity.isFinishing)
                    return
                activity.movieProgressBar.visibility=View.GONE

                if (::onSetMovieListListener.isInitialized && result!=null){
                    onSetMovieListListener.onSetMovieList(result)
                }else{
                    Log.d("error", "Error")
                }
            }

        }
    }
}