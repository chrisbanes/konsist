package com.lemonappdev.konsist.core.ext

import java.io.File

val sep: String = File.separator

fun String.toCanonicalPaths(): String = replace("/", File.separator)
