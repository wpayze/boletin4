package es.uv.garcosda.domain;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


@Entity
@Table(name = "POSTS")
public class Post implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@SequenceGenerator(name="id_post_generator", sequenceName = "id_post_seq", allocationSize=50, initialValue=4)
	@Column(name = "post_id")
	private Integer id;
	
	@Column(name = "title", nullable = false, length = 150)
	private String title;
	
	@Lob
	@Column(name = "content", nullable = false, columnDefinition = "TEXT")
	private String content;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on")
	private Date createdOn = new Date();
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on")
	private Date updatedOn;

	public Post() { }

	public Post(Integer postId) {
		this.id = postId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
}
