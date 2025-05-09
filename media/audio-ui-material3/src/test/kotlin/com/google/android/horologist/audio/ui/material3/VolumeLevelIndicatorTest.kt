/*
 * Copyright 2025 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.horologist.audio.ui.material3

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import androidx.test.filters.MediumTest
import com.google.android.horologist.audio.VolumeState
import com.google.android.horologist.audio.ui.mapper.VolumeUiStateMapper
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@MediumTest
@RunWith(RobolectricTestRunner::class)
class VolumeLevelIndicatorTest {
    @get:Rule
    val composeTestRule = createComposeRule().apply {
        mainClock.autoAdvance = false
    }

    private var volumeState by mutableStateOf(
        VolumeState(
            current = 50,
            max = 100,
        ),
    )

    @Test
    fun testNoAutoHide() {
        composeTestRule.setContent {
            VolumeLevelIndicator(
                modifier = Modifier.testTag(TEST_TAG),
                volumeUiState = { VolumeUiStateMapper.map(volumeState = volumeState) },
            )
        }

        val levelIndicator = composeTestRule.onNodeWithTag(TEST_TAG)

        levelIndicator.assertIsDisplayed()
    }

    @Test
    fun testAutoHide() {
        composeTestRule.setContent {
            VolumeLevelIndicator(
                modifier = Modifier.testTag(TEST_TAG),
                volumeUiState = { VolumeUiStateMapper.map(volumeState = volumeState) },
                displayIndicatorEvents = flowOf(Unit),
            )
        }

        val levelIndicator = composeTestRule.onNodeWithTag(TEST_TAG)

        levelIndicator.assertDoesNotExist()

        volumeState = volumeState.copy(current = 51)

        composeTestRule.mainClock.advanceTimeByFrame()
        composeTestRule.mainClock.advanceTimeBy(500L)

        composeTestRule.onRoot(useUnmergedTree = true).printToLog("testAutoHide")

        levelIndicator.assertIsDisplayed()
    }

    companion object {
        const val TEST_TAG = "test-tag"
    }
}
