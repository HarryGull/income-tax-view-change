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

package controllers

import javax.inject.{Inject, Singleton}

import controllers.predicates.AuthenticationPredicate
import models._
import models.core.{NinoErrorModel, NinoModel}
import models.incomeSourceDetails.{IncomeSourceDetailsError, IncomeSourceDetailsModel}
import play.api.Logger
import play.api.libs.json.Json
import play.api.mvc._
import services.IncomeSourceDetailsService
import uk.gov.hmrc.play.bootstrap.controller.BaseController

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class IncomeSourceDetailsController @Inject()(val authentication: AuthenticationPredicate,
                                     val incomeSourceDetailsService: IncomeSourceDetailsService
                                      ) extends BaseController {

  def getNino(mtdRef: String): Action[AnyContent] = authentication.async { implicit request =>
    incomeSourceDetailsService.getNino(mtdRef).map {
      case error: NinoErrorModel =>
        Logger.debug(s"[IncomeSourceDetailsController][getNino] - Error Response: $error")
        Status(error.status)(Json.toJson(error))
      case success: NinoModel =>
        Logger.debug(s"[IncomeSourceDetailsController][getNino] - Successful Response: $success")
        Ok(Json.toJson(success))
    }
  }

  def getIncomeSourceDetails(mtdRef: String): Action[AnyContent] = authentication.async { implicit request =>
    incomeSourceDetailsService.getIncomeSourceDetails(mtdRef).map {
      case error: IncomeSourceDetailsError =>
        Logger.debug(s"[IncomeSourceDetailsController][getIncomeSourceDetails] - Error Response: $error")
        Status(error.status)(Json.toJson(error))
      case success: IncomeSourceDetailsModel =>
        Logger.debug(s"[IncomeSourceDetailsController][getIncomeSourceDetails] - Successful Response: $success")
        Ok(Json.toJson(success))
    }
  }

}
