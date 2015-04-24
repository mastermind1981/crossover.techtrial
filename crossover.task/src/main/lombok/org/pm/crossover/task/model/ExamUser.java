/**
 * 
 */
package org.pm.crossover.task.model;

import java.io.Serializable;

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
public class ExamUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5868729022055495242L;
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
