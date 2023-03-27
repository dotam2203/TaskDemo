package com.task

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.task.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    getFragment()
  }

  private fun getFragment() {
    val fragment = ListDataFragment()
    supportFragmentManager.beginTransaction().replace(R.id.fragment, fragment).commit()
  }

}