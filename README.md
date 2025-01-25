# Running the Project

To run the application, follow these steps:

## Prerequisites

- Ensure you have Java installed (version 17 or later is recommended).
- Verify that Maven is installed and configured properly.
- Set the `JAVA_HOME` environment variable to point to your JDK installation.

## Running the Application

1. Open a terminal and clone this repository by executing:
    ```bash
    git clone https://github.com/transactionsTFG/hotelMTA.git
    ```
2. Either use the same terminal or open a terminal and navigate to the project's root directory.
3. Use the following Maven command to run the application:
   ```bash
   mvn spring-boot:run
   ```
4. The WSDL files are in these locations: 
    - http://localhost:8080/hotelMTA/ws/bookings.wsdl
    - http://localhost:8080/hotelMTA/ws/customers.wsdl
    - http://localhost:8080/hotelMTA/ws/rooms.wsdl
5. To see responses, send POST requests to this location:
    - http://localhost:8080/hotelMTA/ws 
    
    with the following bodies:
    ```xml
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                    xmlns:tns="http://ucm.tfg/hotelMTA">
    <soapenv:Header/>
    <soapenv:Body>
        <tns:getBookingRequest>
            <tns:id>1</tns:id>
        </tns:getBookingRequest>
    </soapenv:Body>
    </soapenv:Envelope>
    ```
    ```xml
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                    xmlns:tns="http://ucm.tfg/hotelMTA">
    <soapenv:Header/>
    <soapenv:Body>
        <tns:getCustomerRequest>
            <tns:dni>12345678A</tns:dni>
        </tns:getCustomerRequest>
    </soapenv:Body>
    </soapenv:Envelope>
    ```
    ```xml
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                    xmlns:tns="http://ucm.tfg/hotelMTA">
    <soapenv:Header/>
    <soapenv:Body>
        <tns:getRoomRequest>
            <tns:number>1</tns:number>
        </tns:getRoomRequest>
    </soapenv:Body>
    </soapenv:Envelope>
    ```
## Troubleshooting

- If you encounter issues related to `JAVA_HOME`, ensure that it is correctly set in your environment.

## Additional Information

- Consult the link https://spring.io/guides if any problem occurs

