package com.joesemper.timetable.ui.home

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
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joesemper.timetable.R
import com.joesemper.timetable.ui.classes.adapters.HomeLessonsRVAdapter
import com.joesemper.timetable.util.currentLessonPosition
import com.joesemper.timetable.util.getTodayLessons


class HomeFragment : Fragment(), AndroidScopeComponent {

    override val scope: Scope by fragmentScope()
    private val viewModel: HomeViewModel by viewModel()
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private lateinit var lessonsAdapter: HomeLessonsRVAdapter

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
        setInitialData()
    }

    private fun initToolbar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.homeToolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setTitle(R.string.title_home)
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
        lessonsAdapter = HomeLessonsRVAdapter(lessons)
        binding.rvLessons.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = lessonsAdapter
            scrollToPosition(currentLessonPosition(lessons))
        }
    }

    private fun setInitialData() {
        "${getTodayLessons(viewModel.currentClasses).size} lessons today".also {
            binding.classesToday.text = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}