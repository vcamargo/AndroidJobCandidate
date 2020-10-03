package app.storytel.candidate.com.viewmodel

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import app.storytel.candidate.com.model.PostAndPhoto
import app.storytel.candidate.com.repository.IRepository
import app.storytel.candidate.com.util.RxImmediateSchedulerRule
import app.storytel.candidate.com.util.testObserver
import io.reactivex.Single.error
import io.reactivex.Single.just
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Thread.sleep

@RunWith(MockitoJUnitRunner::class)
class PostListViewModelTest {

    @get:Rule
    val mockitoRule = MockitoJUnit.rule()

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val schedulers = RxImmediateSchedulerRule()

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

    @Mock
    private lateinit var repository: IRepository

    private lateinit var loadingVisibilityObserver: Observer<Int>
    private lateinit var noConnVisibilityObserver: Observer<Int>
    private lateinit var listLayoutVisibilityObserver: Observer<Int>
    private lateinit var postsLiveData: Observer<List<PostAndPhoto>>
    private lateinit var vm: PostListViewModel

    @Before
    fun setUp() {
        vm = PostListViewModel(repository)
    }

    @Test
    fun getPostsAndPhotosSuccessTest() {
        //when
        `when`(repository.getPostAndPhoto()).thenReturn(
            just(postsList)
        )

        val loadingValues = vm.loadingVisibility.testObserver()
        val mainLayoutValues = vm.listLayoutVisibility.testObserver()
        val noConnLayoutValues = vm.noConnVisibility.testObserver()
        val postsLiveData = vm.getPostsAndPhotosLiveData().testObserver()

        //then
        sleep(500)
        Assert.assertEquals(listOf(View.GONE, View.VISIBLE, View.GONE), loadingValues.observedValue)
        Assert.assertEquals(listOf(View.GONE, View.VISIBLE), mainLayoutValues.observedValue)
        Assert.assertEquals(listOf(View.GONE, View.GONE), noConnLayoutValues.observedValue)
        Assert.assertEquals(listOf(postsList), postsLiveData.observedValue)
    }

    @Test
    fun getPostsAndPhotosLoadingErrorTest() {
        //when
        `when`(repository.getPostAndPhoto()).thenReturn(
            error(Exception("exception"))
        )

        val loadingValues = vm.loadingVisibility.testObserver()
        val mainLayoutValues = vm.listLayoutVisibility.testObserver()
        val noConnLayoutValues = vm.noConnVisibility.testObserver()

        vm.getPostsAndPhotosLiveData()

        //then
        sleep(500)
        Assert.assertEquals(listOf(View.GONE, View.VISIBLE, View.GONE), loadingValues.observedValue)
        Assert.assertEquals(listOf(View.GONE, View.GONE), mainLayoutValues.observedValue)
        Assert.assertEquals(listOf(View.GONE, View.VISIBLE), noConnLayoutValues.observedValue)
    }
}
