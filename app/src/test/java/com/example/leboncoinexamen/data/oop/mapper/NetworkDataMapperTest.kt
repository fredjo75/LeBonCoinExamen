package com.example.leboncoinexamen.data.oop.mapper

import com.example.leboncoinexamen.data.datasource.network.model.NetworkAlbum
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase

class NetworkDataMapperTest : TestCase() {
    private val aut = NetworkDataMapper()

    private fun makeNetworkAlbum(
        albumId: Int? = null,
        id: Int? = null,
        title: String? = null,
        url: String? = null,
        thumbnailUrl: String? = null,
    ): NetworkAlbum {
        return NetworkAlbum(
            albumId,
            id,
            title,
            url,
            thumbnailUrl
        )
    }

    fun test_map_should_replace_null_values_with_defaults() {
        val dto = makeNetworkAlbum()

        val actual = aut.map(dto)

        assertThat(actual.albumId).isEqualTo(0)
        assertThat(actual.id).isEqualTo(0)
        assertThat(actual.title).isEmpty()
        assertThat(actual.url).isEmpty()
        assertThat(actual.thumbnailUrl).isEmpty()
    }

    fun test_map_should_replace_assign_correct_values() {
        val dto = makeNetworkAlbum(
            1,
            9,
            "qui eius qui autem sed",
            "https://via.placeholder.com/600/51aa97",
            "https://via.placeholder.com/150/51aa97",
        )
        val actual = aut.map(dto)

        assertThat(actual.albumId).isEqualTo(1)
        assertThat(actual.id).isEqualTo(9)
        assertThat(actual.title).isEqualTo("qui eius qui autem sed")
        assertThat(actual.url).isEqualTo("https://via.placeholder.com/600/51aa97")
        assertThat(actual.thumbnailUrl).isEqualTo("https://via.placeholder.com/150/51aa97")
    }
}