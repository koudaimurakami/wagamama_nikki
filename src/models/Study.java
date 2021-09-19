package models;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "study_hours")
@NamedQueries({
	@NamedQuery(
			name = "sumStudyHour",
			query = "SELECT SUM(s.study_hour) FROM Study AS s WHERE s.user = :user"
			),
	@NamedQuery(
			name = "StudyLog",
			query = "SELECT s FROM Study AS s WHERE s.user = :user AND s.study_date BETWEEN :this_month_1 AND :this_month_last"
			)
})
@Entity
public class Study {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "study_hour", nullable = false)
	private int study_hour;

	@Column(name = "study_date", nullable = false)
	private Date study_date;



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getStudy_hour() {
		return study_hour;
	}

	public void setStudy_hour(int study_hour) {
		this.study_hour = study_hour;
	}

	public Date getStudy_date() {
		return study_date;
	}

	public void setStudy_date(Date study_date) {
		this.study_date = study_date;
	}

public String getStudyMark() {

	if (this.study_hour >= 8) {
		return "☆";
	} else if (this.study_hour < 8 && this.study_hour >= 4) {
		return "◎";
	} else if (this.study_hour < 4 && this.study_hour >=1) {
		return "○";
	} else {
		return "♪";
	}
}


	}
