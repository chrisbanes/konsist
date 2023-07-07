package com.lemonappdev.konsist.core.provider

import com.lemonappdev.konsist.api.provider.KoExtensionProvider
import org.jetbrains.kotlin.psi.KtCallableDeclaration
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.psiUtil.isExtensionDeclaration

internal interface KoExtensionProviderCore: KoExtensionProvider {
    val ktCallableDeclaration: KtCallableDeclaration

    override fun isExtension(): Boolean = ktCallableDeclaration.isExtensionDeclaration()
}
