/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2025-09-06 14:48:00 UTC+08:00
 ****************************************************/
package host.fairy.recursion;

/**
 * 递归实现字符串反向打印
 *
 * @author Beau Dean
 */
public class ReverseString {
    public static void main(String[] args) {
        String s = "abcd";
        reversePrintString(s, 0);
    }
    
    public static void reversePrintString(String s, Integer i) {
        if (i.equals(s.length())) {
            return;
        }
        
        reversePrintString(s, i + 1);
        
        System.out.println(s.charAt(i));
    }
}
