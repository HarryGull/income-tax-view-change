/*
 * Copyright 2017 HM Revenue & Customs
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

package connectors

import javax.inject.{Inject, Singleton}

import config.MicroserviceAppConfig
import models._
import play.api.Logger
import play.api.http.Status
import play.api.http.Status._
import uk.gov.hmrc.play.config.ServicesConfig
import uk.gov.hmrc.play.http._

import uk.gov.hmrc.play.http.logging.MdcLoggingExecutionContext._
import scala.concurrent.Future
import uk.gov.hmrc.http.{CoreGet, HeaderCarrier, HttpGet, HttpResponse}
import uk.gov.hmrc.http.logging.Authorization

@Singleton
class FinancialDataConnector @Inject()(val http: CoreGet,
                                      val appConfig: MicroserviceAppConfig
                                      ) extends RawResponseReads {

  val getLastEstimatedTaxCalculationUrl: (String, String, String) => String =
    (nino, year, calcType) => s"${appConfig.desUrl}/calculation-store/previous-calculation/$nino?year=$year&type=$calcType"

  def getLastEstimatedTaxCalculation(nino: String, year: String, `type`:String)
                                    (implicit headerCarrier: HeaderCarrier): Future[LastTaxCalculationResponseModel] = {

    val url = getLastEstimatedTaxCalculationUrl(nino, year, `type`)
    val desHC = headerCarrier.copy(authorization = Some(Authorization(s"Bearer ${appConfig.desToken}")))
      .withExtraHeaders("Environment" -> appConfig.desEnvironment)

    Logger.debug(s"[FinancialDataConnector][getLastEstimatedTaxCalculation] - Calling GET $url \n\nHeaders: $desHC")
    http.GET[HttpResponse](url)(httpReads, desHC, implicitly) flatMap {
      response =>
        response.status match {
          case OK =>
            Logger.debug(s"[FinancialDataConnector][getLastEstimatedTaxCalculation] - RESPONSE status: ${response.status}, body: ${response.body}")
            Future.successful(response.json.validate[LastTaxCalculation].fold(
              invalid => {
                Logger.warn(s"[FinancialDataConnector][getLastEstimatedTaxCalculation] - Json ValidationError. Parsing Financial Data")
                Logger.debug(s"[FinancialDataConnector][getLastEstimatedTaxCalculation] - Response possibly returned `None` for calcAmount: ${response.body}")
                LastTaxCalculationError(Status.INTERNAL_SERVER_ERROR, "Json Validation Error. Parsing Financial Data ")
              },
              valid => valid
            ))
          case _ =>
            Logger.debug(s"[FinancialDataConnector][getLastEstimatedTaxCalculation] - RESPONSE status: ${response.status}, body: ${response.body}")
            Logger.warn(s"[FinancialDataConnector][getLastEstimatedTaxCalculation] - Response status: [${response.status}] returned from Latest Calc call")
            Future.successful(LastTaxCalculationError(response.status, response.body))
        }
    } recoverWith {
      case _ =>
        Logger.warn(s"[FinancialDataConnector][getLastEstimatedTaxCalculation] - Unexpected failed future")
        Future.successful(LastTaxCalculationError(Status.INTERNAL_SERVER_ERROR, s"Unexpected failed future"))
    }
  }
}
