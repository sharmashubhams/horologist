/*
 * Copyright 2022 The Android Open Source Project
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

package com.google.android.horologist.media.data.mapper

import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.media.database.model.MediaDownloadEntity
import com.google.android.horologist.media.database.model.PopulatedPlaylist
import com.google.android.horologist.media.model.PlaylistDownload

/**
 * Functions to map models from other layers and / or packages into a [PlaylistDownload].
 */
@ExperimentalHorologistApi
public class PlaylistDownloadMapper(
    private val playlistMapper: PlaylistMapper,
) {

    /**
     * Maps from a [PopulatedPlaylist] and a list of [MediaDownloadEntity].
     */
    public fun map(
        populatedPlaylist: PopulatedPlaylist,
        mediaDownloadEntity: List<MediaDownloadEntity>,
    ): PlaylistDownload {
        val playlist = playlistMapper.map(populatedPlaylist)
        return PlaylistDownload(
            playlist = playlist,
            mediaList = MediaDownloadMapper.map(playlist, mediaDownloadEntity),
        )
    }
}
