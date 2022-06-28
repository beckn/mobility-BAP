package org.beckn.one.sandbox.bap.configurations

import org.beckn.one.sandbox.bap.client.shared.services.PollForResponseService
import org.beckn.one.sandbox.bap.message.entities.*
import org.beckn.one.sandbox.bap.message.mappers.ProtocolGenericResponseMapper
import org.beckn.one.sandbox.bap.message.repositories.BecknProtocolResponseRepository
import org.beckn.one.sandbox.bap.message.services.MessageService
import org.beckn.one.sandbox.bap.message.services.ProtocolResponseStorageService
import org.beckn.one.sandbox.bap.message.services.ProtocolResponseStorageServiceImpl
import org.beckn.protocol.schemas.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ProtocolServicesConfiguration {
  @Bean
  fun onSearchStorageService(
    @Autowired responseRepo: BecknProtocolResponseRepository<OnSearchDao>,
    @Autowired mapper: ProtocolGenericResponseMapper<ProtocolOnSearch, OnSearchDao>
  ): ProtocolResponseStorageService<ProtocolOnSearch> = ProtocolResponseStorageServiceImpl(responseRepo, mapper)

  @Bean
  fun onSelectStorageService(
    @Autowired responseRepo: BecknProtocolResponseRepository<OnSelectDao>,
    @Autowired mapper: ProtocolGenericResponseMapper<ProtocolOnSelect, OnSelectDao>
  ): ProtocolResponseStorageService<ProtocolOnSelect> = ProtocolResponseStorageServiceImpl(responseRepo, mapper)


  @Bean
  fun onInitStorageService(
    @Autowired responseRepo: BecknProtocolResponseRepository<OnInitDao>,
    @Autowired mapper: ProtocolGenericResponseMapper<ProtocolOnInit, OnInitDao>
  ): ProtocolResponseStorageService<ProtocolOnInit> = ProtocolResponseStorageServiceImpl(responseRepo, mapper)

  @Bean
  fun onConfirmStorageService(
    @Autowired responseRepo: BecknProtocolResponseRepository<OnConfirmDao>,
    @Autowired mapper: ProtocolGenericResponseMapper<ProtocolOnConfirm, OnConfirmDao>
  ): ProtocolResponseStorageService<ProtocolOnConfirm> = ProtocolResponseStorageServiceImpl(responseRepo, mapper)

  @Bean
  fun onTrackStorageService(
    @Autowired responseRepo: BecknProtocolResponseRepository<OnTrackDao>,
    @Autowired mapper: ProtocolGenericResponseMapper<ProtocolOnTrack, OnTrackDao>,
  ): ProtocolResponseStorageService<ProtocolOnTrack> = ProtocolResponseStorageServiceImpl(responseRepo, mapper)

  @Bean
  fun onSupportStorageService(
    @Autowired responseRepo: BecknProtocolResponseRepository<OnSupportDao>,
    @Autowired mapper: ProtocolGenericResponseMapper<ProtocolOnSupport, OnSupportDao>
  ): ProtocolResponseStorageService<ProtocolOnSupport> = ProtocolResponseStorageServiceImpl(responseRepo, mapper)

  @Bean
  fun onRatingStorageService(
    @Autowired responseRepo: BecknProtocolResponseRepository<OnRatingDao>,
    @Autowired mapper: ProtocolGenericResponseMapper<ProtocolOnRating, OnRatingDao>,
  ): ProtocolResponseStorageService<ProtocolOnRating> = ProtocolResponseStorageServiceImpl(responseRepo, mapper)

  @Bean
  fun onCancelStorageService(
    @Autowired responseRepo: BecknProtocolResponseRepository<OnCancelDao>,
    @Autowired mapper: ProtocolGenericResponseMapper<ProtocolOnCancel, OnCancelDao>
  ): ProtocolResponseStorageService<ProtocolOnCancel> = ProtocolResponseStorageServiceImpl(responseRepo, mapper)

  @Bean
  fun onCancellationReasonsStorageService(
    @Autowired responseRepo: BecknProtocolResponseRepository<OnCancellationReasonDao>,
    @Autowired mapper: ProtocolGenericResponseMapper<ProtocolOnCancellationReasons, OnCancellationReasonDao>
  ): ProtocolResponseStorageService<ProtocolOnCancellationReasons> = ProtocolResponseStorageServiceImpl(responseRepo, mapper)

  @Bean
  fun onOrderStatusStorageService(
    @Autowired responseRepo: BecknProtocolResponseRepository<OnOrderStatusDao>,
    @Autowired mapper: ProtocolGenericResponseMapper<ProtocolOnOrderStatus, OnOrderStatusDao>,
  ): ProtocolResponseStorageService<ProtocolOnOrderStatus> = ProtocolResponseStorageServiceImpl(responseRepo, mapper)

  @Bean
  fun pollForSearchResponseService(
    responseStorageService: ProtocolResponseStorageService<ProtocolOnSearch>
  ) = PollForResponseService( responseStorageService)

  @Bean
  fun pollForSelectResponseService(
    responseStorageService: ProtocolResponseStorageService<ProtocolOnSelect>
  ) = PollForResponseService(responseStorageService)

  @Bean
  fun pollForInitResponseService(
    responseStorageService: ProtocolResponseStorageService<ProtocolOnInit>
  ) = PollForResponseService( responseStorageService)

  @Bean
  fun pollForConfirmResponseService(
    responseStorageService: ProtocolResponseStorageService<ProtocolOnConfirm>
  ) = PollForResponseService(responseStorageService)

  @Bean
  fun pollForTrackResponseService(
    responseStorageService: ProtocolResponseStorageService<ProtocolOnTrack>
  ) = PollForResponseService( responseStorageService)

  @Bean
  fun pollForSupportResponseService(
    responseStorageService: ProtocolResponseStorageService<ProtocolOnSupport>
  ) = PollForResponseService( responseStorageService)

  @Bean
  fun pollForRatingResponseService(
    responseStorageService: ProtocolResponseStorageService<ProtocolOnRating>
  ) = PollForResponseService( responseStorageService)

  @Bean
  fun pollForCancelResponseService(
    responseStorageService: ProtocolResponseStorageService<ProtocolOnCancel>
  ) = PollForResponseService( responseStorageService)

  @Bean
  fun pollForOrderStatusResponseService(
    responseStorageService: ProtocolResponseStorageService<ProtocolOnOrderStatus>
  ) = PollForResponseService( responseStorageService)


  @Bean
  fun pollForCancellationReasonsResponseService(
    responseStorageService: ProtocolResponseStorageService<ProtocolOnCancellationReasons>
  ) = PollForResponseService( responseStorageService)

}
