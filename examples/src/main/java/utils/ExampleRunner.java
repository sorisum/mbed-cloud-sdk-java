package utils;

import java.util.List;

import org.junit.Test;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

public class ExampleRunner extends BlockJUnit4ClassRunner {

    public ExampleRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    protected List<FrameworkMethod> computeTestMethods() {
        List<FrameworkMethod> list = getTestClass().getAnnotatedMethods(Test.class);
        List<FrameworkMethod> list2 = getTestClass().getAnnotatedMethods(Example.class);
        if (list == null || list.isEmpty()) {
            return list2;
        }
        if (list2 == null || list2.isEmpty()) {
            return list;
        }
        list2.addAll(list);
        return list2;

    }

    @Override
    protected void validateTestMethods(List<Throwable> errors) {
        validatePublicVoidNoArgMethods(Test.class, false, errors);
        validatePublicVoidNoArgMethods(Example.class, false, errors);
    }

}
