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

package config

import javax.inject.Singleton

import com.google.inject.Inject
import play.api.{Configuration, Play}
import uk.gov.hmrc.play.config.ServicesConfig

trait AppConfig {
  val desToken: String
  val desEnvironment: String
  val desUrl: String
}

@Singleton
class MicroserviceAppConfig @Inject()(val configuration: Configuration) extends AppConfig with ServicesConfig{

  override protected def mode = Play.current.mode
  override protected def runModeConfiguration = Play.current.configuration

  private def loadConfig(key: String) = configuration.getString(key).getOrElse(throw new Exception(s"Missing configuration key: $key"))

  override val desEnvironment: String = loadConfig("microservice.services.des.environment")
  override val desToken: String = loadConfig("microservice.services.des.authorization-token")
  override val desUrl: String = loadConfig("microservice.services.des.url")

}
