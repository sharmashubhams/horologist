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

package com.google.android.horologist.media.ui.material3.screens.entity

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.ButtonDefaults
import androidx.wear.compose.material3.FilledTonalButton
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.IconButton
import androidx.wear.compose.material3.Text
import androidx.wear.compose.material3.rememberPlaceholderState
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.images.coil.CoilPaintable
import com.google.android.horologist.media.ui.material3.composables.PlaceholderButton
import com.google.android.horologist.media.ui.model.R
import com.google.android.horologist.media.ui.state.model.DownloadMediaUiModel
import com.google.android.horologist.media.ui.state.model.PlaylistUiModel

/**
 * An implementation of [EntityScreen] using [PlaylistUiModel] and [DownloadMediaUiModel] as
 * models.
 */
@ExperimentalHorologistApi
@Composable
public fun PlaylistStreamingScreen(
    playlistName: String,
    playlistDownloadScreenState: PlaylistDownloadScreenState<PlaylistUiModel, DownloadMediaUiModel>,
    onShuffleButtonClick: () -> Unit,
    onPlayButtonClick: () -> Unit,
    onPlayItemClick: (DownloadMediaUiModel) -> Unit,
    modifier: Modifier = Modifier,
    defaultMediaTitle: String = "",
) {
    val entityScreenState: EntityScreenState<DownloadMediaUiModel> =
        when (playlistDownloadScreenState) {
            PlaylistDownloadScreenState.Loading -> EntityScreenState.Loading
            is PlaylistDownloadScreenState.Loaded -> EntityScreenState.Loaded(
                playlistDownloadScreenState.mediaList,
            )

            PlaylistDownloadScreenState.Failed -> EntityScreenState.Failed
        }

    // TODO This should be folded into SectionedList
    val placeholderState =
        rememberPlaceholderState(entityScreenState is EntityScreenState.Loading)

    EntityScreen(
        entityScreenState = entityScreenState,
        headerContent = { DefaultEntityScreenHeader(title = playlistName) },
        loadingContent = {
            items(count = 2) {
                PlaceholderButton(
                    colors = ButtonDefaults.filledTonalButtonColors(),
                    placeholderState = placeholderState,
                    secondaryLabel = false,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        },
        mediaContent = { mediaUiModel ->
            val mediaTitle = mediaUiModel.title ?: defaultMediaTitle
            FilledTonalButton(
                label = { Text(mediaTitle) },
                onClick = { onPlayItemClick(mediaUiModel) },
                icon = {
                    Icon(
                        painter = CoilPaintable(mediaUiModel.artworkUri).rememberPainter(),
                        contentDescription = null,
                    )
                },
            )
        },
        modifier = modifier,
        buttonsContent = {
            Row(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .height(52.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = { onShuffleButtonClick() },
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .weight(weight = 0.3F, fill = false),
                ) {
                    Icon(
                        imageVector = Icons.Default.Shuffle,
                        contentDescription = stringResource(id = R.string.horologist_playlist_download_button_shuffle_content_description),
                    )
                }

                IconButton(
                    onClick = { onPlayButtonClick() },
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .weight(weight = 0.3F, fill = false),
                ) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = stringResource(id = R.string.horologist_playlist_download_button_play_content_description),
                    )
                }
            }
        },
    )
}
