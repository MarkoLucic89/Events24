package com.markolucic.cubes.events24.ui.view

import android.content.Context
import android.widget.LinearLayout
import android.widget.EditText
import android.content.res.TypedArray
import com.markolucic.cubes.events24.R
import android.view.Gravity
import android.text.TextWatcher
import android.text.Editable
import android.text.InputType
import com.markolucic.cubes.events24.ui.view.CustomEditText
import androidx.appcompat.widget.LinearLayoutCompat
import android.view.ViewGroup
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Patterns
import android.view.View
import android.widget.ImageView

class CustomEditText : LinearLayout {
    private var linearLayoutEditText: LinearLayout? = null
    private var viewLine: View? = null
    private var imageView1: ImageView? = null
    private var editText: EditText? = null
    private var imageView2: ImageView? = null

    constructor(context: Context?) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomEditText)
        setupLayout(typedArray)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context,
        attrs,
        defStyleAttr) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomEditText)
        setupLayout(typedArray)
    }

    companion object {
        const val inputTypeText = InputType.TYPE_CLASS_TEXT
        const val inputTypeEmail =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        const val inputTypePasswordVisible =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        const val inputTypePasswordInvisible =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        const val inputTypeName =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PERSON_NAME

        fun isValidEmail(target: CharSequence?): Boolean {
            return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }

    private fun setupLayout(typedArray: TypedArray) {
        orientation = VERTICAL
        gravity = Gravity.CENTER_HORIZONTAL
        createLinearLayoutEditText()
        createImageview1()
        createEditText()
        createImageview2()
        createViewLine()
        setupViews(typedArray)
    }

    private fun createLinearLayoutEditText() {

        //create EditText with images
        linearLayoutEditText = LinearLayout(context)
        linearLayoutEditText!!.orientation = HORIZONTAL
        linearLayoutEditText!!.gravity = Gravity.CENTER_VERTICAL
        linearLayoutEditText!!.setPadding(0, 0, 0, 32)
    }

    private fun setupViews(typedArray: TypedArray) {


        //SET TYPE
        when (typedArray.getInt(R.styleable.CustomEditText_android_inputType, 0)) {
            inputTypeEmail -> setViewTypeEmail(inputTypeEmail)
            inputTypePasswordInvisible -> setViewTypePasswordInvisible(inputTypePasswordVisible,
                inputTypePasswordInvisible)
            inputTypePasswordVisible -> setViewTypePasswordVisible(inputTypePasswordVisible,
                inputTypePasswordInvisible)
            inputTypeName -> setViewTypeName(inputTypeName)
            else -> setViewTypeText(inputTypeText, typedArray)
        }

        //SET TEXT

        val text = typedArray.getString(R.styleable.CustomEditText_android_text);

        if (text != null) {
            setText(text);
        }

        //SET HINT

        val hint = typedArray.getString(R.styleable.CustomEditText_android_hint);

        if (hint != null) {
            setHint(hint);
        }

        // IS ENABLED?

        val isEnabled = typedArray.getBoolean(R.styleable.CustomEditText_android_enabled, true);
        setViewEnabled(isEnabled);

        //SET COLOR
        val color = typedArray.getColor(R.styleable.CustomEditText_android_color, 0)
        if (color != 0) {
            setViewColor(color)
        }
    }

    fun getText(): Editable = editText!!.text

    fun setText(text: String?) {
        editText?.setText(text)
    }

    fun setHint(hint: String?) {
        editText?.hint = hint
    }

    fun setViewEnabled(enabled: Boolean) {
        imageView1?.isEnabled = isEnabled
        editText?.isEnabled = isEnabled
        imageView2?.isEnabled = isEnabled
        if (isEnabled) {
            setViewColor(resources.getColor(R.color.white));
        } else {
            setViewColor(resources.getColor(R.color.purple_light));
        }
    }

    fun setViewColor(color: Int) {
        imageView1!!.setColorFilter(color)
        imageView2!!.setColorFilter(color)
        viewLine!!.setBackgroundColor(color)
        editText!!.setTextColor(color)
        val white = resources.getColor(R.color.white)
        val purple = resources.getColor(R.color.purple_light)
        when (color) {
            white -> editText!!.setHintTextColor(resources.getColor(R.color.white_hint))
            purple -> editText!!.setHintTextColor(resources.getColor(R.color.purple_hint))
            else -> editText!!.setHintTextColor(color)
        }
    }

    fun setImageResource(imageResource: Int) {
        imageView1?.setImageResource(imageResource)
    }

    private fun setViewTypeText(inputTypeText: Int, typedArray: TypedArray) {
        setViewColor(resources.getColor(R.color.purple_light))
        val image = typedArray.getResourceId(R.styleable.CustomEditText_android_src, 0)
        editText!!.inputType = inputTypeText
        imageView1!!.setImageResource(image)
    }

    private fun setViewTypeName(inputTypeName: Int) {
        setViewColor(resources.getColor(R.color.purple_light))
        editText!!.inputType = inputTypeName
        imageView1!!.setImageResource(R.drawable.ic_person)
        editText!!.hint = context.getString(R.string.name)
        imageView2!!.visibility = GONE
    }

    private fun setViewTypePasswordVisible(
        inputTypePasswordVisible: Int,
        inputTypePasswordInvisible: Int,
    ) {
        setViewColor(resources.getColor(R.color.purple_light))
        imageView1!!.setImageResource(R.drawable.ic_lock)
        editText!!.inputType = inputTypePasswordVisible
        editText!!.hint = context.getString(R.string.password)
        imageView2!!.setImageResource(R.drawable.ic_visibility_on)
        imageView2!!.setOnClickListener { view: View? ->
            if (editText!!.inputType == inputTypePasswordInvisible) {
                editText!!.inputType = inputTypePasswordVisible
                imageView2!!.setImageResource(R.drawable.ic_visibility_on)
            } else {
                editText!!.inputType = inputTypePasswordInvisible
                imageView2!!.setImageResource(R.drawable.ic_visibility_off)
            }
        }
    }

    private fun setViewTypePasswordInvisible(
        inputTypePasswordVisible: Int,
        inputTypePasswordInvisible: Int,
    ) {
        setViewColor(resources.getColor(R.color.purple_light))
        imageView1!!.setImageResource(R.drawable.ic_lock)
        editText!!.inputType = inputTypePasswordInvisible
        editText!!.hint = context.getString(R.string.password)
        imageView2!!.setImageResource(R.drawable.ic_visibility_off)
        imageView2!!.setOnClickListener { view: View? ->
            if (editText!!.inputType == inputTypePasswordInvisible) {
                editText!!.inputType = inputTypePasswordVisible
                imageView2!!.setImageResource(R.drawable.ic_visibility_on)
            } else {
                editText!!.inputType = inputTypePasswordInvisible
                imageView2!!.setImageResource(R.drawable.ic_visibility_off)
            }
        }
    }

    private fun setViewTypeEmail(inputTypeEmail: Int) {
        setViewColor(resources.getColor(R.color.white))
        imageView1!!.setImageResource(R.drawable.ic_mail)
        editText!!.inputType = inputTypeEmail
        editText!!.hint = context.getString(R.string.email)
        imageView2!!.setImageResource(R.drawable.ic_check)

        //TextChangedListener
        editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (isValidEmail(s)) {
                    imageView2!!.setColorFilter(resources.getColor(R.color.green))
                } else {
                    imageView2!!.setColorFilter(resources.getColor(R.color.white))
                }
            }
        })
    }

    private fun createImageview1() {
        imageView1 = ImageView(context)
        imageView1!!.layoutParams = LinearLayoutCompat.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        linearLayoutEditText!!.addView(imageView1)
    }

    private fun createEditText() {
        editText = EditText(context)
        editText!!.layoutParams = LinearLayoutCompat.LayoutParams(
            0,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            1F
        )
        editText!!.gravity = Gravity.CENTER_VERTICAL
        editText!!.setPadding(16, 0, 16, 0)
        editText!!.setBackgroundColor(resources.getColor(R.color.transparent))
        linearLayoutEditText!!.addView(editText)
    }

    private fun createImageview2() {
        imageView2 = ImageView(context)
        imageView2!!.layoutParams = LinearLayoutCompat.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        linearLayoutEditText!!.addView(imageView2)
        addView(linearLayoutEditText)
    }

    private fun createViewLine() {
        viewLine = View(context)
        viewLine!!.layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            4
        )
        addView(viewLine)
    }


}