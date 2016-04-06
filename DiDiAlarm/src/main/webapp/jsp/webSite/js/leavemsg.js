function leavemsg() {
    var firstname = $("#firstname").val();
    var lastname = $("#lastname").val();
    var company = $("#company").val();
    var title = $("#title").val();
    var country = $("#country").val();
    var city = $("#city").val();
    var phone = $("#phone").val();
    var email = $("#email").val();
    var content = $("#message").val();

    var emailReg = /^[_a-z0-9.]+@([_a-z0-9]+\.)+[a-z0-9]{2,3}/;
    var numReg = /^[0-9]+.?[0-9]*$/;
    var phone1 = /^1[358]\d{9}$/gi;
    var partten = /^0(([1-9]\d)|([3-9]\d{2}))\d{8}$/;

    var ischk = true;
    var stralert = "Please enter:\n"; 
    if (firstname == "" || firstname == "First Name") {
        stralert += "*the firstname!\n";
        ischk = false;
    }
    if (lastname == "" || lastname == "Last Name") {
        stralert += "*the lastname!\n";
        ischk = false;
    }
    if (company == "" || company == "Company") {
        stralert += "*the Company!\n";
        ischk = false;
    }
    if (title == "" || title == "Job Title") {
        stralert += "*the Job Title!\n";
        ischk = false;
    }
    if (country == "" || country == "Country") {
        stralert += "*the Country!\n";
        ischk = false;
    }
    if (city == "" || city == "City") {
        stralert += "*the City!\n";
        ischk = false;
    }
    if (phone == "" || phone == "Phone") {
        stralert += "*the Phone!\n";
        ischk = false;
    }
    if (email == "" || email == "Email") {
        stralert += "*the Email!\n";
        ischk = false;
    }else if (!emailReg.test(email)) {
        stralert += "*the Email Error message format!\n";
        ischk = false;
    } 
    if (content == "") { 
        stralert += "*the Message!\n";
        ischk = false;
    }
    if (firstname.length >= 100) {
        stralert = "First Name Characters long!";
        ischk = false;
    } 
    if (lastname.length >= 100) {
        stralert = "Last Name Characters long!";
        ischk = false;
    } 
    if (company.length >= 100) {
        stralert = "Company Characters long!";
        ischk = false;
    }
    if (email.length >= 100) {
        stralert = "1Email Characters long!";
        ischk = false;
    }
    if (title.length >= 200) {
        stralert = "Title Characters long!";
        ischk = false;
    }  
    if (content.length >= 500) {
        stralert = "Message Keep in 550 characters or less!";
        ischk = false;
    } 
    if (ischk) { 
        jQuery.ajax({
            
            type: "POST",
            url: "/Derby/siteHome.do?p=putMessage",
            data: $("#messageForm").serialize(),
            async: false,
            success: function (data) {
                if (data.type == "SUCESSS") {
                    location.href = "message-jump.html";
                } else
                    if (data.type == "FAIL") {
                        alert("Submit Failure!");
                    } else {
                        alert(data.message);
                    }
            }
        }) 
    } else {
        alert(stralert);
        return false;
    }

}

function clearval(type) {
    if (type = 0) {
        $("#firstname").val("");
        $("#lastname").val("");
        $("#company").val("");
        $("#email").val("");
        $("#title").val("");
        $("#message").val(""); 
        $("#country").val("");
        $("#city").val("");
        $("#phone").val("");
    } 
} 