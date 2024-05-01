package com.yz3ro.flagquiz.ui

import android.widget.Button
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.yz3ro.flagquiz.data.datasource.FlagDataSource
import com.yz3ro.flagquiz.data.repo.FlagRepository
import com.yz3ro.flagquiz.ui.fragments.CountryFragmentFactory
import com.yz3ro.flagquiz.ui.fragments.QuizFragment
import com.yz3ro.flagquiz.ui.viewModels.QuizViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
class QuizFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @Inject
    lateinit var fragmentFactory: CountryFragmentFactory
    @Before
    fun setup() {
        hiltRule.inject()
    }
}