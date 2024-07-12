package api.testcases;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.userEndPoints;
import api.payload.user;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import propertyfile.ConfigReader;

public class UserTest {

	Faker faker;
	user userPayload;
	ConfigReader pro;
	JsonPath jp ;
	public static Logger logger;

	@BeforeClass
	public void generateTestData() {
		faker = new Faker();
		userPayload = new user();
		pro = new ConfigReader();

		// int integerNumber = Integer.parseInt(pro.getPropData("id"));
		userPayload.setId(Integer.parseInt(pro.getPropData("id")));
		userPayload.setUsername(pro.getPropData("username"));
		userPayload.setFirstName(pro.getPropData("firstName"));
		userPayload.setLastName(pro.getPropData("lastName"));
		userPayload.setEmail(pro.getPropData("email"));
		userPayload.setPassword(pro.getPropData("password"));
		userPayload.setPhone(pro.getPropData("phone"));

		System.out.println(userPayload);

		// obtain logger

		logger = LogManager.getLogger("RestAssuredAutomationFramework_test");
	}

	@Test(priority = 1)
	public void testCreateUser() {

		Response response = userEndPoints.createUser(userPayload);

		// log response
		response.then().log().all().extract().response();

		// validation
		 jp = new JsonPath(response.asString());

			int num = jp.getInt("id");
			System.out.println(num);

			String username = jp.getString("username");
			System.out.println(username);

			String firstName = jp.getString("firstName");
			System.out.println(firstName);
			String lastName = jp.getString("lastName");
			System.out.println(lastName);

			// validation
			Assert.assertEquals(jp.getString("username"), "karan");
			Assert.assertEquals(jp.getString("firstName"), "karan");
			Assert.assertEquals(jp.getString("lastName"), "Sharma");
			Assert.assertEquals(jp.getString("email"), "karan1010@gmail.com");

		Assert.assertEquals(response.getStatusCode(), 200);

		// log
		logger.info("Create User executed.");
	}

	@Test(priority = 2)
	public void testGetUserData() {
		Response response = userEndPoints.GetUser(this.userPayload.getUsername());

		System.out.println("Read User Data.");
		// log response
		response.then().log().all();

	 jp = new JsonPath(response.asString());

		int num = jp.getInt("id");
		System.out.println(num);

		String username = jp.getString("username");
		System.out.println(username);

		String firstName = jp.getString("firstName");
		System.out.println(firstName);
		String lastName = jp.getString("lastName");
		System.out.println(lastName);

		// validation
		Assert.assertEquals(jp.getString("username"), "karan");
		Assert.assertEquals(jp.getString("firstName"), "karan");
		Assert.assertEquals(jp.getString("lastName"), "Sharma");
		Assert.assertEquals(jp.getString("email"), "karan1010@gmail.com");
		Assert.assertEquals(response.getStatusCode(), 200);

		// log
		logger.info("Get User Data executed.");
	}

	@Test(priority = 3)
	public void testUpdateUser() {
		userPayload.setFirstName(pro.getPropData("updatedfirstName"));
		Response response = userEndPoints.UpdateUser(this.userPayload.getUsername(), userPayload);

		// log response
		response.then().log().all().extract().response();

		 jp = new JsonPath(response.asString());

		// validation
		String username = jp.getString("username");
		System.out.println(username);

		String firstName = jp.getString("firstName");
		System.out.println(firstName);
		String lastName = jp.getString("lastName");
		System.out.println(lastName);

		// validation
		Assert.assertEquals(jp.getString("username"), "karan");
		Assert.assertEquals(jp.getString("firstName"), "nikhil");
		Assert.assertEquals(jp.getString("lastName"), "Sharma");
		Assert.assertEquals(jp.getString("email"), "karan1010@gmail.com");

		Assert.assertEquals(response.getStatusCode(), 200);

		// Read User data to check if first name is updated

		Response responsePostUpdate = userEndPoints.GetUser(this.userPayload.getUsername());

		System.out.println("After Update User Data.");

		responsePostUpdate.then().log().all();

		// log
		logger.info("Update User executed.");

	}

	@Test(priority = 4)
	public void testDeleteUser() {

		Response response = userEndPoints.DeleteUser(this.userPayload.getUsername());

		System.out.println("Delete User Data.");

		// log response
		response.then().log().all();

		// validation
		Assert.assertEquals(response.getStatusCode(), 200);

		// log
		logger.info("Delete User executed.");

	}
}
