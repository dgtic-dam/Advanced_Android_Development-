package mx.unam.tic.docencia.volleyserviceexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import mx.unam.tic.docencia.volleyserviceexample.services.APIController
import mx.unam.tic.docencia.volleyserviceexample.services.ServiceVolley
import mx.unam.tic.docencia.webserviceexample.adapters.MovieAdapter
import mx.unam.tic.docencia.webserviceexample.models.MoviesList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val service = ServiceVolley()
        val apiController=APIController(service)

        initView()
        apiController.get("http://www.omdbapi.com/?apikey=3e1ff16&s=avengers", null,{ response ->
            val moviesList=Gson().fromJson<MoviesList>(response.toString(),MoviesList::class.java)
            setMovieList(moviesList)
            movieProgressBar.visibility=View.GONE
        })

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

    private fun initView() {
        val layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )
        moviesRecyclerView.layoutManager = layoutManager
        moviesRecyclerView.setHasFixedSize(true)
    }

    fun setMovieList(moviesList: MoviesList){
        val movieAdapter= MovieAdapter(this,moviesList.search)
        movieAdapter.setOnMovieClickListener { imdbID ->
         /*   val detailIntent= Intent(this,DetailActivity::class.java)
            detailIntent.putExtra("idMovie",imdbID)
            startActivity(detailIntent)*/
        }
        moviesRecyclerView.adapter=movieAdapter
    }

}
