//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package freemarker.core;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import java.io.IOException;
import java.util.Iterator;

public class EnvironmentWrapper {
    Environment environment;

    public EnvironmentWrapper(Environment environment) {
        this.environment = environment;
    }

    void importMacros(Template template) throws TemplateModelException {
        Iterator it = template.getMacros().values().iterator();

        while(it.hasNext()) {
            this.visitMacroDef((Macro)it.next());
        }

    }

    void visitMacroDef(Macro macro) throws TemplateModelException {
        TemplateModel templateModel = this.environment.getCurrentNamespace().get(macro.getName());
        if (templateModel == null) {
            this.environment.visitMacroDef(macro);
        }

    }

    public void include(Template includedTemplate) throws TemplateException, IOException {
        Template prevTemplate = this.environment.getTemplate();
        this.environment.setParent(includedTemplate);
        this.importMacros(includedTemplate);

        try {
            this.visit(includedTemplate.getRootTreeNode());
        } finally {
            this.environment.setParent(prevTemplate);
        }

    }

    void visit(TemplateElement element) throws TemplateException, IOException {
        if (element instanceof Macro) {
            MacroWrapper macroWrapper = new MacroWrapper((Macro)element);
            this.environment.visit(macroWrapper);
        } else if (element instanceof MixedContent) {
            MixedContentWrapper mixedContentWrapper = new MixedContentWrapper((MixedContent)element);
            this.environment.visit(mixedContentWrapper);
        } else {
            this.environment.visit(element);
        }

    }
}
