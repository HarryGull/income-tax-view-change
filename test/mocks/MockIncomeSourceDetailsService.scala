/*
 * Copyright 2018 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mocks

import assets.TestConstants._
import models.IncomeSourceDetailsResponseModel
import org.mockito.ArgumentMatchers
import org.mockito.Mockito._
import org.mockito.stubbing.OngoingStubbing
import org.scalatest.BeforeAndAfterEach
import org.scalatest.mockito.MockitoSugar
import services.IncomeSourceDetailsService
import uk.gov.hmrc.play.test.UnitSpec

import scala.concurrent.Future


trait MockIncomeSourceDetailsService extends UnitSpec with MockitoSugar with BeforeAndAfterEach {

  val mockIncomeSourceDetailsService: IncomeSourceDetailsService = mock[IncomeSourceDetailsService]

  override def beforeEach(): Unit = {
    super.beforeEach()
    reset(mockIncomeSourceDetailsService)
  }

  def setupMockIncomeSourceDetailsResponse(mtdRef: String)(response: IncomeSourceDetailsResponseModel):
    OngoingStubbing[Future[IncomeSourceDetailsResponseModel]] = when(mockIncomeSourceDetailsService.getIncomeSourceDetails(
        ArgumentMatchers.eq(mtdRef))(ArgumentMatchers.any())).thenReturn(Future.successful(response))

  def setupMockNinoResponse(mtdRef: String)(response: IncomeSourceDetailsResponseModel):
    OngoingStubbing[Future[IncomeSourceDetailsResponseModel]] = when(mockIncomeSourceDetailsService.getNino(
        ArgumentMatchers.eq(mtdRef))(ArgumentMatchers.any())).thenReturn(Future.successful(response))

  def mockIncomeSourceDetailsResponse(desResponse: IncomeSourceDetailsResponseModel):
    OngoingStubbing[Future[IncomeSourceDetailsResponseModel]] = setupMockIncomeSourceDetailsResponse(mtdRef)(desResponse)

  def mockNinoResponse(desResponse: IncomeSourceDetailsResponseModel):
    OngoingStubbing[Future[IncomeSourceDetailsResponseModel]] = setupMockNinoResponse(mtdRef)(desResponse)
}
