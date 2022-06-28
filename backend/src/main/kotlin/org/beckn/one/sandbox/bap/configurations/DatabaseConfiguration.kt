package org.beckn.one.sandbox.bap.configurations

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoDatabase
import org.beckn.one.sandbox.bap.message.entities.*
import org.beckn.one.sandbox.bap.message.repositories.BecknProtocolResponseRepository
import org.beckn.one.sandbox.bap.message.repositories.BecknResponseRepository
import org.beckn.one.sandbox.bap.message.repositories.GenericRepository
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollectionOfName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DatabaseConfiguration @Autowired constructor(
  @Value("\${database.mongo.url}") private val connectionString: String,
  @Value("\${database.mongo.name}") private val databaseName: String
) {
  @Bean
  fun database(): MongoDatabase {
    val settings = MongoClientSettings.builder()
      .applyConnectionString(ConnectionString(connectionString))
      .build()
    val client = KMongo.createClient(settings)
    return client.getDatabase(databaseName)
  }

  @Bean
  fun createOrderDb(@Autowired database: MongoDatabase): BecknResponseRepository<OrderDao> =
    BecknResponseRepository(database.getCollectionOfName("order"))

  @Bean
  fun createDeliverAddressResponseDb(@Autowired database: MongoDatabase): BecknResponseRepository<AddDeliveryAddressDao> =
    BecknResponseRepository(database.getCollectionOfName("delivery_address"))


  @Bean
  fun createBillingResponseDb(@Autowired database: MongoDatabase): BecknResponseRepository<BillingDetailsDao> =
    BecknResponseRepository(database.getCollectionOfName("billing"))

  @Bean
  fun createAccountDetailDb(@Autowired database: MongoDatabase): BecknResponseRepository<AccountDetailsDao> =
    BecknResponseRepository(database.getCollectionOfName("user"))


  @Bean
  fun searchResponseRepo(@Autowired database: MongoDatabase): BecknProtocolResponseRepository<OnSearchDao> =
    BecknProtocolResponseRepository(database.getCollectionOfName("on_search"))

  @Bean
  fun messageResponseRepo(@Autowired database: MongoDatabase): GenericRepository<MessageDao> =
    GenericRepository.create(database, "message_responses")

  @Bean
  fun onSelectResponseRepo(@Autowired database: MongoDatabase): BecknProtocolResponseRepository<OnSelectDao> =
    BecknProtocolResponseRepository(database.getCollectionOfName("on_select"))

  @Bean
  fun onInitResponseRepo(@Autowired database: MongoDatabase): BecknProtocolResponseRepository<OnInitDao> =
    BecknProtocolResponseRepository(database.getCollectionOfName("on_init"))

  @Bean
  fun onConfirmResponseRepo(@Autowired database: MongoDatabase): BecknProtocolResponseRepository<OnConfirmDao> =
    BecknProtocolResponseRepository(database.getCollectionOfName("on_confirm"))

  @Bean
  fun onTrackResponseRepo(@Autowired database: MongoDatabase): BecknProtocolResponseRepository<OnTrackDao> =
    BecknProtocolResponseRepository(database.getCollectionOfName("on_track"))

  @Bean
  fun onSupportResponseRepo(@Autowired database: MongoDatabase): BecknProtocolResponseRepository<OnSupportDao> =
    BecknProtocolResponseRepository(database.getCollectionOfName("on_support"))

  @Bean
  fun onRatingResponseRepo(@Autowired database: MongoDatabase): BecknProtocolResponseRepository<OnRatingDao> =
    BecknProtocolResponseRepository(database.getCollectionOfName("on_rating"))

  @Bean
  fun onCancelResponseRepo(@Autowired database: MongoDatabase): BecknProtocolResponseRepository<OnCancelDao> =
    BecknProtocolResponseRepository(database.getCollectionOfName("on_cancel"))

  @Bean
  fun onOrderStatusResponseRepo(@Autowired database: MongoDatabase): BecknProtocolResponseRepository<OnOrderStatusDao> =
    BecknProtocolResponseRepository(database.getCollectionOfName("on_order_status"))

  @Bean
  fun onCancellationReasonsResponseRepo(@Autowired database: MongoDatabase): BecknProtocolResponseRepository<OnCancellationReasonDao> =
    BecknProtocolResponseRepository(database.getCollectionOfName("on_cancellation_reasons"))

}
