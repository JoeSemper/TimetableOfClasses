package com.joesemper.timetable.ui.classes

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.joesemper.timetable.R
import com.joesemper.timetable.databinding.FragmentClassesBinding
import com.joesemper.timetable.ui.classes.adapters.ClassesRvAdapter
import com.joesemper.timetable.util.getCurrentDate
import com.joesemper.timetable.util.getCurrentLessonPosition
import com.joesemper.timetable.util.getTodayLessons
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope

class ClassesFragment : Fragment(), AndroidScopeComponent {

    override val scope: Scope by fragmentScope()
    private val viewModel: ClassesViewModel by viewModel()

    private var _binding: FragmentClassesBinding? = null
    private val binding get() = _binding!!

    private lateinit var classesAdapter: ClassesRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClassesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.classes_menu, menu)
    }

    private fun initViews() {
        initToolbar()
        initLessonsRV()
    }

    private fun initToolbar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.lessonsToolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = getString(R.string.classes)
            subtitle = getString(R.string.today) + ", " + getCurrentDate()
        }
    }

    private fun initLessonsRV() {
        val lessons = viewModel.currentClasses
        val currentLessonPosition = getCurrentLessonPosition(lessons)
        classesAdapter = ClassesRvAdapter(
            data = lessons,
            onClick = {
                startSkype()
            },
            currentPosition = currentLessonPosition
        )
        binding.rvClasses.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = classesAdapter
            scrollToPosition(currentLessonPosition)
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