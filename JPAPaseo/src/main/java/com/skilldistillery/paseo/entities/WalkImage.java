package com.skilldistillery.paseo.entities;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="walk_image")
public class WalkImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "image_url")
	private String imageUrl;
	
	@ManyToOne
	@JoinColumn(name = "walk_id")
	private Walk walkId;
	
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User userId;

	public WalkImage() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public Walk getWalkId() {
		return walkId;
	}

	public void setWalkId(Walk walkId) {
		this.walkId = walkId;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "WalkImage [id=" + id + ", imageUrl=" + imageUrl + ", walkId=" + walkId + ", description=" + description
				+ ", userId=" + userId + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, id, imageUrl, userId, walkId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WalkImage other = (WalkImage) obj;
		return Objects.equals(description, other.description) && id == other.id
				&& Objects.equals(imageUrl, other.imageUrl) && userId == other.userId && walkId == other.walkId;
	}
	
	
	
}
