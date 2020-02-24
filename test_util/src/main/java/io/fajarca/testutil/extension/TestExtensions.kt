package io.fajarca.testutil.extension

import io.fajarca.testutil.rule.CoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest

@UseExperimental(ExperimentalCoroutinesApi::class)
fun CoroutineTestRule.runBlockingTest(block : suspend TestCoroutineScope.() -> Unit) {
    testDispatcher.runBlockingTest(block)
}