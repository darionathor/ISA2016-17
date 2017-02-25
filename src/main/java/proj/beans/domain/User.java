package proj.beans.domain;

import org.hibernate.validator.constraints.NotEmpty;

public class User {
	private Long id;
    
	private UserType type;
    @NotEmpty(message = "Email je obavezan.")
    private String email;

    private String username;
    @NotEmpty(message = "lozinka je obavezna.")
    private String password;
    private String repeatedPassword;
	private String ime;
	private String prezime;
	private String datumRodjenja;
	private int konfekcijskiBroj;
	private int velicinaObuce;

    public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getDatumRodjenja() {
		return datumRodjenja;
	}

	public void setDatumRodjenja(String datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}

	public int getKonfekcijskiBroj() {
		return konfekcijskiBroj;
	}

	public void setKonfekcijskiBroj(int konfekcijskiBroj) {
		this.konfekcijskiBroj = konfekcijskiBroj;
	}

	public int getVelicinaObuce() {
		return velicinaObuce;
	}

	public void setVelicinaObuce(int velicinaObuce) {
		this.velicinaObuce = velicinaObuce;
	}

	public User() {

    }

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepeatedPassword() {
		return repeatedPassword;
	}

	public void setRepeatedPassword(String repeatedPassword) {
		this.repeatedPassword = repeatedPassword;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}
}
