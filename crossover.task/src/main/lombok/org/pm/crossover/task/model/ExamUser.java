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

/**
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "examuser")
public class ExamUser {

	@Id
	@GeneratedValue
	private Integer id;
	private String fullName;
	private String username;
	private String password;
	private Boolean enabled;

}
