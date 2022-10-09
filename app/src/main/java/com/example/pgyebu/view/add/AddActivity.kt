package com.example.pgyebu.view.add

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Selection
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import com.example.pgyebu.MyApplication
import com.example.pgyebu.R
import com.example.pgyebu.Utils.Util
import com.example.pgyebu.databinding.ActivityAddBinding
import com.example.pgyebu.entity.EventCategory
import com.example.pgyebu.view.home.HomeActivity
import java.text.DecimalFormat
import java.time.LocalDateTime

class AddActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding
    var viewModel: AddViewModel = AddViewModel()
    private lateinit var isOnlySelected: View
    private lateinit var isOnlySelected_type: View
    private lateinit var categoryDict: Map<String, Button>
    private lateinit var categorySeqDict: List<EventCategory>
    private var currentSelectDay: String = ""
    private var eventId: Long = -1L

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_add)

        setCategoryDict()
        setEvent()
        setCategorySelected()
        isIntent()

        binding.addViewModel = viewModel
    }

    private fun setCategoryDict(){
        categoryDict = mapOf(
            "식비" to binding.btnFood,
            "카페" to binding.btnCafe,
            "쇼핑" to binding.btnShop,
            "술" to binding.btnWine,
            "문화생활" to binding.btnCulture,
            "애완" to binding.btnPet,
            "교육" to binding.btnEdu,
            "교통" to binding.btnTraffic,
            "주유" to binding.btnGas,
            "통신" to binding.btnPhone,
            "주거" to binding.btnHome,
            "기타" to binding.btnSpendingEtc,
            "근로소득" to binding.btnSalary,
            "부수입" to binding.btnAdditional,
            "기타수입" to binding.btnIncomeEtc,
        )
        viewModel.getCategoryList { categories ->
            categorySeqDict = categories
        }
    }

    private fun isIntent(){
        if (intent.hasExtra(EDIT_EVENT)){
            // 기존 항목 편집
            binding.apply {
                editAddMoney.setText(intent.getStringExtra("amount"))
                if(intent.getStringExtra("eventType") == "INCOME") {
                    btnIncome.isSelected = true
                    isOnlySelected_type = btnIncome
                    categoryIncome.isVisible = true
                    categorySpend.isVisible = false
                }
                else {
                    btnSpending.isSelected = true
                    isOnlySelected_type = btnSpending
                    categoryIncome.isVisible = false
                    categorySpend.isVisible = true
                }
                editAddDesc.setText(intent.getStringExtra("content"))
                isOnlySelected = categoryDict[intent.getStringExtra("category")]!!
                categoryDict[intent.getStringExtra("category")]?.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.btn_selected_bg))
                //isSelectedBgChange(categoryDict[intent.getStringExtra("category")]!!.rootView)
            }
            eventId = intent.getLongExtra("seq", -1L)
        } else{
            // 새 항목 추가
            isOnlySelected_type = binding.btnIncome
            binding.categoryIncome.isVisible = false
            binding.categorySpend.isVisible = false
        }
        currentSelectDay = intent.getStringExtra(CREATE_EVENT).toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setEvent(){
        binding.editAddMoney.initComma()

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnIncome.setOnClickListener {
            // 버튼 눌린 상태로 유지될 수 있게
            binding.categorySpend.isVisible = false
            isSelectedBgChange_type(it, 0)
        }

        binding.btnSpending.setOnClickListener {
            binding.categoryIncome.isVisible = false
            isSelectedBgChange_type(it, 1)
        }

        viewModel.startSaveActivityEvent.observe(this) {
            if(eventId == -1L)
                setEventBefore()
            else
                editEvent()
        }

        binding.layout.setOnTouchListener { view, motionEvent ->
            Util.hideKeyboard(this)
            return@setOnTouchListener false
        }

    }

    private fun setCategorySelected(){
        // 초기값은 임시로 제일 첫번째 버튼
        isOnlySelected = binding.btnFood

        categoryDict.values.forEach { button ->
            button.setOnClickListener {
                isSelectedBgChange(it)
            }
        }
    }

    // MARK: - 이벤트 저장/수정 api 요청
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setEventBefore(){
        // 전달 파라미터 할당
        val userId = MyApplication.prefs.getString(Util.IS_LOGIN, Util.NOLOGIN)
        val amount = binding.editAddMoney.text.toString().replace(",","")
        if(!binding.btnIncome.isSelected && !binding.btnSpending.isSelected){
            Toast.makeText(this@AddActivity, "이벤트 타입을 선택해주세요.", Toast.LENGTH_SHORT).show()
        }else if(amount.isNullOrEmpty()){
            Toast.makeText(this@AddActivity, "금액을 입력해주세요.", Toast.LENGTH_SHORT).show()
        }else{
            val eventType = if(binding.btnIncome.isSelected) Util.EVENTTYPE_INCOME else Util.EVENTTYPE_EXPENDITURE
            val now = LocalDateTime.now().toString()
            currentSelectDay += now.substring(now.indexOf("T"))
            val desc = binding.editAddDesc.text.toString()
            val categorySeq = categorySeqDict.filter { it.name == (isOnlySelected as Button).text }[0].seq.toString()
                //(categoryDict.values.indexOf(isOnlySelected) + 1).toString()
            // 저장 api 전달
            viewModel.saveEvent(userId, eventType, currentSelectDay, amount, desc, categorySeq, operationSuccess = {
                val intent = Intent(this@AddActivity, HomeActivity::class.java)
                intent.putExtra(HomeActivity.EDITDATE, currentSelectDay.split("T")[0])
                startActivity(intent)
            }, operationFailure = {
                Toast.makeText(this@AddActivity, "저장 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            })
        }
    }

    private fun editEvent(){
        val amount = binding.editAddMoney.text.toString().replace(",","")
        if(!binding.btnIncome.isSelected && !binding.btnSpending.isSelected){
            Toast.makeText(this@AddActivity, "이벤트 타입을 선택해주세요.", Toast.LENGTH_SHORT).show()
        }else if(amount.isNullOrEmpty()){
            Toast.makeText(this@AddActivity, "금액을 입력해주세요.", Toast.LENGTH_SHORT).show()
        }else{
            val eventType = if(binding.btnIncome.isSelected) Util.EVENTTYPE_INCOME else Util.EVENTTYPE_EXPENDITURE
            val desc = binding.editAddDesc.text.toString()
            val categorySeq = categorySeqDict.filter { it.name == (isOnlySelected as Button).text }[0].seq.toString()
            //(categoryDict.values.indexOf(isOnlySelected) + 1).toString()
            // 저장 api 전달
            viewModel.editEvent(eventId, eventType, amount, desc, categorySeq, operationSuccess = {
                val intent = Intent(this@AddActivity, HomeActivity::class.java)
                intent.putExtra(HomeActivity.EDITDATE, currentSelectDay)
                startActivity(intent)
            }, operationFailure = {
                Toast.makeText(this@AddActivity, "저장 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            })
        }
    }

    // MARK: - 기타 함수
    private fun isSelectedBgChange(it: View) {
        // 이미 눌러진 카테고리 선택 해제
        isOnlySelected?.isSelected = false
        isOnlySelected?.setBackgroundColor(Color.TRANSPARENT)

        it.isSelected = it.isSelected != true
        if (it.isSelected) {
            it.setBackgroundColor(ContextCompat.getColor(this, R.color.btn_selected_bg))
        } else {
            it.setBackgroundColor(Color.TRANSPARENT)
        }

        isOnlySelected = it
    }

    private fun isSelectedBgChange_type(it: View, type: Int) {
        // 이미 눌러진 카테고리 선택 해제
        isOnlySelected_type?.isSelected = false
        it.isSelected = it.isSelected != true
        isOnlySelected_type = it
        if(type==0){
            // 수입
            binding.categoryIncome.isVisible = it.isSelected
        }else{
            binding.categorySpend.isVisible = it.isSelected
        }
    }

    // 돈 입력칸 콤마 추가 함수
    private fun EditText.initComma(listener: ((originNumber: Double) -> Unit)? = null) {
        var amount = ""
        this.doOnTextChanged { text, _, _, _ ->
            if (text.isNullOrBlank() || amount == text.toString())
                return@doOnTextChanged
            val removeCommaNumberString = text.toString().replace(",", "")
            amount = makeStringComma(removeCommaNumberString)
            listener?.invoke(removeCommaNumberString.toDouble())
            this.setText(amount)
            Selection.setSelection(this.text, amount.length)
        }
    }

    private fun makeStringComma(str: String): String {
        if(str.isEmpty())
            return ""
        val value = str.toLong()
        val format = DecimalFormat("###,###")
        return format.format(value)
    }

    companion object {
        val CREATE_EVENT = "CREATE_EVENT"
        val EDIT_EVENT = "EDIT_EVENT"
    }
}

