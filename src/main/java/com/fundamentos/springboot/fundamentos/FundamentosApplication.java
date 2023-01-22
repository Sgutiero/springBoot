package com.fundamentos.springboot.fundamentos;

import com.fundamentos.springboot.fundamentos.bean.MyBean;
import com.fundamentos.springboot.fundamentos.bean.MyBeanProperties;
import com.fundamentos.springboot.fundamentos.bean.MyBeanWithDependency;
import com.fundamentos.springboot.fundamentos.component.ComponentDependency;
import com.fundamentos.springboot.fundamentos.entity.User;
import com.fundamentos.springboot.fundamentos.pojo.UserPojo;
import com.fundamentos.springboot.fundamentos.repository.UserRepository;
import com.fundamentos.springboot.fundamentos.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class FundamentosApplication implements CommandLineRunner {

	private final Log LOGGER = LogFactory.getLog(FundamentosApplication.class);
	private ComponentDependency componentDependency;
	private MyBean myBean;
	private MyBeanWithDependency myBeanWithDependency;
	private MyBeanProperties myBeanProperties;
	private UserPojo userPojo;
	private UserRepository userRepository;
	private UserService userService;


	public  FundamentosApplication(ComponentDependency componentDependency, MyBean myBean, MyBeanWithDependency myBeanWithDependency, MyBeanProperties myBeanProperties, UserPojo userPojo, UserRepository userRepository, UserService userService){
		this.componentDependency = componentDependency;
		this.myBean = myBean;
		this.myBeanWithDependency = myBeanWithDependency;
		this.myBeanProperties = myBeanProperties;
		this.userPojo = userPojo;
		this.userRepository = userRepository;
		this.userService = userService;

	}
	public static void main(String[] args) {
		SpringApplication.run(FundamentosApplication.class, args);
	}

	@Override
	public void run(String... args) {
		//ejemplosAnteriores();
		saveUsersInDataBase();
		getInformationJpqlFromUser();
		saveWithErrorTransactional();
	}

	private void saveWithErrorTransactional(){
		User test1 = new User("TestTransactional1", "TestTransactional1@domain.com", LocalDate.now());
		User test2 = new User("TestTransactional2", "TestTransactional2@domain.com", LocalDate.now());
		User test3 = new User("TestTransactional3", "TestTransactional3@domain.com", LocalDate.now());
		User test4 = new User("TestTransactional4", "TestTransactional4@domain.com", LocalDate.now());

		List<User> users = Arrays.asList(test1, test2, test3, test4);

		try {

		}catch (Exception e){
			LOGGER.error("Esta es una anotacion dentro del metodo transactional " + e);
		}
		userService.saveTransactional(users);

		userService.getAllUsers().stream()
				.forEach(user -> LOGGER.info("Este es el usuario dentro del metodo transactional " + user));


	}

	private void getInformationJpqlFromUser(){
		/*LOGGER.info("Usuario con el metodo findByUserEmail" +
				userRepository.findByUserEmail("valen@gmail.com")
				.orElseThrow(() -> new RuntimeException("No se encontro el usuario")));

		userRepository.findAndSort("User", Sort.by("id").descending())
				.stream()
				.forEach(user -> LOGGER.info("Usuario con el metodo Sort " + user));

		userRepository.findByName("Juan")
				.stream()
				.forEach(user -> LOGGER.info("Usuario con el metodo query " + user));

		LOGGER.info("Usuario con query method findByEmailAndName" +
				userRepository.findByEmailAndName("ines@gmail.com", "Ines")
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado")));

		userRepository.findByNameLike("%e%")
				.stream()
				.forEach(user -> LOGGER.info("Usuario findByNameLike " + user));

		userRepository.findByNameOrEmail(null, "mary@gmail.com")
				.stream()
				.forEach(user -> LOGGER.info("Usuario findByNameOrEmail " + user)); */

		userRepository.findByBirthdateBetween(LocalDate.of(2021, 3, 1), LocalDate.of(2023, 3, 2))
				.stream()
				.forEach(user -> LOGGER.info("Usuario con intervalo de fechas " + user));

		userRepository.findByNameContainingOrderByIdDesc("User")
				.stream()
				.forEach(user -> LOGGER.info("Usuario encontrado con like y ordenado " + user));

		LOGGER.info("El usuario a partir del named parameter " + userRepository.getAllByBirthdateAndEmail(LocalDate.of(2023, 11, 12), "ines@gmail.com")
				.orElseThrow(() -> new RuntimeException("No se encontro el usuario a partir del named parameter")));
	}

	private void saveUsersInDataBase(){
		User user1 = new User("Sebas", "sebas@gmail.com", LocalDate.of(2023, 10, 5));
		User user2 = new User("Valen", "valen@gmail.com", LocalDate.of(2023, 3, 18));
		User user3 = new User("Juan", "juan@gmail.com", LocalDate.of(2022, 12, 30));
		User user4 = new User("User1", "mary@gmail.com", LocalDate.of(2020, 10, 18));
		User user5 = new User("User2", "oscar@gmail.com", LocalDate.of(2023, 1, 13));
		User user6 = new User("User3", "andre@gmail.com", LocalDate.of(2018, 6, 24));
		User user7 = new User("User4", "luis@gmail.com", LocalDate.of(2022, 1, 19));
		User user8 = new User("Juan", "manue@gmail.com", LocalDate.of(2015, 4, 10));
		User user9 = new User("Ines", "ines@gmail.com", LocalDate.of(2023, 11, 12));
		User user10 = new User("Saul", "saul@gmail.com", LocalDate.of(2021, 7, 20));

		List<User> list = Arrays.asList(user1, user2, user3, user4, user5, user6, user7, user8, user9, user10);

		list.stream().forEach(userRepository::save);
	}

	private void ejemplosAnteriores(){
		componentDependency.saludar();
		myBean.print();
		myBeanWithDependency.printWithDependency();
		System.out.println(myBeanProperties.function());
		System.out.println(userPojo.getEmail() + "-" + userPojo.getPassword());

		try {
			//error
			int value = 10/0;
			LOGGER.debug("Mi valor: " + value);
		} catch (Exception e){
			LOGGER.error("esto es un error al dividir por cero " + e.getMessage());
		}
	}
}
