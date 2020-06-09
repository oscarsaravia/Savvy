package com.example.android.proyecto_final

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import com.example.android.proyecto_final.databinding.ActivityMainBinding
import com.example.android.proyecto_final.network.CurrentUserInfo
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.nav_header.view.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {


    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var navHead: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
        navHead = navView.getHeaderView(0)

    }




    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_inicio -> {

                findNavController(R.id.myNavHostFragment).navigate(R.id.homeFragment)
            }
            R.id.nav_acercade -> {
                findNavController(R.id.myNavHostFragment).navigate(R.id.aboutFragment)
            }
            R.id.nav_salir -> {
                findNavController(R.id.myNavHostFragment).navigate(R.id.loginFragment)
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun actualizar(){
        navHead.CurrentUserName.text = CurrentUserInfo.name.toUpperCase()
        navHead.CurrentUserEmail.text = CurrentUserInfo.mail
        navHead.CurrentUserCompany.text = CurrentUserInfo.company
    }


}
