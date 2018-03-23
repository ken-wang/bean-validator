package bean.validator.test;

import org.junit.Test;

import bean.validation.ValidationExecutor;
import bean.validation.validator.Length;
import bean.validation.validator.Required;

public class ValidatorTest {

    @Test
    public void test() throws Exception {

        Beanobject bean = new Beanobject();
        bean.setName("Hello");
        bean.setStr("12345667890");

        ValidationExecutor validator = new ValidationExecutor();
        System.out.println(validator.validate(bean).getResultsMessage());
    }

    public static class Beanobject {
        private String name;
        @Required
        @Length(maxLength = 10, minLength = 5)
        private String str;

        @Required
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }
    }

}
