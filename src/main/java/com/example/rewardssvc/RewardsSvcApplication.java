package com.example.rewardssvc;

import com.example.rewardssvc.model.Customer;
import com.example.rewardssvc.model.Order;
import com.example.rewardssvc.repository.CustomerRepository;
import com.example.rewardssvc.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
@Slf4j
public class RewardsSvcApplication  implements CommandLineRunner {

	private final CustomerRepository customerRepository;
	private final OrderRepository orderRepository;

	public RewardsSvcApplication(CustomerRepository customerRepository, OrderRepository orderRepository) {
		this.customerRepository = customerRepository;
		this.orderRepository = orderRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(RewardsSvcApplication.class, args);
	}


	@Override
	public void run(String... args) {

		log.info("load data to h2 started...");

		loadCustomerData();
		loadOrderData();

		log.info("load data to h2 complete...");
	}

	private void loadCustomerData(){
		customerRepository.save(Customer.builder().customerId(1L).name("John Doe").email("john.doe@email.com").build());
		customerRepository.save(Customer.builder().customerId(2L).name("Jane Doe").email("jane.doe@email.com").build());
		customerRepository.save(Customer.builder().customerId(3L).name("Joe Blow").email("joe.blow@email.com").build());
	}

	private void loadOrderData(){
		orderRepository.save(Order.builder().customerId(1L).purchaseDate(LocalDateTime.now()).purchaseAmount(75.56).build());
		orderRepository.save(Order.builder().customerId(1L).purchaseDate(LocalDateTime.now()).purchaseAmount(25.90).build());
		orderRepository.save(Order.builder().customerId(1L).purchaseDate(LocalDateTime.now()).purchaseAmount(125.36).build());
		orderRepository.save(Order.builder().customerId(2L).purchaseDate(LocalDateTime.now()).purchaseAmount(5.56).build());
		orderRepository.save(Order.builder().customerId(2L).purchaseDate(LocalDateTime.now()).purchaseAmount(75.24).build());
		orderRepository.save(Order.builder().customerId(2L).purchaseDate(LocalDateTime.now()).purchaseAmount(175.56).build());
		orderRepository.save(Order.builder().customerId(3L).purchaseDate(LocalDateTime.now()).purchaseAmount(15.56).build());

	}
}
