package app.storytel.candidate.com.repository

import app.storytel.candidate.com.model.Photo
import app.storytel.candidate.com.model.PostAndPhoto
import app.storytel.candidate.com.webservice.Webservice
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when`

@RunWith(MockitoJUnitRunner::class)
class RepositoryTest {

    @Mock
    private lateinit var webservice: Webservice

    private lateinit var repository: IRepository

    @Test
    fun getPostsTest() {
        `when`(webservice.getPosts()).thenReturn(Single.create{ emmiter ->
            emmiter.onSuccess(listOf())
        })

        repository = Repository(webservice)

        val posts = repository.getPosts()

        assertNotNull(posts)
    }

    @Test
    fun getPhotosTest() {
        `when`(webservice.getPhotos()).thenReturn(Single.create{ emmiter ->
            emmiter.onSuccess(listOf())
        })

        repository = Repository(webservice)

        val photos = repository.getPhotos()

        assertNotNull(photos)
    }

    @Test
    fun getPostAndPhotoTest() {
        val postsList = listOf(
            PostAndPhoto(1, "sunt aut facere repellat provident",
                "quia et suscipit nsuscipit recusandae consequuntur", ""),
            PostAndPhoto(2, "sunt aut facere repellat provident",
                "quia et suscipit nsuscipit recusandae consequuntur", ""),
            PostAndPhoto(3, "sunt aut facere repellat provident",
                "quia et suscipit nsuscipit recusandae consequuntur", ""))

        val photosList = listOf(
            Photo(1,1,"accusamus beatae",
                "https://via.placeholder.com/600/92c952",
                "https://via.placeholder.com/150/92c952"),
            Photo(1,2,"accusamus beatae",
                "https://via.placeholder.com/600/771796",
                "https://via.placeholder.com/150/771796"),
            Photo(1,3,"accusamus beatae",
                "https://via.placeholder.com/600/24f355",
                "https://via.placeholder.com/150/24f355"),
            Photo(1,4,"accusamus beatae",
                "https://via.placeholder.com/600/d32776",
                "https://via.placeholder.com/150/d32776"),
            Photo(1,5,"accusamus beatae",
                "https://via.placeholder.com/600/f66b97",
                "https://via.placeholder.com/150/f66b97")
        )

        `when`(webservice.getPhotos()).thenReturn(Single.create{ emmiter ->
            emmiter.onSuccess(photosList)
        })
        `when`(webservice.getPosts()).thenReturn(Single.create{ emmiter ->
            emmiter.onSuccess(postsList)
        })

        repository = Repository(webservice)
        assertNotNull(repository.getPostAndPhoto())
    }

    @Test
    fun getCommentsTest() {
        `when`(webservice.getComments(1)).thenReturn(Single.create{ emmiter ->
            emmiter.onSuccess(listOf())
        })

        repository = Repository(webservice)

        val comments = repository.getComments(1)

        assertNotNull(comments)
    }
}