package com.lemonappdev.konsist.architecture1

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.core.architecture.Layer
import com.lemonappdev.konsist.core.exception.KoPreconditionFailedException
import com.lemonappdev.konsist.core.verify.assert
import org.amshove.kluent.shouldThrow
import org.amshove.kluent.withMessage
import org.junit.jupiter.api.Test

class Architecture1Test {
    @Test
    fun `passes when dependency is set that layers are independent`() {
        // given
        val domain = Layer("Domain", "com.lemonappdev.konsist.architecture1.project.domain..")
        val presentation = Layer("Presentation", "com.lemonappdev.konsist.architecture1.project.presentation..")

        val koArchitecture = Konsist
            .architecture()
            .addDependencies {
                domain.dependsOnNothing()
                presentation.dependsOnNothing()
            }
        val scope = Konsist.scopeFromDirectory("lib/src/konsistArchitectureApiTest/kotlin/com/lemonappdev/konsist/architecture1/project")

        // then
        koArchitecture.assert(scope)
    }

    // Todo("fix test)
    @Test
    fun `throws an exception when layer is defined by package without two dots at the end`() {
        // given
        val domain = Layer("Domain", "com.lemonappdev.konsist.architecture1.project.domain")
        val presentation = Layer("Presentation", "com.lemonappdev.konsist.architecture1.project.presentation..")

        val sut = {
            Konsist
                .architecture()
                .addDependencies {
                    domain.dependsOnNothing()
                    domain.dependsOn(presentation)
                }
        }

        // then
        sut shouldThrow KoPreconditionFailedException::class withMessage """
            Layer Domain must be defined by package ending with '..'. Now: com.lemonappdev.konsist.architecture1.project.domain .
        """.trimIndent()
    }

    @Test
    fun `throws an exception when self dependency is set`() {
        // given
        val domain = Layer("Domain", "com.lemonappdev.konsist.architecture1.project.domain..")

        val sut = {
            Konsist
                .architecture()
                .addDependencies { domain.dependsOn(domain) }
        }

        // then
        sut shouldThrow KoPreconditionFailedException::class withMessage "Layer Domain cannot be dependent on itself."
    }

    @Test
    fun `throws an exception when layer is set as independent and then set as depend on other layer`() {
        // given
        val domain = Layer("Domain", "com.lemonappdev.konsist.architecture1.project.domain..")
        val presentation = Layer("Presentation", "com.lemonappdev.konsist.architecture1.project.presentation..")

        val sut = {
            Konsist
                .architecture()
                .addDependencies {
                    domain.dependsOnNothing()
                    domain.dependsOn(presentation)
                }
        }

        // then
        sut shouldThrow KoPreconditionFailedException::class withMessage """
            Layer Domain was previously set as depend on nothing, so it cannot be depend on Presentation layer.
        """.trimIndent()
    }

    @Test
    fun `throws an exception when layer is set as independent twice`() {
        // given
        val domain = Layer("Domain", "com.lemonappdev.konsist.architecture1.project.domain..")

        val sut = {
            Konsist
                .architecture()
                .addDependencies {
                    domain.dependsOnNothing()
                    domain.dependsOnNothing()
                }
        }

        // then
        sut shouldThrow KoPreconditionFailedException::class withMessage """
            Duplicated the dependency that Domain layer should be depend on nothing.
        """.trimIndent()
    }

    @Test
    fun `throws an exception when layer is set as dependent on other layer and then as independent`() {
        // given
        val domain = Layer("Domain", "com.lemonappdev.konsist.architecture1.project.domain..")
        val presentation = Layer("Presentation", "com.lemonappdev.konsist.architecture1.project.presentation..")

        val sut = {
            Konsist
                .architecture()
                .addDependencies {
                    domain.dependsOn(presentation)
                    domain.dependsOnNothing()
                }
        }

        // then
        sut shouldThrow KoPreconditionFailedException::class withMessage """
            Layer Domain had a dependency previously set with Presentation layer, so it cannot be depend on nothing.
        """.trimIndent()
    }

    @Test
    fun `throws an exception when layer is set as dependent on the same layer twice`() {
        // given
        val domain = Layer("Domain", "com.lemonappdev.konsist.architecture1.project.domain..")
        val presentation = Layer("Presentation", "com.lemonappdev.konsist.architecture1.project.presentation..")

        val sut = {
            Konsist
                .architecture()
                .addDependencies {
                    domain.dependsOn(presentation)
                    domain.dependsOn(presentation)
                }
        }

        // then
        sut shouldThrow KoPreconditionFailedException::class withMessage "Duplicated the dependency between Domain and Presentation layers."
    }
}
