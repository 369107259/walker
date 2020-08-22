package alone.walker;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@SpringBootTest
class WalkerApplicationTests {

    @Test
    void contextLoads() {


    }

    public static void main(String[] args) {
        Random random = new Random();
        for (int i = 0; i <100 ; i++) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(90).append(41);
            stringBuffer.append(System.currentTimeMillis());
            stringBuffer.append(random.nextInt(9));
            System.out.println(stringBuffer.toString() );
        }
    }

}
