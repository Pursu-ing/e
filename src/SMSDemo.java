import java.util.Random;

public class SMSDemo {
    public static void main(String[] args) {
        send();
    }

    public static boolean send(){
        Random r = new Random();
        // 生成6位随机数（注意是6位）
        int code = r.nextInt(900000)+100000;
        System.out.println("[快递e栈]本次操作验证码为："+code);
        return true;
    }
}
