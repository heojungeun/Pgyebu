package com.example.pgyebu.Utils

import android.app.Activity
import android.content.Context
import android.text.Selection
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import com.example.pgyebu.R
import com.example.pgyebu.entity.EventCategory
import java.text.DecimalFormat

class Util {
    companion object {
        var categoryList = listOf<EventCategory>()

        fun numberStringComma(num: String): String{
            //숫자 천 단위 콤마
            val decimal = DecimalFormat("#,###")
            return decimal.format(num.toInt())
        }

        fun hideKeyboard(activity: Activity) {
            val inputManager: InputMethodManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                activity.currentFocus?.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }

        val RADIO_MAINSCREEN = mapOf(
            R.id.radio_cur_spend to "월 사용 금액",
            R.id.radio_rest_month to "월 잔액 (월 수입 - 지출)",
            R.id.radio_rest_total to "총 잔액 (총 수입 - 지출)"
        )
        val RADIO_MAINSCREEN_API = mapOf(
            R.id.radio_cur_spend to "AMOUNT",
            R.id.radio_rest_month to "MONTH_BALANCE",
            R.id.radio_rest_total to "TOTAL_BALANCE"
        )
        const val SET_MAINSCREEN_NUM = "SET_MAINSCREEN_NUM"
        const val SET_MAINSCREEN_NAME = "SET_MAINSCREEN_NAME"
        const val SET_STANDARD_NUM = "SET_STANDARD_NUM"
        const val NO_STANDARD_NUM = 1L
        const val IS_LOGIN = "IS_LOGIN"
        const val NOLOGIN = "NOLOGIN"
        const val EVENTTYPE_INCOME = "INCOME"
        const val EVENTTYPE_EXPENDITURE = "EXPENDITURE"
        const val STATUSOK = "OK"

    }
}

object IconDict{
    val icon_dict = mapOf(
        "식비" to R.drawable.ic_baseline_fastfood_35,
        "카페" to R.drawable.ic_baseline_local_cafe_35,
        "쇼핑" to R.drawable.ic_baseline_shopping_basket_35,
        "술" to R.drawable.ic_baseline_wine_bar_35,
        "문화생활" to R.drawable.ic_baseline_local_play_35,
        "애완" to R.drawable.ic_baseline_pets_35,
        "교육" to R.drawable.ic_baseline_school_35,
        "교통" to R.drawable.ic_baseline_directions_bus_35,
        "주유" to R.drawable.ic_baseline_local_gas_station_35,
        "통신" to R.drawable.ic_baseline_phone_android_35,
        "주거" to R.drawable.ic_baseline_home_work_35,
        "기타" to R.drawable.ic_baseline_category_35,
        "근로소득" to R.drawable.ic_baseline_money_35,
        "부수입" to R.drawable.ic_baseline_note_add_35,
        "기타수입" to R.drawable.ic_baseline_extension_35,
    )

}

