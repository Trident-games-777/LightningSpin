package com.mobirate.rovercraft.gpla.ui.web

import androidx.lifecycle.ViewModel
import com.mobirate.rovercraft.gpla.data.repo.ZeusRepo

class WebViewModel(
    private val repo: ZeusRepo
) : ViewModel() {
    suspend fun setUrl(url: String) = repo.setUrl(url)
}