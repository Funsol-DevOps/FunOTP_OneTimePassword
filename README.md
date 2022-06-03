# FunOTP_OneTimePassword

[![](https://jitpack.io/v/Funsol-DevOps/FunOTP_OneTimePassword.svg)](https://jitpack.io/#Funsol-DevOps/FunOTP_OneTimePassword)

OTP-OneTimePassword is an "OTP Sending Platform" to send OTP for email verification. 

## Getting Started

### Step 1

Add maven repository in project level build.gradle or in latest project setting.gradle file
```
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
```  


### Step 2

Add OTP-OneTimePassword dependencies in App level build.gradle.
```
    dependencies {
           implementation 'com.github.Funsol-DevOps:FunOTP_OneTimePassword:x.x'
    }
```  


### Step 3

Finally initialize the ViewModel Instance of "OTPViewModel" Class by following code.

```
    val otpViewModel = ViewModelProvider(this)[OTPViewModel::class.java]
```


### Step 4

Now call the method from "OtpViewModel" to receive the OTP as follows

```
    otpViewModel.sendOtp(authToken, email, otpRequest){ isOtpSent ->
    
    }
```
##### authToken: Authorization Token for API
##### email: Email on which OTP will be send.
##### otpRequest: OtpRequest object with subject, upper and lower text.
##### isOtpSend: Is a boolean callback which ensure if OTP is sent or not.

### Step 5

Create "OtpRequest" object as follows.

```
    val otpRequest = OtpRequest("subject_here", "text_before_otp", "text_after_otp")
```


### Step 6
Get remaining time for "OTP" Resend in "LiveData" object in "OTPViewModel" class.
```
  otpViewModel.otpResendRemainingTime.observe(this) { response ->
      //response contains timeInMillis.
  }
```

### Step 7
Verify OTP by sending user entered OTP in following method
```
  otpViewModel.verifyOtp("user_entered_otp")
```
##### This function will return Boolean Response for OTP.

##### Note: you will get complete code to get OTP in Sample App.


## License

MIT License

Copyright (c) [2022] [Osama Mumtaz](https://www.osamamumtaz.com/)


Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

