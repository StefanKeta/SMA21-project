package com.example.licenta.activity.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.licenta.R
import com.example.licenta.util.Util
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var parentLayout: LinearLayout
    private lateinit var scrollView: ScrollView
    private lateinit var firstNameLayout: TextInputLayout
    private lateinit var firstNameET: TextInputEditText
    private lateinit var lastNameLayout: TextInputLayout
    private lateinit var lastNameET: EditText
    private lateinit var emailLayout: TextInputLayout
    private lateinit var emailET: EditText
    private lateinit var dobLayout: TextInputLayout
    private lateinit var dobET: EditText
    private lateinit var genderDropdown: AutoCompleteTextView
    private lateinit var passwordLayout: TextInputLayout
    private lateinit var passwordET: EditText
    private lateinit var confirmPasswordLayout: TextInputLayout
    private lateinit var confirmPasswordET: EditText
    private lateinit var heightLayout: TextInputLayout
    private lateinit var heightET: EditText
    private lateinit var weightLayout: TextInputLayout
    private lateinit var weightET: EditText
    private lateinit var heightGroup: MaterialButtonToggleGroup
    private lateinit var heightCmBtn: Button
    private lateinit var heightFeetBtn: Button
    private lateinit var weightGroup: MaterialButtonToggleGroup
    private lateinit var weightKgBtn: Button
    private lateinit var weightLbsBtn: Button
    private lateinit var signUpBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()
        initComponents()
    }

    private fun initComponents() {
        parentLayout = findViewById(R.id.activity_register_parent_linear_layout)
        parentLayout.setOnClickListener(this)
        scrollView = findViewById(R.id.activity_register_scroll_view)
        scrollView.setOnClickListener(this)
        firstNameLayout = findViewById(R.id.activity_register_first_name_layout)
        firstNameET = findViewById(R.id.activity_register_first_name_et)
        lastNameLayout = findViewById(R.id.activity_register_last_name_layout)
        lastNameET = findViewById(R.id.activity_register_last_name_et)
        emailLayout = findViewById(R.id.activity_register_email_layout)
        emailET = findViewById(R.id.activity_register_email_et)
        dobLayout = findViewById(R.id.activity_register_dob_layout)
        dobET = findViewById(R.id.activity_register_dob_et)
        passwordLayout = findViewById(R.id.activity_register_password_layout)
        passwordET = findViewById(R.id.activity_register_password_et)
        confirmPasswordLayout = findViewById(R.id.activity_register_confirm_password_layout)
        confirmPasswordET = findViewById(R.id.activity_register_confirm_password_et)
        heightLayout = findViewById(R.id.activity_register_height_layout)
        heightET = findViewById(R.id.activity_register_height_et)
        heightGroup = findViewById(R.id.activity_register_height_tbg)
        heightCmBtn = findViewById(R.id.activity_register_cm_btn)
        heightFeetBtn = findViewById(R.id.activity_register_feet_btn)
        weightLayout = findViewById(R.id.activity_register_weight_layout)
        weightET = findViewById(R.id.activity_register_weight_et)
        weightGroup = findViewById(R.id.activity_register_weight_tbg)
        weightKgBtn = findViewById(R.id.activity_register_kg_btn)
        weightLbsBtn = findViewById(R.id.activity_register_lbs_btn)
        signUpBtn = findViewById(R.id.activity_register_sign_up_btn)
        signUpBtn.setOnClickListener(this)
        setUpDropdown()
    }

    private fun setUpDropdown() {
        genderDropdown = findViewById(R.id.activity_register_gender_dropdown)
        genderDropdown.setOnClickListener(this)
        val list = listOf("M", "F")
        genderDropdown.setText(list[0])
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        genderDropdown.setAdapter(adapter)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.activity_register_parent_linear_layout -> Util.hideKeyboard(this)
            R.id.activity_register_gender_dropdown -> Util.hideKeyboard(this)
            R.id.activity_register_scroll_view -> Util.hideKeyboard(this)
            R.id.activity_register_cm_btn -> heightET.hint =
                "${getString(R.string.activity_register_height_et)} (cm)"
            R.id.activity_register_feet_btn -> heightET.hint =
                "${getString(R.string.activity_register_height_et)} (ft.inch)"
            R.id.activity_register_sign_up_btn -> registerUser()
        }
    }

    private fun registerUser() {
        if (!validateNames() ||
            !validateEmail() ||
            !validatePassword(passwordET) ||
            !validatePassword(confirmPasswordET) ||
            !arePasswordsEqual() ||
            !dobET.text.isNotEmpty() ||
            !isHeightValid() ||
            !isWeightValid()
        ) {
            //do nothing
        } else {
            //register error
            startActivity(
                Intent(
                    this@RegisterActivity,
                    LoginActivity::class.java
                )
            )
        }
    }

    private fun validateNames(): Boolean {
        if (firstNameET.text!!.isNotEmpty() && lastNameET.text!!.isNotEmpty()) {
            return true
        }
        return if (firstNameET.text!!.isEmpty()) {
            firstNameLayout.error = getString(R.string.activity_register_error_required_field)
            false
        } else {
            lastNameLayout.error = getString(R.string.activity_register_error_required_field)
            false
        }
    }


    private fun validateEmail(): Boolean {
        val email = emailET.text
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLayout.error = getString(R.string.activity_register_invalid_email_error)
            return false
        }
        return true
    }

    private fun validatePassword(password: EditText): Boolean {
        if (password.text.length < 8) {
            if (password.id == R.id.activity_register_password_et)
                passwordLayout.error =
                    getString(R.string.activity_register_error_password_invalid_length)
            else
                confirmPasswordLayout.error =
                    getString(R.string.activity_register_error_password_invalid_length)
            return false
        }
        return true
    }

    private fun arePasswordsEqual(): Boolean {
        val password = passwordET.text.toString()
        val confirmedPassword = confirmPasswordET.text.toString()
        return password == confirmedPassword
    }

    private fun isHeightValid(): Boolean {
        if (heightCmBtn.isSelected) {
            val height = if (heightET.text.isNotEmpty()) heightET.text.toString().toDouble() else 0
            return checkIfTooSmallOrTooTall(height.toDouble())
        } else {
            val heightList = heightET.text.split(".")
            return if (heightList.size > 2 || heightList[0].toDouble() > 8 || (heightList.size == 2 && heightList[1].toDouble() > 11)) {
                heightLayout.error = getString(R.string.activity_register_error_height_invalid)
                false
            } else if (heightList.size == 1) {
                val height = Util.convertFeetInchesToCm(heightList[0].toInt())
                checkIfTooSmallOrTooTall(height)
            } else {
                val feet = heightList[0].toInt()
                val inch = heightList[1].toInt()
                val height = Util.convertFeetInchesToCm(feet, inch)
                checkIfTooSmallOrTooTall(height)
            }
        }
    }

    private fun checkIfTooSmallOrTooTall(height: Double): Boolean {
        if (height < Util.MINIMUM_HEIGHT_CM || height > Util.MAXIMUM_HEIGHT_CM) {
            heightLayout.error = getString(R.string.activity_register_error_height_invalid)
            return false
        }
        return true
    }

    private fun isWeightValid(): Boolean {
        if (weightKgBtn.isSelected) {
            if (weightET.text.isEmpty() || weightET.text.toString()
                    .toDouble() < Util.MINIMUM_WEIGHT_KG
            ) {
                weightLayout.error = getString(R.string.activity_register_error_weight_invalid)
                return false
            }
        } else {
            val lbs = weightET.text.toString().toInt().or(0)
            val kg = Util.convertLbsToKg(lbs)
            if (weightET.text.isEmpty() || weightET.text.contains(".") || kg > Util.MINIMUM_WEIGHT_KG) {
                weightLayout.error = getString(R.string.activity_register_error_weight_invalid)
                return false
            }
        }
        return true
    }


}