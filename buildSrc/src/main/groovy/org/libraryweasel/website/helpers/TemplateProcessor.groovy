package org.libraryweasel.website.helpers

import freemarker.template.*

class TemplateProcessor {
    Configuration cfg = new Configuration();

    TemplateProcessor(File templateDirectory) {
        // Specify the data source where the template files come from. Here I set a
        // plain directory for it, but non-file-system are possible too:
        cfg.setDirectoryForTemplateLoading(templateDirectory);
        // Specify how templates will see the data-model. This is an advanced topic...
        // for now just use this:
        cfg.setObjectWrapper(new DefaultObjectWrapper());
        // Set your preferred charset template files are stored in. UTF-8 is
        // a good choice in most applications:
        cfg.setDefaultEncoding("UTF-8");
        // Sets how errors will appear. Here we assume we are developing HTML pages.
        // For production systems TemplateExceptionHandler.RETHROW_HANDLER is better.
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        // At least in new projects, specify that you want the fixes that aren't
        // 100% backward compatible too (these are very low-risk changes as far as the
        // 1st and 2nd version number remains):
        cfg.setIncompatibleImprovements(new Version(2, 3, 20));  // FreeMarker 2.3.20
    }

    def processTemplate(String templateName, def dataModel, File output) {
        File parent = output.getParentFile() //TODO add more checking here
        parent.mkdirs()
        Template template = cfg.getTemplate(templateName)
        OutputStream os = new FileOutputStream(output)
        Writer writer = new OutputStreamWriter(os)
        template.process(dataModel, writer)
        os.close()
    }

    String processTemplate(String templateName, def dataModel) {
        Template template = cfg.getTemplate(templateName)
        StringBuilder sb = new StringBuilder()
        Writer writer = new OutputStreamWriter(sb)
        template.process(dataModel, writer)
        return sb.toString()
    }
}
