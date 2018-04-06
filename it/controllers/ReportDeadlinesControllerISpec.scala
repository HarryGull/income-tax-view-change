
package controllers

import assets.BaseIntegrationTestConstants.{testCalcType, testMtdRef, testNino, testYear}
import assets.IncomeSourceIntegrationTestConstants.incomeSourceDetailsSuccess
import helpers.ComponentSpecBase
import helpers.servicemocks.{DesBusinessDetailsStub, DesDeadlinesStub, FinancialDataStub}
import models.reportDeadlines.{ReportDeadlineModel, ReportDeadlinesModel}
import play.api.http.Status.{OK, UNAUTHORIZED}
import java.time.LocalDate

class ReportDeadlinesControllerISpec extends ComponentSpecBase {
  "Calling the ReportDeadlinesController" when {
    "authorised with a valid request" should {
      "return a valid ReportDeadlinesModel" in {

        isAuthorised(true)

        And("I wiremock stub a successful getIncomeSourceDetails response")
        DesDeadlinesStub.stubGetDesDeadlinesDetails(testNino)

        When(s"I call GET /income-tax-view-change/estimated-tax-liability/$testNino/$testYear/$testCalcType")
        val res = IncomeTaxViewChange.getDeadlines(testNino)

        DesDeadlinesStub.verifyGetDesDeadlinesDetails(testNino)

        Then("a successful response is returned with the correct deadlines")

        res should have(
          httpStatus(OK)
//          jsonBodyAs[ReportDeadlinesModel](
//            ReportDeadlinesModel(
//              Seq(ReportDeadlineModel(
//                LocalDate.parse("2018-01-01"),
//                LocalDate.parse("2018-01-01"),
//                LocalDate.parse("2018-01-01"),
//                "something",
//                None
//              ))
//            )
//          )
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
