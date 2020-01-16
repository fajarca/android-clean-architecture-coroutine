package io.fajarca.uiview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout

class UiStateView : ConstraintLayout {

    private lateinit var tvStatus: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var ivStatus: ImageView
    private lateinit var btnRetry: Button
    private var retryAction : () -> Unit = {}

    private var initialText: String? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        getAttributes(attrs)
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        getAttributes(attrs)
        init()
    }


    private fun init() {
        val view = View.inflate(context, R.layout.layout_ui_state_view, this)
        tvStatus = view.findViewById(R.id.tvStatus)
        ivStatus = view.findViewById(R.id.ivStatus)
        progressBar = view.findViewById(R.id.progressBar)
        btnRetry = view.findViewById(R.id.btnRetry)
        btnRetry.setOnClickListener {
            retryAction()
        }
        
        showLoading(initialText ?: "Loading. Please wait")
    }

    fun showLoading(loadingMessage : String = "Loading. Please wait") {
        showProgressBar()
        showTextView(loadingMessage)
        hideImageView()
        hideRetryButton()
    }

    fun showEmptyData(emptyMessage: String = "No data found", emptyImageDrawable : Int = R.drawable.ic_no_data) {
        showTextView(emptyMessage)
        showImageView(emptyImageDrawable)
        hideProgressBar()
        hideRetryButton()
    }

    fun showError(errorMessage: String, errorImageDrawable : Int = R.drawable.ic_error, retryAction : () -> Unit = {}) {
        showTextView(errorMessage)
        showImageView(errorImageDrawable)
        hideProgressBar()
        showRetryButton()
        this.retryAction = retryAction
    }

    fun showNoInternetConnection(noConnectionMessage : String = "No internet connection", noConnectionImageDrawable : Int = R.drawable.ic_no_connection, retryAction : () -> Unit = {}) {
        showTextView(noConnectionMessage)
        showImageView(noConnectionImageDrawable)
        hideProgressBar()
        showRetryButton()
        this.retryAction = retryAction
    }

    fun dismiss() {
        hideProgressBar()
        hideTextView()
        hideImageView()
    }


    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    private fun showTextView(initialText: String = "") {
        tvStatus.visibility = View.VISIBLE
        tvStatus.text = initialText
    }

    private fun hideTextView() {
        tvStatus.visibility = View.GONE
    }

    private fun showImageView(initialDrawable: Int) {
        ivStatus.visibility = View.VISIBLE
        ivStatus.setImageResource(initialDrawable)
    }

    private fun hideImageView() {
        ivStatus.visibility = View.GONE
    }

    private fun getAttributes(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.UiStateView)

        initialText = typedArray.getString(R.styleable.UiStateView_text)

        typedArray.recycle()
    }

    private fun showRetryButton() {
        btnRetry.visibility = View.VISIBLE
    }

    private fun hideRetryButton() {
        btnRetry.visibility = View.GONE
    }

}