

package assets

import java.time.LocalDate

import models.core.{AccountingPeriodModel, AddressModel, ContactDetailsModel}
import models.incomeSourceDetails.BusinessDetailsModel
import play.api.libs.json.{JsValue, Json}

object ReportDeadlineIntegrationTestConstants {

  val testBusinessModel = List(
    BusinessDetailsModel(
      incomeSourceId = "111111111111111",
      accountingPeriod = AccountingPeriodModel(
        start = LocalDate.parse("2017-06-01"),
        end = LocalDate.parse("2018-05-31")),
      tradingName = Some("Test Business"),
      address = Some(AddressModel(
        addressLine1 = "Test Lane",
        addressLine2 = Some("Test Unit"),
        addressLine3 = Some("Test Town"),
        addressLine4 = Some("Test City"),
        postCode = Some("TE5 7TE"),
        countryCode = "GB")),
      contactDetails = Some(ContactDetailsModel(
        phoneNumber = Some("01332752856"),
        mobileNumber = Some("07782565326"),
        faxNumber = Some("01332754256"),
        emailAddress = Some("stephen@manncorpone.co.uk"))),
      tradingStartDate = Some(LocalDate.parse("2017-01-01")),
      cashOrAccruals = Some("cash"),
      seasonal = Some(true),
      cessation = None,
      paperless = Some(true)
    )
  )

  def successResponse(nino: String): JsValue = {

    Json.obj(
      "obligations" -> Json.arr(
        Json.obj(
          "identification" -> Json.obj(
            "incomeSourceType" -> "ITSA",
            "referenceNumber" -> nino,
            "referenceType" -> "NINO"
          ),
          "obligationDetails" -> Json.arr(
            Json.obj(
              "status" -> "O",
              "inboundCorrespondenceFromDate" -> "2018-02-29",
              "inboundCorrespondenceToDate" -> "2018-02-29",
              "inboundCorrespondenceDateReceived" -> "2018-02-29",
              "inboundCorrespondenceDueDate" -> "2018-02-29",
              "periodKey" -> "#001"
            ),
            Json.obj(
              "status" -> "F",
              "inboundCorrespondenceFromDate" -> "2018-02-29",
              "inboundCorrespondenceToDate" -> "2018-02-29",
              "inboundCorrespondenceDateReceived" -> "2018-02-29",
              "inboundCorrespondenceDueDate" -> "2018-02-29",
              "periodKey" -> "#001"
            )
          )
        )
      )
    )

  }

  def successResponseTwo(nino: String): JsValue = {

    Json.obj(
      "obligations" -> Json.arr(
        Json.obj(
          "inboundCorrespondenceFromDate" -> "2017-06-01",
          "inboundCorrespondenceToDate" -> "2018-05-31",
          "inboundCorrespondenceDueDate" -> "2018-06-01",
          "periodKey" -> "#001"
        ),
        Json.obj(
          "inboundCorrespondenceFromDate" -> "2017-06-01",
          "inboundCorrespondenceToDate" -> "2018-05-31",
          "inboundCorrespondenceDueDate" -> "2018-06-01",
          "periodKey" -> "#001"
        )
      )
    )

  }

  val testDeadlineFromJson: JsValue = Json.obj(
    "inboundCorrespondenceFromDate" -> "2017-06-01",
    "inboundCorrespondenceToDate" -> "2018-05-31",
    "inboundCorrespondenceDueDate" -> "2018-06-01",
    "periodKey" -> "#001",
    "inboundCorrespondenceDateReceived" -> "2018-02-29"
  )

//  def successResponseTwo(nino: String): JsValue = {
//
//    Json.obj(
//      "obligations" -> Json.toJson(Seq(testDeadlineFromJson, testDeadlineFromJson, testDeadlineFromJson))
//    )
//
//  }

  def failureResponse(code: String, reason: String): JsValue =
    Json.obj(
      "code" -> code,
      "reason" -> reason
    )

}
