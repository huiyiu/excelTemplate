//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package jxl.report;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.StringTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.core.Environment;
import freemarker.core.ExistMethodModel;
import freemarker.core.ExtendsDirective;
import freemarker.core.FormatDirective;
import freemarker.core.Utils;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.log4j.Logger;

public class FreemarkerHelper {
    private static Logger LOG = Logger.getLogger(FreemarkerHelper.class);
    private static final String DEFAULT_ENCODING = "UTF-8";

    public FreemarkerHelper() {
    }

    public static Configuration createConfiguration() {
        Configuration configuration = new Configuration();
        configuration.setSharedVariable("extends", new ExtendsDirective());
        configuration.setSharedVariable("format", new FormatDirective());
        configuration.setSharedVariable("exist", new ExistMethodModel());
        return configuration;
    }

    public static TemplateLoader createTemplateLoader(String templatePath) throws IOException {
        if (templatePath == null) {
            return new ClassTemplateLoader(FreemarkerHelper.class, "/");
        } else {
            List<TemplateLoader> loaders = new ArrayList();
            File curf = new File(templatePath);
            if (curf.exists()) {
                loaders.add(new FileTemplateLoader(curf));
                LOG.debug("load template from " + curf);
            }

            URL classUrl = FreemarkerHelper.class.getProtectionDomain().getCodeSource().getLocation();
            if ("file".equals(classUrl.getProtocol())) {
                String parent = (new File(classUrl.getPath())).getParent();
                File clsf = new File(parent, templatePath);
                if (clsf.exists()) {
                    loaders.add(new FileTemplateLoader(clsf));
                    LOG.debug("load template from " + clsf);
                }
            }

            loaders.add(new ClassTemplateLoader(FreemarkerHelper.class, templatePath));
            TemplateLoader[] lds = new TemplateLoader[loaders.size()];

            for(int i = 0; i < loaders.size(); ++i) {
                lds[i] = (TemplateLoader)loaders.get(i);
            }

            return new MultiTemplateLoader(lds);
        }
    }

    public static StringWriter produceAsStringWriter(Map context, String templatePath, String templateName) throws TemplateException, IOException {
        return produceAsStringWriter((TemplateLoader)null, context, "UTF-8", templatePath, templateName, "UTF-8");
    }

    public static StringWriter produceAsStringWriter(Map context, String outEncoding, String templatePath, String templateName, String templateEncoding) throws TemplateException, IOException {
        return produceAsStringWriter((TemplateLoader)null, context, outEncoding, templatePath, templateName, templateEncoding);
    }

    public static StringWriter produceAsStringWriter(TemplateLoader loader, Map context, String outEncoding, String templatePath, String templateName, String templateEncoding) throws TemplateException, IOException {
        Configuration cfg = createConfiguration();
        if (loader == null) {
            cfg.setTemplateLoader(createTemplateLoader(templatePath));
        } else {
            cfg.setTemplateLoader(loader);
        }

        StringWriter writer = new StringWriter(1024);
        BufferedWriter bw = new BufferedWriter(writer);
        Template template = getTemplate(cfg, templateEncoding, templateName);
        Environment env = template.createProcessingEnvironment(createContext(context), bw);
        if (outEncoding != null) {
            env.setOutputEncoding(outEncoding);
        }

        env.process();
        bw.flush();
        String tempResult = writer.toString();
        if (tempResult != null && tempResult.trim().length() == 0) {
            LOG.debug("Generated output is empty.");
            return null;
        } else {
            return writer;
        }
    }

    private static SimpleHash createContext(Map context) {
        SimpleHash ctx = new SimpleHash(ObjectWrapper.BEANS_WRAPPER);
        ctx.put("utils", new Utils());
        if (context != null) {
            Iterator iterator = context.entrySet().iterator();

            while(iterator.hasNext()) {
                Entry element = (Entry)iterator.next();
                String key = (String)element.getKey();
                Object value = element.getValue();
                ctx.put(key, value);
            }
        }

        return ctx;
    }

    public static Template getTemplate(Configuration cfg, String encoding, String template) throws IOException {
        return encoding == null ? cfg.getTemplate(template) : cfg.getTemplate(template, encoding);
    }

    public static void produce(Map context, File outFile, String templatePath, String templateName) throws IOException, TemplateException {
        produce((TemplateLoader)null, context, "UTF-8", outFile, templatePath, templateName, "UTF-8");
    }

    public static void produce(Map context, String outEncoding, File outFile, String templatePath, String templateName, String templateEncoding) throws IOException, TemplateException {
        produce((TemplateLoader)null, context, outEncoding, outFile, templatePath, templateName, templateEncoding);
    }

    public static void produce(TemplateLoader loader, Map context, String outEncoding, File outFile, String templatePath, String templateName, String templateEncoding) throws IOException, TemplateException {
        Configuration cfg = createConfiguration();
        if (loader == null) {
            cfg.setTemplateLoader(createTemplateLoader(templatePath));
        } else {
            cfg.setTemplateLoader(loader);
        }

        boolean ex = outFile.exists();
        ensureExistence(outFile);
        Template template = getTemplate(cfg, templateEncoding, templateName);
        BufferedWriter writer = null;

        try {
            if (outEncoding == null) {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
            } else {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), outEncoding));
            }

            Environment env = template.createProcessingEnvironment(createContext(context), writer);
            if (outEncoding != null) {
                env.setOutputEncoding(outEncoding);
            }

            env.process();
        } finally {
            if (writer != null) {
                try {
                    writer.flush();
                } catch (IOException var17) {
                }

                writer.close();
            }

        }

        if (ex) {
            LOG.warn("Replace " + outFile.getAbsolutePath());
        } else {
            LOG.info(outFile.getAbsolutePath());
        }

    }

    private static void ensureExistence(File destination) {
        File dir = destination.getAbsoluteFile().getParentFile();
        if (dir.exists() && !dir.isDirectory()) {
            throw new RuntimeException("The path: " + dir.getAbsolutePath() + " exists, but is not a directory");
        } else if (!dir.exists() && !dir.mkdirs()) {
            if (!dir.getName().equals(".") || !dir.getParentFile().mkdirs()) {
                throw new RuntimeException("unable to create directory: " + dir.getAbsolutePath());
            }
        }
    }

    public static String produceAsString(Map context, String ftl) {
        return produceAsString(context, "UTF-8", ftl, "UTF-8");
    }

    public static String produceAsString(Map context, String outEncoding, String ftl, String templateEncoding) {
        try {
            String templateName = "stringTemplate";
            Configuration cfg = createConfiguration();
            StringTemplateLoader templateLoader = new StringTemplateLoader();
            templateLoader.putTemplate(templateName, ftl);
            cfg.setTemplateLoader(templateLoader);
            if (templateEncoding != null) {
                cfg.setDefaultEncoding(templateEncoding);
            }

            Template template = getTemplate(cfg, templateEncoding, templateName);
            StringWriter writer = new StringWriter(1024);
            Environment env = template.createProcessingEnvironment(createContext(context), writer);
            if (outEncoding != null) {
                env.setOutputEncoding(outEncoding);
            }

            env.process();
            return writer.toString();
        } catch (Exception var10) {
            LOG.error(ftl);
            LOG.error(var10.getMessage());
            throw new RuntimeException(var10);
        }
    }
}
