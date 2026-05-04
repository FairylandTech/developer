/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2025-08-28 15:22:21 UTC+08:00
 ****************************************************/
package host.fairy.exception.employee;

/**
 * 员工被禁用异常
 *
 * @author Beau Dean
 */
public class EmployeeForbiddenException extends BaseException {
    public EmployeeForbiddenException(String message) {
        super(message);
    }
}
