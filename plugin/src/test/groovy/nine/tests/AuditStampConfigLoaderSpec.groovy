package nine.tests

import gorm.AuditStampConfigLoader
import org.springframework.util.FileCopyUtils
import spock.lang.IgnoreRest
import spock.lang.Specification

import java.nio.file.Files

class AuditStampConfigLoaderSpec extends Specification {
    AuditStampConfigLoader loader = new AuditStampConfigLoader();

    void "test load from classpath"() {
        setup: "Move audit-trail-config.groovy to classpath"
        File source = new File("src/test/resources/test-config.groovy")
        File dir = new File(getClass().getResource("/").toURI())
        File destination = new File(dir, "audit-trail-config.groovy")
        Files.copy(source.toPath(), destination.toPath())
        assert destination.exists()

        loader._CO = null

        when: "Load from classpath"
        Map config = loader.load()

        then:
        config != null
        config.grails.plugin.audittrail.test == "test"

        cleanup:
        destination.delete()
    }

    void "test load from conf:audit-trail-config.groovy"() {
        setup:
        File source = new File("src/test/resources/test-config.groovy")
        File destination = new File("grails-app/conf/audit-trail-config.groovy")

        Files.copy(source.toPath(), destination.toPath())

        loader._CO = null

        when: "Load from conf/audit-trail-config.groovy"
        Map config = loader.load()

        then:
        config != null
        config.grails.plugin.audittrail.test == "test"

        cleanup:
        destination.delete()
    }

    void "test load from application.groovy"() {
        setup: "Move audit-trail-config.groovy to classpath"
        File source = new File("src/test/resources/test-config.groovy")

        loader._CO = null

        when: "Load from application.groovy"
        Map config = loader.load()

        then:
        config != null
        config.grails.plugin.audittrail.enabled == true
    }
}
