GET http://localhost:8080/api/v1/catalog - Get courseName and course fee details
POST http://localhost:8080/api/v1/users/register - Register user using userName, Email , courseName , Contact Number
GET http://localhost:8080/api/v1/users/abc - Get userDetails by userId 
GET http://localhost:8080/api/v1/fee/payments/pendingfees - Get Pending fees by userId , accepts form data
POST  http://localhost:8080/api/v1/fee/payments/collectfees - Collect fees uses form data
GET http://localhost:8080/api/v1/receipts/ORD-XYZSPRINGBOOT - Get receipt details by orderId
GET http://localhost:8080/api/v1/users/xyz/fee-details - Get Payments for the user by userId PathVariable
