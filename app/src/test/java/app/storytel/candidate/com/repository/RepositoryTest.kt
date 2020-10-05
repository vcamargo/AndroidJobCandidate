package app.storytel.candidate.com.repository

import app.storytel.candidate.com.model.Comment
import app.storytel.candidate.com.model.Photo
import app.storytel.candidate.com.model.PostAndPhoto
import app.storytel.candidate.com.webservice.Webservice
import io.reactivex.Observable
import io.reactivex.Single.just
import io.reactivex.observers.TestObserver
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyInt
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RepositoryTest {

    @Mock
    private lateinit var webservice: Webservice

    private lateinit var repository: IRepository

    @Test
    fun getPostsTest() {
        `when`(webservice.getPosts()).thenReturn(
            just(postsMockData)
        )

        repository = Repository(webservice)

        val postsObserver: TestObserver<List<PostAndPhoto>> = repository.getPosts().test()

        postsObserver.awaitTerminalEvent()

        postsObserver
            .assertNoErrors()
            .assertValue { it.size == postsMockData.size }
    }

    @Test
    fun getPhotosTest() {
        `when`(webservice.getPhotos()).thenReturn(
            just(photosMockData)
        )

        repository = Repository(webservice)

        val photosObserver: TestObserver<List<Photo>> = repository.getPhotos().test()

        photosObserver.awaitTerminalEvent()

        photosObserver
            .assertNoErrors()
            .assertValue { it.size == photosMockData.size }
    }

    @Test
    fun getPostAndPhotoTest() {
        `when`(webservice.getPhotos()).thenReturn(
            just(photosMockData)
        )

        `when`(webservice.getPosts()).thenReturn(
            just(postsMockData)
        )

        repository = Repository(webservice)

        val postAndPhotoObserver: TestObserver<List<PostAndPhoto>> =
            repository.getPostAndPhoto().test()

        postAndPhotoObserver.awaitTerminalEvent()

        postAndPhotoObserver
            .assertNoErrors()
            .assertValue { it.size == postsMockData.size }
            .assertValue { list ->
                Observable.fromIterable(list)
                    .map { it.thumbnailUrl }
                    .toList()
                    .blockingGet() ==
                        photosMockData.map { it.thumbnailUrl }
                            .take(postsMockData.size)
            }
    }

    @Test
    fun getCommentsTest() {
        `when`(webservice.getComments(anyInt())).thenReturn(
            just(commentsMockData)
        )

        repository = Repository(webservice)

        val commentsObserver: TestObserver<List<Comment>> = repository.getComments(anyInt()).test()

        commentsObserver.awaitTerminalEvent()

        commentsObserver
            .assertNoErrors()
            .assertValue { it.size == Repository.COMMENTS_NUMBER.toInt() }
    }
}