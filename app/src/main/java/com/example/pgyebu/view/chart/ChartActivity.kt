package com.example.pgyebu.view.chart

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.pgyebu.MyApplication
import com.example.pgyebu.R
import com.example.pgyebu.Utils.Util
import com.example.pgyebu.databinding.ActivityChartBinding
import com.example.pgyebu.entity.EventStatic
import com.example.pgyebu.entity.TypeResponse
import com.example.pgyebu.network.ApiService.Companion.getStaticsData
import com.example.pgyebu.network.EcoEventObject
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ChartActivity: AppCompatActivity() {
    private val TAG: String = "ChartActivity"

    private lateinit var binding: ActivityChartBinding
    var viewModel: ChartViewModel = ChartViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_chart
        )
        binding.lifecycleOwner = this
        binding.chartViewModel = viewModel

        setYYYYMM()

        initPieChart(binding.piechart)

        setStatic()

        binding.btnBack.setOnClickListener {
            finish()
        }

    }

    private fun setYYYYMM(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val localDateTime: LocalDateTime = LocalDateTime.now()
            val formatted = "${localDateTime.year}년 ${localDateTime.monthValue}월"

            binding.textCurrentDate.text = formatted
        }

    }

    private fun initPieChart(pieChart: PieChart){
        pieChart.apply {
            setUsePercentValues(true)
            description.text = ""
            // 도넛형 차트로 만들기
            isDrawHoleEnabled = false
            setTouchEnabled(false)
            setDrawEntryLabels(false)
            // 패딩 더하기
            setExtraOffsets(20f, 0f, 20f, 20f)
            setUsePercentValues(true)
            isRotationEnabled = false
            setDrawEntryLabels(false)
            legend.orientation = Legend.LegendOrientation.VERTICAL
            legend.isWordWrapEnabled = true
        }
    }

    private fun setStatic(){
        viewModel.getStatic(operSuccess = { result ->
            val dataEntries = ArrayList<PieEntry>().apply {
                add(PieEntry(result.thisMonthExpenditureInfoPercent[0].toFloat(), "지출"))
                add(PieEntry(result.thisMonthExpenditureInfoPercent[1].toFloat(), "수입"))
            }

            binding.piechart.setUsePercentValues(true)

            val colors= ArrayList<Int>().apply {
                add(Color.GRAY)
                add(Color.parseColor("#0064C7"))
            }

            val dataSet = PieDataSet(dataEntries, "")
            val data = PieData(dataSet)

            // In Percentage
            data.setValueFormatter(PercentFormatter())
            dataSet.sliceSpace = 3f
            dataSet.colors = colors
            binding.piechart.data = data
            data.setValueTextSize(15f)
            binding.piechart.setExtraOffsets(5f, 10f, 5f,5f)
            binding.piechart.animateY(1400, Easing.EaseInOutQuad)

            binding.piechart.apply {
                // create hole in center
                holeRadius = 58f
                transparentCircleRadius = 61f
                isDrawHoleEnabled = true
                setHoleColor(Color.WHITE)

                // add text in center
                setDrawCenterText(true)
                centerText = "${result.thisMonthExpenditureInfos[1]}%"
                setCenterTextSize(25f)

                invalidate()
            }

        })
    }
}