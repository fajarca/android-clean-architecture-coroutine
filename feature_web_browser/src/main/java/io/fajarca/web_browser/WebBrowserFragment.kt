package io.fajarca.web_browser


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import io.fajarca.navigation.Origin
import io.fajarca.web_browser.databinding.FragmentWebBrowserBinding

/**
 * A simple [Fragment] subclass.
 */
class WebBrowserFragment : Fragment() {

    private lateinit var binding : FragmentWebBrowserBinding
    private val args : WebBrowserFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_web_browser, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val category = args.category
        val url = args.url
        val origin = args.origin
        initToolbar(origin, category)
        initWebView(url)
    }


    private fun initToolbar(origin: Origin, category: String) {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.contentToolbar.toolbar)
        val appBarConfiguration = AppBarConfiguration(findNavController().graph)
        binding.contentToolbar.toolbar.setupWithNavController(findNavController(), appBarConfiguration)
        when(origin) {
            Origin.NEWS -> binding.contentToolbar.toolbar.title = "Detail"
            Origin.CHANNEL -> binding.contentToolbar.toolbar.title = category
            Origin.CATEGORY -> binding.contentToolbar.toolbar.title = category
        }
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
