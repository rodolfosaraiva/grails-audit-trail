/*
* Copyright 2019 Yak.Works - Licensed under the Apache License, Version 2.0 (the "License")
* You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
*/
package org.grails.plugins

import grails.plugins.Plugin

class AuditTrailGrailsPlugin extends Plugin {

    def grailsVersion = "3.3.9 > *"

    def profiles = ['web']

    def title = "Grails Audit Trail Plugin" // Headline display name of the plugin
    def author = "Joshua Burnett"
    def authorEmail = "joshua@greenbill.com"
    def description = 'Provides an annotation and Hibernate events to take care of audit trail stamping for your GORM objects'
    def organization = [name: "9ci", url: "http://www.9ci.com/"]
    def developers = [[name: "Joshua Burnet", email: "joshua@greenbill.com"]]
    def issueManagement = [system: "github", url: "https://github.com/9ci/grails-audit-trail/issues"]
    def scm = [url: "https://github.com/9ci/grails-audit-trail"]
    def documentation = "http://grails.org/plugin/audit-trail"

    def pluginExcludes = [
            "grails-app/views/**/*",
            'grails-app/domain/**',
            'grails-app/controllers/**',
            'grails-app/conf/**',
            'nine/tests/**',
            'test/**'
    ]

    def license = "APACHE"

    def loadAfter = ['hibernate', 'hibernate4']

    Closure doWithSpring() {
        { ->
            Closure closure = AuditTrailsGrailsPluginSupport.doWithSpring
            closure.delegate = delegate
            closure.call()
        }
    }

}
