package ru.javacat.statsview.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
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
    private var textSize = AndroidUtils.dp(context, 20).toFloat()
    private var lineWidth = AndroidUtils.dp(context, 5)
    private var colors = emptyList<Int>()
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
        invalidate()
    }

    private var radius = 0F
    private var center = PointF()
    private var oval = RectF()
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

    override fun onDraw(canvas: Canvas) {
        if (data.isEmpty()){
            return
        }

        var startAngle = -90F

        //вариант с цифрами вместо углов:
//        var dataSum = 0F
//        for (i in data){
//            dataSum += i
//        }
//        data.forEachIndexed {index, datum ->
//            val angle = (1/(dataSum/datum)) * 360F
//            paint.color = colors.getOrElse(index) {generateRandomColor()}
//            canvas.drawArc(oval, startAngle, angle, false,paint)
//            startAngle += angle
//        }

        paint.color = 0xFFCCCCCC.toInt()
        canvas.drawCircle(center.x, center.y, radius, paint)

        data.forEach{
            val angle  = it * 360F
            paint.color = generateRandomColor()
            canvas.drawArc(oval, startAngle,angle,false, paint)
            startAngle += angle
        }
        canvas.drawText(
            "%.2f%%".format(data.sum()*100),
            center.x,
            center.y+textPaint.textSize/4,
            textPaint
        )

    }

    private fun generateRandomColor() = Random.nextInt(0xFF000000.toInt(), 0xFFFFFFFF.toInt())
}