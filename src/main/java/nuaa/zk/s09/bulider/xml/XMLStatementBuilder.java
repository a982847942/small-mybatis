package nuaa.zk.s09.bulider.xml;

import nuaa.zk.s09.bulider.BaseBuilder;
import nuaa.zk.s09.mapping.MapperStatement;
import nuaa.zk.s09.mapping.SqlCommandType;
import nuaa.zk.s09.mapping.SqlSource;
import nuaa.zk.s09.scripting.LanguageDriver;
import nuaa.zk.s09.session.Configuration;
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

    /**
     *  解析语句(select|insert|update|delete)
     * <select
     *     id="selectPerson"
     *     parameterType="int"
     *     parameterMap="deprecated"
     *     resultType="hashmap"
     *     resultMap="personResultMap"
     *     flushCache="false"
     *     useCache="true"
     *     timeout="10000"
     *     fetchSize="256"
     *     statementType="PREPARED"
     *     resultSetType="FORWARD_ONLY" >
     *
     *     SELECT * FROM PERSON WHERE ID = #{id}
     *
     * </select>
     * id: 接口中的方法名称  currentNameSpace + id = 方法唯一标识符
     * parameterType:参数类型
     * parameterTypeClass: 获取参数类型对应的Class resolveAlias是BaseBuilder中的typeAlias进行解析(注意自定义类型也要传入全限定名)
     * resultType  resultTypeClass同理
     * nodeName: 即select、update、insert、delete
     * sqlCommandType:对应的枚举类
     */

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

        //相应的语句封装
        MapperStatement mappedStatement = new MapperStatement.Builder(configuration, currentNamespace + "." + id, sqlCommandType, sqlSource, resultTypeClass).build();

        // 添加解析 SQL
        configuration.addMappedStatement(mappedStatement);
    }
}
