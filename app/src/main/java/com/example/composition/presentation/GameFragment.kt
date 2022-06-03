package com.example.composition.presentation

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.navigation.fragment.findNavController
import com.example.composition.R
import com.example.composition.databinding.FragmentGameBinding
import com.example.composition.domain.entity.GameResult
import com.example.composition.domain.entity.GameSettings
import com.example.composition.domain.entity.Level
import java.lang.RuntimeException

class GameFragment : Fragment() {

    private val viewModelFactory by lazy {
        val args = GameFragmentArgs.fromBundle(requireArguments())
        GameViewModelFactory(requireActivity().application, args.level)
    }
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]
    }

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    private fun observeViewModel() {
        viewModel.gameResult.observe(viewLifecycleOwner) {
            launchGameFinishedFragment(it)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        observeViewModel()
    }



    private fun launchGameFinishedFragment(result: GameResult) {
//        val args = Bundle().apply {
//            putParcelable(GameFinishedFragment.KEY_RESULT, result)
//        }
        findNavController().navigate(
            GameFragmentDirections.actionGameFragment2ToGameFinishedFragment2(result))

//        requireActivity().supportFragmentManager.beginTransaction()
//            .replace(R.id.main_container, GameFinishedFragment.newInstance(result))
//            .addToBackStack(null)
//            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    private fun parseArgs() {
//        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
//            level = it
//        }
//    }

    companion object {

        const val KEY_LEVEL = "level"
        const val NAME = "GameFragment"

        fun newInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_LEVEL, level)
                }
            }
        }
    }
}