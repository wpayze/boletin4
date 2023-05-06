package es.uv.garcosda.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;


@Entity
@Table(name="users")
public class User {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable=false)
	private String name;
	
	@Column(nullable=false, unique=true)
	private String username;
	
	@Column(nullable=false)
	private String password;
	
	@ManyToMany(cascade=CascadeType.MERGE)
	@JoinTable(name="user_role",
	           joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")},
	           inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ID")})
	private List<Role> roles;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}
