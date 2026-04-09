package Project.Annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EducationInfo {
    boolean isMutable()
            default true;
}