package pop2016.openservice.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User implements java.io.Serializable {

	// Fields

	private Integer id;
	private String username;
	private String password;
	private Timestamp lastLogin;
	private Boolean isSuper;
	private String email;
	private Timestamp dateJoined;
	private String unameBaidu;
	private String unameSina;
	private String unameSae;
	private String unameCode;
	private String pwdBaidu;
	private String pwdSae;
	private Timestamp registerTime;
	private String validateCode;
	private Boolean registerStatus;
	private String token;
	private Boolean isRemembered;
	private Set apps = new HashSet(0);

	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(String token) {
		this.token = token;
	}

	/** full constructor */
	public User(String username, String password, Timestamp lastLogin,
			Boolean isSuper, String email, Timestamp dateJoined,
			String unameBaidu, String unameSina, String unameSae,
			String unameCode, String pwdBaidu, String pwdSae,
			Timestamp registerTime, String validateCode,
			Boolean registerStatus, String token, Boolean isRemembered, Set apps) {
		this.username = username;
		this.password = password;
		this.lastLogin = lastLogin;
		this.isSuper = isSuper;
		this.email = email;
		this.dateJoined = dateJoined;
		this.unameBaidu = unameBaidu;
		this.unameSina = unameSina;
		this.unameSae = unameSae;
		this.unameCode = unameCode;
		this.pwdBaidu = pwdBaidu;
		this.pwdSae = pwdSae;
		this.registerTime = registerTime;
		this.validateCode = validateCode;
		this.registerStatus = registerStatus;
		this.token = token;
		this.isRemembered = isRemembered;
		this.apps = apps;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Timestamp getLastLogin() {
		return this.lastLogin;
	}

	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Boolean getIsSuper() {
		return this.isSuper;
	}

	public void setIsSuper(Boolean isSuper) {
		this.isSuper = isSuper;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getDateJoined() {
		return this.dateJoined;
	}

	public void setDateJoined(Timestamp dateJoined) {
		this.dateJoined = dateJoined;
	}

	public String getUnameBaidu() {
		return this.unameBaidu;
	}

	public void setUnameBaidu(String unameBaidu) {
		this.unameBaidu = unameBaidu;
	}

	public String getUnameSina() {
		return this.unameSina;
	}

	public void setUnameSina(String unameSina) {
		this.unameSina = unameSina;
	}

	public String getUnameSae() {
		return this.unameSae;
	}

	public void setUnameSae(String unameSae) {
		this.unameSae = unameSae;
	}

	public String getUnameCode() {
		return this.unameCode;
	}

	public void setUnameCode(String unameCode) {
		this.unameCode = unameCode;
	}

	public String getPwdBaidu() {
		return this.pwdBaidu;
	}

	public void setPwdBaidu(String pwdBaidu) {
		this.pwdBaidu = pwdBaidu;
	}

	public String getPwdSae() {
		return this.pwdSae;
	}

	public void setPwdSae(String pwdSae) {
		this.pwdSae = pwdSae;
	}

	public Timestamp getRegisterTime() {
		return this.registerTime;
	}

	public void setRegisterTime(Timestamp registerTime) {
		this.registerTime = registerTime;
	}

	public String getValidateCode() {
		return this.validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public Boolean getRegisterStatus() {
		return this.registerStatus;
	}

	public void setRegisterStatus(Boolean registerStatus) {
		this.registerStatus = registerStatus;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Boolean getIsRemembered() {
		return this.isRemembered;
	}

	public void setIsRemembered(Boolean isRemembered) {
		this.isRemembered = isRemembered;
	}

	public Set getApps() {
		return this.apps;
	}

	public void setApps(Set apps) {
		this.apps = apps;
	}

}