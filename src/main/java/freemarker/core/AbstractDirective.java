//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package freemarker.core;

import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;
import java.util.Map;

public abstract class AbstractDirective implements TemplateDirectiveModel {
    public AbstractDirective() {
    }

    String getTemplatePath(Environment env) {
        String templateName = env.getTemplate().getName();
        int lastSlash = templateName.lastIndexOf(47);
        return lastSlash == -1 ? "" : templateName.substring(0, lastSlash + 1);
    }

    String getRequiredParam(Map params, String key) throws TemplateException {
        Object value = params.get(key);
        if (value != null && value.toString().trim().length() != 0) {
            return value.toString();
        } else {
            throw new TemplateModelException("not found required parameter:" + key + " for directive " + this.toString());
        }
    }

    String getParam(Map params, String key, String defaultValue) throws TemplateException {
        Object value = params.get(key);
        return value == null ? defaultValue : value.toString();
    }

    public String toString() {
        return "<@" + this.getDirectiveName() + ">";
    }

    public abstract String getDirectiveName();
}
