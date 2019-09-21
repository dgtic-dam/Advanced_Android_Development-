package com.cybertch.navigationview

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.cybertch.navigationview.fragments.ConfigurationFragment
import com.cybertch.navigationview.fragments.FavoriteFragment
import com.cybertch.navigationview.fragments.HomeFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.container_main.*
import kotlinx.android.synthetic.main.header.view.*
import kotlinx.android.synthetic.main.toolbar.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    val homeFragment=HomeFragment.newInstance("","")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeAsUpIndicator(android.R.drawable.ic_menu_view)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val actionBarDrawerToggle = ActionBarDrawerToggle(this,mainDrawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer)
        mainDrawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        mainNavigationView.setNavigationItemSelectedListener(this)

        //Para interuactar con el header
        val header=mainNavigationView.getHeaderView(0)
        header.titleHeaderTextView.setText("Hola Mundo")

        supportFragmentManager.beginTransaction().add(R.id.contentMain,homeFragment, "Home").commit()

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

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
            return when(p0.itemId){
                R.id.homeItem->{
                    supportFragmentManager.popBackStack()
                    mainDrawerLayout.closeDrawers() //Cerrar menú
                    true
                }
                R.id.favoriteItem->{
                    switchFragment(R.id.contentMain, FavoriteFragment.newInstance("",""), "FAVORITE")
                    mainDrawerLayout.closeDrawers() //Cerrar menú
                    true
                }
                R.id.configurationItem->{
                    switchFragment(R.id.contentMain, ConfigurationFragment.newInstance("",""), "CONFIGURATION")
                    mainDrawerLayout.closeDrawers() //Cerrar menú
                    true
                }
                else -> {
                    false
                }
        }
    }

    fun switchFragment(idContainer:Int, fragment: Fragment, tag: String){
        if(fragment!=null){
            lateinit var fragmentTransaction:FragmentTransaction
            while (supportFragmentManager.popBackStackImmediate()){
                //Se liberan los fragmentos de arriba de la pila
            }
            fragmentTransaction=supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(idContainer, fragment)
            if(!(fragment is HomeFragment)) //Se agregar a la pila los que no son homeFragment
                fragmentTransaction.addToBackStack(tag)
                fragmentTransaction.commit()
        }
    }
}
