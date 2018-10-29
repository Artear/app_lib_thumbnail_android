package com.artear.app_library_android_cdnthumbnailkit.connection

import java.util.*

internal class ByteArrayScanner {
    private var mData: ByteArray? = null
    private var mCurrentOffset: Int = 0
    private var mTotalLength: Int = 0
    private var mDelimiter: Char = ' '
    private var mDelimiterSet: Boolean = false

    fun reset(buffer: ByteArray, length: Int): ByteArrayScanner {
        mData = buffer
        mCurrentOffset = 0
        mTotalLength = length
        mDelimiterSet = false
        return this
    }

    fun useDelimiter(delimiter: Char): ByteArrayScanner {
        throwIfNotReset()
        mDelimiter = delimiter
        mDelimiterSet = true
        return this
    }

    private fun throwIfNotReset() {
        if (mData == null) {
            throw IllegalStateException("Must call reset first")
        }
    }

    private fun throwIfDelimiterNotSet() {
        if (!mDelimiterSet) {
            throw IllegalStateException("Must call useDelimiter first")
        }
    }

    /**
     * @return The next token, parsed as a string.
     * @throws NoSuchElementException
     */
    @Throws(NoSuchElementException::class)
    fun nextString(): String {
        throwIfNotReset()
        throwIfDelimiterNotSet()
        val offset = mCurrentOffset
        val length = advance()
        return String(mData!!, offset, length)
    }

    /**
     * Matches the next token with a string.
     * @param str String to match the next token with.
     * @return True if the next token matches, false otherwise.
     * @throws NoSuchElementException
     */
    @Throws(NoSuchElementException::class)
    fun nextStringEquals(str: String): Boolean {
        var offset = mCurrentOffset
        val length = advance()
        if (str.length != length) {
            return false
        }
        for (i in 0 until str.length) {
            if (str[i].toByte() != mData!![offset]) {
                return false
            }
            offset++
        }
        return true
    }

    /**
     * @return The next token, parsed as an integer.
     * @throws NoSuchElementException
     */
    @Throws(NoSuchElementException::class)
    fun nextInt(): Int {
        throwIfNotReset()
        throwIfDelimiterNotSet()
        val offset = mCurrentOffset
        val length = advance()
        return parseInt(
                mData,
                offset,
                offset + length)
    }

    /**
     * Move to the next token.
     * @throws NoSuchElementException
     */
    @Throws(NoSuchElementException::class)
    fun skip() {
        throwIfNotReset()
        throwIfDelimiterNotSet()
        advance()
    }

    @Throws(NoSuchElementException::class)
    private fun advance(): Int {
        throwIfNotReset()
        throwIfDelimiterNotSet()
        if (mTotalLength <= mCurrentOffset) {
            throw NoSuchElementException("Reading past end of input stream at $mCurrentOffset.")
        }
        val index = indexOf(
                mData,
                mCurrentOffset,
                mTotalLength,
                mDelimiter)
        if (index == -1) {
            val length = mTotalLength - mCurrentOffset
            mCurrentOffset = mTotalLength
            return length
        } else {
            val length = index - mCurrentOffset
            mCurrentOffset = index + 1
            return length
        }
    }

    @Throws(NumberFormatException::class)
    private fun parseInt(buffer: ByteArray?, start: Int, end: Int): Int {
        var start = start
        val radix = 10
        var result = 0
        while (start < end) {
            val digit = buffer!![start++] - '0'.toByte()
            if (digit < 0 || digit > 9) {
                throw NumberFormatException("Invalid int in buffer at " + (start - 1) + ".")
            }
            val next = result * radix + digit
            result = next
        }
        return result
    }

    private fun indexOf(data: ByteArray?, start: Int, end: Int, ch: Char): Int {
        for (i in start until end) {
            if (data!![i] == ch.toByte()) {
                return i
            }
        }
        return -1
    }
}
