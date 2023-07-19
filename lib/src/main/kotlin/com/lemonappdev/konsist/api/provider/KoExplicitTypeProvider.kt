package com.lemonappdev.konsist.api.provider

import com.lemonappdev.konsist.api.declaration.KoTypeDeclaration

interface KoExplicitTypeProvider : KoProvider {
    /**
     * Declaration explicit type.
     */
    val explicitType: KoTypeDeclaration?

    /**
     * Whatever declaration has an explicit type.
     *
     * @param type the type to check for (optional).
     * @return `true` if the declaration has the specified type (or any type if [type] is `null`), `false` otherwise.
     */
    fun hasExplicitType(type: String? = null): Boolean
}
