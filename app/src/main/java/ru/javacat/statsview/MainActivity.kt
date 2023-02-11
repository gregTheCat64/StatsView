package ru.javacat.statsview

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.BounceInterpolator
import android.view.animation.LinearInterpolator
import ru.javacat.statsview.ui.StatsView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val view = findViewById<StatsView>(R.id.statsView)
        view.data = listOf(
            0.25F,
            0.25F,
            0.25F,
            0.25F
        )
                //  *** View Animations ***
        //старый метод
//        view.startAnimation(
//            AnimationUtils.loadAnimation(this, R.anim.animation).apply  {
//                setAnimationListener(object: Animation.AnimationListener{
//                    override fun onAnimationStart(animation: Animation?) {
//                        TODO("Not yet implemented")
//                    }
//
//                    override fun onAnimationEnd(animation: Animation?) {
//                        TODO("Not yet implemented")
//                    }
//
//                    override fun onAnimationRepeat(animation: Animation?) {
//                        TODO("Not yet implemented")
//                    }
//
//                })
//            }
//        )


                        // *** ObjectAnimator ***
//        ObjectAnimator.ofFloat(view, View.ROTATION, 0.1F, 360F).apply {
//            startDelay = 500
//            duration = 5000
//            interpolator = BounceInterpolator()
//        }.start()

                        // *** PropertyValuesHolder ***
        //если надо сразу несколько свойств одним ObjectAnimator'ом:
//        val rotation = PropertyValuesHolder.ofFloat(View.ROTATION, 0F, 360F)
//        val alpha = PropertyValuesHolder.ofFloat(View.ALPHA, 0F, 1F)
//        ObjectAnimator.ofPropertyValuesHolder(view, rotation, alpha)
//            .apply {
//                startDelay = 500
//                duration = 1000
//                interpolator = LinearInterpolator()
//            }.start()

                    // *** ViewPropertyAnimator ***
        //более простой способ:
//        view.animate()
//            .rotation(360F)
//            .scaleX(2F)
//            .scaleY(2F)
//            .setInterpolator(LinearInterpolator())
//            .setStartDelay(500)
//            .setDuration(1000)
//            .start()

        // *** AnimatorSet ***
        //набор анимаций в нужном порядке нам:

//        val alpha = ObjectAnimator.ofFloat(view, View.ALPHA, 0.25F, 1F).apply{
//            duration = 3000
//            interpolator = LinearInterpolator()
//        }
//        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0F, 1F)
//        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0F, 1F)
//        val scale = ObjectAnimator.ofPropertyValuesHolder(view, scaleX,scaleY).apply {
//            duration = 3000
//            interpolator = BounceInterpolator()
//        }
//        AnimatorSet().apply {
//            startDelay = 500
//            playSequentially( scale,alpha)
//        }.start()

    }
}