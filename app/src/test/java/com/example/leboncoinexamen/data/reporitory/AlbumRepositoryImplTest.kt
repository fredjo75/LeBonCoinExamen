package com.example.leboncoinexamen.data.reporitory

import com.example.leboncoinexamen.data.datasource.db.AlbumDatabaseDOA
import com.example.leboncoinexamen.data.datasource.db.model.DbAlbum
import com.example.leboncoinexamen.data.datasource.network.model.NetworkAlbum
import com.example.leboncoinexamen.data.network.AlbumApiService
import com.example.leboncoinexamen.data.oop.mapper.Mapper
import com.example.leboncoinexamen.domain.model.Album
import com.nhaarman.mockitokotlin2.mock
import junit.framework.TestCase
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import retrofit2.Call


class AlbumRepositoryImplTest : TestCase() {


    private val albumDao: AlbumDatabaseDOA = mock()
    private val albumApiService: AlbumApiService = mock()
    private val dbMapper: Mapper<DbAlbum, Album> = mock()
    private val networkMapper: Mapper<NetworkAlbum, DbAlbum> = mock()

    @Captor
    private val callbackArgumentCaptor: ArgumentCaptor<Call<List<NetworkAlbum>>>? = null

    private val sut = AlbumRepositoryImpl(albumDao, albumApiService, dbMapper, networkMapper)

/*    fun test_allAlbums_should_return_albums_when_albumDao_returns() {
        val networkAlbum = NetworkAlbum(null, null, null, null, null)
        val dbAlbum = DbAlbum(0, 0, "", "", "")

        whenever(albumApiService.getAlbums())
            .thenReturn(callbackArgumentCaptor?.capture())

        whenever(albumApiService.getAlbums())
            .thenReturn(Single.just(Result.Success(networkAlbum)))
        Mockito.verify(albumApiService)
            .getAlbums(callbackArgumentCaptor.capture());

        Mockito.verify(albumApiService)
            .getAlbums()
        val objectsQuantity = 10
        val list: MutableList<NetworkAlbum> = ArrayList<NetworkAlbum>()
        for (i in 0 until objectsQuantity) {
            list.add(NetworkAlbum(null, i, null, null, null))
        }

        callbackArgumentCaptor!!.value.succe(list, null)
        whenever(networkMapper.map(networkAlbum)).thenReturn(dbAlbum)

        val testObserver = sut.getAlbumById(ALBUM_ID).test()

        testObserver.assertValue(Result.Success(album))
    }*/

    /*fun test_allAlbums_should_return_albums_when_albumDao_returns() {
    ResolveResponse resolveResponse = gson
    .fromJson(Utils.getRawResourceAsString(com.sensorberg.sdk.test.R.raw.resolve_response_enter_exit_action, InstrumentationRegistry
    .getContext()), ResolveResponse.class);
    Mockito.when(mockRetrofitApiService.getBeacon(Mockito.anyString(), Mockito.anyString(), Matchers.<TreeMap<String, String>>any()))
    .thenReturn(Calls.response(resolveResponse));

    ResolverListener testListener = new ResolverListener() {
        @Override
        public void onResolutionFailed(Throwable cause, ScanEvent scanEvent) {
            Assert.fail(cause.getMessage());
        }

        @Override
        public void onResolutionsFinished(List<BeaconEvent> events) {
            Assertions.assertThat(events).hasSize(1);
        }
    };

    tested.setListener(testListener);
    tested.resolve(TestConstants.RESOLVABLE_ENTRY_EVENT_WITH_ID_3);
    }
    */
    /* @Test
     fun `getAlbumById should return unmapped Failure when apiService call is not successful`() {
         val throwable = Throwable()
         whenever(albumDao.getAll())
             .thenReturn(Single.just(Result.Failure(throwable)))

         val testObserver = sut.getAlbumById(ALBUM_ID).test()

         testObserver.assertValue(Result.Failure(throwable))
     }*/

    private companion object {
        const val ALBUM_ID = "id"
    }

    fun testGetAllAlbums() {}

    fun testFetchAlbums() {}

    fun testGetAlbumDao() {}

    fun testSetAlbumDao() {}

    fun testGetAlbumApiService() {}

    fun testSetAlbumApiService() {}

    fun testGetDbMapper() {}

    fun testSetDbMapper() {}

    fun testGetNetworkMapper() {}

    fun testSetNetworkMapper() {}
}