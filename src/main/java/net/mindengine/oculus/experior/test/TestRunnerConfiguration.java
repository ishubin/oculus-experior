package net.mindengine.oculus.experior.test;

import java.util.Collection;

import net.mindengine.oculus.experior.test.descriptors.TestDescriptor;
import net.mindengine.oculus.experior.test.resolvers.actions.ActionResolver;
import net.mindengine.oculus.experior.test.resolvers.cleanup.CleanupResolver;
import net.mindengine.oculus.experior.test.resolvers.dataprovider.DataDependencyResolver;
import net.mindengine.oculus.experior.test.resolvers.dataprovider.DataProviderResolver;
import net.mindengine.oculus.experior.test.resolvers.errors.ErrorResolver;
import net.mindengine.oculus.experior.test.resolvers.parameters.ParameterResolver;
import net.mindengine.oculus.experior.test.resolvers.rollbacks.RollbackResolver;
import net.mindengine.oculus.experior.test.resolvers.test.TestResolver;

/**
 * Contains all needed resolvers to provide test run logic
 * @author Ivan Shubin
 *
 */
public class TestRunnerConfiguration {
    
    /**
     * Stores classes for annotations which are supported for test runner. Used in {@link TestDescriptor} in order to fetch all important fields of tests.  
     */
    private Collection<Class<?>> supportedFieldAnnotations;
    
    /**
     * Stores classes for annotations which are supported for test runner. Used in {@link TestDescriptor} in order to fetch all important events of tests. 
     */
    private Collection<Class<?>> supportedEventAnnotations;
    
    private CleanupResolver cleanupResolver;
    private ParameterResolver parameterResolver;
    private DataDependencyResolver dataDependencyResolver;
    private DataProviderResolver dataProviderResolver;
    private ActionResolver actionResolver;
    private RollbackResolver rollbackResolver;
    private ErrorResolver errorResolver;
    private TestResolver testResolver;
    
    
    public DataDependencyResolver getDataDependencyResolver() {
        return dataDependencyResolver;
    }
    public void setDataDependencyResolver(DataDependencyResolver dataDependencyResolver) {
        this.dataDependencyResolver = dataDependencyResolver;
    }
    public DataProviderResolver getDataProviderResolver() {
        return dataProviderResolver;
    }
    public void setDataProviderResolver(DataProviderResolver dataProviderResolver) {
        this.dataProviderResolver = dataProviderResolver;
    }
    public void setParameterResolver(ParameterResolver parameterResolver) {
        this.parameterResolver = parameterResolver;
    }
    public ParameterResolver getParameterResolver() {
        return parameterResolver;
    }
    public void setCleanupResolver(CleanupResolver cleanupResolver) {
        this.cleanupResolver = cleanupResolver;
    }
    public CleanupResolver getCleanupResolver() {
        return cleanupResolver;
    }
    public void setSupportedFieldAnnotations(Collection<Class<?>> supportedFieldAnnotations) {
        this.supportedFieldAnnotations = supportedFieldAnnotations;
    }
    public Collection<Class<?>> getSupportedFieldAnnotations() {
        return supportedFieldAnnotations;
    }
    public Collection<Class<?>> getSupportedEventAnnotations() {
        return supportedEventAnnotations;
    }
    public void setSupportedEventAnnotations(Collection<Class<?>> supportedEventAnnotations) {
        this.supportedEventAnnotations = supportedEventAnnotations;
    }
    public void setActionResolver(ActionResolver actionResolver) {
        this.actionResolver = actionResolver;
    }
    public ActionResolver getActionResolver() {
        return actionResolver;
    }
    public void setRollbackResolver(RollbackResolver rollbackResolver) {
        this.rollbackResolver = rollbackResolver;
    }
    public RollbackResolver getRollbackResolver() {
        return rollbackResolver;
    }
    public void setErrorResolver(ErrorResolver errorResolver) {
        this.errorResolver = errorResolver;
    }
    public ErrorResolver getErrorResolver() {
        return errorResolver;
    }
    public void setTestResolver(TestResolver testResolver) {
        this.testResolver = testResolver;
    }
    public TestResolver getTestResolver() {
        return testResolver;
    }

}
