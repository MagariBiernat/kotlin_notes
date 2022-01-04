package com.kikunote.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.awesomedialog.*
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.kikunote.R
import com.kikunote.adapter.SectionsPagerAdapter
import com.kikunote.databinding.ActivityMainBinding
import com.kikunote.session.UserSession
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var appUpdateManager: AppUpdateManager
    private lateinit var session: UserSession
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initListener()
//        initContextMenuToolbar()
        initSession()


        appUpdateManager = AppUpdateManagerFactory.create(this)
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo
        appUpdateInfoTask.addOnSuccessListener {
            if (it.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && it.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                appUpdateManager.startUpdateFlowForResult(
                    it,
                    AppUpdateType.IMMEDIATE,
                    this,
                    999
                )
            } else {
                // TODO: do something in here if update not available
            }
        }

    }

    private fun initView() {

        val sectionsPagerAdapter =
            SectionsPagerAdapter(
                this,
                supportFragmentManager
            )
        binding.viewPager.adapter = sectionsPagerAdapter
        binding.tabs.setupWithViewPager(view_pager)
    }

    private fun initListener() {
        binding.toolbar.ibSearch.setOnClickListener(this)
        binding.toolbar.ibMenu.setOnClickListener(this)
        binding.floatingActionButton.setOnClickListener(this)
    }

    private fun initSession() {
        session = UserSession(applicationContext)
    }

//    private fun initContextMenuToolbar() {
//        registerForContextMenu(binding.toolbar.ibMenu)
//    }
//
//    override fun onCreateContextMenu(
//        menu: ContextMenu?,
//        v: View?,
//        menuInfo: ContextMenu.ContextMenuInfo
//    ) {
//        super.onCreateContextMenu(menu, v, menuInfo)
//        menu?.setHeaderTitle("Pick option")
//        menu?.add(0, v!!.getId(),0, "Profile")
//        menu?.add(0, v!!.getId(),0, "Logout")
//    }
//
//
//    override fun onContextItemSelected(item: MenuItem): Boolean {
//        if(item.getTitle() == "Profile") {
//            Toast
//                .makeText(this@MainActivity, "Profile clicked", Toast.LENGTH_SHORT)
//                .show()
//        }
//        return true
//    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ib_search -> {
                val intent = Intent(this@MainActivity, SearchActivity::class.java)
                startActivity(intent)
            }
            R.id.floatingActionButton -> {
                startActivity(Intent(this, EditActivity::class.java))
            }
            R.id.ib_menu -> {
                session.logoutUser()
            }
        }
    }

    override fun onResume() {
        appUpdateManager.appUpdateInfo
            .addOnSuccessListener {
                if (it.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    appUpdateManager.startUpdateFlowForResult(
                        it,
                        AppUpdateType.IMMEDIATE,
                        this,
                        999
                    )
                }
            }
        super.onResume()
    }

    override fun onBackPressed() {

        AwesomeDialog
            .build(this)
            .body("Are You sure You want to leave ?", color = ContextCompat.getColor(this, R.color.colorTitle))
            .background(R.drawable.background_dialog)
            .icon(R.mipmap.ic_launcher)
            .onPositive("Yes, leave",
                buttonBackgroundColor = android.R.color.holo_red_light,
                textColor = ContextCompat.getColor(this, R.color.colorTitle)
            ){
                finish()
            }
            .onNegative(
                "Cancel",
                buttonBackgroundColor = R.drawable.bg_btn_black,
                textColor = ContextCompat.getColor(this, R.color.background)
            ){
                return@onNegative
            }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 999 && resultCode == Activity.RESULT_OK) {
            // TODO: do something in here if in-app updates success
        } else {
            // TODO: do something in here if in-app updates failure
        }
    }
}
