import com.typesafe.sbt.pgp.PgpKeys._
import sbt.Keys._
import sbt._
import xerial.sbt.Sonatype.SonatypeKeys._

object Publishing {
  val publishSettings = Seq(
    credentials += Credentials("Sonatype Nexus Repository Manager",
      "oss.sonatype.org",
      sys.env.getOrElse("SONATYPE_USER", default = "paradoxicalio"),
      sys.env.getOrElse("SONATYPE_PASSWORD", default = "")),

    publishMavenStyle := true,

    publishTo := Some(
      if (isSnapshot.value)
        Opts.resolver.sonatypeSnapshots
      else
        Opts.resolver.sonatypeStaging
    ),

    publishArtifact in Test := false,

    sonatypeProfileName := "io.paradoxical",

    pgpPublicRing := new File(".deployment/gpg/paradoxical-io.pubgpg"),
    pgpSecretRing := new File(".deployment/gpg/paradoxical-io-private.gpg"),
    pgpPassphrase := Some(sys.env.getOrElse("GPG_PASSWORD", default = "").toArray),

    pomIncludeRepository := { _ => false },

// To sync with Maven central, you need to supply the following information:
    pomExtra := (
      <url>https://github.com/paradoxical-io/scalaglobal</url>
        <licenses>
          <license>
            <name>Apache 2</name>
            <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
          </license>
        </licenses>
        <scm>
          <connection>scm:git:github.com/paradoxical-io/scalaglobal</connection>
          <developerConnection>scm:git:git@github.com:paradoxical-io/scalaglobal.git</developerConnection>
          <url>github.com:paradoxical-io/scalaglobal.git</url>
        </scm>
        <developers>
          <developer>
            <id>devshorts</id>
            <name>Anton Kropp</name>
          </developer>
        </developers>
      )
  )
}
