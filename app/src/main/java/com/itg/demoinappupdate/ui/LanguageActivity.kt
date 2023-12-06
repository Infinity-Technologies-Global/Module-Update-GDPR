package com.itg.demoinappupdate.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.itg.demoinappupdate.databinding.ActivityLanguageBinding
import com.itg.demoinappupdate.databinding.ActivityMainBinding


class LanguageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLanguageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}