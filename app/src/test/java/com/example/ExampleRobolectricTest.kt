package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.repository.AiToolRepository
import com.example.data.repository.ConnectionsRepository
import com.example.data.repository.LinuxCommandRepository
import com.example.data.repository.SoftwareRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context matches HackMatrix`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("HackMatrix", appName)
    }

    @Test
    fun `linux repository has comprehensive commands`() {
        assertTrue("Linux commands repository must not be empty", LinuxCommandRepository.commands.isNotEmpty())
        val lsCmd = LinuxCommandRepository.commands.find { it.name == "ls" }
        assertNotNull(lsCmd)
        assertTrue(lsCmd!!.flags.isNotEmpty())
    }

    @Test
    fun `ai tools repository includes indian and paid tools`() {
        assertTrue("AI tools repository must not be empty", AiToolRepository.tools.isNotEmpty())
        val sarvam = AiToolRepository.tools.find { it.id == "sarvam_ai" }
        assertNotNull(sarvam)
        val chatgpt = AiToolRepository.tools.find { it.id == "chatgpt_plus" }
        assertNotNull(chatgpt)
        assertTrue("ChatGPT Plus must contain paid pricing tier", chatgpt!!.pricingTiers.any { it.isPaid })
    }

    @Test
    fun `software repository has security and dev tools`() {
        assertTrue("Software repository must not be empty", SoftwareRepository.softwares.isNotEmpty())
        val nmap = SoftwareRepository.softwares.find { it.id == "nmap_soft" }
        assertNotNull(nmap)
        val docker = SoftwareRepository.softwares.find { it.id == "docker" }
        assertNotNull(docker)
    }

    @Test
    fun `connections repository has valid links`() {
        assertTrue("Connections matrix must not be empty", ConnectionsRepository.connections.isNotEmpty())
        val dockerLinks = ConnectionsRepository.getConnectionsFor("docker")
        assertTrue("Docker must have interconnected tech links", dockerLinks.isNotEmpty())
    }
}
