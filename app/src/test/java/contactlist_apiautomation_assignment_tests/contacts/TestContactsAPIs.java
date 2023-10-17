package contactlist_apiautomation_assignment_tests.contacts;

import contactlist_apiautomation_assignment_tests.users.TestUserAPIs;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import resources.APIResources;
import resources.Utils;
import resources.requestbody.contacts.AddContactPayload;
import resources.responsebody.contacts.AddContactResponse;
import resources.testdata.contacts.TestDataBuilder_contact;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class TestContactsAPIs {
    private TestDataBuilder_contact testDataBuilderContact;
    private String firstName;
   private String lastName;
   private String email;
    private String phoneNumber;
   private String token;


    @BeforeClass
    public void setUp() throws IOException {
        testDataBuilderContact = new TestDataBuilder_contact();
        firstName = Utils.generateFirstName();
        lastName = Utils.generateLastName();
        email = Utils.generateEmail();
        phoneNumber = Utils.generatePhoneNumber();
        new TestUserAPIs().shouldTestCreateUser();
        token = TestUserAPIs.token;
    }

    @Test
    public void shouldTestAddContact() throws IOException {
        //Arrange
        String resource = APIResources.AddContactAPI.getResource();
        AddContactPayload addContactPayload = testDataBuilderContact.createAddContactPayload(firstName, lastName, email, phoneNumber);
        //Act
        AddContactResponse addContactResponse = given().spec(Utils.requestSpecificationBuilder())
                .header("Authorization", "Bearer " + token)
                .body(addContactPayload)
                .when().post(resource)
                .then().spec(Utils.responseSpecificationBuilder()).assertThat().statusCode(201)
                .extract().response().as(AddContactResponse.class);
        //Assert
        Assert.assertEquals(addContactResponse.getFirstName(),firstName);
    }
}
