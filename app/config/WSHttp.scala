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

import javax.inject.{Inject, Singleton}

import play.api.{Configuration, Play}
import uk.gov.hmrc.http._
import uk.gov.hmrc.play.audit.http.HttpAuditing
import uk.gov.hmrc.play.config.{AppName, RunMode}
import uk.gov.hmrc.play.http.ws._

@Singleton
class WSHttp @Inject()(override val auditConnector: MicroserviceAuditConnector)
  extends WSGet with HttpGet
  with WSPut with HttpPut
  with WSPost with HttpPost
  with WSDelete with HttpDelete
  with WSPatch with HttpPatch
  with AppName with RunMode with HttpAuditing {
  override protected def mode = Play.current.mode
  override protected def runModeConfiguration = Play.current.configuration
  override protected def appNameConfiguration: Configuration = Play.current.configuration
  override val hooks = Seq(AuditingHook)
}
object WSHttp extends WSHttp(MicroserviceAuditConnector)