import com.onsdomein.proxy.ProxyOnsDomein;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    private static Scanner in ;
    private static ProxyOnsDomein proxyOnsDomein;
    private static String myId;
    private static String hc_id;
    private static String wrongHc_id;



    @BeforeAll
    public static void init() {
        in = new Scanner(System.in);
        proxyOnsDomein = new ProxyOnsDomein();
        myId = "1234";
        hc_id = "5678";
        wrongHc_id = "wrongID";
    }

    @BeforeEach
    void setUp() {
        try {
            proxyOnsDomein.connectClientToServer(myId);
        } catch (IOException e) {
            System.out.println("Test failed because of: " + e);
        }
    }


    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("1e protocol test: setHc")
    void setHc() {
        // Arrange
        String expectedResponse = "setHc;5678;Arduino received message: TestSetHc and says hi!";
        // Act
        String actualResponse = proxyOnsDomein.sendRequest("setHc",myId, hc_id,"TestSetHc");
        // Assert
        assertEquals(expectedResponse,actualResponse);
    }

    @Test
    @DisplayName("2e protocol test: getHc")
    void getHc() {
        // Arrange
        String expectedResponse = "setHc;5678;Arduino received message: TestGetHc and says hi!";
        // Act
        String actualResponse = proxyOnsDomein.sendRequest("getHc",myId, hc_id,"TestGetHc");
        // Assert
        assertEquals(expectedResponse,actualResponse);
    }

    @Test
    @DisplayName("3e protocol test: setState")
    void setState() {
        // Arrange
        String expectedResponse = "setStateOK";
        // Act
        String actualResponse = proxyOnsDomein.sendRequest("setState",myId, hc_id,"TestSetState");
        // Assert
        assertEquals(expectedResponse,actualResponse);
    }

    @Test
    @DisplayName("4e protocol test: getState")
    void getState() {
        // Arrange
        String expectedResponse = "TestSetState";
        // Act
        String actualResponse = proxyOnsDomein.sendRequest("getState",myId, hc_id,"a");
        // Assert
        assertEquals(expectedResponse,actualResponse);
    }

    @Test
    @DisplayName("5e protocol test: setConfig")
    void setConfig() {
        // Arrange
        String expectedResponse = "setConfigOK";
        // Act
        String actualResponse = proxyOnsDomein.sendRequest("setConfig",myId, hc_id,"TestSetConfig");
        // Assert
        assertEquals(expectedResponse,actualResponse);
    }

    @Test
    @DisplayName("6e protocol test: getConfig")
    void getConfig() {
        // Arrange
        String expectedResponse = "TestSetConfig";
        // Act
        String actualResponse = proxyOnsDomein.sendRequest("getConfig",myId, hc_id,"a");
        // Assert
        assertEquals(expectedResponse,actualResponse);
    }

    @Test
    @DisplayName("Wrong protocol text used")
    void wrongProtocolText() {
        // Arrange
        String expectedResponse = "Error: not conform to protocol.";
        // Act
        String actualResponse = proxyOnsDomein.sendRequest("WRONG_PROTOCOL_TEXT",myId, hc_id,"SomeMessage");
        // Assert
        assertEquals(expectedResponse,actualResponse);
    }

    @Test
    @DisplayName("Wrong protocol used")
    void wrongProtocolLength() {
        // Arrange
        String expectedResponse = "Error: not conform to protocol.";
        // Act
        String actualResponse = proxyOnsDomein.sendRequest("WRONG_PROTOCOL_LENGTH",myId, hc_id,"protocol;length");
        // Assert
        assertEquals(expectedResponse,actualResponse);
    }

    @Test
    @DisplayName("Connect with offline HC")
    void connectOfflineHC() {
        // Arrange
        String expectedResponse = "No connection with HC";
        // Act
        String actualResponse = proxyOnsDomein.sendRequest("getHc",myId, wrongHc_id,"message");
        // Assert
        assertEquals(expectedResponse,actualResponse);
    }

    @Test
    @DisplayName("Invoke dbLog when querie size exceeds")
    void exceedDbLogSize() {
        // Arrange
        String expectedResponse = "setConfigOK";
        String actualResponse = "";
        // Act
        for (int i = 0; i < 100;i ++) {
            actualResponse = proxyOnsDomein.sendRequest("setConfig", myId, hc_id, "message");
        }
        // Assert
        assertEquals(expectedResponse,actualResponse);
    }
}