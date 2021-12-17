//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package freemarker.core;

import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import java.io.IOException;

public class MacroWrapper extends TemplateElement implements TemplateModel {
    Macro macro;

    public MacroWrapper(Macro macro) {
        this.macro = macro;
    }

    void accept(Environment env) throws TemplateException, IOException {
        EnvironmentWrapper environmentWrapper = new EnvironmentWrapper(env);
        environmentWrapper.visitMacroDef(this.macro);
    }

    public String getDescription() {
        return this.macro.getDescription();
    }

    public String getCanonicalForm() {
        return this.macro.getCanonicalForm();
    }
}
