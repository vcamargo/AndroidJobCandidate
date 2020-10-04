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

    val postsList = listOf(
        PostAndPhoto(
            1, "sunt aut facere repellat provident",
            "quia et suscipit nsuscipit recusandae consequuntur", ""
        ),
        PostAndPhoto(
            2, "sunt aut facere repellat provident",
            "quia et suscipit nsuscipit recusandae consequuntur", ""
        ),
        PostAndPhoto(
            3, "sunt aut facere repellat provident",
            "quia et suscipit nsuscipit recusandae consequuntur", ""
        )
    )

    val photosList = listOf(
        Photo(
            1, 1, "accusamus beatae",
            "https://via.placeholder.com/600/92c952",
            "https://via.placeholder.com/150/92c952"
        ),
        Photo(
            1, 2, "accusamus beatae",
            "https://via.placeholder.com/600/771796",
            "https://via.placeholder.com/150/771796"
        ),
        Photo(
            1, 3, "accusamus beatae",
            "https://via.placeholder.com/600/24f355",
            "https://via.placeholder.com/150/24f355"
        ),
        Photo(
            1, 4, "accusamus beatae",
            "https://via.placeholder.com/600/d32776",
            "https://via.placeholder.com/150/d32776"
        ),
        Photo(
            1, 5, "accusamus beatae",
            "https://via.placeholder.com/600/f66b97",
            "https://via.placeholder.com/150/f66b97"
        )
    )

    val postComments = listOf(
        Comment(
            1, 1, "id labore ex",
            "Eliseo@gardner.biz", "laudantium enim quasi est"
        ),
        Comment(
            1, 2, "id labore ex",
            "Jayne_Kuhic@sydney.com", "laudantium enim quasi est"
        ),
        Comment(
            1, 3, "id labore ex",
            "Nikita@garfield.biz", "laudantium enim quasi est"
        ),
        Comment(
            1, 4, "id labore ex",
            "Lew@alysha.tv", "laudantium enim quasi est"
        ),
        Comment(
            1, 5, "id labore ex",
            "Hayden@althea.biz", "laudantium enim quasi est"
        )
    )

    @Test
    fun getPostsTest() {
        `when`(webservice.getPosts()).thenReturn(
            just(postsList)
        )

        repository = Repository(webservice)

        val postsObserver: TestObserver<List<PostAndPhoto>> = repository.getPosts().test()

        postsObserver.awaitTerminalEvent()

        postsObserver
            .assertNoErrors()
            .assertValue { it.size == postsList.size }
    }

    @Test
    fun getPhotosTest() {
        `when`(webservice.getPhotos()).thenReturn(
            just(photosList)
        )

        repository = Repository(webservice)

        val photosObserver: TestObserver<List<Photo>> = repository.getPhotos().test()

        photosObserver.awaitTerminalEvent()

        photosObserver
            .assertNoErrors()
            .assertValue { it.size == photosList.size }
    }

    @Test
    fun getPostAndPhotoTest() {
        `when`(webservice.getPhotos()).thenReturn(
            just(photosList)
        )

        `when`(webservice.getPosts()).thenReturn(
            just(postsList)
        )

        repository = Repository(webservice)

        val postAndPhotoObserver: TestObserver<List<PostAndPhoto>> =
            repository.getPostAndPhoto().test()

        postAndPhotoObserver.awaitTerminalEvent()

        postAndPhotoObserver
            .assertNoErrors()
            .assertValue { it.size == postsList.size }
            .assertValue { list ->
                Observable.fromIterable(list)
                    .map { it.thumbnailUrl }
                    .toList()
                    .blockingGet() == photosList.map { it.thumbnailUrl }
                    .take(Repository.COMMENTS_NUMBER.toInt())
                    .toList()
            }
    }

    @Test
    fun getCommentsTest() {
        `when`(webservice.getComments(anyInt())).thenReturn(
            just(postComments)
        )

        repository = Repository(webservice)

        val commentsObserver: TestObserver<List<Comment>> = repository.getComments(anyInt()).test()

        commentsObserver.awaitTerminalEvent()

        commentsObserver
            .assertNoErrors()
            .assertValue { it.size == Repository.COMMENTS_NUMBER.toInt() }
    }
}