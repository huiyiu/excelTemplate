package freemarker.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Utils {
    public Utils() {
    }

    public Map map() {
        return new HashMap();
    }

    public Map lmap() {
        return new LinkedHashMap();
    }

    public List list() {
        return new ArrayList();
    }
}