package com.lemon.konsist.core.declaration

import com.lemon.konsist.core.cache.KoDeclarationCache
import org.jetbrains.kotlin.psi.KtObjectDeclaration

class KoCompanionObject private constructor(ktObjectDeclaration: KtObjectDeclaration) :
    KoComplexDeclaration(ktObjectDeclaration) {
    fun hasExplicitName() = name != DEFAULT_COMPANION_OBJECT_NAME

    companion object {
        const val DEFAULT_COMPANION_OBJECT_NAME = "Companion"

        private val cache = KoDeclarationCache<KoCompanionObject>()

        fun getInstance(ktObjectDeclaration: KtObjectDeclaration) =
            cache.getOrCreateInstance(ktObjectDeclaration) { KoCompanionObject(ktObjectDeclaration) }
    }
}
