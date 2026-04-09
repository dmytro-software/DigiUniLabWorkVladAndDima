package Project.Annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface WorkLoad {
    double maxRate() default 1.0;
    double minRate() default 0.25;
}