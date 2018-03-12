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

import assets.TestConstants.BusinessDetails._
import assets.TestConstants._
import org.scalatest.Matchers
import play.api.http.Status
import play.api.libs.json._
import utils.TestSupport

class BusinessDetailsModelSpec extends TestSupport with Matchers {

  "The BusinessDetailsaModel" should {

    "read from Json with all fields" in {
      Json.fromJson[BusinessDetailsModel](testBusinessDetailsJson) shouldBe JsSuccess(testBusinessDetailsModel)
    }

    "read from Json with minimum fields" in {
      Json.fromJson[BusinessDetailsModel](testMinimumBusinessDetailsJson) shouldBe JsSuccess(testMinimumBusinessDetailsModel)
    }

    "write to Json" in {
      Json.toJson(testBusinessDetailsModel) shouldBe testBusinessDetailsToJson
    }

  }
}