package com.example.pgyebu.view.home

//import com.events.calendar.utils.EventsCalendarUtil
//import com.events.calendar.utils.EventsCalendarUtil.MULTIPLE_SELECTION
//import com.events.calendar.utils.EventsCalendarUtil.today
//import com.events.calendar.views.EventsCalendar

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.text.style.StyleSpan
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pgyebu.MyApplication
import com.example.pgyebu.R
import com.example.pgyebu.Utils.Util
import com.example.pgyebu.adapter.finRecyclerAdapter
import com.example.pgyebu.databinding.ActivityHomeBinding
import com.example.pgyebu.entity.*
import com.example.pgyebu.network.EcoEventObject
import com.example.pgyebu.view.add.AddActivity
import com.example.pgyebu.view.chart.ChartActivity
import com.example.pgyebu.view.mypage.MypagemenuActivity
import com.example.pgyebu.view.setting.SettingmenuActivity
import com.prolificinteractive.materialcalendarview.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.text.Format
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*


class HomeActivity: AppCompatActivity(), OnDateSelectedListener {

    private val TAG: String = "HomeActivity"
    private val viewModel: HomeViewModel = HomeViewModel()
    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapter: finRecyclerAdapter
    private var currentSelectDay: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // MyApplication.prefs.setString(Util.IS_LOGIN, Util.NOLOGIN)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home)
        binding.lifecycleOwner = this

        isIntent()
        getEventData()
        initRecyclerView()
        initCalendar()
        clickListener()
        observeEvent()

