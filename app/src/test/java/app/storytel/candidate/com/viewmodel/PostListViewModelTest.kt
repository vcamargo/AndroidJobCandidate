package app.storytel.candidate.com.viewmodel

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.storytel.candidate.com.repository.IRepository
import app.storytel.candidate.com.repository.postsMockData
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

    @Mock
    private lateinit var repository: IRepository
    private lateinit var vm: PostListViewModel

    @Before
    fun setUp() {
        vm = PostListViewModel(repository)
    }

    @Test
    fun getPostsAndPhotosSuccessTest() {
        //when
        `when`(repository.getPostAndPhoto()).thenReturn(
            just(postsMockData)
        )

        val loadingValues = vm.loadingVisibility.testObserver()
        val mainLayoutValues = vm.listLayoutVisibility.testObserver()
        val noConnLayoutValues = vm.noConnVisibility.testObserver()
        val postsLiveData = vm.getPostsAndPhotosLiveData().testObserver()

        //then
        // TODO: Find a proper way to wait for rxjava single completion before evaluate the results
        sleep(100)
        Assert.assertEquals(listOf(View.GONE, View.VISIBLE, View.GONE), loadingValues.observedValue)
        Assert.assertEquals(listOf(View.GONE, View.VISIBLE), mainLayoutValues.observedValue)
        Assert.assertEquals(listOf(View.GONE, View.GONE), noConnLayoutValues.observedValue)
        Assert.assertEquals(listOf(postsMockData), postsLiveData.observedValue)
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
        // TODO: Find a proper way to wait for rxjava single completion before evaluate the results
        sleep(100)
        Assert.assertEquals(listOf(View.GONE, View.VISIBLE, View.GONE), loadingValues.observedValue)
        Assert.assertEquals(listOf(View.GONE, View.GONE), mainLayoutValues.observedValue)
        Assert.assertEquals(listOf(View.GONE, View.VISIBLE), noConnLayoutValues.observedValue)
    }
}
