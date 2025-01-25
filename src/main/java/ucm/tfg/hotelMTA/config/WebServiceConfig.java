package ucm.tfg.hotelMTA.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(
            ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/hotelMTA/ws/*");
    }

    @Bean(name = "rooms")
    public DefaultWsdl11Definition defaultWsdl11DefinitionRooms(XsdSchema roomsSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("RoomsPort");
        wsdl11Definition.setLocationUri("/hotelMTA/ws");
        wsdl11Definition.setTargetNamespace("http://ucm.tfg/hotelMTA");
        wsdl11Definition.setSchema(roomsSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema roomsSchema() {
        return new SimpleXsdSchema(new ClassPathResource("rooms.xsd"));
    }

    @Bean(name = "customers")
    public DefaultWsdl11Definition defaultWsdl11DefinitionCustomers(XsdSchema customersSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("CustomersPort");
        wsdl11Definition.setLocationUri("/hotelMTA/ws");
        wsdl11Definition.setTargetNamespace("http://ucm.tfg/hotelMTA");
        wsdl11Definition.setSchema(customersSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema customersSchema() {
        return new SimpleXsdSchema(new ClassPathResource("customers.xsd"));
    }

    @Bean(name = "bookings")
    public DefaultWsdl11Definition defaultWsdl11DefinitionBookings(XsdSchema bookingsSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("BookingsPort");
        wsdl11Definition.setLocationUri("/hotelMTA/ws");
        wsdl11Definition.setTargetNamespace("http://ucm.tfg/hotelMTA");
        wsdl11Definition.setSchema(bookingsSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema bookingsSchema() {
        return new SimpleXsdSchema(new ClassPathResource("bookings.xsd"));
    }
}