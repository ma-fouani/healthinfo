package co.fouani.healthinfo.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import co.fouani.healthinfo.R
import co.fouani.healthinfo.databinding.ActivityMainBinding
import co.fouani.healthinfo.ui.fragments.DummyFragmentDirections
import co.fouani.healthinfo.ui.viewmodels.MainActivityViewModel
import co.fouani.healthinfo.utils.CachingHelper.FIRST_OPEN
import co.fouani.healthinfo.utils.CachingHelper.getBoolean

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(binding.toolbar)

        navController = findNavController(R.id.fragment)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.dummyFragment))

        setupActionBarWithNavController(navController, appBarConfiguration)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        viewModel.title.observe(this, {
            binding.toolbar.title = it
        })
        if (getBoolean(FIRST_OPEN, true, this)) {
            navController.navigate(R.id.dummyFragment)
        }
    }

    override fun onBackPressed() {
        if (navController.currentDestination?.id == R.id.dummyFragment) {
            finish()
        }
        super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}