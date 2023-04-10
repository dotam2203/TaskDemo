package com.task

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.task.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    getFragment()
  }

  private fun getFragment() {
    binding.tbTop.visibility = View.GONE
    val fragment = MoviesFragment()
    supportFragmentManager.beginTransaction().replace(R.id.fragment, fragment).commit()
  }

}