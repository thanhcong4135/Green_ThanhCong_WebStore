package com.green.webstoremodels.formdata;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.green.webstoremodels.dto.CustomerDto;

public class CustomerForm {

	@Email(message = "Ban phai su dung dia chi email chinh xac")
	private String email;

	@NotNull
	@Size(min = 8, max = 24)
	private String password;

	private String passwordRetype;

	@NotNull(message = "Ten ban khong duoc de trong")
	@Size(min = 2, max = 64, message = "do dai ten ban phai tu 2 ky tu , toi da 64 ky tu")
	private String name;

	@NotNull
	@Size(min = 10, max = 12, message = "do dai ten ban phai tu 10 ky tu , toi da 12 ky tu")
	private String phoneNumber;

	private String imagebase64;
	private String photoPath;

	public static CustomerForm getCustomerForm(CustomerDto customer) {
		CustomerForm form = new CustomerForm();
		if (customer != null) {
			form.setEmail(customer.getEmail());
			form.setPhoneNumber(customer.getPhoneNumber());
			// gộp họ tên đơn giản
			String fullName = (customer.getFirstName() != null ? customer.getFirstName() : "") +
					(customer.getLastName() != null ? " " + customer.getLastName() : "");
			form.setName(fullName.trim());
		}
		return form;
	}

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	public String getPasswordRetype() { return passwordRetype; }
	public void setPasswordRetype(String passwordRetype) { this.passwordRetype = passwordRetype; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getPhoneNumber() { return phoneNumber; }
	public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
	public String getImagebase64() { return imagebase64; }
	public void setImagebase64(String imagebase64) { this.imagebase64 = imagebase64; }
	public String getPhotoPath() { return photoPath; }
	public void setPhotoPath(String photoPath) { this.photoPath = photoPath; }
}
