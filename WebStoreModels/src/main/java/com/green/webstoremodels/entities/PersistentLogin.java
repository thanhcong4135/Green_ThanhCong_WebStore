package com.green.webstoremodels.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * create table persistent_logins (username varchar(64) not null,
                                series varchar(64) primary key,
                                token varchar(64) not null,
                                last_used timestamp not null);
 */

@Entity
@Table(name = "persistent_logins")
public class PersistentLogin implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7687747916229166413L;

	@Id
	@Column(name = "series", length = 64)
	private String series;
	
	@Column(name = "username", length = 64, nullable = false)
	private String username;
	
	@Column(name = "token", length = 64, nullable = false)
	private String token;
	
	@Column(name = "last_used", columnDefinition = "timestamp")
	private LocalDateTime last_used;
	
}







