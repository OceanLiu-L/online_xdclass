package net.xdclass.online_xdclass;

import io.jsonwebtoken.Claims;
import net.xdclass.online_xdclass.model.entity.User;
import net.xdclass.online_xdclass.utils.JWTUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OnlineXdclassApplicationTests {

	@Test
	public void testToken(){
		User user = new User();
		user.setId(66);
		user.setName("小柰子");
		user.setPhone("65545133");
		user.setHeadImg("hjalksdasd5a4sd56a");

		String token = JWTUtils.geneJsonWebToken(user);

		System.out.println(token);

		System.out.println("===============================================");

		Claims claims = JWTUtils.checkJWT(token);

		System.out.println(claims.get("name") + ", " + claims.get("phone"));

	}



}
