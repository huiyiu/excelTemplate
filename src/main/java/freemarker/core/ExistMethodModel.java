//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package freemarker.core;

import freemarker.cache.TemplateCache;
import freemarker.template.Template;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;
import java.io.IOException;
import java.util.List;

public class ExistMethodModel implements TemplateMethodModel {
    public static final String METHOD_NAME = "exist";

    public ExistMethodModel() {
    }

    public Object exec(List arguments) throws TemplateModelException {
        if (arguments.size() != 1) {
            throw new TemplateModelException("Wrong arguments");
        } else {
            String name = (String)arguments.get(0);
            return this.isExist(name, (String)null, true);
        }
    }

    public boolean isExist(String name, String encoding, boolean parse) {
        try {
            Environment env = Environment.getCurrentEnvironment();
            String includeTemplateName = TemplateCache.getFullTemplatePath(env, this.getTemplatePath(env), name);
            Template template = env.getTemplateForInclusion(includeTemplateName, encoding, parse);
            return template != null;
        } catch (IOException var7) {
            return false;
        }
    }

    public String getTemplatePath(Environment env) {
        String templateName = env.getTemplate().getName();
        int lastSlash = templateName.lastIndexOf(47);
        return lastSlash == -1 ? "" : templateName.substring(0, lastSlash + 1);
    }
}
