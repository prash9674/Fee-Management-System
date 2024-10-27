Get all catalog items

GET http://localhost:8080/api/v1/catalog



Register Student

POST http://localhost:8080/api/v1/users/register
{
    "name" :"Prashant Kumar",
    "email" : "abc@gmail.com",
    "contactNo" : "9768563451",
    "courseName" : "Java8"
}


{
    "userId": "abc",
    "name": "Prashant Kumar",
    "email": "abc@gmail.com",
    "courseName": "Java8",
    "contactNo": 9768563451
}

Get Student details by StudentId

GET http://localhost:8080/api/v1/users/abc@gmail.com

POST form-data - http://localhost:8080/api/v1/fee/payments/collectfees


GET http://localhost:8080/api/v1/receipts/REC-1730030456451
