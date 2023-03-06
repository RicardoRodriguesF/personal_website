package rodrigues.ferreira.ricardo.auth.user.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	@Column(unique = true)
	private String email;
	private String password;
	@Enumerated(EnumType.STRING)
	private Type type;
	@CreatedDate
	private OffsetDateTime createdAt;


	public UserEntity(String name, String email, String password, Type type) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.type = type;
	}

	public enum Type {
		ADMIN, CLIENT;
	}
}
