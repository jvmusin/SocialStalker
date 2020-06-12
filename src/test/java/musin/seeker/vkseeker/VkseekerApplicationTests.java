package musin.seeker.vkseeker;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.IfProfileValue;

@SpringBootTest
@IfProfileValue(name = "production")
public class VkseekerApplicationTests {

//  @Test
//  public void contextLoads() {
//  }

}
