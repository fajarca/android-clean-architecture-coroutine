package io.fajarca.web_browser


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import io.fajarca.web_browser.databinding.FragmentWebBrowserBinding

/**
 * A simple [Fragment] subclass.
 */
class WebBrowserFragment : Fragment() {

    private lateinit var binding : FragmentWebBrowserBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_web_browser, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        val url = arguments?.getString("url") ?: ""
        initWebView(url)
    }


    private fun initToolbar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.contentToolbar.toolbar)
        val appBarConfiguration = AppBarConfiguration(findNavController().graph)
        binding.contentToolbar.toolbar.setupWithNavController(findNavController(), appBarConfiguration)
    }

    private fun initWebView(url : String) {
        binding.webView.settings.loadsImagesAutomatically = true
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.domStorageEnabled = true
        binding.webView.webViewClient = webViewClient
        binding.webView.webChromeClient = chromeClient
        binding.webView.loadUrl(url)
    }

    private val webViewClient = object : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }
    private val chromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            setProgressbarProgress(newProgress)
        }
    }

    private fun setProgressbarProgress(progress: Int) {
        if (progress != 100) {
            binding.progressBar.progress = progress
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}
