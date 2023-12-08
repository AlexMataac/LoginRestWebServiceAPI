package com.exam.loginApi.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuccessResponse {

    private String lastName;
    private String firstName;
    private String middleName;
    private Date birthdate;
    private String formattedBirthdate;


    public SuccessResponse(String lastName, String firstName, String middleName, Date birthdate) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.birthdate = birthdate;
        this.formattedBirthdate = formatDate(birthdate);
    }

    private String formatDate(Date birthdate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.format(birthdate);
    }


}
