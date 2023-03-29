package nuaa.zk.s08.bulider.xml;

import nuaa.zk.s08.bulider.BaseBuilder;
import nuaa.zk.s08.mapping.MapperStatement;
import nuaa.zk.s08.mapping.SqlCommandType;
import nuaa.zk.s08.mapping.SqlSource;
import nuaa.zk.s08.scripting.LanguageDriver;
import nuaa.zk.s08.session.Configuration;
import org.dom4j.Element;

import java.util.Locale;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/27 15:17
 */
public class XMLStatementBuilder extends BaseBuilder {
    private Element element;
    private String currentNamespace;
    public XMLStatementBuilder(Configuration configuration, Element element, String currentNamespace) {
        super(configuration);
        this.element = element;
        this.currentNamespace = currentNamespace;
    }
    //解析语句(select|insert|update|delete)
    //<select
    //  id="selectPerson"
    //  parameterType="int"
    //  parameterMap="deprecated"
    //  resultType="hashmap"
    //  resultMap="personResultMap"
    //  flushCache="false"
    //  useCache="true"
    //  timeout="10000"
    //  fetchSize="256"
    //  statementType="PREPARED"
    //  resultSetType="FORWARD_ONLY">
    //  SELECT * FROM PERSON WHERE ID = #{id}
    //</select>
    public void parseStatementNode() {
        String id = element.attributeValue("id");
        // 参数类型
        String parameterType = element.attributeValue("parameterType");
        Class<?> parameterTypeClass = resolveAlias(parameterType);
        // 结果类型
        String resultType = element.attributeValue("resultType");
        Class<?> resultTypeClass = resolveAlias(resultType);
        // 获取命令类型(select|insert|update|delete)
        String nodeName = element.getName();
        SqlCommandType sqlCommandType = SqlCommandType.valueOf(nodeName.toUpperCase(Locale.ENGLISH));

        // 获取默认语言驱动器
        Class<?> langClass = configuration.getLanguageRegistry().getDefaultDriverClass();
        LanguageDriver langDriver = configuration.getLanguageRegistry().getDriver(langClass);

        SqlSource sqlSource = langDriver.createSqlSource(configuration, element, parameterTypeClass);

        MapperStatement mappedStatement = new MapperStatement.Builder(configuration, currentNamespace + "." + id, sqlCommandType, sqlSource, resultTypeClass).build();

        // 添加解析 SQL
        configuration.addMappedStatement(mappedStatement);
    }
}
