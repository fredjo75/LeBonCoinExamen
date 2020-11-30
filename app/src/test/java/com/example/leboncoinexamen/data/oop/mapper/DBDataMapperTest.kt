package com.example.leboncoinexamen.data.oop.mapper

import com.example.leboncoinexamen.data.datasource.db.model.DbAlbum
import com.google.common.truth.Truth
import junit.framework.TestCase


class DBDataMapperTest : TestCase() {
    private val aut = DBDataMapper()

    private fun makeDBAlbum(
        albumId: Int? = null,
        id: Int? = null,
        title: String? = null,
        url: String? = null,
        thumbnailUrl: String? = null,
    ): DbAlbum {
        return DbAlbum(
            albumId,
            id,
            title,
            url,
            thumbnailUrl
        )
    }

    fun test_map_should_replace_null_values_with_defaults() {
        val dto = makeDBAlbum()

        val actual = aut.map(dto)

        Truth.assertThat(actual.albumId).isEqualTo(0)
        Truth.assertThat(actual.id).isEqualTo(0)
        Truth.assertThat(actual.title).isEmpty()
        Truth.assertThat(actual.url).isEmpty()
        Truth.assertThat(actual.thumbnailUrl).isEmpty()
    }

    fun test_map_should_replace_assign_correct_values() {
        val dto = makeDBAlbum(
            1,
            9,
            "qui eius qui autem sed",
            "https://via.placeholder.com/600/51aa97",
            "https://via.placeholder.com/150/51aa97",
        )
        val actual = aut.map(dto)

        Truth.assertThat(actual.albumId).isEqualTo(1)
        Truth.assertThat(actual.id).isEqualTo(9)
        Truth.assertThat(actual.title).isEqualTo("qui eius qui autem sed")
        Truth.assertThat(actual.url).isEqualTo("https://via.placeholder.com/600/51aa97")
        Truth.assertThat(actual.thumbnailUrl).isEqualTo("https://via.placeholder.com/150/51aa97")
    }
}