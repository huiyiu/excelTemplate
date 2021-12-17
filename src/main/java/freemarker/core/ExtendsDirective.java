//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package freemarker.core;

import freemarker.cache.TemplateCache;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.utility.StringUtil;
import java.io.IOException;
import java.util.Map;

public class ExtendsDirective extends AbstractDirective {
    public static final String DIRECTIVE_NAME = "extends";

    public ExtendsDirective() {
    }

    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        String name = this.getRequiredParam(params, "path");
        String encoding = this.getParam(params, "encoding", (String)null);
        String parse = this.getParam(params, "parse", "true");
        String includeTemplateName = TemplateCache.getFullTemplatePath(env, this.getTemplatePath(env), name);
        EnvironmentWrapper environmentWrapper = new EnvironmentWrapper(env);
        environmentWrapper.include(env.getTemplateForInclusion(includeTemplateName, encoding, StringUtil.getYesNo(parse)));
    }

    public String getDirectiveName() {
        return "extends";
    }
}
