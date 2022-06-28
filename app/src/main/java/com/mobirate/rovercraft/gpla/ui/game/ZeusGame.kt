package com.mobirate.rovercraft.gpla.ui.game

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import com.mobirate.rovercraft.gpla.R
import com.mobirate.rovercraft.gpla.databinding.ZeusGameBinding

class ZeusGame : AppCompatActivity() {
    private lateinit var binding: ZeusGameBinding
    private var imgToTap: ImageView? = null
    private val drawables = listOf(
        R.drawable.element1,
        R.drawable.element2,
        R.drawable.element3,
        R.drawable.element4,
        R.drawable.element5,
    )
    private lateinit var images: List<ImageView>
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ZeusGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        images = listOf(
            binding.iv1,
            binding.iv2,
            binding.iv3,
            binding.iv4,
            binding.iv5,
        )

        binding.btnStart.setOnClickListener {
            it.visibility = View.INVISIBLE
            binding.tapLabel.visibility = View.VISIBLE
            loopAnim()
        }
    }

    private fun loopAnim() {
        initImages()
        revealImageToTap {
            revealAllImages {
                loopAnim()
            }
        }
    }

    private fun initImages() {
        //Reset clickListeners
        images.forEach {
            it.setOnClickListener(null)
        }

        //Set image to tap
        val currentImage = drawables.shuffled().random()
        binding.imgToTap.setImageResource(currentImage)

        //Set correct imageView to tap
        val sh = drawables.shuffled()
        imgToTap = when (sh.indexOf(currentImage)) {
            0 -> binding.iv1
            1 -> binding.iv2
            2 -> binding.iv3
            3 -> binding.iv4
            4 -> binding.iv5
            else -> null
        }

        //set images to reveal
        binding.iv1.setImageResource(sh[0])
        binding.iv2.setImageResource(sh[1])
        binding.iv3.setImageResource(sh[2])
        binding.iv4.setImageResource(sh[3])
        binding.iv5.setImageResource(sh[4])
    }

    private fun revealImageToTap(onEnd: () -> Unit) {
        binding.imgToTap.visibility = View.VISIBLE
        val minimiseSet = AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(binding.imgToTap, "scaleX", 40f, 1f),
                ObjectAnimator.ofFloat(binding.imgToTap, "scaleY", 40f, 1f)
            )
            doOnEnd { onEnd() }
            duration = 200
            start()
        }
    }

    private fun revealAllImages(onEnd: () -> Unit) {
        images.forEach {
            it.setOnClickListener { view ->
                if (view == imgToTap) binding.tappedCount.text = (++count).toString()
            }
        }
        val open = AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(binding.iv1, "alpha", 0f, 1f),
                ObjectAnimator.ofFloat(binding.iv2, "alpha", 0f, 1f),
                ObjectAnimator.ofFloat(binding.iv3, "alpha", 0f, 1f),
                ObjectAnimator.ofFloat(binding.iv4, "alpha", 0f, 1f),
                ObjectAnimator.ofFloat(binding.iv5, "alpha", 0f, 1f),
            )
            duration = 500
            startDelay = 1000
        }
        val close = AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(binding.iv1, "alpha", 1f, 0f),
                ObjectAnimator.ofFloat(binding.iv2, "alpha", 1f, 0f),
                ObjectAnimator.ofFloat(binding.iv3, "alpha", 1f, 0f),
                ObjectAnimator.ofFloat(binding.iv4, "alpha", 1f, 0f),
                ObjectAnimator.ofFloat(binding.iv5, "alpha", 1f, 0f),
            )
            duration = 500
        }
        AnimatorSet().apply {
            play(open).before(close)
            doOnEnd { onEnd() }
            start()
        }
    }
}