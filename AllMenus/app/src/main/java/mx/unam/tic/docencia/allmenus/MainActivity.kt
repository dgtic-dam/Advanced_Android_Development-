package mx.unam.tic.docencia.allmenus

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.container_main.*
import kotlinx.android.synthetic.main.toolbar.*
import mx.unam.tic.docencia.pagerexample.adapter.MainAdapter
import mx.unam.tic.docencia.pagerexample.fragments.DetailFragment
import mx.unam.tic.docencia.pagerexample.fragments.FavoritesFragment
import mx.unam.tic.docencia.pagerexample.fragments.HomeFragment

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    private lateinit var previewMenuItem:MenuItem
    val homeFragment=HomeFragment.newInstance("","")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar as Toolbar?)

        supportActionBar?.setHomeAsUpIndicator(android.R.drawable.ic_menu_view)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val actionBarDrawerToggle = ActionBarDrawerToggle(this,mainDrawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer)
        mainDrawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        mainNavigationView.setNavigationItemSelectedListener(this)

        //Para interuactar con el header
      /*  val header=mainNavigationView.getHeaderView(0)
        header.titleHeaderTextView.setText("Hola Mundo")

        supportFragmentManager.beginTransaction().add(R.id.contentMain,homeFragment, "Home").commit()*/


        val mainAdapter=
            MainAdapter(initPageTitles(), initFragments(), supportFragmentManager)
        mainViewPager.adapter=mainAdapter
        //mainTabLayout.setupWithViewPager(mainViewPager)

        mainBottomNavigationView.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.home_item->{
                    mainViewPager.currentItem=0
                    true
                }
                R.id.detail_item -> {
                    mainViewPager.currentItem=1
                    true
                }
                R.id.favorite_item -> {
                    mainViewPager.currentItem=2
                    true
                }
                R.id.home_two_item->{
                    mainViewPager.currentItem=3
                    true
                }
                else -> {
                    false
                }
            }
        }

        mainViewPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                if(::previewMenuItem.isInitialized)
                    previewMenuItem.setChecked(false)
                else
                    mainBottomNavigationView.menu.getItem(0).setChecked(false)

                mainBottomNavigationView.menu.getItem(position).setChecked(true)
                previewMenuItem=mainBottomNavigationView.menu.getItem(position)
            }
        })



        fab.setOnClickListener { view ->
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
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

    private fun initFragments():ArrayList<Fragment>{
        return arrayListOf(
            HomeFragment.newInstance("Inicio", "#000000"),
            DetailFragment.newInstance(),
            FavoritesFragment.newInstance(),
            HomeFragment.newInstance("Inicio2", "#DEDEDE"))
    }

    private fun initPageTitles():ArrayList<String>{
        return arrayListOf("Inicio", "Detalle", "Favoritos", "Inicio2")
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        return when(p0.itemId){
            R.id.homeItem->{
               // supportFragmentManager.popBackStack()
                mainDrawerLayout.closeDrawers() //Cerrar menú
                true
            }
            R.id.favoriteItem->{
              //  switchFragment(R.id.contentMain, FavoritesFragment.newInstance(), "FAVORITE")
                mainDrawerLayout.closeDrawers() //Cerrar menú
                true
            }
            R.id.configurationItem->{
            //   switchFragment(R.id.contentMain, DetailFragment.newInstance(), "DETAIL")
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
            lateinit var fragmentTransaction: FragmentTransaction
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
