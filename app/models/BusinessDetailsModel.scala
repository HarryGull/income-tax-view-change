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

package models

import play.api.libs.json.{Json, OFormat, Reads, Writes}

case class BusinessDetailsModel(incomeSourceId: String,
                                accountingPeriod: AccountingPeriodModel,
                                tradingName: Option[String],
                                address: Option[AddressModel],
                                contactDetails: Option[ContactDetailsModel],
                                tradingStartDate: Option[String],
                                cashOrAccruals: Option[String],
                                seasonal: Option[Boolean],
                                cessation: Option[CessationModel],
                                paperless: Option[Boolean])

object BusinessDetailsModel {
  implicit val contactReads: Reads[ContactDetailsModel] = ContactDetailsModel.reads
  implicit val contactWrites: Writes[ContactDetailsModel] = ContactDetailsModel.writes
  implicit val addressReads: Reads[AddressModel] = AddressModel.reads
  implicit val addressWrites: Writes[AddressModel] = AddressModel.writes
  implicit val cessationReads: Reads[CessationModel] = CessationModel.reads
  implicit val cessationWrites: Writes[CessationModel] = CessationModel.writes
  implicit val accountingPeriodReads: Reads[AccountingPeriodModel] = AccountingPeriodModel.reads
  implicit val accountingPeriodWrites: Writes[AccountingPeriodModel] = AccountingPeriodModel.writes
  implicit val format: OFormat[BusinessDetailsModel] = Json.format[BusinessDetailsModel]
}
