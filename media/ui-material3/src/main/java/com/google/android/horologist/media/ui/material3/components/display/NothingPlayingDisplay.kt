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

package com.google.android.horologist.media.ui.material3.components.display

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.google.android.horologist.media.ui.material3.util.TRACK_SUBTITLE_HEIGHT
import com.google.android.horologist.media.ui.material3.util.TRACK_TITLE_HEIGHT
import com.google.android.horologist.media.ui.model.R

/**
 * A media display indicating nothing is playing.
 */
@Composable
public fun NothingPlayingDisplay(modifier: Modifier = Modifier) {
    val height = TRACK_TITLE_HEIGHT + TRACK_SUBTITLE_HEIGHT
    MessageMediaDisplay(
        message = stringResource(R.string.horologist_nothing_playing),
        modifier = modifier.height(height),
    )
}