        binding.homeViewModel = viewModel

    }

    private fun isIntent(){
        if (intent.hasExtra(EDITDATE)){
            // 기존 항목 편집 후 돌아옴
            currentSelectDay = intent.getStringExtra(EDITDATE).toString()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val localDateTime: LocalDateTime = LocalDateTime.now()
                val formatted = localDateTime.format(DateTimeFormatter.ISO_DATE)
                println("$TAG: today is $formatted")
                currentSelectDay = formatted.toString()
            } else{
                println("$TAG this device is not Oreo")
            }
        }
    }

    private fun getEventData(){
        viewModel.getDisplayMainStatics()

        viewModel.getEvents(currentSelectDay, oper = {
            viewModel.finList.addAll(it)
        })
    }

    private fun initRecyclerView(){
        binding.recyclerViewFin.layoutManager = LinearLayoutManager(this)
        adapter = finRecyclerAdapter(context = applicationContext) {
            // EcoEvent -> intent
            val intent = Intent(this@HomeActivity, AddActivity::class.java)
            intent.apply {
                putExtra(AddActivity.EDIT_EVENT, true)
                putExtra("content", it.content)
                putExtra("eventType", it.eventType)
                putExtra("amount", it.amount.toString())
                putExtra("category", it.category)
                putExtra("seq", it.id)
                putExtra(AddActivity.CREATE_EVENT, currentSelectDay)
            }
            startActivity(intent)
        }

        binding.recyclerViewFin.adapter = adapter
    }

    private fun initCalendar(){
        val curday = currentSelectDay.split("-")
        var calendar = Calendar.getInstance()
        val standardDate = MyApplication.prefs.getLong(Util.SET_STANDARD_NUM, Util.NO_STANDARD_NUM)
        val isSetDate = standardDate != Util.NO_STANDARD_NUM
        calendar.time = Date(standardDate)

        binding.cvCalendar.apply {
            setOnDateChangedListener(this@HomeActivity)
            setDateTextAppearance(R.style.CustomTextAppearance)
            // Calendar.get(Calendar.MONTH)) 범위는 0~11 이고,
            // CalendarDay에서 month 범위 1~12 이기 때문에 1을 더한다.
            if(isSetDate) {
                state().edit().setMinimumDate(
                    CalendarDay.from(
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH) + 1,
                        calendar.get(Calendar.DAY_OF_MONTH)
                    )
                ).commit()
                // 기준 일자에서 한달을 더한다.
                calendar.add(Calendar.MONTH, 1)
                state().edit().setMaximumDate(
                    CalendarDay.from(
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH) + 1,
                        calendar.get(Calendar.DAY_OF_MONTH)
                    )
                ).commit()
            }

            addDecorator(DayDecorator(applicationContext))
            addDecorator(CurrentDayDecorator(applicationContext, CalendarDay.today()))

            selectedDate = CalendarDay.from(curday[0].toInt(), curday[1].toInt(), curday[2].toInt())
        }

    }

    override fun onDateSelected(
        widget: MaterialCalendarView,
        date: CalendarDay,
        selected: Boolean
    ) {
        val selectedDate = "${date.year}-${String.format("%02d",date.month)}-${String.format("%02d",date.day)}"
        println("$TAG: Calendar selected! $selectedDate")
        currentSelectDay = selectedDate

        viewModel.getEvents(currentSelectDay, oper = {
            viewModel.finList.clear()
            viewModel.finList.addAll(it)
            println("$TAG: Data -> ${it}")
        })
    }

    private fun clickListener(){
        binding.btnMenu.setOnClickListener {
            binding.mainDrawerLayout.openDrawer((GravityCompat.START))
        }

        binding.naviHeaderBackBtn.setOnClickListener {
            binding.mainDrawerLayout.closeDrawer((GravityCompat.START))
        }
    }

    private fun observeEvent(){

        viewModel.finList.observe(this){
            // Update UI
            adapter.updateList(it)
        }

        viewModel.startMypagemenuActivityEvent.observe(this) {
            startActivity(Intent(this@HomeActivity, MypagemenuActivity::class.java))
        }

        viewModel.startSettingActivityEvent.observe(this) {
            startActivity(Intent(this@HomeActivity, SettingmenuActivity::class.java))
        }

        viewModel.startAddActivityEvent.observe(this, Observer {
            val intent = Intent(this@HomeActivity, AddActivity::class.java)
            intent.putExtra(AddActivity.CREATE_EVENT, currentSelectDay)
            startActivity(intent)
        })

        viewModel.startChartActivityEvent.observe(this, Observer {
            startActivity(Intent(this@HomeActivity, ChartActivity::class.java))
        })
    }

    /* 선택된 요일의 background를 설정하는 Decorator 클래스 */
    private class DayDecorator(context: Context) : DayViewDecorator {
        private val drawable: Drawable?

        // true를 리턴 시 모든 요일에 내가 설정한 드로어블이 적용된다
        override fun shouldDecorate(day: CalendarDay): Boolean {
            return true
        }

        // 일자 선택 시 내가 정의한 드로어블이 적용되도록 한다
        override fun decorate(view: DayViewFacade) {
            view.setSelectionDrawable(drawable!!)
            //            view.addSpan(new StyleSpan(Typeface.BOLD));   // 달력 안의 모든 숫자들이 볼드 처리됨
        }

        init {
            drawable = ContextCompat.getDrawable(context, R.drawable.calendar_selector)
        }
    }

    private class CurrentDayDecorator(context: Context, currentDay: CalendarDay) : DayViewDecorator {
        private val bgDrawable: Drawable?
        //private val textDrawable: Drawable?
        var myDay = currentDay
        override fun shouldDecorate(day: CalendarDay?): Boolean {
            return day == myDay
        }

        override fun decorate(view: DayViewFacade) {
            view.setSelectionDrawable(bgDrawable!!)
        }

        init {
            bgDrawable = ContextCompat.getDrawable(context, R.drawable.calendar_selector_today)
            //textDrawable = ContextCompat.getDrawable(context, R.drawable.text_calendar_color_select_today)
        }
    }

    override fun onBackPressed() {
//        super.onBackPressed()
    }

    companion object {
        val EDITDATE = "EDITDATE"
    }
}