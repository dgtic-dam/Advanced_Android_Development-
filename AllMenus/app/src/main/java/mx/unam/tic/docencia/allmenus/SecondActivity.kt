package mx.unam.tic.docencia.allmenus

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.second_content_main.*
import kotlinx.android.synthetic.main.second_toolbar.*
import mx.unam.tic.docencia.pagerexample.adapter.MainAdapter
import mx.unam.tic.docencia.pagerexample.fragments.DetailFragment
import mx.unam.tic.docencia.pagerexample.fragments.FavoritesFragment
import mx.unam.tic.docencia.pagerexample.fragments.HomeFragment
import mx.unam.tic.docencia.pagerexample.listener.OnChangeColorBarTabSelected

class SecondActivity : AppCompatActivity(), OnChangeColorBarTabSelected {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
       // setSupportActionBar(secondtoolbar)

        val mainAdapter= MainAdapter(initPageTitles(), initFragments(), supportFragmentManager)
        secondViewPager.adapter=mainAdapter
        mainTabLayout.setupWithViewPager(secondViewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
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
        return arrayListOf(
            HomeFragment.newInstance("Inicio", "#000000"),
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
