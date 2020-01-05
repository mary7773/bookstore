package com.bookstore.models;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bookstore.domain.security.Authority;
import com.bookstore.domain.security.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Marieta
 *
 */
@Entity
@Table(name="users")
public class User implements UserDetails, Serializable {
	private static final long serialVersionUID = 5379037293372325028L;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
	@JsonIgnore
	Set<UserRole> userRoles = new HashSet<>();
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	@JsonIgnore
	Set<UserPayment> userPayments = new HashSet<>();
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	@JsonIgnore
	Set<UserShipping> userShippingList = new HashSet<>();
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	@JsonIgnore
	Set<Order> orderList;
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
	@JsonIgnore
	ShoppingCart shoppingCart;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JoinColumn(name = "Id", nullable = false, updatable = false)
	private Long id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private boolean enabled = true;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public Set<UserPayment> getUserPayments() {
		return userPayments;
	}

	public void setUserPayments(Set<UserPayment> userPayments) {
		this.userPayments = userPayments;
	}

	public Set<UserShipping> getUserShippingList() {
		return userShippingList;
	}

	public void setUserShippingList(Set<UserShipping> userShippingList) {
		this.userShippingList = userShippingList;
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public Set<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(Set<Order> orderList) {
		this.orderList = orderList;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<>();
		userRoles.forEach(userRole -> authorities.add(new Authority(userRole.getRole().getName())));
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
