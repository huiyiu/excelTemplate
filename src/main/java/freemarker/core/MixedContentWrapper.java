//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package freemarker.core;

import freemarker.template.TemplateException;
import java.io.IOException;

public class MixedContentWrapper extends TemplateElement {
    MixedContent mixedContent;

    public MixedContentWrapper(MixedContent mixedContent) {
        this.mixedContent = mixedContent;
    }

    void accept(Environment env) throws TemplateException, IOException {
        EnvironmentWrapper environmentWrapper = new EnvironmentWrapper(env);

        for(int i = 0; i < this.mixedContent.nestedElements.size(); ++i) {
            TemplateElement element = (TemplateElement)this.mixedContent.nestedElements.get(i);
            environmentWrapper.visit(element);
        }

    }

    public String getDescription() {
        return this.mixedContent.getDescription();
    }

    public String getCanonicalForm() {
        return this.mixedContent.getCanonicalForm();
    }
}
