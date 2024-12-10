package main;

import jakarta.xml.ws.Endpoint;
import tfg.hotelmta.soap.HotelWSB;

public class Main {
    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8888/HotelWSB", new HotelWSB());
    }
}
