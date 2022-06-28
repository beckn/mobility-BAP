package org.beckn.one.sandbox.bap.client.external.provider

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.resilience4j.retrofit.CircuitBreakerCallAdapter
import io.github.resilience4j.retrofit.RetryCallAdapter
import io.github.resilience4j.retry.Retry
import okhttp3.OkHttpClient
import org.beckn.one.sandbox.bap.client.shared.Util
import org.beckn.one.sandbox.bap.client.shared.security.SignRequestInterceptor
import org.beckn.one.sandbox.bap.factories.CircuitBreakerFactory
import org.beckn.one.sandbox.bap.factories.RetryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

@Service
class ProtocolClientFactory @Autowired constructor(
  val objectMapper: ObjectMapper,
  @Value("\${protocol_adapter_service.retry.max_attempts}")
  private val maxAttempts: Int,
  @Value("\${protocol_adapter_service.retry.initial_interval_in_millis}")
  private val initialIntervalInMillis: Long,
  @Value("\${protocol_adapter_service.retry.interval_multiplier}")
  private val intervalMultiplier: Double,
  @Value("\${beckn.security.enabled}") val enableSecurity: Boolean,
  private val interceptor: SignRequestInterceptor,
  @Value("\${protocol_adapter_service.timeouts.connection_in_seconds}") private val connectionTimeoutInSeconds: Long,
  @Value("\${protocol_adapter_service.timeouts.read_in_seconds}") private val readTimeoutInSeconds: Long,
  @Value("\${protocol_adapter_service.timeouts.write_in_seconds}") private val writeTimeoutInSeconds: Long,
  @Value("\${protocol_adapter_service.url}") private val protocolUri: String,

  ) {
  @Cacheable("protocolClients")
  fun getClient(uri: String?): BppClient {
    val url : String = Util.getBaseUri(protocolUri)
    val retrofit = Retrofit.Builder()
      .baseUrl(url)
      .client(buildHttpClient())
      .addConverterFactory(JacksonConverterFactory.create(objectMapper))
      .addCallAdapterFactory(RetryCallAdapter.of(getRetryConfig(protocolUri)))
      .addCallAdapterFactory(CircuitBreakerCallAdapter.of(CircuitBreakerFactory.create(protocolUri)))
      .build()
    return retrofit.create(BppClient::class.java)
  }

  private fun buildHttpClient(): OkHttpClient {
    val httpClientBuilder = OkHttpClient.Builder()
      .connectTimeout(connectionTimeoutInSeconds, TimeUnit.SECONDS)
      .readTimeout(readTimeoutInSeconds, TimeUnit.SECONDS)
      .writeTimeout(writeTimeoutInSeconds, TimeUnit.SECONDS)
    if (enableSecurity) {
      httpClientBuilder.addInterceptor(interceptor)
    }
    return httpClientBuilder.build()
  }

  private fun getRetryConfig(bppUri: String): Retry {
    return RetryFactory.create(
      bppUri,
      maxAttempts,
      initialIntervalInMillis,
      intervalMultiplier
    )
  }
}