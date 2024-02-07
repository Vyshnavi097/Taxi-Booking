**TaxiBooking and Billing** 

3 models created namely Booking,Taxi and Usercontroller

having 3 service and controller classes

BookingController contains
1.@PostMapping("/booking")
2.@GetMapping("/booking/{id}")
3.@DeleteMapping("/cancel")
4.@GetMapping("/nearest")

TaxiController contains
1.@PostMapping("/taxi")

UserController contains
1.@PostMapping("/v1/signup")
2.@PostMapping("/v1/login")
3.@PutMapping("/v1/{userId}")