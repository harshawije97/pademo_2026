package com.example.processappdemo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.example.processappdemo.data.AppDatabase
import com.example.processappdemo.data.model.TestEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    companion object {
        private const val TAG = "SplashActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        val loader = findViewById<ProgressBar>(R.id.splashLoader)
        loader.visibility = View.VISIBLE

        lifecycleScope.launch {
            try {
                withContext(Dispatchers.IO){
//                    Init database
                    val isDbExists: Boolean = AppDatabase.isDatabaseConfigured(applicationContext)
                    Log.d(TAG, "Database already exists: $isDbExists")

                    if (isDbExists){
                        Log.d(TAG, "Dropping and recreating all...")
                        AppDatabase.iniMigration(applicationContext)
                        Log.d(TAG, "All tables dropped and recreated successfully")
                    }else{
                        Log.d(TAG,"initializing database...")
                        AppDatabase.getDatabase(applicationContext)
                        Log.d(TAG, "Database initialized successfully")
                    }

                    val db = AppDatabase.getDatabase(applicationContext)
//                    insert sample data
                    val sample = TestEntity(
                        name = "Sample Name",
                        description = "Lorem ipsum"
                    )
                    db.iTestDao().insert(sample)

//                    Get all data
                    val records = db.iTestDao().getAll()
                    Log.d(TAG, "DB configured successfully. Total records of DB: ${records.size}")

//                    Remove loader on success
                    loader.visibility = View.GONE
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }
            }catch (e: Exception){
                Log.e(TAG, "DB setup failed: ${e.message}", e)
                // Still navigate even if DB fails
                loader.visibility = View.GONE
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }
        }
    }
}
