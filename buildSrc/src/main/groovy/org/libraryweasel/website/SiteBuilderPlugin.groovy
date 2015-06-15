package org.libraryweasel.website

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Copy

class SiteBuilderPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {

        project.task('processResources', type:Copy) {
            from("$project.projectDir/src/site/resources")
            into "$project.buildDir/website"
        }

        project.task('buildSite', dependsOn:[':processResources']) << {
            //TODO process md blog posts

            //TODO fill into index

            //TODO write index

        }
    }
}
