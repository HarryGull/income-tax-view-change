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

package services

import connectors.ReportDeadlinesConnector
import javax.inject.{Inject, Singleton}
import models.reportDeadlines.{ReportDeadlinesErrorModel, ReportDeadlinesResponseModel}
import play.api.Logger
import play.api.http.Status
import uk.gov.hmrc.http.HeaderCarrier

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class ReportDeadlinesService @Inject()(val reportDeadlinesConnector: ReportDeadlinesConnector){

  def getReportDeadlines(incomeSourceId: String, nino: String)(implicit headerCarrier: HeaderCarrier): Future[ReportDeadlinesResponseModel] = {

    Logger.debug("[ReportDeadlinesService][getReportDeadlines] - Requesting obligation data from Connector")
    reportDeadlinesConnector.getReportDeadlines(nino) map {
      case Right(deadlines) =>
        deadlines.obligations.find(_.identification == incomeSourceId) getOrElse {
          Logger.debug(s"[ReportDeadlinesService][getReportDeadlines] Report Deadlines could not be found for ID: $incomeSourceId")
          ReportDeadlinesErrorModel(Status.INTERNAL_SERVER_ERROR, "Could not retrieve Report Deadlines for Income Source ID Provided")
        }
      case Left(error) => error
    }
  }

}
