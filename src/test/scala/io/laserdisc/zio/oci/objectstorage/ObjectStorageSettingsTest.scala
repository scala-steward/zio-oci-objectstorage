package io.laserdisc.zio.oci.objectstorage

import com.oracle.bmc.Region
import zio.test.DefaultRunnableSpec
import zio.test.{assert, assertM}
import zio.test.Assertion.{equalTo, startsWithString}
import com.oracle.bmc.auth.SimpleAuthenticationDetailsProvider
import com.oracle.bmc.auth.CustomerAuthenticationDetailsProvider
import java.util.UUID

object ObjectStorageSettingsTest extends DefaultRunnableSpec {
  def spec =
    suite("Settings")(
      suite("auth")(
        testM("const auth") {
          assertM(ObjectStorageAuth.const(SimpleAuthenticationDetailsProvider.builder().build()).map(_.auth.getKeyId()))(
            equalTo(ObjectStorageAuth(SimpleAuthenticationDetailsProvider.builder().build()).auth.getKeyId())
          )
        },
        testM("no profile in config file") {
          for {
            f <- ObjectStorageAuth.fromConfigFileProfile(UUID.randomUUID().toString()).flip.map(_.message)
          } yield assert(f)(startsWithString("No profile named"))
        }
      )
    )
}
