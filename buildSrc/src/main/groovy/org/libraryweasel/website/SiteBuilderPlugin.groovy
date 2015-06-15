package org.libraryweasel.website

import com.google.common.io.Files
import groovy.io.FileType
import groovy.json.JsonSlurper
import groovy.transform.Immutable
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Copy
import org.libraryweasel.website.helpers.FileExtensionMapper
import org.pegdown.PegDownProcessor

class SiteBuilderPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {

        JsonSlurper jsonSlurper = new JsonSlurper()
        FileExtensionMapper fileExtensionMapper = new FileExtensionMapper()
        PegDownProcessor markDownProcessor = new PegDownProcessor()

        project.task('processResources', type:Copy) {
            from("$project.projectDir/src/site/resources")
            into "$project.buildDir/website"
        }

        project.task('buildSite', dependsOn:[':processResources']) << {
            //TODO process md blog posts
            File dir = new File(project.projectDir, "src/site/contents/blog")
            def blogs = []
            dir.eachFile(FileType.FILES) { file ->
                if (Files.getFileExtension(file.getName()) == 'json') {
                    def blogMetadata = jsonSlurper.parse(file)
                    if(fileExtensionMapper.switchFileExtension(file, 'md').isFile()) {
                        blogs << new Blog(blogMetadata.blogTitle, blogMetadata.pubDate, markDownProcessor.markdownToHtml(fileExtensionMapper.switchFileExtension(file, 'md').text))
                    } else if (fileExtensionMapper.switchFileExtension(file, 'html').isFile()) {
                        blogs << new Blog(blogMetadata.blogTitle, blogMetadata.pubDate, fileExtensionMapper.switchFileExtension(file, 'html').text)
                    } else {
                        throw new RuntimeException("$file.path does not have content file.")
                    }
                }
            }
            //TODO fill into index
            //TODO write index
            //process index template and write index
        }
    }
}

@Immutable
class Blog {
    String title
    String date
    String content
}
