package app.storytel.candidate.com.viewmodel

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import app.storytel.candidate.com.fragment.PostDetailsFragmentArgs
import app.storytel.candidate.com.model.Comment
import app.storytel.candidate.com.model.PostAndPhoto
import app.storytel.candidate.com.repository.IRepository
import app.storytel.candidate.com.util.RxImmediateSchedulerRule
import app.storytel.candidate.com.util.testObserver
import io.reactivex.Single
import io.reactivex.Single.just
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PostDetailsViewModelTest {

    @get:Rule
    val mockitoRule = MockitoJUnit.rule()

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val schedulers = RxImmediateSchedulerRule()

    @Mock
    private lateinit var repository: IRepository

    @Mock
    private lateinit var savedStateHandle: SavedStateHandle

    @Mock
    private lateinit var args : PostDetailsFragmentArgs

    private lateinit var vm: PostDetailsViewModel

    private val commentsList = listOf(
        Comment(1,1, "id labore ex et quam laborum",
            "Eliseo@gardner.biz", "laudantium enim quasi est quidem magnam"),
        Comment(1,1, "id labore ex et quam laborum",
            "Eliseo@gardner.biz", "laudantium enim quasi est quidem magnam"),
        Comment(1,1, "id labore ex et quam laborum",
            "Eliseo@gardner.biz", "laudantium enim quasi est quidem magnam")
    )

    private val post = PostAndPhoto(
        1, "sunt aut facere repellat provident",
        "quia et suscipit nsuscipit recusandae consequuntur",
        "https://via.placeholder.com/150/92c952"
    )
    @Before
    fun setUp() {
        vm = PostDetailsViewModel(repository, savedStateHandle)
    }

    @Test
    fun setArgsTest() {
        //when
        `when`(repository.getComments(anyInt())).thenReturn(
            just(commentsList)
        )
        `when`(args.postBody).thenReturn(
            post.body
        )
        `when`(args.postImageUrl).thenReturn(
            post.thumbnailUrl
        )
        `when`(args.postId).thenReturn(
            post.id
        )

        val loadingValues = vm.loadingVisibility.testObserver()
        val mainLayoutValues = vm.detailsLayoutVisibility.testObserver()
        val noConnLayoutValues = vm.noConnVisibility.testObserver()
        val commentsLiveData = vm.commentsLiveData.testObserver()
        val postBodyLiveData = vm.postBody.testObserver()
        val postImageUrlLiveData = vm.postImageUrl.testObserver()

        vm.setArgs(args)

        //then
        Thread.sleep(500)
        Assert.assertEquals(listOf(View.GONE, View.VISIBLE, View.GONE), loadingValues.observedValue)
        Assert.assertEquals(listOf(View.GONE, View.VISIBLE), mainLayoutValues.observedValue)
        Assert.assertEquals(listOf(View.GONE, View.GONE), noConnLayoutValues.observedValue)
        Assert.assertEquals(listOf(commentsList), commentsLiveData.observedValue)
        Assert.assertEquals(listOf(post.body), postBodyLiveData.observedValue)
        Assert.assertEquals(listOf(post.thumbnailUrl), postImageUrlLiveData.observedValue)
    }
}