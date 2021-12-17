//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package freemarker.core;

import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

public class FormatDirective extends AbstractDirective {
    public static final String DIRECTIVE_NAME = "format";

    public FormatDirective() {
    }

    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        if (body != null) {
            String whiteSpace = this.getRequiredParam(params, "blank");
            int blank = Integer.parseInt(whiteSpace);
            if (blank == 0) {
                body.render(env.getOut());
            } else {
                StringWriter writer = new StringWriter();
                body.render(writer);
                String results = this.format(writer.toString(), blank);
                if (results != null) {
                    env.getOut().write(results);
                }

            }
        }
    }

    public String format(String source, int blank) throws IOException {
        if (source == null) {
            return null;
        } else {
            StringBuffer append = null;
            if (blank == 0) {
                return source;
            } else {
                if (blank > 0) {
                    append = new StringBuffer();

                    for(int i = 0; i < blank; ++i) {
                        append.append(" ");
                    }
                }

                StringReader stringReader = new StringReader(source);
                BufferedReader reader = new BufferedReader(stringReader);
                StringBuffer buf = new StringBuffer();
                String line = null;
                int lineNum = 0;
                String sep = System.getProperty("line.separator");

                while(true) {
                    while((line = reader.readLine()) != null) {
                        ++lineNum;
                        if (lineNum >= 2) {
                            buf.append(sep);
                        }

                        if (blank > 0) {
                            buf.append(append).append(line);
                        } else {
                            int beginIndex = 0;
                            int len = line.length();
                            StringBuffer spaces = new StringBuffer();

                            for(int i = 0; i < blank * -1 && i < len; ++i) {
                                char c = line.charAt(i);
                                if (c == ' ') {
                                    ++beginIndex;
                                    spaces.append(" ");
                                } else {
                                    if (c != '\t') {
                                        break;
                                    }

                                    ++beginIndex;
                                    spaces.append("    ");
                                    i += 3;
                                }
                            }

                            if (beginIndex > 0) {
                                if (spaces.length() > blank * -1) {
                                    buf.append(spaces.delete(0, blank * -1));
                                }

                                buf.append(line.substring(beginIndex));
                            } else {
                                buf.append(line);
                            }
                        }
                    }

                    return buf.toString();
                }
            }
        }
    }

    public String getDirectiveName() {
        return "format";
    }
}
