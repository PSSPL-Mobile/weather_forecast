package com.psspl.myweatherdashboard.data.network

import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.io.IOException

/***
 * Name : CurlLoggingInterceptor.kt
 * Author : Prakash Software Pvt Ltd
 * Date : 28 Apr 2025
 * Desc : Interceptor to log HTTP requests as cURL commands for debugging.
 * */
class CurlLoggingInterceptor : Interceptor {

    /***
     * Intercepts the request chain and logs it as a cURL command.
     * @throws IOException if an I/O error occurs.
     */
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuffer = Buffer()
        request.body?.writeTo(requestBuffer)

        // Build the cURL command
        val curlCmd = buildString {
            append("curl -X ${request.method} ")

            // Add URL
            append("'${request.url}' ")

            request.headers.forEach { (name, value) ->
                append("-H '${name}: ${value}' ")
            }

            // Add body if present (for POST/PUT requests)
            val body = requestBuffer.readUtf8()
            if (body.isNotEmpty() && request.method != "GET") {
                append("-d '$body' ")
            }
        }

        // Log the cURL command
        println("cURL: $curlCmd")

        // Proceed with the chain
        return chain.proceed(request)
    }
}