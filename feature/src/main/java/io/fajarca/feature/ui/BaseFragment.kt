package io.fajarca.feature.ui

import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

abstract class BaseFragment<B : ViewDataBinding, V : ViewModel> : Fragment() {

   /* @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var mViewDataBinding: B
    private lateinit var mViewModel: V

    val binding: B
        get() = mViewDataBinding
    val vm: V
        get() = mViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModelClass())
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutResourceId(), container, false)
        mViewDataBinding.lifecycleOwner = this
        mViewDataBinding.executePendingBindings()
        return mViewDataBinding.root
    }*/
/*
    @LayoutRes
    abstract fun getLayoutResourceId(): Int

    abstract fun getViewModelClass(): Class<V>

    override fun onAttach(context: Context) {
        DaggerFeatureComponent.builder().build()
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun snackbar(message: String) = Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    fun longSnackbar(message: String) = Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()*/
}