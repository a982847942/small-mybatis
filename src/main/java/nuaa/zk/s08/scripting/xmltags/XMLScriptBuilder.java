package nuaa.zk.s08.scripting.xmltags;


import nuaa.zk.s08.bulider.BaseBuilder;
import nuaa.zk.s08.mapping.SqlSource;
import nuaa.zk.s08.scripting.defaults.RawSqlSource;
import nuaa.zk.s08.session.Configuration;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class XMLScriptBuilder extends BaseBuilder {

    private Element element;
    private boolean isDynamic;
    private Class<?> parameterType;

    public XMLScriptBuilder(Configuration configuration, Element element, Class<?> parameterType) {
        super(configuration);
        this.element = element;
        this.parameterType = parameterType;
    }

    public SqlSource parseScriptNode() {
        List<SqlNode> contents = parseDynamicTags(element);
        MixedSqlNode rootSqlNode = new MixedSqlNode(contents);
        return new RawSqlSource(configuration, rootSqlNode, parameterType);
    }

    List<SqlNode> parseDynamicTags(Element element) {
        List<SqlNode> contents = new ArrayList<>();
        // element.getText 拿到 SQL
        String data = element.getText();
        contents.add(new StaticTextSqlNode(data));
        return contents;
    }

}
