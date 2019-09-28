package mx.unam.tic.docencia.pagerexample

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import mx.unam.tic.docencia.pagerexample.adapter.MainAdapter
import mx.unam.tic.docencia.pagerexample.fragments.DetailFragment
import mx.unam.tic.docencia.pagerexample.fragments.FavoritesFragment
import mx.unam.tic.docencia.pagerexample.fragments.HomeFragment
import mx.unam.tic.docencia.pagerexample.listener.OnChangeColorBarTabSelected

class MainActivity : AppCompatActivity(), OnChangeColorBarTabSelected {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val mainAdapter=MainAdapter(initPageTitles(), initFragments(), supportFragmentManager)
        mainViewPager.adapter=mainAdapter
        mainTabLayout.setupWithViewPager(mainViewPager)
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

    override fun OnChangeColorBar(title: String, colorBar: String) {
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor(colorBar)))
        supportActionBar?.title=title
    }

    private fun initFragments():ArrayList<Fragment>{
        return arrayListOf(HomeFragment.newInstance("Inicio", "#000000"),
            DetailFragment.newInstance(),
            FavoritesFragment.newInstance(),
            HomeFragment.newInstance("Inicio2", "#DEDEDE"),
            DetailFragment.newInstance(),
            FavoritesFragment.newInstance())
    }

    private fun initPageTitles():ArrayList<String>{
        return arrayListOf("Inicio", "Detalle", "Favoritos","Inicio2", "Detalle2", "Favoritos2")
    }
}
