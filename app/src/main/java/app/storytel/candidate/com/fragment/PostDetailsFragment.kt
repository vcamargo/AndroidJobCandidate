package app.storytel.candidate.com.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import app.storytel.candidate.com.R
import app.storytel.candidate.com.databinding.FragmentPostDetailsBinding
import app.storytel.candidate.com.repository.IRepository
import app.storytel.candidate.com.viewmodel.PostDetailsViewModel
import app.storytel.candidate.com.viewmodel.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PostDetailsFragment : Fragment() {

    companion object {
        val LOG_TAG = "PostDetailsFragment"
    }

    val args: PostDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var repository: IRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPostDetailsBinding.inflate(inflater, container, false)
        context ?: return binding.root

        subscribeUi(binding)

        // Show Back Arrow in the Toolbar
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        binding.root.findViewById<Toolbar>(R.id.toolbar)
            .setupWithNavController(navController, appBarConfiguration)

        return binding.root
    }

    private fun subscribeUi(binding: FragmentPostDetailsBinding) {
        try {
            val vm = ViewModelProvider(
                this,
                ViewModelFactory(
                    repository,
                    this
                )
            ).get(PostDetailsViewModel::class.java)

            binding.vm = vm
            binding.lifecycleOwner = this
            vm.setArgs(args)

        } catch (ex: IllegalArgumentException) {
            Log.wtf(LOG_TAG, ex)
        }
    }
}