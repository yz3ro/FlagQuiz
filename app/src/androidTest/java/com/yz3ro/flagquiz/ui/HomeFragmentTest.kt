package com.yz3ro.flagquiz.ui

import android.annotation.SuppressLint
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.yz3ro.flagquiz.launchFragmentInHiltContainer
import com.yz3ro.flagquiz.ui.fragments.CountryFragmentFactory
import com.yz3ro.flagquiz.ui.fragments.HomeFragment
import com.yz3ro.flagquiz.R
import com.yz3ro.flagquiz.ui.fragments.HomeFragmentDirections
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
class HomeFragmentTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: CountryFragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @SuppressLint("CheckResult")
    @Test
    fun test_navigation_from_home_to_quiz_fragment() {

        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<HomeFragment>(
            factory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.button)).perform(ViewActions.click())
        Mockito.verify(navController).navigate(
            R.id.action_homeFragment_to_quizFragment
        )
    }
}