package app.storytel.candidate.com.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import app.storytel.candidate.com.R
import app.storytel.candidate.com.databinding.FragmentPostDetailsBinding

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

            binding.postBody = args.postBody
            binding.postImageUrl = args.postImageUrl

        } catch (ex: IllegalArgumentException) {

        }
    }
}