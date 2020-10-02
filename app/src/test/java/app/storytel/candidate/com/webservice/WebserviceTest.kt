package app.storytel.candidate.com.webservice

import junit.framework.Assert.assertNotNull
import org.junit.Test

class WebserviceTest {

    @Test
    fun testCreate() {
        val webservice = Webservice.create()
        assertNotNull(webservice)
    }
}