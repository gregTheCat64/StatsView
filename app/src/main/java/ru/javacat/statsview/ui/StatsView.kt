package ru.javacat.statsview.ui

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.withStyledAttributes
import ru.javacat.statsview.R
import ru.javacat.statsview.utils.AndroidUtils
import kotlin.math.min
import kotlin.random.Random


class StatsView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
): View(
    context,
    attributeSet,
    defStyleAttr,
    defStyleRes
) {
    private var radius = 0F
    private var center = PointF()
    private var oval = RectF()

    private var textSize = AndroidUtils.dp(context, 20).toFloat()
    private var lineWidth = AndroidUtils.dp(context, 5)
    private var colors = emptyList<Int>()

    private var progress = 0F
    private var valueAnimator: ValueAnimator? = null

    init {
        context.withStyledAttributes(attributeSet, R.styleable.StatsView){
            textSize = getDimension(R.styleable.StatsView_textSize, textSize)
            lineWidth = getDimension(R.styleable.StatsView_lineWidth, lineWidth.toFloat()).toInt()

            colors = listOf(
                getColor(R.styleable.StatsView_color1,generateRandomColor()),
                getColor(R.styleable.StatsView_color2,generateRandomColor()),
                getColor(R.styleable.StatsView_color3,generateRandomColor()),
                getColor(R.styleable.StatsView_color4,generateRandomColor())
            )

        }
    }
    var data: List<Float> = emptyList()
    set(value) {
        field = value
        //invalidate()
        update()
    }


    private val paint = Paint(
        Paint.ANTI_ALIAS_FLAG
    ).apply {
        strokeWidth = lineWidth.toFloat()
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
    }

    private val textPaint = Paint(
        Paint.ANTI_ALIAS_FLAG
    ).apply {
        textSize = this@StatsView.textSize
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        radius = min(w, h) / 2F - lineWidth
        center = PointF(w/2F, h/2F)
        oval = RectF(
            center.x - radius,
            center.y - radius,
            center.x + radius,
            center.y + radius
        )
    }


   //                *** ДЗ НОМЕР 1 - ROTATION: ***
//    override fun onDraw(canvas: Canvas) {
//        if (data.isEmpty()){
//            return
//        }
//        Log.i("AAA", "onDraw")
//
//        canvas.drawText(
//            "%.2f%%".format(progress*100),
//            center.x,
//            center.y+textPaint.textSize/4,
//            textPaint
//        )
//
//        var startAngle = -90F
//
//        data.forEachIndexed{index, datum ->
//            val angle  = datum * 360F
//            paint.color = colors.getOrElse(index) {generateRandomColor()}
//            canvas.drawArc(oval, startAngle+(progress*360F),angle*progress,false, paint)
//            startAngle += angle
//        }
//
//         //     Фикс бага с 1-й дугой:
////        if (data.sum()==1F){
////            val angle = 1F
////            paint.color = colors[0]
////            canvas.drawArc(oval, startAngle,angle,false, paint)
////        }
//    }

        // *** Доп.задание ***
    override fun onDraw(canvas: Canvas) {
        if (data.isEmpty()){
            return
        }
        Log.i("AAA", "onDraw")

        canvas.drawText(
            "%.2f%%".format(progress*100),
            center.x,
            center.y+textPaint.textSize/4,
            textPaint
        )

            var startAngle = -90F
            // Запоминаем какой сектор уже заполнен для раннего выхода
            var filled = 0F
            // Угол, связанный с прогрессом, по сути максимум
            val progressAngle = 360F * progress
            for ((index, datum) in data.withIndex()) {
                Log.i("AAA", "filled: $filled")
                val angle = 360F * datum
                // При построении угла учитываем прогресс и уже заполненную дугу
                val sweepAngle = progressAngle - filled

                Log.i("AAA", "sweep: $sweepAngle")
                paint.color = colors.getOrNull(index) ?: generateRandomColor()
                canvas.drawArc(oval, startAngle, sweepAngle, false, paint)
                startAngle += angle
                filled += angle
                // Если продвинулись дальше прогресса, выходим. В следующих кадрах отрисуемся
                if (filled > progressAngle) return
            }


//        data.forEachIndexed{index, datum ->
//            val angle  = datum * 360F
//            paint.color = colors.getOrElse(index) {generateRandomColor()}
//            canvas.drawArc(oval, startAngle+(progress*360F),angle*progress,false, paint)
//            startAngle += angle
//        }

    }


    private fun update() {
        Log.i("AAA", "update")
        valueAnimator?.let {
            it.removeAllListeners()
            it.cancel()
        }
        progress = 0F

        valueAnimator = ValueAnimator.ofFloat(0F, 1F).apply {
            addUpdateListener { anim ->
                progress = anim.animatedValue as Float
                println("progerss: " +progress)
                invalidate()
            }
            duration = 2000
            interpolator = LinearInterpolator()
        }.also {
            it.start()
        }
    }


    private fun generateRandomColor() = Random.nextInt(0xFF000000.toInt(), 0xFFFFFFFF.toInt())
}