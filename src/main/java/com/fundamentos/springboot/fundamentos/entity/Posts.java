package com.fundamentos.springboot.fundamentos.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "post")
public class Posts {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // identity para que no se siga el contador de otros registros
	@Column(name = "id_post", nullable = false, unique = true)
	private Long id;
	@Column(name = "description", length = 250)
	private String description;
	@ManyToOne
	private User user;

	public Posts() {
	}

	public Posts(String description, User user) {
		this.description = description;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Post{" +
				"id=" + id +
				", description='" + description + '\'' +
				'}';
	}
}
