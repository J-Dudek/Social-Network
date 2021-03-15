package mjp.socialnetwork.post;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    /**
     * Logger de la classe
     **/
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    // Cette méthode est appelée à chaque fois (et avant) qu'une méthode du
    // package est interceptée
    @Before("execution(* mjp.socialnetwork.post.*.*(..))")
    public void logMethodEntry(JoinPoint joinPoint) {

        Object[] args = joinPoint.getArgs();

        // Nom de la méthode interceptée
        String name = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        StringBuilder sb = new StringBuilder(className + " > " + name + " Begin - called with: [");

        // Liste des valeurs des arguments reçus par la méthode
        for (int i = 0; i < args.length; i++) {
            Object o = args[i];
            sb.append("'").append(o).append("'");
            sb.append((i == (args.length - 1)) ? "" : ", ");
        }
        sb.append("]");

        logger.info(sb.toString());
    }

    @AfterReturning(pointcut = "execution(* mjp.socialnetwork.post.*.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        // Nom de la méthode interceptée
        String name = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        logger.info(className + " > " + name + " End - returning: [" + result + "]");
    }
}