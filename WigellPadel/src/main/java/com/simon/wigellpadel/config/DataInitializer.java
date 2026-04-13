package com.simon.wigellpadel.config;

import com.simon.wigellpadel.entity.*;
import com.simon.wigellpadel.repository.AddressRepository;
import com.simon.wigellpadel.repository.BookingRepository;
import com.simon.wigellpadel.repository.CourtRepository;
import com.simon.wigellpadel.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;

@Configuration
public class DataInitializer {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final CourtRepository courtRepository;
    private final BookingRepository bookingRepository;

    private final PasswordEncoder passwordEncoder;

    public DataInitializer(CustomerRepository customerRepository, AddressRepository addressRepository,
                           CourtRepository courtRepository, BookingRepository bookingRepository,
                           PasswordEncoder passwordEncoder) {

        this.customerRepository = customerRepository;
        this.addressRepository  = addressRepository;
        this.courtRepository    = courtRepository;
        this.bookingRepository  = bookingRepository;

        this.passwordEncoder    = passwordEncoder;
    }

    @Bean
    public CommandLineRunner initData() {

        String encodedPassword = passwordEncoder.encode("1234");

        return args -> {
            if (customerRepository.count() == 0) {

                Customer customer1 = new Customer();
                customer1.setUsername("a");
                customer1.setPassword(encodedPassword);
                customer1.setRole(Role.ADMIN);
                customer1.setFirstName("a");
                customer1.setLastName("a");
                customer1.setAddresses(new ArrayList<>());
                customerRepository.save(customer1);

                Customer customer2 = new Customer();
                customer2.setUsername("u");
                customer2.setPassword(encodedPassword);
                customer2.setRole(Role.USER);
                customer2.setFirstName("u");
                customer2.setLastName("u");
                customer2.setAddresses(new ArrayList<>());
                customerRepository.save(customer2);

                Customer customer3 = new Customer();
                customer3.setUsername("bamsen54");
                customer3.setPassword(encodedPassword);
                customer3.setRole(Role.ADMIN);
                customer3.setFirstName("Simon");
                customer3.setLastName("Toivola");
                customer3.setAddresses(new ArrayList<>());
                customerRepository.save(customer3);

                Address address1 = new Address();
                address1.setStreet("Javastreet 24");
                address1.setCity("Jakarta");
                address1.setPostalCode("0xCAFEBABE");
                address1.setCustomer(customer3);
                addressRepository.save(address1);
                customer3.getAddresses().add(address1);
                customerRepository.save(customer3);

                Customer customer4 = new Customer();
                customer4.setUsername("dwik");
                customer4.setPassword(encodedPassword);
                customer4.setRole(Role.USER);
                customer4.setFirstName("Dennis");
                customer4.setLastName("Wiklund");
                customer4.setAddresses(new ArrayList<>());
                customerRepository.save(customer4);

                Address address2 = new Address();
                address2.setStreet("Randomstreet 67");
                address2.setCity("Tomelilla");
                address2.setPostalCode("11111");
                address2.setCustomer(customer4);
                addressRepository.save(address2);
                customer4.getAddresses().add(address2);
                customerRepository.save(customer4);

                Customer customer5 = new Customer();
                customer5.setUsername("mrbean");
                customer5.setPassword(encodedPassword);
                customer5.setRole(Role.USER);
                customer5.setFirstName("Mr");
                customer5.setLastName("Bean");
                customer5.setAddresses(new ArrayList<>());
                customerRepository.save(customer5);
            }

            if(courtRepository.count() == 0) {

                Court court1 = new Court();
                court1.setCourtName("Cardio B court");
                court1.setPricePerHourSek(400.0);
                court1.setPricePerHourEur(40.0);
                courtRepository.save(court1);

                Court court2 = new Court();
                court2.setCourtName("WAP Court");
                court2.setPricePerHourSek(750.0);
                court2.setPricePerHourEur(75.0);
                courtRepository.save(court2);

                Court court3 = new Court();
                court3.setCourtName("Smash Court");
                court3.setPricePerHourSek(500.0);
                court3.setPricePerHourEur(50.0);
                courtRepository.save(court3);

                Court court4 = new Court();
                court4.setCourtName("Bandy Court");
                court4.setPricePerHourSek(650.0);
                court4.setPricePerHourEur(65.0);
                courtRepository.save(court4);

                Court court5 = new Court();
                court5.setCourtName("La Decima");
                court5.setPricePerHourSek(900.0);
                court5.setPricePerHourEur(90.0);
                courtRepository.save(court5);
            }

            if(bookingRepository.count() == 0) {

                Booking booking1 = new Booking();
                booking1.setCustomer(customerRepository.findById(3L).get());
                booking1.setCourt(courtRepository.findById(1L).get());
                booking1.setStartTime(12);
                booking1.setBookingDate(LocalDate.of(2026, 4, 20));
                booking1.setNumberOfPlayers(4);
                booking1.setTotalPriceSek(courtRepository.findById(1L).get().getPricePerHourSek());
                bookingRepository.save(booking1);

                Booking booking2 = new Booking();
                booking2.setCustomer(customerRepository.findById(4L).get());
                booking2.setCourt(courtRepository.findById(2L).get());
                booking2.setStartTime(15);
                booking2.setBookingDate(LocalDate.of(2026, 4, 25));
                booking2.setNumberOfPlayers(6);
                booking2.setTotalPriceSek(courtRepository.findById(2L).get().getPricePerHourSek());
                bookingRepository.save(booking2);
            }
        };
    }
}
