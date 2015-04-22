/**
 * 
 */
package org.pm.crossover.task.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "examuser")
public class ExamUser {

	@JsonIgnore
	@Id
	@GeneratedValue
	private Integer id;
	private String fullName;
	@JsonIgnore
	private String username;
	@JsonIgnore
	private String password;
	@JsonIgnore
	private Boolean enabled;

}
