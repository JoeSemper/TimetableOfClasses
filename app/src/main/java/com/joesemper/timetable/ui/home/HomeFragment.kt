package com.joesemper.timetable.ui.home

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.joesemper.timetable.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collect
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope
import android.view.*
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.joesemper.timetable.R
import com.joesemper.timetable.ui.home.adapters.HomeHwRvAdapter
import com.joesemper.timetable.ui.home.adapters.HomeLessonsRvAdapter
import com.joesemper.timetable.util.getCurrentLessonPosition
import com.joesemper.timetable.util.getHomeworkLessons
import com.joesemper.timetable.util.getTodayLessons


class HomeFragment : Fragment(), AndroidScopeComponent {

    override val scope: Scope by fragmentScope()
    private val viewModel: HomeViewModel by viewModel()
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private lateinit var lessonsAdapter: HomeLessonsRvAdapter
    private lateinit var homeworkAdapter: HomeHwRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home_menu, menu)
    }

    private fun initViews() {
        initToolbar()
        initCounter()
        initLessonsRV()
        initHomeworkRV()
        setInitialData()
    }

    private fun initToolbar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.homeToolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = getString(R.string.greeting)
        }
    }

    private fun initCounter() {
        lifecycleScope.launchWhenStarted {
            viewModel.remainToExamState.collect {
                binding.counterDays.text = it.days
                binding.counterHours.text = it.hours
                binding.counterMinutes.text = it.minutes
            }
        }
    }

    private fun initLessonsRV() {
        val lessons = getTodayLessons(viewModel.currentClasses)
        val currentLessonPosition = getCurrentLessonPosition(lessons)
        lessonsAdapter = HomeLessonsRvAdapter(
            data = lessons,
            onClick = {
                startSkype()
            },
            currentPosition = currentLessonPosition
        )

        binding.rvLessons.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = lessonsAdapter
            scrollToPosition(currentLessonPosition)
        }
    }

    private fun initHomeworkRV() {
        val homework = getHomeworkLessons(viewModel.currentClasses)
        homeworkAdapter = HomeHwRvAdapter(homework)
        binding.rvHw.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = homeworkAdapter
        }
    }

    private fun setInitialData() {
        "${getTodayLessons(viewModel.currentClasses).size} lessons today".also {
            binding.classesToday.text = it
        }
    }

    private fun startSkype() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setPackage("com.skype.android")
        try {
            startActivity(intent);
        } catch (e: ActivityNotFoundException) {
            try {
                val unrestrictedIntent = Intent(Intent.ACTION_VIEW)
                startActivity(unrestrictedIntent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(context, "Please install Skype application", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}