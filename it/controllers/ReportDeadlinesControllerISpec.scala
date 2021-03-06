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

package controllers

import assets.BaseIntegrationTestConstants._
import assets.ReportDeadlinesIntegrationTestConstants._
import helpers.ComponentSpecBase
import helpers.servicemocks.DesReportDeadlinesStub
import models.reportDeadlines.{ReportDeadlinesErrorModel, ReportDeadlinesModel}
import play.api.http.Status._

class ReportDeadlinesControllerISpec extends ComponentSpecBase {

  "Calling the ReportDeadlinesController" when {
    "authorised with a valid request" should {
      "return a valid ReportDeadlinesModel" in {

        isAuthorised(true)

        And("I wiremock stub a successful Get Report Deadlines response")
        DesReportDeadlinesStub.stubGetDesReportDeadlines(testNino)

        When(s"I call GET /income-tax-view-change/$testNino/income-source/$testIncomeSourceId/report-deadlines")
        val res = IncomeTaxViewChange.getReportDeadlines(testIncomeSourceId, testNino)

        DesReportDeadlinesStub.verifyGetDesReportDeadlines(testNino)

        Then("a successful response is returned with the correct model")

        res should have(
          httpStatus(OK),
          jsonBodyAs[ReportDeadlinesModel](reportDeadlines)
        )
      }
    }
    "authorised with a invalid request" should {
      "return a ReportDeadlinesErrorModel" in {
        isAuthorised(true)

        And("I wiremock stub an unsuccessful Get Report Deadlines response")
        DesReportDeadlinesStub.stubGetDesReportDeadlinesError(testNino)

        When(s"I call GET /income-tax-view-change/$testNino/income-source/$testIncomeSourceId/report-deadlines")
        val res = IncomeTaxViewChange.getReportDeadlines(testIncomeSourceId, testNino)

        DesReportDeadlinesStub.verifyGetDesReportDeadlines(testNino)

        Then("a unsuccessful response is returned with an error model")

        res should have(
          httpStatus(INTERNAL_SERVER_ERROR)
        )
      }
    }
    "unauthorised" should {
      "return an error" in {

        isAuthorised(false)

        When(s"I call GET /income-tax-view-change/estimated-tax-liability/$testNino/$testYear")
        val res = IncomeTaxViewChange.getEstimatedTaxLiability(testNino, testYear, testCalcType)

        res should have(
          httpStatus(UNAUTHORIZED),
          emptyBody
        )
      }
    }
  }

}
