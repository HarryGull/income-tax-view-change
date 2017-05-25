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

import auth.MockAuthentication
import config.MockAppConfig
import play.api.http.Status
import play.api.test.FakeRequest
import uk.gov.hmrc.play.test.{UnitSpec, WithFakeApplication}


class MicroserviceHelloWorldControllerSpec extends UnitSpec with WithFakeApplication {

  object TestController extends MicroserviceHelloWorld()(MockAppConfig, MockAuthentication)

  val fakeRequest = FakeRequest()
  val authorisedFakeRequest = FakeRequest().withHeaders("Authorization" -> "Some Bearer Token")

  "The MicroserviceHelloWorld.hello action" when {

    "called with an Unauthenticated user" should {
      "return Unauthorised (401)" in {
        val result = TestController.hello()(fakeRequest)
        status(result) shouldBe Status.UNAUTHORIZED
      }
    }

    "called with an authenticated user" should {
      "return OK (200)" in {
        val result = TestController.hello()(authorisedFakeRequest)
        status(result) shouldBe Status.OK
      }
    }
  }


}
