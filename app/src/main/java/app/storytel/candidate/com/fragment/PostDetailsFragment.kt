package app.storytel.candidate.com.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import app.storytel.candidate.com.R
import app.storytel.candidate.com.databinding.FragmentPostDetailsBinding
import app.storytel.candidate.com.repository.Repository
import app.storytel.candidate.com.viewmodel.PostDetailsViewModel
import app.storytel.candidate.com.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_post_details.*

class PostDetailsFragment : Fragment() {

    val args: PostDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPostDetailsBinding.inflate(inflater, container, false)
        context ?: return binding.root

        subscribeUi(binding)

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