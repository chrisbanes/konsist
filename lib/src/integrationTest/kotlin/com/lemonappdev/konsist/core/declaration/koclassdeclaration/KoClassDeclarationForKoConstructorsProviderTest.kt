package com.lemonappdev.konsist.core.declaration.koclassdeclaration

import com.lemonappdev.konsist.TestSnippetProvider.getSnippetKoScope
import org.amshove.kluent.assertSoftly
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldHaveSize
import org.junit.jupiter.api.Test

class KoClassDeclarationForKoConstructorsProviderTest {
    @Test
    fun `class-has-primary-and-secondary-constructors`() {
        // given
        val sut = getSnippetFile("class-has-primary-and-secondary-constructors")
            .classes()
            .first()

        // then
        sut.allConstructors shouldHaveSize 3
    }

    private fun getSnippetFile(fileName: String) =
        getSnippetKoScope("core/declaration/koclassdeclaration/snippet/forkoconstructorsprovider/", fileName)
}
