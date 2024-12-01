package in.projectjwt.main.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;




import in.projectjwt.main.dtos.LoginUserDto;
import in.projectjwt.main.dtos.ResetPasswordRequestdto;
import in.projectjwt.main.dtos.OtpVerificationRequestdto;
import in.projectjwt.main.entities.User;
import in.projectjwt.main.exceptions.InvalidOTPException;

import in.projectjwt.main.repositories.UserRepository;
import in.projectjwt.main.services.AuthenticationService;
import in.projectjwt.main.services.JwtService;
import in.projectjwt.main.services.PasswordResetService;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
	
	private final JwtService jwtService;
    
    private final AuthenticationService authenticationService;
    @Autowired
    private PasswordResetService passwordResetService;
    
    @Autowired
    private UserRepository userRepo;
  

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody User user) {
    	Map<String, Object> response = new HashMap<>();

        // Call the service to handle the user signup
        ResponseEntity<Map<String, String>> signupResponse = authenticationService.register(user);

        // If signup response contains a message about the user already existing
        if (signupResponse.getStatusCode() == HttpStatus.CONFLICT) {
        	response.put("success", "false");
            response.put("message", signupResponse.getBody().get("message"));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response); // HTTP 409 Conflict
        }
     // If user is successfully registered
        response.put("success", "true");
        response.put("message", signupResponse.getBody().get("message"));
        
        // Add the user details
        Map<String, String> userDetails = new HashMap<>();
        userDetails.put("fullName", user.getFullName());
        userDetails.put("email", user.getEmail());
        response.put("user", userDetails);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);
  

        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime()).setFullname(authenticatedUser.getFullName()).setEmail(authenticatedUser.getEmail());
        

        return ResponseEntity.ok(loginResponse);
    }
//    @GetMapping("/user")
//    public ResponseEntity<?> getUserDetails(@RequestHeader("Authorization") String token) {
//        try {
//            UserDetailsDto userDetails = userService.getUserDetailsFromToken(token);
//            return ResponseEntity.ok(userDetails);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: " + e.getMessage());
//        }
//    }

//    @GetMapping("/portfolio")
//    public ResponseEntity<?> getDashboardData(@RequestHeader("Authorization") String token) {
//        try {
//            // Your existing logic here
//            if (token.startsWith("Bearer ")) {
//                token = token.substring(7);
//            }
//            String email = jwtService.extractUsername(token);
//            Optional<User> userOptional = userRepo.findByEmail(email);
//            if (userOptional.isEmpty()) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token or user not found");
//            }
//
//            Map<String, Object> dashboardData = new HashMap<>();
//            dashboardData.put("welcomeMessage", "Welcome " + email + "!");
//            dashboardData.put("portfolioValue", 50000);
//            dashboardData.put("recentInvestments", List.of("Stock A", "Stock B", "Stock C"));
//            dashboardData.put("assets", getAssetsData());  // Method to retrieve assets data
//            
//            return ResponseEntity.ok(dashboardData);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
//        }
//    }
//    	private List<Map<String, Object>> getAssetsData() {
//		// TODO Auto-generated method stub
//    		// Replace this with actual asset data
//            List<Map<String, Object>> assets = new ArrayList<>();
//            Map<String, Object> asset = new HashMap<>();
//            asset.put("id", 1);
//            asset.put("name", "BNB");
//            asset.put("symbol", "BNB");
//            asset.put("price", 45897.00);
//            asset.put("change24h", -1.34);
//            asset.put("holdings", 872043.00);
//            asset.put("avgBuyPrice", 42709.00);
//            asset.put("profitLoss", 52384.00);
//            assets.add(asset);
//            return assets;
//		
//    	}

//    public ResponseEntity<?> getDashboardData(@RequestHeader("Authorization") String token) {
//        try {
//            // Extract token after "Bearer "
//            if (token.startsWith("Bearer ")) {
//                token = token.substring(7);
//            }
//
//            // Validate the token
//            String email = jwtService.extractUsername(token);
//            Optional<User> userOptional = userRepo.findByEmail(email);
//            if (userOptional.isEmpty()) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token or user not found");
//            }
//         
//
//            // Create sample dashboard data (replace with actual data retrieval logic)
//            Map<String, Object> dashboardData = new HashMap<>();
//            dashboardData.put("welcomeMessage", "Welcome " + "You User" + "!");
//            dashboardData.put("portfolioValue", 50000); // Sample value
//            dashboardData.put("recentInvestments", List.of("Stock A", "Stock B", "Stock C"));
//
//            return ResponseEntity.ok(dashboardData);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
//        }
//    }
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
    	try {
    		passwordResetService.sendResetLink(request.getEmail());
    		return ResponseEntity.ok("Password reset email sent successfully");
    	}
    	catch (InvalidOTPException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
        
    }
    
    @PostMapping("/otp-verification")
    public ResponseEntity<?> verifyOTP(@RequestBody OtpVerificationRequestdto request) {
        try {
        	passwordResetService.verifyOTP(request.getEmail(), request.getOtp()); // Implement this method in your UserService
            return ResponseEntity.ok("OTP verified successfully");
        } catch (InvalidOTPException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("OTP verification failed: " + ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
    
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequestdto request) {
        try {
        	passwordResetService.resetPassword(request);
            return ResponseEntity.ok("Password reset successfully");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

}
