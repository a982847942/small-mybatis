package nuaa.zk.test07;

import com.alibaba.fastjson.JSON;
import nuaa.zk.s07.reflection.MetaClass;
import nuaa.zk.s07.reflection.Reflector;
import nuaa.zk.s07.reflection.invoke.Invoker;
import nuaa.zk.test07.po.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/25 21:46
 */
public class ProcessingTest {
    private final Logger logger = LoggerFactory.getLogger(ProcessingTest.class);
    @Test
    public void test() throws Exception {
        Reflector reflector = new Reflector(User.class);
        logger.info("reflector:{}",reflector.getDefaultConstructor());
        logger.info("hasUserId getter:{}",reflector.hasGetter("userId"));
        logger.info("getter:{}", JSON.toJSON(reflector.getGetablePropertyNames()));
        logger.info("setter:{}", JSON.toJSON(reflector.getSetablePropertyNames()));
        logger.info("User caseInsensitive:{}", JSON.toJSON(reflector.getCaseInsensitivePropertyMap()));
        User user = new User();
        Invoker setInvoker = reflector.getSetInvoker("userId");
        setInvoker.invoke(user,new Object[]{"10001"});
        Invoker invoker = reflector.getGetInvoker("userId");
        Object res = invoker.invoke(user, null);
        logger.info("userId is : {}",res);
    }

    @Test
    public void test2(){
        Teacher teacher = new Teacher();
        List<Teacher.Student> students = new ArrayList<>();
        Teacher.Student student = new Teacher.Student();
        student.setId("10001");
        students.add(student);
        teacher.setStudents(students);
        teacher.setName("ZL");
        MetaClass metaClass = MetaClass.forClass(Teacher.class);
        logger.info("metaClass for students:,{}",metaClass.findProperty("students[0].id"));
//        logger.info("metaClass students setType:,{}",metaClass.getSetterType("students"));
        logger.info("metaClass students getType:,{}",metaClass.getGetterType("students"));
        logger.info("metaClass students getType:,{}",metaClass.getGetterType("students[0].id"));
        logger.info("metaClass students hasGetter:,{}",metaClass.getGetterType("students[0].id"));
//        logger.info("metaClass students hasSetter:,{}",metaClass.getSetterType("students[0].id"));
    }


    static class Teacher {

        private String name;

        private double price;

        private List<Student> students;

        private Student student;

        public static class Student {

            private String id;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public List<Student> getStudents() {
            return students;
        }

        public void setStudents(List<Student> students) {
            this.students = students;
        }

        public Student getStudent() {
            return student;
        }

        public void setStudent(Student student) {
            this.student = student;
        }
    }
}
