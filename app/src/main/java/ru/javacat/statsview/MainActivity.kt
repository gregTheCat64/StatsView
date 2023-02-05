package ru.javacat.statsview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.javacat.statsview.ui.StatsView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<StatsView>(R.id.statsView).data = listOf(
            0.55F,
            0.15F,
            0.15F,
            0.15F
        )
    }
}