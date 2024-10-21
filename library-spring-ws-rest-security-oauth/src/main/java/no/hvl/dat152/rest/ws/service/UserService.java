/**
 * 
 */
package no.hvl.dat152.rest.ws.service;


import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.hvl.dat152.rest.ws.exceptions.OrderNotFoundException;
import no.hvl.dat152.rest.ws.exceptions.UserNotFoundException;
import no.hvl.dat152.rest.ws.model.Order;
import no.hvl.dat152.rest.ws.model.User;
import no.hvl.dat152.rest.ws.repository.OrderRepository;
import no.hvl.dat152.rest.ws.repository.UserRepository;

/**
 * @author tdoy
 */
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private OrderRepository orderRepository;

	
	public List<User> findAllUsers(){
		
		List<User> allUsers = (List<User>) userRepository.findAll();
		
		return allUsers;
	}
	
	public User findUser(Long userid) throws UserNotFoundException {
		
		Optional<User> user = userRepository.findById(userid);
		if(user == null) {
           throw new UserNotFoundException("User with id: " + userid + " not found");
		}	
		return user.get();
	}
	
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	public void deleteUser(Long id) {

		userRepository.deleteById(id);
		
	}

	 public User updateUser(User user, Long id) throws UserNotFoundException {
		
		User oldUser = userRepository.findById(id)
		.orElseThrow(()-> new UserNotFoundException("User with id: "+id+" not found"));
		if(oldUser.getUserid() == id) {
			user.setUserid(id);
			return userRepository.save(user);
		}
		else return null;
	 }

	 public Set<Order> getUserOrders(Long userid) throws UserNotFoundException {

		Set<Order> orders = findUser(userid).getOrders();
		return orders;
	 }

	 public Order getUserOrder(Long userid, Long oid) throws UserNotFoundException, OrderNotFoundException {

		User user = findUser(userid);
		Optional<Order> order = user.getOrders().stream().filter(o -> o.getId() == oid).findFirst();

		if(order.isPresent()) {
			return order.get();
		}
		else {
			throw new OrderNotFoundException("Order with id:" + oid + " belonging to user with id: " + userid + " not found");
		}
	 }

	 public void deleteOrderForUser(Long userid, Long oid) throws UserNotFoundException, OrderNotFoundException {

	     Order order = getUserOrder(userid, oid);
		 orderRepository.delete(order); 
		 
	 }
	
	 public User createOrdersForUser(Long userid, Order order) throws UserNotFoundException, OrderNotFoundException {

		 User user = findUser(userid);
		 orderRepository.save(order);
		 user.addOrder(order);
		 return userRepository.save(user);
		 
	 }
}
