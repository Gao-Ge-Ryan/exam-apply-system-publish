package top.gaogle;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.gaogle.pojo.enums.AuthorityEnum;

@SpringBootTest
public class EnumTest {

    @Test
    void testShift() {
        // 8 字节  64位
        Long shift;
        for (int i = 0; i <= 63; i++) {
            // 逻辑位移
            shift = 1L << i;
            System.out.println(Long.toBinaryString(1L << i) + "   ============  " + shift);
        }
    }

    @Test
    void testEnum() {
        for (AuthorityEnum authorityEnum : AuthorityEnum.getAllParentEnum()) {
            System.out.println(authorityEnum + " == " + authorityEnum.shift());
            for (AuthorityEnum child : authorityEnum.children()) {

                System.out.print(child +" == "+ child.shift()+"; ");
            }
            System.out.println("下个模块");
        }
    }

    @Test
    void testAuth() {
        Long num2 =2L;
        Long num4 =4L;
        Long num8 =8L;
        Long total =num2 | num4 |num8;

        System.out.println(total +" == "+ Long.toBinaryString(total));
    }

    @Test
    void testVert() {
        Long num14 =14L;

        Long num4 =4L;
        System.out.println(num4==(num14 & num4));

        System.out.println(6L==(num14 & 6L));
        System.out.println(16L==(num14 & 16L));


    }

}
