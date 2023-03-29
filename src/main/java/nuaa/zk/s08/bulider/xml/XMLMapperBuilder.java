package nuaa.zk.s08.bulider.xml;

import nuaa.zk.s08.bulider.BaseBuilder;
import nuaa.zk.s08.io.Resources;
import nuaa.zk.s08.session.Configuration;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/27 15:10
 */
public class XMLMapperBuilder extends BaseBuilder {
    private Element element;
    private String resource;
    private String currentNamespace;
    public XMLMapperBuilder(InputStream inputStream, Configuration configuration, String resource) throws DocumentException {
        this(new SAXReader().read(inputStream), configuration, resource);
    }
    public XMLMapperBuilder(Document document,Configuration configuration,String resource){
        super(configuration);
        this.element = document.getRootElement();
        this.resource = resource;
    }

    public void parse() throws Exception {
        if (!this.configuration.isResourceLoaded(resource)){
            //解析
            configurationElement(element);
            //标记已经加载过
            this.configuration.addLoadedResource(resource);
            //添加MapperFactory
            configuration.addMapper(Resources.classForName(currentNamespace));
        }
    }

    // 配置mapper元素
    // <mapper namespace="org.mybatis.example.BlogMapper">
    //   <select id="selectBlog" parameterType="int" resultType="Blog">
    //    select * from Blog where id = #{id}
    //   </select>
    // </mapper>
    private void configurationElement(Element element) {
        // 1.配置namespace
        currentNamespace = element.attributeValue("namespace");
        if (currentNamespace.equals("")) {
            throw new RuntimeException("Mapper's namespace cannot be empty");
        }

        // 2.配置select|insert|update|delete
        buildStatementFromContext(element.elements("select"));
    }

    // 配置select|insert|update|delete
    private void buildStatementFromContext(List<Element> list) {
        for (Element element : list) {
            final XMLStatementBuilder statementParser = new XMLStatementBuilder(configuration, element, currentNamespace);
            statementParser.parseStatementNode();
        }
    }
}
