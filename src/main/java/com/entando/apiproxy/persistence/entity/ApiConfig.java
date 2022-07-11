package com.entando.apiproxy.persistence.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Data
public class ApiConfig {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

//	private String applicationName;
	
	@Column(nullable = false, length = 600, unique = true)
	private String baseUrl;
	
	@Column(nullable = false, length = 600, unique = true)
	private String contextPath;
	
	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	public ApiConfig() {
		super();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApiConfig other = (ApiConfig) obj;
		return Objects.equals(baseUrl, other.baseUrl) && Objects.equals(contextPath, other.contextPath)
				&& Objects.equals(createdAt, other.createdAt) && Objects.equals(id, other.id)
				&& Objects.equals(updatedAt, other.updatedAt);
	}

	@Override
	public int hashCode() {
		return Objects.hash(baseUrl, contextPath, createdAt, id, updatedAt);
	}

	@Override
	public String toString() {
		return "ApiConfig [id=" + id + ", baseUrl=" + baseUrl + ", contextPath=" + contextPath + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + "]";
	}

}
