package com.artear.app_library_android_cdnthumbnailkit.connection

/**
 * A general enumeration for different connection qualities.
 *
 *
 *
 * In order to compare qualities use the [] method. Qualities are ordered in increasing
 * order of declaration as per the java docs for [java.lang.Enum].
 *
 */
enum class ConnectionQuality {
    /**
     * Bandwidth under 150 kbps.
     */
    POOR,
    /**
     * Bandwidth between 150 and 550 kbps.
     */
    MODERATE,
    /**
     * Bandwidth between 550 and 2000 kbps.
     */
    GOOD,
    /**
     * EXCELLENT - Bandwidth over 2000 kbps.
     */
    EXCELLENT,
    /**
     * Placeholder for unknown bandwidth. This is the initial value and will stay at this value
     * if a bandwidth cannot be accurately found.
     */
    UNKNOWN
}
