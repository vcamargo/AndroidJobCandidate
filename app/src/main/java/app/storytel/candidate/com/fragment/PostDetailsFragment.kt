package app.storytel.candidate.com.fragment

import android.os.Bundle
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
import app.storytel.candidate.com.repository.Repository
import app.storytel.candidate.com.viewmodel.PostDetailsViewModel
import app.storytel.candidate.com.viewmodel.ViewModelFactory
import app.storytel.candidate.com.webservice.Webservice

class PostDetailsFragment : Fragment() {

    val args: PostDetailsFragmentArgs by navArgs()

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
            val vm = ViewModelProvider (
                this,
                ViewModelFactory (
                    Repository(Webservice.create()),
                    this)
            ).get(PostDetailsViewModel::class.java)

            vm.postBody.set(args.postBody)
            vm.postImageUrl.set(args.postImageUrl)

            binding.vm = vm

            vm.loadComments(args.postId)
        } catch (ex: IllegalArgumentException) {

        }
    }
}