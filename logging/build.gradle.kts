plugins {
    `stdx-module`
    `stdx-ktlint`
    `stdx-publishing`
}

listOf("common", "jvm", "js", "native").forEach { platform ->
    val path = "${platform}Main"
    val dir = buildDir.resolve("generated").resolve("src").resolve(path)
    kotlin.sourceSets.getByName(path).apply {
        kotlin.srcDir(dir)
    }
}


kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api("io.github.microutils", "kotlin-logging", "2.1.21")
            }
        }
    }
}

tasks {
    val generateLoggerFunctions = task<GenerateLoggerFunctionsTask>("generateLoggerFunctions") {
        `package`.set("dev.schlaubi.stdx.logging")
        logLevels.set(listOf("debug", "trace", "error", "info", "warn"))
    }

    afterEvaluate {
        "metadataCommonMainClasses"{
            dependsOn(generateLoggerFunctions)
        }
    }
}
